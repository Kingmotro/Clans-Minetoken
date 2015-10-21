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
import repo.ruinspvp.factions.structure.faction.FactionManager;
import repo.ruinspvp.factions.structure.rank.calls.FPlayer;
import repo.ruinspvp.factions.structure.rank.calls.FRank;
import repo.ruinspvp.factions.structure.rank.commands.RankCommand;
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

    public FactionManager factionManager;

    public HashMap<UUID, PermissionAttachment> playerPermission;

    /**
     * Player Ranks
     */
    String[] Default = {"ruinspvp.default"};
    String[] Pioneer = {"ruinspvp.pioneer", "ruinspvp.repair2"};
    String[] Hunter = {"ruinspvp.hunter", "ruinspvp.repair2", "ruinspvp.smelt2"};
    String[] Excavator = {"ruinspvp.excavator", "ruinspvp.repair2", "ruinspvp.smelt2", "ruinspvp.smelt3"};
    String[] Lost = {"ruinspvp.lost", "ruinspvp.repair2", "ruinspvp.smelt2", "ruinspvp.smelt3"};
    String[] Forgotten = {"ruinspvp.forgotten", "ruinspvp.repair2", "ruinspvp.smelt2", "ruinspvp.smelt3"};
    String[] Youtube = {"ruinspvp.youtube", "ruinspvp.repair2", "ruinspvp.smelt2", "ruinspvp.smelt3"};
    /**
     * Staff Ranks
     */
    String[] Assistant = {"ruinspvp.assistant", "ruinspvp.repair2", "ruinspvp.smelt2", "ruinspvp.smelt3"};
    String[] MOD = {"ruinspvp.mod", "ruinspvp.repair2", "ruinspvp.smelt2", "ruinspvp.smelt3"};
    String[] Admin = {"ruinspvp.admin", "ruinspvp.repair2", "ruinspvp.smelt2", "ruinspvp.smelt3"};
    String[] Leader = {"ruinspvp.leader", "*.*"};

    public RankManager(JavaPlugin plugin, FactionManager factionManager) {
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
        this.factionManager = factionManager;

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
        Ranks rank = fRank.getRank(player.getUniqueId());
        event.setJoinMessage(Format.main("Join", player.getName()));
        if (fRank.getRank(player.getUniqueId()) == Ranks.DEFAULT) {
            if(factionManager.fPlayer.hasFaction(player.getUniqueId()) == Result.TRUE) {
                player.setDisplayName(ChatColor.YELLOW + factionManager.fPlayer.getFaction(player.getUniqueId())
                        + ChatColor.RED + "[" + factionManager.fPlayer.getFRank(player.getUniqueId()).getAbv() + "]" + player.getName());
            } else {
                player.setDisplayName(player.getName());
            }
        } else {
            if(factionManager.fPlayer.hasFaction(player.getUniqueId()) == Result.TRUE) {
                player.setDisplayName(ChatColor.YELLOW + factionManager.fPlayer.getFaction(player.getUniqueId())
                        + ChatColor.RED + "[" + factionManager.fPlayer.getFRank(player.getUniqueId()).getAbv() + "]"
                        + rank.getTag(true, true) + ChatColor.RESET + " " + player.getName());
            } else {
                player.setDisplayName(rank.getTag(true, true) + ChatColor.RESET + " " + player.getName());
            }
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
                for(String perms : getDefault()) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case PIONEER:
                for(String perms : getPioneer()) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case HUNTER:
                for(String perms : getHunter()) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case EXCAVATOR:
                for(String perms : getExcavator()) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case LOST:
                for(String perms : getLost()) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case FORGOTTEN:
                for(String perms : getForgotten()) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case YOUTUBE:
                for(String perms : getYoutube()) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case ASSISTANT:
                for(String perms : getAssistant()) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case MOD:
                for(String perms : getMOD()) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case ADMIN:
                for(String perms : getAdmin()) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
            case LEADER:
                for(String perms : getLeader()) {
                    permissionAttachment.setPermission(perms, true);
                }
                break;
        }
        playerPermission.put(player.getUniqueId(), permissionAttachment);
    }

    @EventHandler
    public void onRankChange(RankChangeEvent event) {
        try {
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
                PermissionAttachment permissionAttachment = playerPermission.get(event.getPlayer().getUniqueId());

                switch (rank) {
                    case DEFAULT:
                        for(String perms : getDefault()) {
                            permissionAttachment.setPermission(perms, true);
                        }
                        break;
                    case PIONEER:
                        for(String perms : getPioneer()) {
                            permissionAttachment.setPermission(perms, true);
                        }
                        break;
                    case HUNTER:
                        for(String perms : getHunter()) {
                            permissionAttachment.setPermission(perms, true);
                        }
                        break;
                    case EXCAVATOR:
                        for(String perms : getExcavator()) {
                            permissionAttachment.setPermission(perms, true);
                        }
                        break;
                    case LOST:
                        for(String perms : getLost()) {
                            permissionAttachment.setPermission(perms, true);
                        }
                        break;
                    case FORGOTTEN:
                        for(String perms : getForgotten()) {
                            permissionAttachment.setPermission(perms, true);
                        }
                        break;
                    case YOUTUBE:
                        for(String perms : getYoutube()) {
                            permissionAttachment.setPermission(perms, true);
                        }
                        break;
                    case ASSISTANT:
                        for(String perms : getAssistant()) {
                            permissionAttachment.setPermission(perms, true);
                        }
                        break;
                    case MOD:
                        for(String perms : getMOD()) {
                            permissionAttachment.setPermission(perms, true);
                        }
                        break;
                    case ADMIN:
                        for(String perms : getAdmin()) {
                            permissionAttachment.setPermission(perms, true);
                        }
                        break;
                    case LEADER:
                        for(String perms : getLeader()) {
                            permissionAttachment.setPermission(perms, true);
                        }
                        break;
                }
            }
        } catch (Exception ignore) {}
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) throws SQLException {
        Player player = e.getPlayer();
        String rank;
        String faction;
        if (fRank.getRank(player.getUniqueId()) == Ranks.DEFAULT) {
            rank = ChatColor.DARK_AQUA + player.getName();
        } else {
            Ranks ranks = fRank.getRank(player.getUniqueId());
            rank = ranks.getTag(true, true) + " " + ChatColor.DARK_AQUA + player.getName();
        }
        if(factionManager.fPlayer.hasFaction(player.getUniqueId()) == Result.TRUE) {
            faction = ChatColor.RED + "[" + factionManager.fPlayer.getFRank(player.getUniqueId()).getAbv() + "] " + ChatColor.YELLOW + factionManager.fPlayer.getFaction(player.getUniqueId()) + " ";
        } else {
            faction = "";
        }
        e.setFormat(faction + rank + ChatColor.GRAY + ": " + ChatColor.WHITE + e.getMessage());
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

    public static String[] combine(String[] arg1, String[] arg2) {
        String[] result = new String[arg1.length + arg2.length];
        System.arraycopy(arg1, 0, result, 0, arg1.length);
        System.arraycopy(arg2, 0, result, arg1.length, arg2.length);
        return result;
    }

    public String[] getDefault() {
        return Default;
    }

    public String[] getPioneer() {
        return combine(getDefault(), Pioneer);
    }

    public String[] getHunter() {
        return combine(getPioneer(), Hunter);
    }

    public String[] getExcavator() {
        return combine(getHunter(), Excavator);
    }

    public String[] getLost() {
        return combine(getExcavator(), Lost);
    }

    public String[] getForgotten() {
        return combine(getLost(), Forgotten);
    }

    public String[] getYoutube() {
        return combine(getForgotten(), Youtube);
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
