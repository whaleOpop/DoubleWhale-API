package doublewhaleapi.dwapi.DataModels;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

/**
 * GuildModel class to save guilds' data to
 * 
 * @author WhaleOpop, BlackWarlow
 *
 */
public class GuildModel {
	/**
	 * Enum of all guild roles descending in permissions:<br>
	 * - Creator is able to op, deop players, delete guild + Operator
	 * permissions<br>
	 * - Operator is able to add requested, exile players with lower level
	 * permissions, modify guild + Member permissions<br>
	 * - Member is able to view guild info + Requested permissions<br>
	 * - Requested is able to list all players in a guild, leave guild<br>
	 * <br>
	 * - Not in guild players are able to create new guilds and list all guilds<br>
	 * - Everyone is able to use help command
	 */
	public enum RoleGuild {
		Creator, Opperator, Member, Requested
	};

	private String guildName;
	private String guildPrefix;
	private String guildColor;
	private List<PlayerModel> players = new ArrayList<PlayerModel>();

	/**
	 * Color names supported by Mojang team command
	 */
	private final List<String> supportedColorNames = Lists.newArrayList("aqua", "black", "blue", "dark_aqua",
			"dark_blue", "dark_gray", "dark_green", "dark_purple", "dark_red", "gold", "gray", "green", "light_purple",
			"red", "white", "yellow");

	/**
	 * Simple constructor
	 * 
	 * @param nameGuild
	 * @param prefixGuild
	 * @param colorGuild
	 * @param playersList may be null, if so players ArrayList will be empty
	 */
	public GuildModel(String nameGuild, String prefixGuild, String colorGuild,
			@Nullable List<PlayerModel> playersList) {
		// Simple constructor
		setGuildName(nameGuild);
		setGuildPrefix(prefixGuild);
		setGuildColor(colorGuild);

		if (playersList == null)
			playersList = new ArrayList<PlayerModel>();
		players = playersList;
	}

	/**
	 * Adds a player to the players ArrayList if he was not added
	 * 
	 * @param playerName
	 * @param status     Boolean true if player is active, false otherwise
	 * @param role       A RoleGuild enum value
	 * @return true if user had been added false otherwise
	 */
	public Boolean addPlayer(String playerName, Boolean status, RoleGuild role) {
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
	 * Searches for player PlayerModel in players ArrayList by String playerName
	 * 
	 * @param playerName
	 * @return PlayerModel object or null
	 */
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
	 * Searches for player PlayerModel in players ArrayList by RoleGuild role
	 * 
	 * @param playerRole
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
	 * Removes player from players ArrayList
	 * 
	 * @param playerName
	 * @return true if player was removed, false otherwise
	 */
	public Boolean removePlayer(PlayerModel playerToRemove) {
		for (PlayerModel player : players) {
			if (player == playerToRemove) {
				players.remove(player);
				return true;
			}
		}
		return false;
	}

	/**
	 * Searches for player in a guild by String playerName
	 * 
	 * @param playerName
	 * @return true if player is found in a guild, false otherwise
	 */
	public Boolean hasPlayer(String playerName) {
		boolean playerFound = false;
		for (PlayerModel player : players) {
			if (player.getName() == playerName) {
				playerFound = true;
				break;
			}
		}
		return playerFound;
	}

	/**
	 * guildName getter
	 * 
	 * @return guildName
	 */
	public String getGuildName() {
		return guildName;
	}

	/**
	 * guildName setter with regex symbol escape
	 * 
	 * @param guildName
	 * @return new String that guildName was set to after escaping
	 */
	public String setGuildName(String guildName) {
		this.guildName = guildName.replaceAll("[-+=*&|\\\\/@{}.^:,<>\\[\\]!?\\'\\\"]", "");
		return this.guildName;
	}

	/**
	 * guildPrefix getter
	 * 
	 * @return guildPrefix
	 */
	public String getGuildPrefix() {
		return guildPrefix;
	}

	/**
	 * guildPrefix setter with regex symbol escape
	 * 
	 * @param guildPrefix
	 * @return new String guildPrefix was set to after escaping
	 */
	public String setGuildPrefix(String guildPrefix) {
		this.guildPrefix = guildPrefix.replaceAll("[-+=*&|\\\\/@{}.^:,<>\\[\\]!?\\'\\\"]", "");
		return this.guildPrefix;
	}

	/**
	 * guildColor getter
	 * 
	 * @return guildColor
	 */
	public String getGuildColor() {
		return guildColor;
	}

	/**
	 * guildColor setter with regex pattern for hex color and check with
	 * supportedColorNames
	 * 
	 * @param guildColor
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
}
