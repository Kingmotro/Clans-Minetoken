package repo.minetoken.clans.structure.clan.clanCalls;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import repo.minetoken.clans.structure.clan.enums.ClanRanks;
import repo.minetoken.clans.structure.clan.events.ClanCreateEvent;
import repo.minetoken.clans.structure.clan.events.ClanDeleteEvent;
import repo.minetoken.clans.structure.clancenter.events.ClanEnlistEvent;
import repo.minetoken.clans.structure.clancenter.events.ClanUnenlistEvent;
import repo.minetoken.clans.structure.database.DatabaseCall;
import repo.minetoken.clans.structure.rank.enums.Result;
import repo.minetoken.clans.structure.clan.ClanManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CClans extends DatabaseCall<ClanManager> {

    public CClans(ClanManager plugin) {
        super(plugin);
    }

    public String getDateCreated(String faction) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT date FROM clans WHERE name=?");
            ps.setString(1, faction);
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
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT founder FROM clans WHERE name=?");
            ps.setString(1, faction);
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
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT name FROM clans WHERE name=?");
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

    public Result createClan(String name, Player player) {
        plugin.checkConnection();
        if (checkExists(name) == Result.FALSE) {
            PreparedStatement ps;
            try {
                ps = plugin.connection.prepareStatement("INSERT INTO `clans` VALUES (?,?,?)");
                ps.setString(1, name);
                ps.setString(2, plugin.getCurrentDate());
                ps.setString(3, player.getName());
                ps.executeUpdate();
                plugin.cPlayer.joinFaction(player.getUniqueId(), player.getName(), name);
                plugin.cPlayer.setCRank(player.getUniqueId(), ClanRanks.FOUNDER);
                Bukkit.getServer().getPluginManager().callEvent(new ClanCreateEvent(player, name));
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
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT name FROM clancenter WHERE name=?");
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

    public Result enlistClan(String name) {
        plugin.checkConnection();
        if (checkExists(name) == Result.FALSE) {
            PreparedStatement ps;
            try {
                ps = plugin.connection.prepareStatement("INSERT INTO `clancenter` VALUES (?,?)");
                ps.setString(1, name);
                ps.setString(2, plugin.getCurrentDate());
                ps.executeUpdate();
                Bukkit.getServer().getPluginManager().callEvent(new ClanEnlistEvent(name));
                return Result.SUCCESS;
            } catch (SQLException e) {
                e.printStackTrace();
                return Result.ERROR;
            }
        } else {
            return Result.ERROR;
        }
    }

    public Result unenlistClan(String name) {
        plugin.checkConnection();
        if (checkExists(name) == Result.TRUE) {
            PreparedStatement ps;
            try {
                ps = plugin.connection.prepareStatement("DELETE FROM clancenter WHERE name=?");
                ps.setString(1, name);
                ps.executeUpdate();
                Bukkit.getServer().getPluginManager().callEvent(new ClanUnenlistEvent(name));
                return Result.SUCCESS;
            } catch (SQLException e) {
                e.printStackTrace();
                return Result.ERROR;
            }
        } else {
            return Result.ERROR;
        }
    }

    public Result deleteClan(String name) {
        plugin.checkConnection();
        if (checkExists(name) == Result.TRUE) {
            PreparedStatement ps;
            try {
                ps = plugin.connection.prepareStatement("DELETE FROM clans WHERE name=?");
                ps.setString(1, name);
                ps.executeUpdate();
                Bukkit.getServer().getPluginManager().callEvent(new ClanDeleteEvent(name));
                if(isEnlisted(name) == Result.TRUE) {
                    unenlistClan(name);
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

    public ClanRanks getClansRanksFromString(String rank) {
        for (ClanRanks r : ClanRanks.values()) {
            if (rank.equalsIgnoreCase(r.getName())) {
                return r;
            }
        }
        return null;
    }
}
