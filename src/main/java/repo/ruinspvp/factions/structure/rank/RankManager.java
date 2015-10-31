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
import repo.ruinspvp.factions.Ruin;
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
    String[] D1 = {"ruinspvp.d1", "ruinspvp.repair2"};
    String[] D2 = {"ruinspvp.d2", "ruinspvp.repair2", "ruinspvp.smelt2"};
    String[] D3 = {"ruinspvp.d3", "ruinspvp.repair2", "ruinspvp.smelt2", "ruinspvp.smelt3"};
    String[] D4 = {"ruinspvp.d4", "ruinspvp.repair2", "ruinspvp.smelt2", "ruinspvp.smelt3"};
    String[] D5 = {"ruinspvp.d5", "ruinspvp.repair2", "ruinspvp.smelt2", "ruinspvp.smelt3"};
    String[] Youtube = {"ruinspvp.youtube", "ruinspvp.repair2", "ruinspvp.smelt2", "ruinspvp.smelt3"};
    /**
     * Staff Ranks
     */
    String[] Assistant = {"ruinspvp.assistant", "ruinspvp.repair2", "ruinspvp.smelt2", "ruinspvp.smelt3"};
    String[] MOD = {"ruinspvp.mod", "ruinspvp.repair2", "ruinspvp.smelt2", "ruinspvp.smelt3"};
    String[] Admin = {"ruinspvp.admin", "ruinspvp.repair2", "ruinspvp.smelt2", "ruinspvp.smelt3"};
    String[] Leader = {"ruinspvp.leader", "*.*"};

    public Ruin ruin;

    public RankManager(JavaPlugin plugin, FactionManager factionManager, Ruin ruin) {
        super("root", "ThePyxel", "", "3306", "localhost");
        connection = openConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `ranks` (`uuid` varchar(36) NOT NULL, `name` varchar(32) NOT NULL, `date` varchar(32) NOT NULL, `rank` varchar(16) NOT NULL, `ruin` varchar(32) NOT NULL)");
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

        this.ruin = ruin;

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
            if (factionManager.fPlayer.hasFaction(player.getUniqueId()) == Result.TRUE) {
                player.setDisplayName(ChatColor.YELLOW + factionManager.fPlayer.getFaction(player.getUniqueId())
                        + ChatColor.RED + "[" + factionManager.fPlayer.getFRank(player.getUniqueId()).getAbv() + "]" + player.getName());
            } else {
                player.setDisplayName(player.getName());
            }
        } else {
            if (factionManager.fPlayer.hasFaction(player.getUniqueId()) == Result.TRUE) {
                player.setDisplayName(ChatColor.YELLOW + factionManager.fPlayer.getFaction(player.getUniqueId())
                        + ChatColor.RED + "[" + factionManager.fPlayer.getFRank(player.getUniqueId()).getAbv() + "]"
                        + rank.getTag(getRankwithRuin(rank), true, true) + ChatColor.RESET + " " + player.getName());
            } else {
                player.setDisplayName(rank.getTag(getRankwithRuin(rank), true, true) + ChatColor.RESET + " " + player.getName());
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
        if (factionManager.fPlayer.checkExists(player.getUniqueId()) == Result.TRUE) {
            if (!factionManager.fPlayer.getName(player.getUniqueId()).equalsIgnoreCase(player.getName())) {
                try {
                    factionManager.fPlayer.updatePlayerName(player.getUniqueId(), player.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        PermissionAttachment permissionAttachment = player.addAttachment(plugin);
        playerPermission.put(player.getUniqueId(), permissionAttachment);

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
                Ranks rank = event.getRank();
                String name = event.getPlayer().getName();
                String prefix;
                if (rank.getPermLevel() > Ranks.DEFAULT.getPermLevel()) {
                    prefix = rank.getTag(getRankwithRuin(rank), true, false) + " " + ChatColor.YELLOW + name;
                } else {
                    prefix = ChatColor.YELLOW + name;
                }
                event.getPlayer().setDisplayName(prefix);
                PermissionAttachment permissionAttachment = playerPermission.get(event.getPlayer().getUniqueId());

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
        } catch (Exception ignore) {
        }
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
            rank = ranks.getTag(getRankwithRuin(ranks), true, true) + " " + ChatColor.DARK_AQUA + player.getName();
        }
        if (factionManager.fPlayer.hasFaction(player.getUniqueId()) == Result.TRUE) {
            faction = ChatColor.RED + "[" + factionManager.fPlayer.getFRank(player.getUniqueId()).getAbv() + "] " + ChatColor.YELLOW + factionManager.fPlayer.getFaction(player.getUniqueId()) + " ";
        } else {
            faction = "";
        }
        e.setFormat(faction + rank + ChatColor.GRAY + ": " + ChatColor.WHITE + e.getMessage());
    }

    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent event) {
        if (Bukkit.getServer().hasWhitelist()) {
            if (fRank.getRank(event.getUniqueId()).getPermLevel() >= Ranks.MOD.getPermLevel()) {
                event.allow();
            } else {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.RED + "You don't have the sufficient permissions to join at this time.");
            }
        } else {
            event.allow();
        }
    }

    public String getRankwithRuin(Ranks rank) {
        String name = null;
        switch (rank) {
            case LEADER:
                name = rank.LEADER.getName();
                break;
            case ADMIN:
                name = rank.ADMIN.getName();
                break;
            case MOD:
                name = rank.MOD.getName();
                break;
            case ASSISTANT:
                name = rank.ASSISTANT.getName();
                break;
            case YOUTUBE:
                name = rank.YOUTUBE.getName();
                break;
            case DONOR1:
                if (ruin.getName().equalsIgnoreCase(Ruin.HUB.getName())) {
                    name = "Donor";
                } else if (ruin.getName().equalsIgnoreCase(Ruin.AZTEC_MOUNTAIN.getName())) {
                    name = "Native";
                } else if (ruin.getName().equalsIgnoreCase(Ruin.TEMPLARS_CASCADE.getName())) {
                    name = "Squire";
                }
                break;
            case DONOR2:
                if (ruin.getName().equalsIgnoreCase(Ruin.HUB.getName())) {
                    name = "Donor";
                } else if (ruin.getName().equalsIgnoreCase(Ruin.AZTEC_MOUNTAIN.getName())) {
                    name = "Warrior";
                } else if (ruin.getName().equalsIgnoreCase(Ruin.TEMPLARS_CASCADE.getName())) {
                    name = "Sergeant";
                }
                break;
            case DONOR3:
                if (ruin.getName().equalsIgnoreCase(Ruin.HUB.getName())) {
                    name = "Donor";
                } else if (ruin.getName().equalsIgnoreCase(Ruin.AZTEC_MOUNTAIN.getName())) {
                    name = "Grand";
                } else if (ruin.getName().equalsIgnoreCase(Ruin.TEMPLARS_CASCADE.getName())) {
                    name = "Knight";
                }
                break;
            case DONOR4:
                if (ruin.getName().equalsIgnoreCase(Ruin.HUB.getName())) {
                    name = "Donor";
                } else if (ruin.getName().equalsIgnoreCase(Ruin.AZTEC_MOUNTAIN.getName())) {
                    name = "Elder";
                } else if (ruin.getName().equalsIgnoreCase(Ruin.TEMPLARS_CASCADE.getName())) {
                    name = "Lieutenant";
                }
                break;
            case DONOR5:
                if (ruin.getName().equalsIgnoreCase(Ruin.HUB.getName())) {
                    name = "Donor";
                } else if (ruin.getName().equalsIgnoreCase(Ruin.AZTEC_MOUNTAIN.getName())) {
                    name = "Aztec";
                } else if (ruin.getName().equalsIgnoreCase(Ruin.TEMPLARS_CASCADE.getName())) {
                    name = "Crusader";
                }
                break;
            case DEFAULT:
                name = rank.DEFAULT.getName();
                break;
        }
        return name;
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
