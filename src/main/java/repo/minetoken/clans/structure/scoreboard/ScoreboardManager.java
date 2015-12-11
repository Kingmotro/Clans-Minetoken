package repo.minetoken.clans.structure.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import repo.minetoken.clans.structure.clan.events.ClanRankChangeEvent;
import repo.minetoken.clans.structure.clan.events.PlayerJoinFactionEvent;
import repo.minetoken.clans.structure.economy.EconomyManager;
import repo.minetoken.clans.structure.economy.events.PointChangeEvent;
import repo.minetoken.clans.structure.rank.RankManager;
import repo.minetoken.clans.structure.rank.events.RankChangeEvent;
import repo.minetoken.clans.structure.scoreboard.commands.ScoreboardCommand;
import repo.minetoken.clans.structure.scoreboard.scoreboards.ClanScoreboard;
import repo.minetoken.clans.structure.scoreboard.scoreboards.PlayerScoreboard;
import repo.minetoken.clans.structure.voting.VoteManager;
import repo.minetoken.clans.structure.voting.events.VoteEvent;
import repo.minetoken.clans.structure.clan.ClanManager;
import repo.minetoken.clans.structure.clan.events.PlayerLeaveFactionEvent;
import repo.minetoken.clans.structure.inventory.MenuManager;

import java.util.HashMap;
import java.util.UUID;


public class ScoreboardManager implements Listener {

    public JavaPlugin plugin;
    public ClanManager clanManager;
    public RankManager rankManager;
    public EconomyManager economyManager;
    public VoteManager voteManager;
    public MenuManager menuManager;

    public HashMap<UUID, PlayerScoreboard> playerScoreboard;
    public HashMap<UUID, ClanScoreboard> clanScoreboard;

    public ScoreboardManager(JavaPlugin plugin, ClanManager clanManager, RankManager rankManager, EconomyManager economyManager, VoteManager voteManager, MenuManager menuManager) {
        this.plugin = plugin;
        this.clanManager = clanManager;
        this.rankManager = rankManager;
        this.economyManager = economyManager;
        this.voteManager = voteManager;
        this.menuManager = menuManager;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        plugin.getCommand("scoreboard").setExecutor(new ScoreboardCommand(this));

        menuManager.addMenu("Scoreboard Menu", new ScoreboardMenu(this));

        this.playerScoreboard = new HashMap<>();
        this.clanScoreboard = new HashMap<>();
    }

    public void addPlayerScoreboard(Player player, PlayerScoreboard scoreboard) {
        if(!clanScoreboard.containsKey(player.getUniqueId())) {
            if (playerScoreboard.containsKey(player.getUniqueId())) {
                playerScoreboard.remove(player.getUniqueId());
                playerScoreboard.put(player.getUniqueId(), scoreboard);
            } else {
                playerScoreboard.put(player.getUniqueId(), scoreboard);
            }
        } else {
            clanScoreboard.remove(player.getUniqueId());
            if (playerScoreboard.containsKey(player.getUniqueId())) {
                playerScoreboard.remove(player.getUniqueId());
                playerScoreboard.put(player.getUniqueId(), scoreboard);
            } else {
                playerScoreboard.put(player.getUniqueId(), scoreboard);
            }
        }
    }

    public void addClanScoreboard(Player player, ClanScoreboard scoreboard) {
        if(!playerScoreboard.containsKey(player.getUniqueId())) {
            if (clanScoreboard.containsKey(player.getUniqueId())) {
                clanScoreboard.remove(player.getUniqueId());
                clanScoreboard.put(player.getUniqueId(), scoreboard);
            } else {
                clanScoreboard.put(player.getUniqueId(), scoreboard);
            }
        } else {
            playerScoreboard.remove(player.getUniqueId());
            if (clanScoreboard.containsKey(player.getUniqueId())) {
                clanScoreboard.remove(player.getUniqueId());
                clanScoreboard.put(player.getUniqueId(), scoreboard);
            } else {
                clanScoreboard.put(player.getUniqueId(), scoreboard);
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if(playerScoreboard.containsKey(event.getPlayer().getUniqueId())) {
            playerScoreboard.remove(event.getPlayer().getUniqueId());
        }
        if(clanScoreboard.containsKey(event.getPlayer().getUniqueId())) {
            clanScoreboard.remove(event.getPlayer().getUniqueId());
        }
    }

    /*
    PLAYER SCOREBOARD UPDATE EVENTS
     */

    @EventHandler
    public void onClanJoin(PlayerJoinFactionEvent event) {
        if (playerScoreboard.containsKey(event.getPlayer().getUniqueId())) {
            PlayerScoreboard pScoreboard = playerScoreboard.get(event.getPlayer().getUniqueId());
            pScoreboard.setClan(event.getFaction());
        }
    }

    @EventHandler
    public void onClanLeave(PlayerLeaveFactionEvent event) {
        if (playerScoreboard.containsKey(event.getPlayer().getUniqueId())) {
            PlayerScoreboard pScoreboard = playerScoreboard.get(event.getPlayer().getUniqueId());
            pScoreboard.setClan("None");
        }
    }

    @EventHandler
    public void onPointsChange(PointChangeEvent event) {
        if (playerScoreboard.containsKey(event.getPlayer().getUniqueId())) {
            PlayerScoreboard pScoreboard = playerScoreboard.get(event.getPlayer().getUniqueId());
            pScoreboard.setPoints(economyManager.cEco.getPoints(event.getPlayer().getUniqueId()) + event.getAmount());
        }
    }

    @EventHandler
    public void onCreditsChange(PointChangeEvent event) {
        if (playerScoreboard.containsKey(event.getPlayer().getUniqueId())) {
            PlayerScoreboard pScoreboard = playerScoreboard.get(event.getPlayer().getUniqueId());
            pScoreboard.setCredits(economyManager.cEco.getCredits(event.getPlayer().getUniqueId()) + event.getAmount());
        }
    }

    @EventHandler
    public void onRankChange(RankChangeEvent event) {
        if (playerScoreboard.containsKey(event.getPlayer().getUniqueId())) {
            PlayerScoreboard pScoreboard = playerScoreboard.get(event.getPlayer().getUniqueId());
            pScoreboard.setRanks(event.getRank());
        }
    }

    @EventHandler
    public void onClanRankChange(ClanRankChangeEvent event) {
        if (playerScoreboard.containsKey(event.getPlayer().getUniqueId())) {
            PlayerScoreboard pScoreboard = playerScoreboard.get(event.getPlayer().getUniqueId());
            pScoreboard.setClanRanks(event.getRank());
        }
    }

    /*
    CLAN SCOREBOARD UPDATE EVENT
     */

}
