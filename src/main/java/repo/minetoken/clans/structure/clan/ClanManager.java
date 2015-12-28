package repo.minetoken.clans.structure.clan;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import repo.minetoken.clans.structure.clan.clanCalls.CClans;
import repo.minetoken.clans.structure.clan.command.ClanCommand;
import repo.minetoken.clans.structure.clan.events.ClanCreateEvent;
import repo.minetoken.clans.structure.clan.events.ClanDeleteEvent;
import repo.minetoken.clans.structure.clan.playerCalls.CPlayer;
import repo.minetoken.clans.structure.database.Database;
import repo.minetoken.clans.utilities.Format;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class ClanManager extends Database implements Listener {

    public JavaPlugin plugin;

    public Connection connection;

    public CPlayer cPlayer;
    public repo.minetoken.clans.structure.clan.clanCalls.CClans CClans;

    public HashMap<UUID, ClanInvite> invitedPlayer;

    public ClanManager(JavaPlugin plugin) {
        super("root", "ThePyxel", "", "3306", "localhost");
        this.plugin = plugin;

        connection = openConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `clans` (`name` varchar(36) NOT NULL, `date` varchar(32) NOT NULL, `founder` varchar(16) NOT NULL)");
            ps.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not check if table exists, restarting server.");
            e.printStackTrace();
            Bukkit.getServer().shutdown();
        }
        try {
            PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `cplayer` (`uuid` varchar(36) NOT NULL, `name` varchar(32) NOT NULL, `date` varchar(32) NOT NULL, `crank` varchar(16) NOT NULL, `clan` varchar(16) NOT NULL)");
            ps.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not check if table exists, restarting server.");
            e.printStackTrace();
            Bukkit.getServer().shutdown();
        }

        this.cPlayer = new CPlayer(this);
        this.CClans = new CClans(this);

        invitedPlayer = new HashMap<>();

        plugin.getCommand("clan").setExecutor(new ClanCommand(plugin, this));

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClanDelete(ClanDeleteEvent event) {
        String faction = event.getFaction();
        for (String uuid : cPlayer.getPlayersInAClan(faction)) {
            try {
                Player player = Bukkit.getPlayer(UUID.fromString(uuid));
                Bukkit.broadcastMessage(Format.main("Clans", ChatColor.AQUA + player.getName() + ChatColor.YELLOW + " has left the Clan " + ChatColor.LIGHT_PURPLE + faction + ChatColor.YELLOW + "!"));
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
        }
        cPlayer.kickAllPlayerFromFaction(faction);
    }

    @EventHandler
    public void onClanCreate(ClanCreateEvent event) {
        String faction = event.getFaction();
        Player creator = event.getPlayer();
        Bukkit.broadcastMessage(Format.main("Clans", ChatColor.AQUA + creator.getName() + ChatColor.YELLOW +" Created the Clan: " + ChatColor.GREEN + faction));
    }
}
