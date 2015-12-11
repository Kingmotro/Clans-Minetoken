package repo.minetoken.clans.structure.economy.calls;

import org.bukkit.Bukkit;
import repo.minetoken.clans.structure.database.DatabaseCall;
import repo.minetoken.clans.structure.economy.EconomyManager;
import repo.minetoken.clans.structure.economy.events.CreditChangeEvent;
import repo.minetoken.clans.structure.economy.events.PointChangeEvent;
import repo.minetoken.clans.structure.rank.enums.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CEco extends DatabaseCall<EconomyManager> {

    public CEco(EconomyManager plugin) {
        super(plugin);
    }

    public int getPoints(UUID uuid) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT points FROM eco WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("points");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Result setPoints(UUID uuid, int amount) {
        plugin.checkConnection();
        PreparedStatement ps;
        try {
            ps = plugin.connection.prepareStatement("UPDATE `eco` SET points=? WHERE uuid=?");
            ps.setInt(1, amount);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            Bukkit.getPluginManager().callEvent(new PointChangeEvent(Bukkit.getPlayer(uuid), amount));
            return Result.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.ERROR;
        }
    }

    public Result addPoints(UUID uuid, int amount) {
        plugin.checkConnection();
        PreparedStatement ps;
        try {
            ps = plugin.connection.prepareStatement("UPDATE `eco` SET points=? WHERE uuid=?");
            ps.setInt(1, getPoints(uuid) + amount);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            Bukkit.getPluginManager().callEvent(new PointChangeEvent(Bukkit.getPlayer(uuid), amount));
            return Result.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.ERROR;
        }
    }

    public Result removePoints(UUID uuid, int amount) {
        plugin.checkConnection();
        PreparedStatement ps;
        try {
            ps = plugin.connection.prepareStatement("UPDATE `eco` SET points=? WHERE uuid=?");
            ps.setInt(1, getPoints(uuid) - amount);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            Bukkit.getPluginManager().callEvent(new PointChangeEvent(Bukkit.getPlayer(uuid), amount));
            return Result.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.ERROR;
        }
    }
    public int getCredits(UUID uuid) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT credits FROM eco WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("credits");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Result setCredits(UUID uuid, int amount) {
        plugin.checkConnection();
        PreparedStatement ps;
        try {
            ps = plugin.connection.prepareStatement("UPDATE `eco` SET credits=? WHERE uuid=?");
            ps.setInt(1, amount);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            Bukkit.getPluginManager().callEvent(new PointChangeEvent(Bukkit.getPlayer(uuid), amount));
            return Result.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.ERROR;
        }
    }

    public Result addCredits(UUID uuid, int amount) {
        plugin.checkConnection();
        PreparedStatement ps;
        try {
            ps = plugin.connection.prepareStatement("UPDATE `eco` SET credits=? WHERE uuid=?");
            ps.setInt(1, getCredits(uuid) + amount);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            Bukkit.getPluginManager().callEvent(new CreditChangeEvent(Bukkit.getPlayer(uuid), amount));
            return Result.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.ERROR;
        }
    }

    public Result removeCredits(UUID uuid, int amount) {
        plugin.checkConnection();
        PreparedStatement ps;
        try {
            ps = plugin.connection.prepareStatement("UPDATE `eco` SET credits=? WHERE uuid=?");
            ps.setInt(1, getCredits(uuid) - amount);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            Bukkit.getPluginManager().callEvent(new CreditChangeEvent(Bukkit.getPlayer(uuid), amount));
            return Result.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.ERROR;
        }
    }
}
