package repo.ruinspvp.factions.structure.factioncenter;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.Ruin;
import repo.ruinspvp.factions.structure.faction.FactionManager;

public class FactionCenterManager implements Listener {

    public JavaPlugin plugin;

    public FactionManager factionManager;

    public Ruin ruin;

    public FactionCenterManager(JavaPlugin plugin, FactionManager factionManager, Ruin ruin) {
        this.plugin = plugin;
        this.ruin = ruin;
        this.factionManager = factionManager;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
