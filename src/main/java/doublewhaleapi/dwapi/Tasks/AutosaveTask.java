package doublewhaleapi.dwapi.Tasks;

import org.bukkit.scheduler.BukkitRunnable;

import doublewhaleapi.dwapi.DWAPI;

/**
 * Simple autosave storage task.
 * 
 * @author BlackWarlow
 *
 */
public class AutosaveTask extends BukkitRunnable {
	/**
	 * Implements storage saving.
	 */
	@Override
	public void run() {
		DWAPI plugin = DWAPI.getInstance();
		plugin.coinStorage.saveData();
		plugin.guildStorage.saveData();
	}
}
