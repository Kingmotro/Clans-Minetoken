package repo.minetoken.clans.structure.voting.calls;

import org.bukkit.Bukkit;
import repo.minetoken.clans.structure.database.DatabaseCall;
import repo.minetoken.clans.structure.rank.enums.Result;
import repo.minetoken.clans.structure.voting.VoteManager;
import repo.minetoken.clans.structure.voting.events.VoteEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CVote extends DatabaseCall<VoteManager> {

    public CVote(VoteManager plugin) {
        super(plugin);
    }

    public String getName(UUID uuid) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT name FROM votes WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Deprecated
    public UUID getUUID(String name) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT uuid FROM votes WHERE name=?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return UUID.fromString(rs.getString("UUID"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Result checkExists(UUID uuid) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT uuid FROM votes WHERE uuid=?");
            ps.setString(1, uuid.toString());
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

    public Result addPlayer(UUID uuid, String name) {
        plugin.checkConnection();
        if (checkExists(uuid) == Result.FALSE) {
            PreparedStatement ps;
            try {
                ps = plugin.connection.prepareStatement("INSERT INTO `votes` VALUES (?,?,?,?)");
                ps.setString(1, uuid.toString());
                ps.setString(2, name);
                ps.setString(3, plugin.getCurrentDate());
                ps.setInt(4, 0);
                ps.executeUpdate();
                return Result.SUCCESS;
            } catch (SQLException e) {
                e.printStackTrace();
                return Result.ERROR;
            }
        } else {
            return Result.ERROR;
        }
    }

    public Result updatePlayerName(UUID p, String newName) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("UPDATE `votes` SET name=?,date=? WHERE uuid=?");
            ps.setString(1, newName);
            ps.setString(2, plugin.getCurrentDate());
            ps.setString(3, p.toString());
            ps.executeUpdate();
            return Result.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.ERROR;
        }
    }

    public int getVotes(UUID uuid) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT votes FROM votes WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("votes");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Result setVotes(UUID uuid, int amount) {
        plugin.checkConnection();
        PreparedStatement ps;
        try {
            ps = plugin.connection.prepareStatement("UPDATE `votes` SET votes = ? WHERE uuid=?");
            ps.setInt(1, amount);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            Bukkit.getPluginManager().callEvent(new VoteEvent(Bukkit.getPlayer(uuid), amount));
            return Result.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.ERROR;
        }

    }
}