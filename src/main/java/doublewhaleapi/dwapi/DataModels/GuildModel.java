package doublewhaleapi.dwapi.DataModels;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;

import com.google.common.collect.Lists;

/**
 * GuildModel class to save guilds' data to
 *
 * @author WhaleOpop, BlackWarlow
 */
public class GuildModel extends ArrayList {
    /**
     * Enum of all guild roles descending in permissions:<br>
     * - Creator is able to op, deop players, delete guild + Operator
     * permissions.<br>
     * - Operator is able to add requested, exile players with lower level
     * permissions, modify guild + Member permissions.<br>
     * - Member is able to view guild info + Requested permissions.<br>
     * - Requested is able to list all players in a guild, leave guild.<br>
     * <br>
     * - Not in guild players are able to create new guilds and list all guilds.<br>
     * - Everyone is able to use help command.
     */
    public enum RoleGuild {
        Creator, Operator, Member, Requested
    }


    @SerializedName("guildName")
    @Expose
    private String guildName;
    @SerializedName("guildPrefix")
    @Expose
    private String guildPrefix;
    @SerializedName("guildColor")
    @Expose
    private String guildColor;
    @SerializedName("creatorName")
    @Expose
    private String creatorName;
    @SerializedName("players")
    @Expose
    private List<PlayerModel> players = new ArrayList<PlayerModel>();

    /**
     * Color names supported by Mojang team command.
     */
    @SerializedName("supportedColorNames")
    @Expose
    private final List<String> supportedColorNames = Lists.newArrayList("aqua", "black", "blue", "dark_aqua",
            "dark_blue", "dark_gray", "dark_green", "dark_purple", "dark_red", "gold", "gray", "green", "light_purple",
            "red", "white", "yellow");

    /**
     * Simple constructor.
     *
     * @param nameGuild   String guild name
     * @param prefixGuild String guild prefix
     * @param colorGuild  String guild color
     */

    public GuildModel(String creatorName, String nameGuild, String prefixGuild, String colorGuild) {
        this.creatorName = creatorName;

        // Create new Minecraft:team
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        Server server = Bukkit.getServer();
        server.dispatchCommand(console, "team add " + creatorName + " {\"text\":\"" + nameGuild + "\"}");

        // Add creator
        players.add(new PlayerModel(creatorName, true, RoleGuild.Creator));
        server.dispatchCommand(console, "team join " + creatorName + " " + creatorName);

        // Modify team and guild settings
		setGuildColor(colorGuild);
        setGuildName(nameGuild);
        setGuildPrefix(prefixGuild);

    }

    /**
     * Adds a player to the players ArrayList if he was not added.
     *
     * @param playerName Player name
     * @param status     Boolean true if player is active, false otherwise (null =
     *                   false)
     * @param role       A RoleGuild enum value
     * @return true if user had been added false otherwise
     */
    @Nonnull
    public Boolean addPlayer(String playerName, @Nullable Boolean status, RoleGuild role) {
        if (!hasPlayer(playerName)) {
            // Check players array - there can only be one Creator player
            if (role == RoleGuild.Creator) {
                for (PlayerModel pm : players) {
                    if (pm.getRole() == RoleGuild.Creator)
                        return false;
                }
            }
            players.add(new PlayerModel(playerName, status, role));
            return true;
        }
        return false;
    }

    /**
     * guildColor getter.
     *
     * @return guildColor String guild color
     */
    public String getGuildColor() {
        return guildColor;
    }

    /**
     * guildName getter.
     *
     * @return guildName String guild name
     */
    public String getGuildName() {
        return guildName;
    }

    /**
     * guildPrefix getter.
     *
     * @return guildPrefix String guild prefix
     */
    public String getGuildPrefix() {
        return guildPrefix;
    }

    /**
     * creatorName getter.
     *
     * @return Guild creator name
     */
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * Searches for player PlayerModel in players ArrayList by String playerName.
     *
     * @param playerName Player name
     * @return PlayerModel object or null
     */
    @Nullable
    public PlayerModel getPlayerByName(String playerName) {
        PlayerModel playerFound = null;
        for (PlayerModel player : players) {
            if (player.getName() == playerName) {
                playerFound = player;
                break;
            }
        }
        return playerFound;
    }

    /**
     * Searches for player PlayerModel in players ArrayList by RoleGuild role.
     *
     * @param playerRole Player RoleGuild role
     * @return PlayerModel object or null
     */
    public PlayerModel getPlayerByRole(RoleGuild playerRole) {
        PlayerModel playerFound = null;
        for (PlayerModel player : players) {
            if (player.getRole() == playerRole) {
                playerFound = player;
                break;
            }
        }
        return playerFound;
    }

    /**
     * Searches for player in a guild by String playerName.
     *
     * @param playerName String player name
     * @return true if player is found in a guild, false otherwise
     */
    public Boolean hasPlayer(String playerName) {
        return getPlayerByName(playerName) != null;
    }

    /**
     * Removes player from players ArrayList.
     *
     * @param playerName Player to remove name
     * @return true if player was removed, false otherwise
     */
    public Boolean removePlayer(String playerName) {
        for (PlayerModel player : players) {
            if (player.getName() == playerName) {
                players.remove(player);
                return true;
            }
        }
        return false;
    }

    /**
     * Opps player according to their role.
     *
     * @param playerName Player to op name
     * @return true if operation is successful, else false
     */
    public Boolean opPlayer(String playerName) {
        PlayerModel player = getPlayerByName(playerName);
        if (player == null)
            return false;

        if (player.getRole() == RoleGuild.Member) {
            player.setRole(RoleGuild.Operator);
            return true;
        }
        return false;
    }

    /**
     * Deopps player according to their role.
     *
     * @param playerName Player to deop name
     * @return true is operation is successful, else false
     */
    public Boolean deopPlayer(String playerName) {
        PlayerModel player = getPlayerByName(playerName);
        if (player == null)
            return false;

        if (player.getRole() == RoleGuild.Operator) {
            player.setRole(RoleGuild.Member);
            return true;
        }
        return false;
    }

    /**
     * guildColor setter with regex pattern for hex color and check with
     * supportedColorNames.
     *
     * @param guildColor String guild color (if null will not be set)
     * @return true if color was set, false otherwise
     */
    public Boolean setGuildColor(String guildColor) {
        if (guildColor == null)
            return false;

        if (guildColor.startsWith("#") && (guildColor.length() == 7)) {
            if (Pattern.matches("#[[0-9]a-f]{6}", guildColor)) {
                this.guildColor = guildColor;
                return true;
            }

        } else if (supportedColorNames.contains(guildColor)) {
            this.guildColor = guildColor;
            return true;
        }
        return false;
    }

    /**
     * guildName setter with regex symbol escape.
     *
     * @param guildName String guild name
     * @return new String that guildName was set to after escaping
     */
    public String setGuildName(String guildName) {
        this.guildName = guildName.replaceAll("[-+=*&|\\\\/@{}.^:,<>\\[\\]!?\\'\\\"]", "");
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "team modify " + creatorName
                + " displayName [{\"text\" : \"" + guildName + ",\"color\":\"" + guildColor + "\"}]");
        return this.guildName;
    }

    /**
     * guildPrefix setter with regex symbol escape.
     *
     * @param guildPrefix String guild prefix
     * @return new String guildPrefix was set to after escaping
     */
    public String setGuildPrefix(String guildPrefix) {
        this.guildPrefix = guildPrefix.replaceAll("[-+=*&|\\\\/@{}.^:,<>\\[\\]!?\\'\\\"]", "");
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "team modify " + creatorName
                + " prefix [{\"text\":\"[" + guildPrefix + "] \",\"color\":\"" + guildColor + "\"}]");
        return this.guildPrefix;
    }
    public GuildModel withGuildName(String guildName) {
        this.guildName = guildName;
        return this;
    }

    public GuildModel withGuildPrefix(String guildPrefix) {
        this.guildPrefix = guildPrefix;
        return this;
    }

    public GuildModel withGuildColor(String guildColor) {
        this.guildColor = guildColor;
        return this;
    }

    public GuildModel withCreatorName(String creatorName) {
        this.creatorName = creatorName;
        return this;
    }

    public GuildModel withPlayers(List<PlayerModel> players) {
        this.players = players;
        return this;
    }
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        System.out.println("Our writeObject");
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        System.out.println("Our readObject");
    }

    @Override
    public String toString() {
        return "GuildModel{" +
                "guildName='" + guildName + '\'' +
                ", guildPrefix='" + guildPrefix + '\'' +
                ", guildColor='" + guildColor + '\'' +
                ", creatorName='" + creatorName + '\'' +
                ", players=" + players +
                ", supportedColorNames=" + supportedColorNames +
                '}';
    }



}
