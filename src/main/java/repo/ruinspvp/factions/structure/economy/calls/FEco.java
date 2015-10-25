package repo.ruinspvp.factions.structure.economy.calls;

import org.bukkit.Bukkit;
import repo.ruinspvp.factions.Ruin;
import repo.ruinspvp.factions.structure.database.DatabaseCall;
import repo.ruinspvp.factions.structure.economy.EconomyManager;
import repo.ruinspvp.factions.structure.economy.events.MoneyChangeEvent;
import repo.ruinspvp.factions.structure.rank.enums.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class FEco extends DatabaseCall<EconomyManager> {

    public FEco(EconomyManager plugin) {
        super(plugin);
    }

    public int getMoney(UUID uuid) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT money FROM eco WHERE uuid=? AND ruin=?");
            ps.setString(1, uuid.toString());
            ps.setString(2, plugin.ruin.getName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("money");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Result setMoney(UUID uuid, int amount) {
        plugin.checkConnection();
        PreparedStatement ps;
        try {
            ps = plugin.connection.prepareStatement("UPDATE `eco` SET money=? WHERE uuid=? AND ruin=?");
            ps.setInt(1, amount);
            ps.setString(2, uuid.toString());
            ps.setString(3, plugin.ruin.getName());
            ps.executeUpdate();
            Bukkit.getPluginManager().callEvent(new MoneyChangeEvent(Bukkit.getPlayer(uuid), amount));
            return Result.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.ERROR;
        }
    }

    public Result addMoney(UUID uuid, int amount) {
        plugin.checkConnection();
        PreparedStatement ps;
        try {
            ps = plugin.connection.prepareStatement("UPDATE `eco` SET money=? WHERE uuid=? AND ruin=?");
            ps.setInt(1, getMoney(uuid) + amount);
            ps.setString(2, uuid.toString());
            ps.setString(3, plugin.ruin.getName());
            ps.executeUpdate();
            Bukkit.getPluginManager().callEvent(new MoneyChangeEvent(Bukkit.getPlayer(uuid), amount));
            return Result.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.ERROR;
        }
    }

    public Result addMoney(UUID uuid, int amount, Ruin ruin) {
        plugin.checkConnection();
        PreparedStatement ps;
        try {
            ps = plugin.connection.prepareStatement("UPDATE `eco` SET money=? WHERE uuid=? AND ruin=?");
            ps.setInt(1, getMoney(uuid) + amount);
            ps.setString(2, uuid.toString());
            ps.setString(3, ruin.getName());
            ps.executeUpdate();
            Bukkit.getPluginManager().callEvent(new MoneyChangeEvent(Bukkit.getPlayer(uuid), amount));
            return Result.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.ERROR;
        }
    }

    public Result removeMoney(UUID uuid, int amount) {
        plugin.checkConnection();
        PreparedStatement ps;
        try {
            ps = plugin.connection.prepareStatement("UPDATE `eco` SET money=? WHERE uuid=? AND ruin=?");
            ps.setInt(1, getMoney(uuid) - amount);
            ps.setString(2, uuid.toString());
            ps.setString(3, plugin.ruin.getName());
            ps.executeUpdate();
            Bukkit.getPluginManager().callEvent(new MoneyChangeEvent(Bukkit.getPlayer(uuid), amount));
            return Result.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.ERROR;
        }
    }
}
