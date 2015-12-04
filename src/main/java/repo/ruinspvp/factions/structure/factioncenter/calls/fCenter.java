package repo.ruinspvp.factions.structure.factioncenter.calls;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import repo.ruinspvp.factions.structure.database.DatabaseCall;
import repo.ruinspvp.factions.structure.faction.enums.FactionRanks;
import repo.ruinspvp.factions.structure.faction.events.FactionCreateEvent;
import repo.ruinspvp.factions.structure.faction.events.FactionDeleteEvent;
import repo.ruinspvp.factions.structure.factioncenter.FactionCenterManager;
import repo.ruinspvp.factions.structure.factioncenter.events.FactionEnlistEvent;
import repo.ruinspvp.factions.structure.factioncenter.events.FactionUnenlistEvent;
import repo.ruinspvp.factions.structure.rank.enums.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FCenter extends DatabaseCall<FactionCenterManager> {

    public FCenter(FactionCenterManager plugin) {
        super(plugin);
    }

    public String getDateEnlisted(String name) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT date FROM factioncenter WHERE name=? AND ruin=?");
            ps.setString(1, name);
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

    List<String> factions;

    public List<String> getEnlistedFactions() {
        factions = new ArrayList<>();
        factions.clear();
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT * FROM factioncenter WHERE ruin=?");
            ps.setString(1, plugin.ruin.getName());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String s = rs.getString("name");
                factions.add(s);
            }
            return factions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Result checkExists(String name) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT name FROM factioncenter WHERE name=? AND ruin=?");
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
}
