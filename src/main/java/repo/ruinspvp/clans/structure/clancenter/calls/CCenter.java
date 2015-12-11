package repo.ruinspvp.clans.structure.clancenter.calls;

import org.bukkit.Bukkit;
import repo.ruinspvp.clans.structure.database.DatabaseCall;
import repo.ruinspvp.clans.structure.clancenter.ClansCenterManager;
import repo.ruinspvp.clans.structure.clancenter.events.ClanEnlistEvent;
import repo.ruinspvp.clans.structure.clancenter.events.ClanUnenlistEvent;
import repo.ruinspvp.clans.structure.rank.enums.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CCenter extends DatabaseCall<ClansCenterManager> {

    public CCenter(ClansCenterManager plugin) {
        super(plugin);
    }

    public String getDateEnlisted(String name) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT date FROM clancenter WHERE name=?");
            ps.setString(1, name);
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
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT * FROM clancenter");
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

    public Result enlistFaction(String name) {
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

    public Result unenlistFaction(String name) {
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
}
