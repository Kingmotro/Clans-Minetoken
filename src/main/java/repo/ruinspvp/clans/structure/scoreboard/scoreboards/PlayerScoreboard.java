package repo.ruinspvp.clans.structure.scoreboard.scoreboards;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import repo.ruinspvp.clans.structure.economy.EconomyManager;
import repo.ruinspvp.clans.structure.clan.ClanManager;
import repo.ruinspvp.clans.structure.clan.enums.ClanRanks;
import repo.ruinspvp.clans.structure.rank.RankManager;
import repo.ruinspvp.clans.structure.rank.enums.Ranks;
import repo.ruinspvp.clans.structure.rank.enums.Result;
import repo.ruinspvp.clans.structure.scoreboard.Scoreboard;
import repo.ruinspvp.clans.structure.scoreboard.ScoreboardManager;
import repo.ruinspvp.clans.structure.voting.VoteManager;

public class PlayerScoreboard extends Scoreboard {

    public Player player;
    public ScoreboardManager scoreboardManager;
    public RankManager rankManager;
    public EconomyManager economyManager;
    public VoteManager voteManager;
    public ClanManager clanManager;

    public Ranks ranks;
    public int points, credits;
    public String clan;
    public ClanRanks clanRanks;
    public int votes;

    public PlayerScoreboard(Player player, ScoreboardManager scoreboardManager) {
        super(player.getName());
        this.player = player;
        this.scoreboardManager = scoreboardManager;
        this.rankManager = scoreboardManager.rankManager;
        this.economyManager = scoreboardManager.economyManager;
        this.voteManager = scoreboardManager.voteManager;
        this.clanManager = scoreboardManager.clanManager;

        this.ranks = rankManager.cRank.getRank(player.getUniqueId());
        this.points = economyManager.cEco.getPoints(player.getUniqueId());
        this.votes = voteManager.cVote.getVotes(player.getUniqueId());

        if(clanManager.cPlayer.hasClan(player.getUniqueId()) == Result.FALSE) {
            this.clan = "None";
            this.clanRanks = ClanRanks.NONE;
        } else {
            this.clan = clanManager.cPlayer.getClan(player.getUniqueId());
            this.clanRanks = clanManager.cPlayer.getCRank(player.getUniqueId());
        }

        add(ChatColor.GREEN + "" + ChatColor.BOLD + "Rank", 16);
        add(ranks.getName(), 15);
        add("§r  ", 14);
        add(ChatColor.AQUA + "" + ChatColor.BOLD + "Points", 13);
        add(Integer.toString(points), 12);
        add("§4  ", 11);
        add(ChatColor.AQUA + "" + ChatColor.BOLD + "Credits", 10);
        add(Integer.toString(points), 9);
        add("§d  ", 8);
        add(ChatColor.GOLD + "" + ChatColor.BOLD + "Faction", 7);
        add(clan, 6);
        add("§2  ", 5);
        add(ChatColor.RED + "" + ChatColor.BOLD + "Faction Rank", 4);
        add(clanRanks.getName(), 3);
        add("§c  ", 2);
        add(ChatColor.GOLD + "" + ChatColor.BOLD + "Votes", 1);
        add(Integer.toString(votes), 0);

        build();
    }

    public void setRanks(Ranks ranks) {
        getScoreboard().resetScores(this.ranks.getName());
        this.ranks = ranks;
        getObjective().getScore(ranks.getName()).setScore(13);
    }

    public void setPoints(int points) {
        getScoreboard().resetScores(Integer.toString(this.points));
        this.points = points;
        getObjective().getScore(Integer.toString(points)).setScore(10);
    }

    public void setCredits(int credits) {
        getScoreboard().resetScores(Integer.toString(this.credits));
        this.credits = credits;
        getObjective().getScore(Integer.toString(credits)).setScore(10);
    }

    public void setFaction(String clan) {
        getScoreboard().resetScores(this.clan);
        this.clan = clan;
        add(clan, 7);
        getObjective().getScore(clan).setScore(7);
    }

    public void setClanRanks(ClanRanks clanRanks) {
        getScoreboard().resetScores(this.clanRanks.getName());
        this.clanRanks = clanRanks;
        getObjective().getScore(clanRanks.getName()).setScore(3);
    }

    public void setVotes(int votes) {
        getScoreboard().resetScores(Integer.toString(this.votes));
        this.votes = votes;
        getObjective().getScore(Integer.toString(votes)).setScore(1);
    }
}
