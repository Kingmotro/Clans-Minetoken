package repo.ruinspvp.factions.structure.economy.calls;

import org.bukkit.Bukkit;
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

    public int getJewels(UUID uuid) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT money FROM eco WHERE uuid = ?");
            ps.setString(1, uuid.toString());
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

    public Result updateJewels(UUID uuid, int amount) {
        int currentPoints = -1;
        currentPoints = getJewels(uuid);
        if (currentPoints == -1) {
            return Result.ERROR;
        }
        PreparedStatement ps;
        try {
            ps = plugin.connection.prepareStatement("UPDATE `eco` SET money = ? WHERE uuid = ?");
            ps.setInt(1, amount + currentPoints);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            Bukkit.getPluginManager().callEvent(new MoneyChangeEvent(Bukkit.getPlayer(uuid), amount + currentPoints));
            return Result.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.ERROR;
        }
    }

    public Result setJewels(UUID uuid, int amount) {
        plugin.checkConnection();
        PreparedStatement ps;
        try {
            ps = plugin.connection.prepareStatement("UPDATE `eco` SET money = ? WHERE uuid = ?");
            ps.setInt(1, amount);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            Bukkit.getPluginManager().callEvent(new MoneyChangeEvent(Bukkit.getPlayer(uuid), amount));
            return Result.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.ERROR;
        }

    }
}
