package doublewhaleapi.dwapi;

import doublewhaleapi.dwapi.DataModels.CoinModel;
import doublewhaleapi.dwapi.DataModels.GuildModel;

import doublewhaleapi.dwapi.Serializers.Serializes;
import doublewhaleapi.dwapi.Vaults.CoinVault;
import doublewhaleapi.dwapi.Vaults.GuildVault;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.google.common.collect.Lists;

import doublewhaleapi.dwapi.Tasks.AutosaveTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * DWAPI core Bukkit Plugin implementation
 * 
 * @author BlackWarlow, WhaleOpop
 *
 */
public final class DWAPI extends JavaPlugin {
	/**
	 * Singleton instance.
	 */
	private static DWAPI instance;

	/**
	 * Plugin logger.
	 */
	private Logger logger = getLogger();

	/**
	 * Autosave model data task.
	 */
	private BukkitTask autosaveTask;

	/**
	 * Public model serializers DWAPI offers as storages.
	 */


	@Nullable
	public ArrayList<CoinModel> coinStorage= new ArrayList<CoinModel>();
	@Nullable
	public ArrayList<GuildModel> guildStorage= new ArrayList<GuildModel>();

	public Serializes coinPath = new Serializes("plugins/DWdatabases/Coins.json"),
					guildPath = new Serializes("plugins/DWdatabases/Coins.json");


	public Serializes getCoinPath() {
		return coinPath;

	}
	public Serializes getGuildPath() {
		return guildPath;

	}

	/**
	 * HashMap of plugin name against boolean enabled/disabled.<br>
	 * "enabled" means DWAPI was able to found .jar file upon onEnable call.
	 */
	public HashMap<String, Boolean> enabledAddons = new HashMap<String, Boolean>();

	/**
	 * ArrayList with supported plugin names.
	 */
	public final List<String> supportedAddons = Lists.newArrayList("CoinMaterial", "Guilded");

	/**
	 * Plugin startup method. Finds addons, registers EventListeners, loads data.
	 */


	 public CoinVault coinVault = new CoinVault(instance, coinStorage);
	 public GuildVault guildVault = new GuildVault(instance,guildStorage);
	@Override
	public void onEnable() {




		logger.info("DW Core Start");

		// Save default config.yml
		saveDefaultConfig();

		// Initialize all addons as not found
		for (String pluginName : supportedAddons)
			enabledAddons.put(pluginName, false);

		// Find addons in local plugins/ directory
		for (String pluginName : supportedAddons) {
			for (final File fileEntry : new File("plugins/").listFiles()) {
				if (!fileEntry.isDirectory()) {
					String fileName = fileEntry.getName().toLowerCase();
					if (fileName.contains(pluginName) && fileName.endsWith(".jar")) {
						enabledAddons.put(pluginName, true);
						logger.info("Found addon: " + pluginName);
					}
				}
			}
		}

		// Singleton implementation
		instance = this;

		// Initialize serializers
		coinPath.deserialize();
		guildPath.deserialize();

		// Setup autosave task to run according to setting in plugin.yml
		Long taskTime = getConfig().getLong("settings.autosaveTimeout");
		autosaveTask = new AutosaveTask().runTaskTimer(instance, taskTime, taskTime);
	}

	/**
	 * Plugin shutdown method. Cancels autosaveTask, saves all .json files before
	 * exiting.
	 */
	@Override
	public void onDisable() {
		autosaveTask.cancel();
		coinPath.serialize(coinStorage);
		guildPath.serialize(guildStorage);


		logger.info("Storage data saved.");
	}

	/**
	 * Singleton implementation.
	 *
	 * @return DWAPI plugin instance
	 */
	@Nonnull
	public static DWAPI getInstance() {
		return instance;
	}

	/**
	 * Tests if DW addon is enabled against enabledAddons list.
	 *
	 * @param pluginName Plugin name
	 * @return true if found, false if disabled or not found
	 */
	@Nonnull
	public Boolean getAddonEnabled(@Nullable String pluginName) {
		return enabledAddons.getOrDefault(pluginName, false);
	}

	/**
	 * Sets enabled value of addon in enabledAddons HashMap.<br>
	 * On any null values does not set enabledAddons.
	 *
	 * @param pluginName Plugin name
	 * @param enabled    Boolean true or false
	 */
	public void setAddonEnabled(String pluginName, Boolean enabled) {
		if ((pluginName == null) || (enabled == null))
			return;
		enabledAddons.put(pluginName, enabled);
	}
}
