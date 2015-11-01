package repo.ruinspvp.factions.structure.voting;

import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.Ruin;
import repo.ruinspvp.factions.structure.database.Database;
import repo.ruinspvp.factions.structure.economy.EconomyManager;
import repo.ruinspvp.factions.structure.rank.RankManager;
import repo.ruinspvp.factions.structure.rank.enums.Result;
import repo.ruinspvp.factions.structure.voting.calls.FVote;
import repo.ruinspvp.factions.utilities.Format;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class VoteManager extends Database implements Listener {

    public Connection connection;
    public FVote fVote;
    public RankManager rankManager;
    public EconomyManager economyManager;

    public VoteManager(JavaPlugin plugin, RankManager rankManager, EconomyManager economyManager) {
        super("root", "ThePyxel", "", "3306", "localhost");
        connection = openConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `votes` (`uuid` varchar(36) NOT NULL, `name` varchar(32) NOT NULL, `date` varchar(32) NOT NULL, `votes` int(8) NOT NULL)");
            ps.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not check if table exists, restarting server.");
            e.printStackTrace();
            Bukkit.getServer().shutdown();
        }

        this.rankManager = rankManager;
        this.economyManager = economyManager;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        fVote = new FVote(this);
    }


    public String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }


    @EventHandler
    public void onVote(VotifierEvent event) throws SQLException {
        Player player = Bukkit.getPlayer(event.getVote().getUsername());
        UUID uuid = player.getUniqueId();

        if (fVote.checkExists(uuid) == Result.FALSE) {
            if (rankManager.fPlayer.checkExists(uuid) == Result.FALSE) {
                Bukkit.getServer().broadcastMessage(Format.main("Vote", Format.highlight(player.getName()) + " just voted but isn't registered on our client system."));
                Bukkit.getServer().broadcastMessage(Format.main("Vote", "If you see this message contact a Leader/Admin or post a forum thread."));
                return;
            } else {
                //TODO: Reward Player
                for (Ruin ruin : Ruin.values()) {
                    economyManager.fEco.addMoney(player.getUniqueId(), 1000, ruin);
                }
                return;
            }
        } else {
            if (rankManager.fPlayer.checkExists(uuid) == Result.FALSE) {
                Bukkit.getServer().broadcastMessage(Format.main("Vote", Format.highlight(player.getName()) + " just voted but isn't registered on our client system."));
                Bukkit.getServer().broadcastMessage(Format.main("Vote", "If you see this message contact a Leader/Admin or post a forum thread."));
                return;
            } else {
                Bukkit.getServer().broadcastMessage(Format.main("Vote", Format.highlight(player.getName()) + " just voted for RuinsPvP on " + Format.highlight(event.getVote().getServiceName())));
                fVote.setVotes(uuid, fVote.getVotes(uuid) + 1);
                for (Ruin ruin : Ruin.values()) {
                    economyManager.fEco.addMoney(player.getUniqueId(), 1000, ruin);
                }
                try {
                    if (player.isOnline()) {
                        player.sendMessage(Format.main("Vote", "You have received $1000 dollars on all ruins, for voting be sure to vote once a day on all the sites."));
                    }
                } catch (Exception ignore) {}
                return;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (fVote.checkExists(player.getUniqueId()) == Result.FALSE) {
            try {
                fVote.addPlayer(player.getUniqueId(), player.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!fVote.getName(player.getUniqueId()).equalsIgnoreCase(player.getName())) {
            try {
                fVote.updatePlayerName(player.getUniqueId(), player.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
