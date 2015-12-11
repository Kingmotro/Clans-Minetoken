package repo.minetoken.clans.structure.rank;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;
import repo.minetoken.clans.structure.rank.calls.CPlayer;
import repo.minetoken.clans.structure.rank.commands.RankCommand;
import repo.minetoken.clans.structure.rank.enums.Ranks;
import repo.minetoken.clans.structure.rank.events.RankChangeEvent;
import repo.minetoken.clans.structure.database.Database;
import repo.minetoken.clans.structure.clan.ClanManager;
import repo.minetoken.clans.structure.rank.calls.CRank;
import repo.minetoken.clans.structure.rank.enums.Result;
import repo.minetoken.clans.utilities.Format;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class RankManager extends Database implements Listener {

    public Connection connection;

    public CPlayer cPlayer;
    public CRank cRank;
    public JavaPlugin plugin;

    public ClanManager clanManager;

    public HashMap<UUID, PermissionAttachment> playerPermission;

    /**
     * Player Ranks
     */
    String[] Default = {"minetoken.default"};
    String[] D1 = {"minetoken.d1"};
    String[] D2 = {"minetoken.d2"};
    String[] D3 = {"minetoken.d3"};
    String[] D4 = {"minetoken.d4"};
    String[] D5 = {"minetoken.d5"};
    String[] Youtube = {"minetoken.youtube"};
    /**
     * Staff Ranks
     */
    String[] Assistant = {"minetoken.assistant"};
    String[] MOD = {"minetoken.mod"};
    String[] Admin = {"minetoken.admin"};
    String[] Leader = {"minetoken.leader", "*.*"};

    public RankManager(JavaPlugin plugin, ClanManager clanManager) {
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

        this.cPlayer = new CPlayer(this);
        this.cRank = new CRank(this);
        this.plugin = plugin;
        this.clanManager = clanManager;

        this.playerPermission = new HashMap<>();

        plugin.getCommand("rank").setExecutor(new RankCommand(plugin, this));

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
        event.setJoinMessage(Format.main("Join", player.getName()));
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
        if (clanManager.cPlayer.checkExists(player.getUniqueId()) == Result.TRUE) {
            if (!clanManager.cPlayer.getName(player.getUniqueId()).equalsIgnoreCase(player.getName())) {
                try {
                    clanManager.cPlayer.updatePlayerName(player.getUniqueId(), player.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        PermissionAttachment permissionAttachment = player.addAttachment(plugin);
        playerPermission.put(player.getUniqueId(), permissionAttachment);

        Ranks rank = cRank.getRank(player.getUniqueId());

        switch (rank) {
            case DEFAULT:
                for (String perms : getDefault()) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case DONOR1:
                for (String perms : getD1()) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case DONOR2:
                for (String perms : getD2()) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case DONOR3:
                for (String perms : getD3()) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case DONOR4:
                for (String perms : getD4()) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case DONOR5:
                for (String perms : getD5()) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case YOUTUBE:
                for (String perms : getYoutube()) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case ASSISTANT:
                for (String perms : getAssistant()) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case MOD:
                for (String perms : getMOD()) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case ADMIN:
                for (String perms : getAdmin()) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case LEADER:
                for (String perms : getLeader()) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
        }
    }

    @EventHandler
    public void onRankChange(RankChangeEvent event) {
        try {
            if (event.getPlayer().isOnline() == true) {
                Ranks ranks = event.getRank();
                String name = event.getPlayer().getName();
                String prefix;
                if (ranks.getPermLevel() > Ranks.DEFAULT.getPermLevel()) {
                    prefix = ranks.getTag(ranks.getName(), true, false) + " " + ChatColor.YELLOW + name;
                } else {
                    prefix = ChatColor.YELLOW + name;
                }
                event.getPlayer().setDisplayName(prefix);
                PermissionAttachment permissionAttachment = playerPermission.get(event.getPlayer().getUniqueId());

                switch (ranks) {
                    case DEFAULT:
                        for (String perms : getDefault()) {
                            permissionAttachment.setPermission(perms, true);
                        }
                        break;
                    case DONOR1:
                        for (String perms : getD1()) {
                            permissionAttachment.setPermission(perms, true);
                        }
                        break;
                    case DONOR2:
                        for (String perms : getD2()) {
                            permissionAttachment.setPermission(perms, true);
                        }
                        break;
                    case DONOR3:
                        for (String perms : getD3()) {
                            permissionAttachment.setPermission(perms, true);
                        }
                        break;
                    case DONOR4:
                        for (String perms : getD4()) {
                            permissionAttachment.setPermission(perms, true);
                        }
                        break;
                    case DONOR5:
                        for (String perms : getD5()) {
                            permissionAttachment.setPermission(perms, true);
                        }
                        break;
                    case YOUTUBE:
                        for (String perms : getYoutube()) {
                            permissionAttachment.setPermission(perms, true);
                        }
                        break;
                    case ASSISTANT:
                        for (String perms : getAssistant()) {
                            permissionAttachment.setPermission(perms, true);
                        }
                        break;
                    case MOD:
                        for (String perms : getMOD()) {
                            permissionAttachment.setPermission(perms, true);
                        }
                        break;
                    case ADMIN:
                        for (String perms : getAdmin()) {
                            permissionAttachment.setPermission(perms, true);
                        }
                        break;
                    case LEADER:
                        for (String perms : getLeader()) {
                            permissionAttachment.setPermission(perms, true);
                        }
                        break;
                }
            }
        } catch (Exception ignore) {
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        String rank;
        String faction;
        if (cRank.getRank(player.getUniqueId()) == Ranks.DEFAULT) {
            rank = ChatColor.DARK_AQUA + player.getName();
        } else {
            Ranks ranks = cRank.getRank(player.getUniqueId());
            rank = ranks.getTag(ranks.getName(), true, true) + " " + ChatColor.DARK_AQUA + player.getName();
        }
        if (clanManager.cPlayer.hasClan(player.getUniqueId()) == Result.TRUE) {
            faction = ChatColor.RED + "[" + clanManager.cPlayer.getCRank(player.getUniqueId()).getAbv() + "] " + ChatColor.YELLOW + clanManager.cPlayer.getClan(player.getUniqueId()) + " ";
        } else {
            faction = "";
        }
        e.setFormat(faction + rank + ChatColor.GRAY + ": " + ChatColor.WHITE + "%2$s");
    }

    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent event) {
        if (Bukkit.getServer().hasWhitelist()) {
            if (cRank.getRank(event.getUniqueId()).getPermLevel() >= Ranks.MOD.getPermLevel()) {
                event.allow();
            } else {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.RED + "You don't have the sufficient permissions to join at this time.");
            }
        } else {
            event.allow();
        }
    }

    public static String[] combine(String[] arg1, String[] arg2) {
        String[] result = new String[arg1.length + arg2.length];
        System.arraycopy(arg1, 0, result, 0, arg1.length);
        System.arraycopy(arg2, 0, result, arg1.length, arg2.length);
        return result;
    }

    public String[] getDefault() {
        return Default;
    }

    public String[] getD1() {
        return combine(getDefault(), D1);
    }

    public String[] getD2() {
        return combine(getD1(), D2);
    }

    public String[] getD3() {
        return combine(getD2(), D3);
    }

    public String[] getD4() {
        return combine(getD3(), D4);
    }

    public String[] getD5() {
        return combine(getD4(), D5);
    }

    public String[] getYoutube() {
        return combine(getD5(), Youtube);
    }

    public String[] getAssistant() {
        return combine(getYoutube(), Assistant);
    }

    public String[] getMOD() {
        return combine(getAssistant(), MOD);
    }

    public String[] getAdmin() {
        return combine(getMOD(), Admin);
    }

    public String[] getLeader() {
        return combine(getAdmin(), Leader);
    }
}
