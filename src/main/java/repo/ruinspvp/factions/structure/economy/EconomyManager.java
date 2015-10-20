package repo.ruinspvp.factions.structure.economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.structure.database.Database;
import repo.ruinspvp.factions.structure.economy.calls.FEco;
import repo.ruinspvp.factions.structure.economy.calls.FPlayer;
import repo.ruinspvp.factions.structure.economy.commands.EconomyCommand;
import repo.ruinspvp.factions.structure.rank.enums.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EconomyManager extends Database implements Listener {

    public Connection connection;

    public FPlayer fPlayer;
    public FEco fEco;
    public JavaPlugin plugin;

    public EconomyManager(JavaPlugin plugin) {
        super("root", "ThePyxel", "", "3306", "localhost");
        connection = openConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `eco` (`uuid` varchar(36) NOT NULL, `name` varchar(32) NOT NULL, `date` varchar(32) NOT NULL, `money` int(8) NOT NULL)");
            ps.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not check if table exists, restarting server.");
            e.printStackTrace();
            Bukkit.getServer().shutdown();
        }

        this.fPlayer = new FPlayer(this);
        this.fEco = new FEco(this);
        this.plugin = plugin;

        plugin.getCommand("money").setExecutor(new EconomyCommand(plugin, this));

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (fPlayer.checkExists(player.getUniqueId()) == Result.FALSE) {
            try {
                fPlayer.addPlayer(player.getUniqueId(), player.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!fPlayer.getName(player.getUniqueId()).equalsIgnoreCase(player.getName())) {
            try {
                fPlayer.updatePlayerName(player.getUniqueId(), player.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
