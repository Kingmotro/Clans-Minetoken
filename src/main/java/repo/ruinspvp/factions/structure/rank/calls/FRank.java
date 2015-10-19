package repo.ruinspvp.factions.structure.rank.calls;

import org.bukkit.Bukkit;
import repo.ruinspvp.factions.structure.database.DatabaseCall;
import repo.ruinspvp.factions.structure.rank.RankManager;
import repo.ruinspvp.factions.structure.rank.enums.Ranks;
import repo.ruinspvp.factions.structure.rank.enums.Result;
import repo.ruinspvp.factions.structure.rank.events.RankChangeEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class FRank extends DatabaseCall<RankManager> {

    public FRank(RankManager plugin) {
        super(plugin);
    }

    public Ranks getRank(UUID uuid) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("SELECT rank FROM ranks WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String rank = rs.getString("rank");
                return getRankFromString(rank);
            } else {
                return Ranks.DEFAULT;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Ranks.DEFAULT;
        }
    }

    public Result setRank(UUID uuid, Ranks rank) {
        plugin.checkConnection();
        try {
            PreparedStatement ps = plugin.connection.prepareStatement("UPDATE `ranks` SET rank=? WHERE uuid=?");
            ps.setString(1, rank.getName());
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            Bukkit.getPluginManager().callEvent(new RankChangeEvent(Bukkit.getPlayer(uuid), rank));
            return Result.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.ERROR;
        }
    }

    public Ranks getRankFromString(String rank) {
        for (Ranks r : Ranks.values()) {
            if (rank.equalsIgnoreCase(r.getName())) {
                return r;
            }
        }
        return null;
    }
}
