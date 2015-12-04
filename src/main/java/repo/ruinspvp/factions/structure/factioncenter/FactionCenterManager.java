package repo.ruinspvp.factions.structure.factioncenter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.Ruin;
import repo.ruinspvp.factions.structure.database.Database;
import repo.ruinspvp.factions.structure.faction.FactionManager;
import repo.ruinspvp.factions.structure.factioncenter.calls.FCenter;
import repo.ruinspvp.factions.structure.inventory.MenuManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FactionCenterManager extends Database implements Listener {

    public Connection connection;
    public JavaPlugin plugin;
    public FactionManager factionManager;
    public Ruin ruin;
    public MenuManager menuManager;

    public FCenter fCenter;

    public FactionCenterManager(JavaPlugin plugin, FactionManager factionManager, Ruin ruin, MenuManager menuManager) {
        super("root", "ThePyxel", "", "3306", "localhost");
        connection = openConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `factioncenter` (`name` varchar(36) NOT NULL, `date` varchar(32) NOT NULL, `ruin` varchar(32) NOT NULL)");
            ps.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not check if table exists, restarting server.");
            e.printStackTrace();
            Bukkit.getServer().shutdown();
        }
        this.plugin = plugin;
        this.ruin = ruin;
        this.factionManager = factionManager;
        this.menuManager = menuManager;

        this.fCenter = new FCenter(this);

        menuManager.addMenu("Wise One", new FactionCenterMenu(this));

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
