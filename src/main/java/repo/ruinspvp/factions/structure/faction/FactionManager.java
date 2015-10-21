package repo.ruinspvp.factions.structure.faction;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.structure.database.Database;
import repo.ruinspvp.factions.structure.faction.command.FactionCommand;
import repo.ruinspvp.factions.structure.faction.factionCalls.FFaction;
import repo.ruinspvp.factions.structure.faction.playerCalls.FPlayer;
import repo.ruinspvp.factions.structure.rank.RankManager;
import repo.ruinspvp.factions.structure.rank.enums.Ranks;
import repo.ruinspvp.factions.structure.rank.enums.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FactionManager extends Database implements Listener {

    public JavaPlugin plugin;

    public Connection connection;

    public FPlayer fPlayer;
    public FFaction fFaction;

    public FactionManager(JavaPlugin plugin) {
        super("root", "ThePyxel", "", "3306", "localhost");
        this.plugin = plugin;

        connection = openConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `factions` (`name` varchar(36) NOT NULL, `ruin` varchar(32) NOT NULL, `date` varchar(32) NOT NULL, `founder` varchar(16) NOT NULL)");
            ps.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not check if table exists, restarting server.");
            e.printStackTrace();
            Bukkit.getServer().shutdown();
        }
        try {
            PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `fplayer` (`uuid` varchar(36) NOT NULL, `name` varchar(32) NOT NULL, `date` varchar(32) NOT NULL, `frank` varchar(16) NOT NULL, `faction` varchar(16) NOT NULL)");
            ps.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not check if table exists, restarting server.");
            e.printStackTrace();
            Bukkit.getServer().shutdown();
        }

        this.fPlayer = new FPlayer(this);
        this.fFaction = new FFaction(this);

        plugin.getCommand("faction").setExecutor(new FactionCommand(plugin, this));

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
