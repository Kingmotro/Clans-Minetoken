package repo.minetoken.clans.structure.rank.calls;

import repo.minetoken.clans.structure.database.DatabaseCall;
import repo.minetoken.clans.structure.rank.RankManager;
import repo.minetoken.clans.structure.rank.enums.Ranks;
import repo.minetoken.clans.structure.rank.enums.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CPlayer extends DatabaseCall<RankManager> {

    public CPlayer(RankManager plugin) {
        super(plugin);
    }

    public String getName(UUID uuid) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT name FROM ranks WHERE uuid=?");
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

    public UUID getUUID(String name) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT uuid FROM ranks WHERE name=?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return UUID.fromString(rs.getString("uuid"));
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
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT uuid FROM ranks WHERE uuid=?");
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
                ps = plugin.connection.prepareStatement("INSERT INTO `ranks` VALUES (?,?,?,?)");
                ps.setString(1, uuid.toString());
                ps.setString(2, name);
                ps.setString(3, plugin.getCurrentDate());
                ps.setString(4, Ranks.DEFAULT.getCommandName());
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

    public Result updatePlayerName(UUID uuid, String newName) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("UPDATE `ranks` SET name=? WHERE uuid=?");
            ps.setString(1, newName);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            return Result.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.ERROR;
        }
    }
}
