package repo.ruinspvp.factions.structure.kits;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class KitManager implements Listener {

    public JavaPlugin plugin;

    public KitManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }
}
