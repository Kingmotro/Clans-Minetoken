package repo.ruinspvp.factions.structure.faction.playerCalls;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import repo.ruinspvp.factions.structure.database.DatabaseCall;
import repo.ruinspvp.factions.structure.faction.FactionManager;
import repo.ruinspvp.factions.structure.faction.enums.FactionRanks;
import repo.ruinspvp.factions.structure.faction.events.*;
import repo.ruinspvp.factions.structure.rank.enums.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FPlayer extends DatabaseCall<FactionManager> {

    public FPlayer(FactionManager plugin) {
        super(plugin);
    }

    public String getName(UUID uuid) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT name FROM fplayer WHERE uuid=?");
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
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT uuid FROM fplayer WHERE name=?");
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
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT uuid FROM fplayer WHERE uuid=?");
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

    public Result hasFaction(UUID uuid) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT faction FROM fplayer WHERE uuid=?");
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

    public String getFaction(UUID uuid) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT faction FROM fplayer WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("faction");
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public FactionRanks getFRank(UUID uuid) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT frank FROM fplayer WHERE uuid=?");
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
            PreparedStatement ps = plugin.connection.prepareStatement("UPDATE `fplayer` SET frank=? WHERE uuid=?");
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

    public Result joinFaction(UUID uuid, String name, String faction) {
        plugin.checkConnection();
        if (checkExists(uuid) == Result.FALSE) {
            PreparedStatement ps;
            try {
                ps = plugin.connection.prepareStatement("INSERT INTO `fplayer` VALUES (?,?,?,?,?)");
                ps.setString(1, uuid.toString());
                ps.setString(2, name);
                ps.setString(3, plugin.getCurrentDate());
                ps.setString(4, FactionRanks.PLEB.getName());
                ps.setString(5, faction);
                ps.executeUpdate();
                Bukkit.getServer().getPluginManager().callEvent(new PlayerJoinFactionEvent(Bukkit.getPlayer(uuid), faction));
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
            PreparedStatement ps = plugin.connection.prepareStatement("UPDATE `fplayer` SET name=? WHERE uuid=?");
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

    public Result leaveFaction(UUID uuid) {
        plugin.checkConnection();
        String name = getFaction(uuid);
        if (checkExists(uuid) == Result.TRUE) {
            PreparedStatement ps;
            try {
                ps = plugin.connection.prepareStatement("DELETE FROM fplayer WHERE uuid=?");
                ps.setString(1, uuid.toString());
                ps.executeUpdate();
                Bukkit.getServer().getPluginManager().callEvent(new PlayerLeaveFactionEvent(Bukkit.getPlayer(uuid), name));
                return Result.SUCCESS;
            } catch (SQLException e) {
                e.printStackTrace();
                return Result.ERROR;
            }
        } else {
            return Result.ERROR;
        }
    }

    public Result kickAllPlayerFromFaction(String name) {
        plugin.checkConnection();
        PreparedStatement ps;
        try {
            ps = plugin.connection.prepareStatement("DELETE FROM fplayer WHERE faction=?");
            ps.setString(1, name);
            ps.executeUpdate();
            return Result.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.ERROR;
        }
    }

    List<String> players;

    public List<String> getPlayersInAFaction(String faction) {
        players = new ArrayList<>();
        players.clear();
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT * FROM fplayer WHERE faction=?");
            ps.setString(1, faction);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String s = rs.getString("uuid");
                players.add(s);
            }
            return players;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
