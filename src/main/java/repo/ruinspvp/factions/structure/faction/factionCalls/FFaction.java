package repo.ruinspvp.factions.structure.faction.factionCalls;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import repo.ruinspvp.factions.structure.database.DatabaseCall;
import repo.ruinspvp.factions.structure.faction.FactionManager;
import repo.ruinspvp.factions.structure.faction.enums.FactionRanks;
import repo.ruinspvp.factions.structure.faction.events.FactionCreateEvent;
import repo.ruinspvp.factions.structure.faction.events.FactionDeleteEvent;
import repo.ruinspvp.factions.structure.faction.events.FactionRankChangeEvent;
import repo.ruinspvp.factions.structure.factioncenter.events.FactionEnlistEvent;
import repo.ruinspvp.factions.structure.factioncenter.events.FactionUnenlistEvent;
import repo.ruinspvp.factions.structure.rank.enums.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FFaction extends DatabaseCall<FactionManager> {

    public FFaction(FactionManager plugin) {
        super(plugin);
    }

    public String getDateCreated(String faction) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT date FROM factions WHERE name=? AND ruin=?");
            ps.setString(1, faction);
            ps.setString(2, plugin.ruin.getName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("date");
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getFounder(String faction) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT founder FROM factions WHERE name=? AND ruin=?");
            ps.setString(1, faction);
            ps.setString(2, plugin.ruin.getName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("founder");
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Result checkExists(String name) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT name FROM factions WHERE name=? AND ruin=?");
            ps.setString(1, name);
            ps.setString(2, plugin.ruin.getName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Result.TRUE;
            } else {
                return Result.FALSE;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.ERROR;
        }
    }

    public Result createFaction(String name, Player player) {
        plugin.checkConnection();
        if (checkExists(name) == Result.FALSE) {
            PreparedStatement ps;
            try {
                ps = plugin.connection.prepareStatement("INSERT INTO `factions` VALUES (?,?,?,?)");
                ps.setString(1, name);
                ps.setString(2, plugin.ruin.getName());
                ps.setString(3, plugin.getCurrentDate());
                ps.setString(4, player.getName());
                ps.executeUpdate();
                plugin.fPlayer.joinFaction(player.getUniqueId(), player.getName(), name);
                plugin.fPlayer.setFRank(player.getUniqueId(), FactionRanks.FOUNDER);
                Bukkit.getServer().getPluginManager().callEvent(new FactionCreateEvent(player, name));
                return Result.SUCCESS;
            } catch (SQLException e) {
                e.printStackTrace();
                return Result.ERROR;
            }
        } else {
            return Result.ERROR;
        }
    }

    public Result isEnlisted(String name) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT name FROM factioncenter WHERE name=?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Result.TRUE;
            } else {
                return Result.FALSE;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.ERROR;
        }
    }

    public Result enlistFaction(String name) {
        plugin.checkConnection();
        if (checkExists(name) == Result.FALSE) {
            PreparedStatement ps;
            try {
                ps = plugin.connection.prepareStatement("INSERT INTO `factioncenter` VALUES (?,?,?)");
                ps.setString(1, name);
                ps.setString(2, plugin.getCurrentDate());
                ps.setString(3, plugin.ruin.getName());
                ps.executeUpdate();
                Bukkit.getServer().getPluginManager().callEvent(new FactionEnlistEvent(name));
                return Result.SUCCESS;
            } catch (SQLException e) {
                e.printStackTrace();
                return Result.ERROR;
            }
        } else {
            return Result.ERROR;
        }
    }

    public Result unenlistFaction(String name) {
        plugin.checkConnection();
        if (checkExists(name) == Result.TRUE) {
            PreparedStatement ps;
            try {
                ps = plugin.connection.prepareStatement("DELETE FROM factioncenter WHERE name=? AND ruin=?");
                ps.setString(1, name);
                ps.setString(2, plugin.ruin.getName());
                ps.executeUpdate();
                Bukkit.getServer().getPluginManager().callEvent(new FactionUnenlistEvent(name));
                return Result.SUCCESS;
            } catch (SQLException e) {
                e.printStackTrace();
                return Result.ERROR;
            }
        } else {
            return Result.ERROR;
        }
    }

    public Result deleteFaction(String name) {
        plugin.checkConnection();
        if (checkExists(name) == Result.TRUE) {
            PreparedStatement ps;
            try {
                ps = plugin.connection.prepareStatement("DELETE FROM factions WHERE name=? AND ruin=?");
                ps.setString(1, name);
                ps.setString(2, plugin.ruin.getName());
                ps.executeUpdate();
                Bukkit.getServer().getPluginManager().callEvent(new FactionDeleteEvent(name));
                if(isEnlisted(name) == Result.TRUE) {
                    unenlistFaction(name);
                }
                return Result.SUCCESS;
            } catch (SQLException e) {
                e.printStackTrace();
                return Result.ERROR;
            }
        } else {
            return Result.ERROR;
        }
    }

    public FactionRanks getFactionRanksFromString(String rank) {
        for (FactionRanks r : FactionRanks.values()) {
            if (rank.equalsIgnoreCase(r.getName())) {
                return r;
            }
        }
        return null;
    }
}
