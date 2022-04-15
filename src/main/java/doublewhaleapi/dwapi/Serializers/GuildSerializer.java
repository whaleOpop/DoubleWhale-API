package doublewhaleapi.dwapi.Serializers;

import doublewhaleapi.dwapi.DataModels.GuildModel;
import doublewhaleapi.dwapi.DataModels.GuildModel.RoleGuild;
import doublewhaleapi.dwapi.DataModels.PlayerModel;

import java.util.ArrayList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;

/**
 * GuildSerializer class to work with guilds and underlying Mojang
 * Minecraft:team commands.
 * 
 * @author WhaleOpop, BlackWarlow
 */
public class GuildSerializer extends AbstractSerializer<GuildModel> {
	/**
	 * Simple constructor with super method.
	 * 
	 * @param jsonFileName .json filename to save data to
	 * @param initialData  Initial set of data
	 */
	public GuildSerializer(String jsonFileName, @Nullable ArrayList<GuildModel> initialData) {
		super(jsonFileName, initialData);
	}

	/**
	 * Gets guild from data by playerName if it is in a guild.
	 * 
	 * @param playerName Player name
	 * @return GuildModel guild
	 */
	@Nullable
	public GuildModel getGuildByPlayer(String playerName) {
		for (GuildModel guild : data)
			if (guild.hasPlayer(playerName))
				return guild;
		return null;
	}

	/**
	 * Gets guild from data by playerName and lowest guild role needed.
	 * 
	 * @param playerName Player name
	 * @param lowestRole RoleGuild role to test player with
	 * @return true if guild is found, false otherwise
	 */
	@Nullable
	public GuildModel getGuildByPlayerRole(String playerName, RoleGuild lowestRole) {
		for (GuildModel guild : data) {
			PlayerModel player = guild.getPlayerByName(playerName);
			if (player != null)
				if (player.testRole(lowestRole))
					return guild;
		}
		return null;
	}

	/**
	 * Searchers data for guilds with creatorName.
	 * 
	 * @param creatorName Guild creator's name
	 * @return GuildModel guild or null if not found
	 */
	public GuildModel getGuildByCreatorName(String creatorName) {
		for (GuildModel guild : data) {
			if (guild.getCreatorName() == creatorName)
				return guild;
		}
		return null;
	}

	/**
	 * Creates new GuildModel object and adds it to data.<br>
	 * Also creates Minecraft:team to work with.
	 * 
	 * @param creatorName Player name
	 * @param prefixGuild Guild prefix string
	 * @param colorGuild  Guild color string
	 * @return newly created GuildModel
	 */
	@Nonnull
	public GuildModel createGuild(String creatorName, String nameGuild, String prefixGuild, String colorGuild) {
		GuildModel guild = new GuildModel(creatorName, nameGuild, prefixGuild, colorGuild);

		// Add creator, add to data
		guild.addPlayer(creatorName, true, RoleGuild.Creator);
		data.add(guild);

		return guild;
	}

	/**
	 * Finds guild by creatorName and deletes it.<br>
	 * Deletes Minecraft:team with it.
	 * 
	 * @param creatorName Player name
	 * @return true if guild was removed, false otherwise
	 */
	@Nonnull
	public Boolean removeGuild(String creatorName) {
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "team remove " + creatorName);
		return data.remove(getGuildByCreatorName(creatorName));
	}
}
