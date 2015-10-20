package repo.ruinspvp.factions.structure.faction.factionCalls;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import repo.ruinspvp.factions.structure.database.DatabaseCall;
import repo.ruinspvp.factions.structure.faction.FactionManager;
import repo.ruinspvp.factions.structure.faction.enums.FactionRanks;
import repo.ruinspvp.factions.structure.faction.events.FactionCreateEvent;
import repo.ruinspvp.factions.structure.faction.events.FactionRankChangeEvent;
import repo.ruinspvp.factions.structure.rank.enums.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class FFaction extends DatabaseCall<FactionManager> {

    public FFaction(FactionManager plugin) {
        super(plugin);
    }

    public Result checkExists(String name) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT name FROM factions WHERE name=?");
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

    public FactionRanks getFRank(UUID uuid) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT frank FROM factions WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String rank = rs.getString("frank");
                return getRankFromString(rank);
            } else {
                return FactionRanks.PLEB;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return FactionRanks.PLEB;
        }
    }

    public Result setFRank(UUID uuid, FactionRanks rank) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("UPDATE `factions` SET frank=? WHERE uuid=?");
            ps.setString(1, rank.getName());
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            Bukkit.getPluginManager().callEvent(new FactionRankChangeEvent(Bukkit.getPlayer(uuid), rank));
            return Result.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.ERROR;
        }
    }

    public Result createFaction(String name, String ruin, Player player) {
        plugin.checkConnection();
        if (checkExists(name) == Result.FALSE) {
            PreparedStatement ps;
            try {
                ps = plugin.connection.prepareStatement("INSERT INTO `factions` VALUES (?,?,?,?)");
                ps.setString(1, name);
                ps.setString(2, ruin);
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

    public Result updatePlayerName(UUID uuid, String newName) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("UPDATE `factions` SET name=? WHERE uuid=?");
            ps.setString(1, newName);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            return Result.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.ERROR;
        }
    }

    public FactionRanks getRankFromString(String rank) {
        for (FactionRanks r : FactionRanks.values()) {
            if (rank.equalsIgnoreCase(r.getName())) {
                return r;
            }
        }
        return null;
    }
}
