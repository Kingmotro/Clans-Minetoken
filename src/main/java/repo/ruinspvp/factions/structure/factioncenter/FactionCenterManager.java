package repo.ruinspvp.factions.structure.factioncenter;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.Ruin;
import repo.ruinspvp.factions.structure.faction.FactionManager;
import repo.ruinspvp.factions.structure.inventory.MenuManager;

public class FactionCenterManager implements Listener {

    public JavaPlugin plugin;
    public FactionManager factionManager;
    public Ruin ruin;
    public MenuManager menuManager;

    public FactionCenterManager(JavaPlugin plugin, FactionManager factionManager, Ruin ruin, MenuManager menuManager) {
        this.plugin = plugin;
        this.ruin = ruin;
        this.factionManager = factionManager;
        this.menuManager = menuManager;

        menuManager.addMenu("Wise One", new FactionCenterMenu(this));

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
