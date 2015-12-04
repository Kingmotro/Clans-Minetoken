package repo.ruinspvp.factions.structure.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.structure.economy.EconomyManager;
import repo.ruinspvp.factions.structure.economy.events.MoneyChangeEvent;
import repo.ruinspvp.factions.structure.faction.FactionManager;
import repo.ruinspvp.factions.structure.faction.events.FactionRankChangeEvent;
import repo.ruinspvp.factions.structure.faction.events.PlayerJoinFactionEvent;
import repo.ruinspvp.factions.structure.faction.events.PlayerLeaveFactionEvent;
import repo.ruinspvp.factions.structure.inventory.MenuManager;
import repo.ruinspvp.factions.structure.rank.RankManager;
import repo.ruinspvp.factions.structure.rank.events.RankChangeEvent;
import repo.ruinspvp.factions.structure.scoreboard.commands.ScoreboardCommand;
import repo.ruinspvp.factions.structure.scoreboard.scoreboards.FactionScoreboard;
import repo.ruinspvp.factions.structure.scoreboard.scoreboards.PlayerScoreboard;
import repo.ruinspvp.factions.structure.voting.VoteManager;
import repo.ruinspvp.factions.structure.voting.events.VoteEvent;

import java.util.HashMap;
import java.util.UUID;


public class ScoreboardManager implements Listener {

    public JavaPlugin plugin;
    public FactionManager factionManager;
    public RankManager rankManager;
    public EconomyManager economyManager;
    public VoteManager voteManager;
    public MenuManager menuManager;

    public HashMap<UUID, PlayerScoreboard> playerScoreboard;
    public HashMap<UUID, FactionScoreboard> factionScoreboard;

    public ScoreboardManager(JavaPlugin plugin, FactionManager factionManager, RankManager rankManager, EconomyManager economyManager, VoteManager voteManager, MenuManager menuManager) {
        this.plugin = plugin;
        this.factionManager = factionManager;
        this.rankManager = rankManager;
        this.economyManager = economyManager;
        this.voteManager = voteManager;
        this.menuManager = menuManager;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        plugin.getCommand("scoreboard").setExecutor(new ScoreboardCommand(this));

        menuManager.addMenu("Scoreboard Menu", new ScoreboardMenu(this));

        this.playerScoreboard = new HashMap<>();
        this.factionScoreboard = new HashMap<>();
    }

    public void addPlayerScoreboard(Player player, PlayerScoreboard scoreboard) {
        if(!factionScoreboard.containsKey(player.getUniqueId())) {
            if (playerScoreboard.containsKey(player.getUniqueId())) {
                playerScoreboard.remove(player.getUniqueId());
                playerScoreboard.put(player.getUniqueId(), scoreboard);
            } else {
                playerScoreboard.put(player.getUniqueId(), scoreboard);
            }
        } else {
            factionScoreboard.remove(player.getUniqueId());
            if (playerScoreboard.containsKey(player.getUniqueId())) {
                playerScoreboard.remove(player.getUniqueId());
                playerScoreboard.put(player.getUniqueId(), scoreboard);
            } else {
                playerScoreboard.put(player.getUniqueId(), scoreboard);
            }
        }
    }

    public void addFactionScoreboard(Player player, FactionScoreboard scoreboard) {
        if(!playerScoreboard.containsKey(player.getUniqueId())) {
            if (factionScoreboard.containsKey(player.getUniqueId())) {
                factionScoreboard.remove(player.getUniqueId());
                factionScoreboard.put(player.getUniqueId(), scoreboard);
            } else {
                factionScoreboard.put(player.getUniqueId(), scoreboard);
            }
        } else {
            playerScoreboard.remove(player.getUniqueId());
            if (factionScoreboard.containsKey(player.getUniqueId())) {
                factionScoreboard.remove(player.getUniqueId());
                factionScoreboard.put(player.getUniqueId(), scoreboard);
            } else {
                factionScoreboard.put(player.getUniqueId(), scoreboard);
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if(playerScoreboard.containsKey(event.getPlayer().getUniqueId())) {
            playerScoreboard.remove(event.getPlayer().getUniqueId());
        }
        if(factionScoreboard.containsKey(event.getPlayer().getUniqueId())) {
            factionScoreboard.remove(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onFactionJoin(PlayerJoinFactionEvent event) {
        if (playerScoreboard.containsKey(event.getPlayer().getUniqueId())) {
            PlayerScoreboard pScoreboard = playerScoreboard.get(event.getPlayer().getUniqueId());
            pScoreboard.setFaction(event.getFaction());
        }
    }

    @EventHandler
    public void onFactionLeave(PlayerLeaveFactionEvent event) {
        if (playerScoreboard.containsKey(event.getPlayer().getUniqueId())) {
            PlayerScoreboard pScoreboard = playerScoreboard.get(event.getPlayer().getUniqueId());
            pScoreboard.setFaction("None");
        }
    }

    @EventHandler
    public void onMoneyChange(MoneyChangeEvent event) {
        if (playerScoreboard.containsKey(event.getPlayer().getUniqueId())) {
            PlayerScoreboard pScoreboard = playerScoreboard.get(event.getPlayer().getUniqueId());
            pScoreboard.setEco(economyManager.fEco.getMoney(event.getPlayer().getUniqueId()) + event.getAmount());
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
    public void onFactionsRankChange(FactionRankChangeEvent event) {
        if (playerScoreboard.containsKey(event.getPlayer().getUniqueId())) {
            PlayerScoreboard pScoreboard = playerScoreboard.get(event.getPlayer().getUniqueId());
            pScoreboard.setFactionRanks(event.getRank());
        }
    }

    @EventHandler
    public void onVote(VoteEvent event) {
        if (playerScoreboard.containsKey(event.getPlayer().getUniqueId())) {
            PlayerScoreboard pScoreboard = playerScoreboard.get(event.getPlayer().getUniqueId());
            pScoreboard.setVotes(event.getAmount());
        }
    }
}
