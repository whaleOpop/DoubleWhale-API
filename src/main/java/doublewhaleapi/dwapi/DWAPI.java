package doublewhaleapi.dwapi;

import org.bukkit.plugin.java.JavaPlugin;

import javax.lang.model.SourceVersion;
import java.util.logging.Logger;

public final class DWAPI extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Logger log=null;
        log.info("Connection yep");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
