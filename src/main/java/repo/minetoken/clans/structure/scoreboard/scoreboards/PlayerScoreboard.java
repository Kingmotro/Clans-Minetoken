package repo.minetoken.clans.structure.scoreboard.scoreboards;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import repo.minetoken.clans.structure.economy.EconomyManager;
import repo.minetoken.clans.structure.clan.ClanManager;
import repo.minetoken.clans.structure.clan.enums.ClanRanks;
import repo.minetoken.clans.structure.rank.RankManager;
import repo.minetoken.clans.structure.rank.enums.Ranks;
import repo.minetoken.clans.structure.rank.enums.Result;
import repo.minetoken.clans.structure.scoreboard.Scoreboard;
import repo.minetoken.clans.structure.scoreboard.ScoreboardManager;
import repo.minetoken.clans.structure.voting.VoteManager;

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

        add(ChatColor.GREEN + "" + ChatColor.BOLD + "Rank", 15);
        add(ranks.getName(), 14);
        add("§r  ", 13);
        add(ChatColor.AQUA + "" + ChatColor.BOLD + "Points", 12);
        add(Integer.toString(points), 11);
        add("§4  ", 10);
        add(ChatColor.BLUE + "" + ChatColor.BOLD + "Credits", 9);
        add(Integer.toString(points), 8);
        add("§d  ", 7);
        add(ChatColor.GOLD + "" + ChatColor.BOLD + "Clan", 6);
        add(clan, 5);
        add("§2  ", 4);
        add(ChatColor.RED + "" + ChatColor.BOLD + "Clan Rank", 3);
        add(clanRanks.getName(), 2);
        add("----------------", 1);

        build();
    }

    public void setRanks(Ranks ranks) {
        getScoreboard().resetScores(this.ranks.getName());
        this.ranks = ranks;
        getObjective().getScore(ranks.getName()).setScore(14);
    }

    public void setPoints(int points) {
        getScoreboard().resetScores(Integer.toString(this.points));
        this.points = points;
        getObjective().getScore(Integer.toString(points)).setScore(11);
    }

    public void setCredits(int credits) {
        getScoreboard().resetScores(Integer.toString(this.credits));
        this.credits = credits;
        getObjective().getScore(Integer.toString(credits)).setScore(8);
    }

    public void setClan(String clan) {
        getScoreboard().resetScores(this.clan);
        this.clan = clan;
        add(clan, 7);
        getObjective().getScore(clan).setScore(5);
    }

    public void setClanRanks(ClanRanks clanRanks) {
        getScoreboard().resetScores(this.clanRanks.getName());
        this.clanRanks = clanRanks;
        getObjective().getScore(clanRanks.getName()).setScore(2);
    }
}
