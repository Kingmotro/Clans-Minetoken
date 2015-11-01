package repo.ruinspvp.factions.structure.scoreboard.scoreboards;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.structure.economy.EconomyManager;
import repo.ruinspvp.factions.structure.economy.events.MoneyChangeEvent;
import repo.ruinspvp.factions.structure.faction.FactionManager;
import repo.ruinspvp.factions.structure.faction.enums.FactionRanks;
import repo.ruinspvp.factions.structure.faction.events.FactionRankChangeEvent;
import repo.ruinspvp.factions.structure.faction.events.PlayerJoinFactionEvent;
import repo.ruinspvp.factions.structure.faction.events.PlayerLeaveFactionEvent;
import repo.ruinspvp.factions.structure.rank.RankManager;
import repo.ruinspvp.factions.structure.rank.enums.Ranks;
import repo.ruinspvp.factions.structure.rank.events.RankChangeEvent;
import repo.ruinspvp.factions.structure.scoreboard.Scoreboard;
import repo.ruinspvp.factions.structure.scoreboard.ScoreboardManager;
import repo.ruinspvp.factions.structure.voting.VoteManager;
import repo.ruinspvp.factions.structure.voting.events.VoteEvent;

public class PlayerScoreboard extends Scoreboard implements Listener {

    public Player player;
    public ScoreboardManager scoreboardManager;
    public RankManager rankManager;
    public EconomyManager economyManager;
    public VoteManager voteManager;
    public FactionManager factionManager;

    public int eco;
    public Ranks ranks;
    public String faction;
    public FactionRanks factionRanks;
    public int votes;

    public PlayerScoreboard(Player player, ScoreboardManager scoreboardManager) {
        super(player.getName());
        this.player = player;
        this.scoreboardManager = scoreboardManager;
        this.rankManager = scoreboardManager.rankManager;
        this.economyManager = scoreboardManager.economyManager;
        this.voteManager = scoreboardManager.voteManager;
        this.factionManager = scoreboardManager.factionManager;

        this.eco = economyManager.fEco.getMoney(player.getUniqueId());
        this.ranks = rankManager.fRank.getRank(player.getUniqueId());
        this.faction = factionManager.fPlayer.getFaction(player.getUniqueId());
        this.factionRanks = factionManager.fPlayer.getFRank(player.getUniqueId());
        this.votes = voteManager.fVote.getVotes(player.getUniqueId());

        add(ChatColor.GREEN + "" + ChatColor.BOLD + "Server Rank", 14);
        add(ranks.getName(), 13);
        add("§r  ", 12);
        add(ChatColor.AQUA + "" + ChatColor.BOLD + "Economy", 11);
        add(Integer.toString(eco), 10);
        add("§4  ", 9);
        add(ChatColor.GOLD + "" + ChatColor.BOLD + "Faction", 8);
        add(faction, 7);
        add("§2  ", 6);
        add(ChatColor.RED + "" + ChatColor.BOLD + "Faction Rank", 5);
        add(factionRanks.getName(), 4);
        add("§c  ", 3);
        add(ChatColor.GOLD + "" + ChatColor.BOLD + "Votes", 2);
        add(Integer.toString(votes), 1);

        build();
    }

    @EventHandler
    public void onFactionJoin(PlayerJoinFactionEvent event) {
        if (scoreboardManager.playerScoreboard.get(event.getPlayer().getUniqueId()) == this) {
            this.faction = event.getFaction();
        }
    }

    @EventHandler
    public void onFactionLeave(PlayerLeaveFactionEvent event) {
        if (scoreboardManager.playerScoreboard.get(event.getPlayer().getUniqueId()) == this) {
            this.faction = "None";
        }
    }

    @EventHandler
    public void onMoneyChange(MoneyChangeEvent event) {
        if (scoreboardManager.playerScoreboard.get(event.getPlayer().getUniqueId()) == this) {
            this.eco = economyManager.fEco.getMoney(event.getPlayer().getUniqueId()) + event.getAmount();
        }
    }

    @EventHandler
    public void onRankChange(RankChangeEvent event) {
        if (scoreboardManager.playerScoreboard.get(event.getPlayer().getUniqueId()) == this) {
            this.ranks = event.getRank();
        }
    }

    @EventHandler
    public void onFactionsRankChange(FactionRankChangeEvent event) {
        if (scoreboardManager.playerScoreboard.get(event.getPlayer().getUniqueId()) == this) {
            this.factionRanks = event.getRank();
        }
    }

    @EventHandler
    public void onVote(VoteEvent event) {
        if (scoreboardManager.playerScoreboard.get(event.getPlayer().getUniqueId()) == this) {
            this.votes = event.getAmount();
        }
    }
}
