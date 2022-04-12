package doublewhaleapi.dwapi;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Lists;

import doublewhaleapi.dwapi.Serializers.CoinSerializer;
import doublewhaleapi.dwapi.Serializers.GuildSerializer;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * DWAPI core Bukkit Plugin implementation
 * 
 * @author BlackWarlow, WhaleOpop
 *
 */
public final class DWAPI extends JavaPlugin {
	private static DWAPI instance;
	private Logger logger = Bukkit.getLogger();

	private CoinSerializer coinStorage = new CoinSerializer("Coins.json", null);
	private GuildSerializer guildStorage = new GuildSerializer("Guilds.json", null);

	/**
	 * HashMap of plugin name against boolean enabled/disabled<br>
	 * enabled means DWAPI was able to found .jar file upon onEnable call
	 */
	public HashMap<String, Boolean> enabledAddons = new HashMap<String, Boolean>();

	/**
	 * ArrayList with supported plugin names
	 */
	public final List<String> supportedAddons = Lists.newArrayList("CoinMaterial", "Guilded");

	/**
	 * Plugin startup method - finds addons, registers EventListeners, loads data
	 */
	@Override
	public void onEnable() {
		logger.info("DW Core Start");

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
		coinStorage.loadData();
		guildStorage.loadData();
	}

	/**
	 * Plugin shutdown method - saves all .json files before exiting
	 */
	@Override
	public void onDisable() {
		coinStorage.saveData();
		guildStorage.saveData();

		logger.info("Storage data saved.");
	}

	/**
	 * Simple singleton implementation
	 * 
	 * @return DWAPI plugin instance
	 */
	public static DWAPI getInstance() {
		return instance;
	}

	/**
	 * Tests if DW addon is enabled against enabledAddons list
	 * 
	 * @param pluginName
	 * @return true if found, false if disabled or not found
	 */
	public Boolean getAddonEnabled(String pluginName) {
		return enabledAddons.getOrDefault(pluginName, false);
	}

	/**
	 * Sets enabled value of addon in enabledAddons HashMap
	 * 
	 * @param pluginName
	 * @param enabled    Boolean true or false
	 */
	public void setAddonEnabled(String pluginName, Boolean enabled) {
		enabledAddons.put(pluginName, enabled);
	}
}
