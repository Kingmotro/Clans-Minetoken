package repo.ruinspvp.factions.structure.rank;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.structure.database.Database;
import repo.ruinspvp.factions.structure.rank.calls.FPlayer;
import repo.ruinspvp.factions.structure.rank.calls.FRank;
import repo.ruinspvp.factions.structure.rank.enums.Ranks;
import repo.ruinspvp.factions.structure.rank.enums.Result;
import repo.ruinspvp.factions.structure.rank.events.RankChangeEvent;
import repo.ruinspvp.factions.utilities.Format;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class RankManager extends Database implements Listener {

    public Connection connection;

    public FPlayer fPlayer;
    public FRank fRank;
    public JavaPlugin plugin;

    public HashMap<UUID, PermissionAttachment> playerPermission;

    /**
     * Player Ranks
     */
    String[] Default = {"ruinspvp.default"};
    String[] Pioneer = {"ruinspvp.default", "ruinspvp.pioneer"};
    String[] Hunter = {"ruinspvp.default", "ruinspvp.pioneer", "ruinspvp.hunter"};
    String[] Excavator = {"ruinspvp.default", "ruinspvp.pioneer", "ruinspvp.hunter", "ruinspvp.excavator"};
    String[] Lost = {"ruinspvp.default", "ruinspvp.pioneer", "ruinspvp.hunter", "ruinspvp.excavator", "ruinspvp.lost"};
    String[] Forgotten = {"ruinspvp.default", "ruinspvp.pioneer", "ruinspvp.hunter", "ruinspvp.excavator", "ruinspvp.lost", "ruinspvp.forgotten"};
    String[] Youtube = {"ruinspvp.default", "ruinspvp.pioneer", "ruinspvp.hunter", "ruinspvp.excavator", "ruinspvp.lost", "ruinspvp.forgotten", "ruinspvp.youtube"};
    /**
     * Staff Ranks
     */
    String[] Assistant = {"ruinspvp.default", "ruinspvp.pioneer", "ruinspvp.hunter", "ruinspvp.excavator", "ruinspvp.lost", "ruinspvp.forgotten", "ruinspvp.youtube", "ruinspvp.assistant"};
    String[] MOD = {"ruinspvp.default", "ruinspvp.pioneer", "ruinspvp.hunter", "ruinspvp.excavator", "ruinspvp.lost", "ruinspvp.forgotten", "ruinspvp.youtube", "ruinspvp.assistant", "ruinspvp.mod"};
    String[] Admin = {"ruinspvp.default", "ruinspvp.pioneer", "ruinspvp.hunter", "ruinspvp.excavator", "ruinspvp.lost", "ruinspvp.forgotten", "ruinspvp.youtube", "ruinspvp.assistant", "ruinspvp.mod", "ruinspvp.admin"};
    String[] Leader = {"ruinspvp.default", "ruinspvp.pioneer", "ruinspvp.hunter", "ruinspvp.excavator", "ruinspvp.lost", "ruinspvp.forgotten", "ruinspvp.youtube", "ruinspvp.assistant", "ruinspvp.mod", "ruinspvp.admin", "ruinspvp.leader", "*.*"};

    public RankManager(JavaPlugin plugin) {
        super("root", "ThePyxel", "", "3306", "localhost");
        connection = openConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `ranks` (`uuid` varchar(36) NOT NULL, `name` varchar(32) NOT NULL, `date` varchar(32) NOT NULL, `rank` varchar(16) NOT NULL)");
            ps.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not check if table exists, restarting server.");
            e.printStackTrace();
            Bukkit.getServer().shutdown();
        }

        this.fPlayer = new FPlayer(this);
        this.fRank = new FRank(this);
        this.plugin = plugin;

        this.playerPermission = new HashMap<>();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(Format.main("Quit", player.getName()));
        player.removeAttachment(playerPermission.get(player.getUniqueId()));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) throws SQLException {
        Player player = event.getPlayer();
        Ranks rank = fRank.getRank(player.getUniqueId());
        event.setJoinMessage(Format.main("Join", player.getName()));
        if (fRank.getRank(player.getUniqueId()) == Ranks.DEFAULT) {
            player.setDisplayName(player.getName());
        } else {
            player.setDisplayName(rank.getTag(true, true) + ChatColor.RESET + " " + player.getName());
        }
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
        PermissionAttachment permissionAttachment = player.addAttachment(plugin);

        switch (rank) {
            case DEFAULT:
                for(String perms : Default) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case PIONEER:
                for(String perms : Pioneer) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case HUNTER:
                for(String perms : Hunter) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case EXCAVATOR:
                for(String perms : Excavator) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case LOST:
                for(String perms : Lost) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case FORGOTTEN:
                for(String perms : Forgotten) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case YOUTUBE:
                for(String perms : Youtube) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case ASSISTANT:
                for(String perms : Assistant) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case MOD:
                for(String perms : MOD) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case ADMIN:
                for(String perms : Admin) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case LEADER:
                for(String perms : Leader) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
        }
        playerPermission.put(player.getUniqueId(), permissionAttachment);
    }

    @EventHandler
    public void onRankChange(RankChangeEvent event) {
        if (event.getPlayer().isOnline() == true) {
            Ranks rank = event.getRank();
            String name = event.getPlayer().getName();
            String prefix;
            if (rank.getPermLevel() > Ranks.DEFAULT.getPermLevel()) {
                prefix = rank.getTag(true, false) + " " + ChatColor.YELLOW + name;
            } else {
                prefix = ChatColor.YELLOW + name;
            }
            event.getPlayer().setDisplayName(prefix);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) throws SQLException {
        Player player = e.getPlayer();
        String rank;
        if (fRank.getRank(player.getUniqueId()) == Ranks.DEFAULT) {
            rank = ChatColor.DARK_AQUA + player.getName();
        } else {
            Ranks ranks = fRank.getRank(player.getUniqueId());
            rank = ranks.getTag(true, true) + " " + ChatColor.DARK_AQUA + player.getName();
        }
        e.setFormat(rank + ChatColor.GRAY + ": " + ChatColor.WHITE + e.getMessage());
    }

    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent event) {
        if (Bukkit.getServer().hasWhitelist()) {
            if (Bukkit.getPlayer(event.getUniqueId()).hasPermission("ruinspvp.mod")) {
                event.allow();
            } else {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.RED + "You don't have the sufficient permissions to join at this time.");
            }
        } else {
            event.allow();
        }
    }
}
