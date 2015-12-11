package repo.ruinspvp.clans.structure.clan.playerCalls;

import org.bukkit.Bukkit;
import repo.ruinspvp.clans.structure.database.DatabaseCall;
import repo.ruinspvp.clans.structure.clan.ClanManager;
import repo.ruinspvp.clans.structure.clan.enums.ClanRanks;
import repo.ruinspvp.clans.structure.clan.events.*;
import repo.ruinspvp.clans.structure.rank.enums.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CPlayer extends DatabaseCall<ClanManager> {

    public CPlayer(ClanManager plugin) {
        super(plugin);
    }

    public String getName(UUID uuid) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT name FROM cplayer WHERE uuid=?");
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
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT uuid FROM cplayer WHERE name=?");
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
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT uuid FROM cplayer WHERE uuid=?");
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

    public Result hasClan(UUID uuid) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT clan FROM cplayer WHERE uuid=?");
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

    public String getClan(UUID uuid) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT clan FROM cplayer WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("clan");
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ClanRanks getCRank(UUID uuid) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT crank FROM cplayer WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String rank = rs.getString("crank");
                return getRankFromString(rank);
            } else {
                return ClanRanks.PLEB;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ClanRanks.PLEB;
        }
    }

    public Result setCRank(UUID uuid, ClanRanks rank) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("UPDATE `cplayer` SET crank=? WHERE uuid=?");
            ps.setString(1, rank.getName());
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            Bukkit.getPluginManager().callEvent(new ClanRankChangeEvent(Bukkit.getPlayer(uuid), rank));
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
                ps = plugin.connection.prepareStatement("INSERT INTO `cplayer` VALUES (?,?,?,?,?)");
                ps.setString(1, uuid.toString());
                ps.setString(2, name);
                ps.setString(3, plugin.getCurrentDate());
                ps.setString(4, ClanRanks.PLEB.getName());
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
            PreparedStatement ps = plugin.connection.prepareStatement("UPDATE `cplayer` SET name=? WHERE uuid=?");
            ps.setString(1, newName);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            return Result.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.ERROR;
        }
    }

    public ClanRanks getRankFromString(String rank) {
        for (ClanRanks r : ClanRanks.values()) {
            if (rank.equalsIgnoreCase(r.getName())) {
                return r;
            }
        }
        return null;
    }

    public Result leaveFaction(UUID uuid) {
        plugin.checkConnection();
        String name = getClan(uuid);
        if (checkExists(uuid) == Result.TRUE) {
            PreparedStatement ps;
            try {
                ps = plugin.connection.prepareStatement("DELETE FROM cplayer WHERE uuid=?");
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
            ps = plugin.connection.prepareStatement("DELETE FROM cplayer WHERE clan=?");
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
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT * FROM cplayer WHERE clan=?");
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
