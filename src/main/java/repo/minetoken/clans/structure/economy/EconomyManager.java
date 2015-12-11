package repo.minetoken.clans.structure.economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import repo.minetoken.clans.structure.economy.calls.CEco;
import repo.minetoken.clans.structure.economy.calls.CPlayer;
import repo.minetoken.clans.structure.economy.ccommands.CreditCommand;
import repo.minetoken.clans.structure.rank.enums.Result;
import repo.minetoken.clans.structure.database.Database;
import repo.minetoken.clans.structure.economy.pcommands.PointsCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EconomyManager extends Database implements Listener {

    public Connection connection;

    public CPlayer cPlayer;
    public CEco cEco;
    public JavaPlugin plugin;

    public EconomyManager(JavaPlugin plugin) {
        super("root", "ThePyxel", "", "3306", "localhost");
        connection = openConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `eco` (`uuid` varchar(36) NOT NULL, `name` varchar(32) NOT NULL, `date` varchar(32) NOT NULL, `points` int(8) NOT NULL, `credits` int(8) NOT NULL)");
            ps.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not check if table exists, restarting server.");
            e.printStackTrace();
            Bukkit.getServer().shutdown();
        }

        this.cPlayer = new CPlayer(this);
        this.cEco = new CEco(this);
        this.plugin = plugin;

        plugin.getCommand("points").setExecutor(new PointsCommand(plugin, this));
        plugin.getCommand("credits").setExecutor(new CreditCommand(plugin, this));

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (cPlayer.checkExists(player.getUniqueId()) == Result.FALSE) {
            try {
                cPlayer.addPlayer(player.getUniqueId(), player.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!cPlayer.getName(player.getUniqueId()).equalsIgnoreCase(player.getName())) {
            try {
                cPlayer.updatePlayerName(player.getUniqueId(), player.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
