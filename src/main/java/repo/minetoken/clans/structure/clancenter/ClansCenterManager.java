package repo.minetoken.clans.structure.clancenter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import repo.minetoken.clans.structure.clan.ClanManager;
import repo.minetoken.clans.structure.inventory.MenuManager;
import repo.minetoken.clans.structure.database.Database;
import repo.minetoken.clans.structure.clancenter.calls.CCenter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClansCenterManager extends Database implements Listener {

    public Connection connection;
    public JavaPlugin plugin;
    public ClanManager clanManager;
    public MenuManager menuManager;

    public CCenter cCenter;

    public ClansCenterManager(JavaPlugin plugin, ClanManager clanManager, MenuManager menuManager) {
        super("root", "ThePyxel", "", "3306", "localhost");
        connection = openConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `clancenter` (`name` varchar(36) NOT NULL, `date` varchar(32) NOT NULL)");
            ps.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not check if table exists, restarting server.");
            e.printStackTrace();
            Bukkit.getServer().shutdown();
        }
        this.plugin = plugin;
        this.clanManager = clanManager;
        this.menuManager = menuManager;

        this.cCenter = new CCenter(this);

        menuManager.addMenu("Wise One", new ClansCenterMenu(this));

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
