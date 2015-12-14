package repo.minetoken.clans.structure.scoreboard.scoreboards;

import org.bukkit.entity.Player;
import repo.minetoken.clans.structure.scoreboard.Scoreboard;
import repo.minetoken.clans.structure.scoreboard.ScoreboardManager;
import repo.minetoken.clans.utilities.C;
import repo.minetoken.clans.structure.clan.ClanManager;
import repo.minetoken.clans.structure.clan.enums.ClanRanks;
import repo.minetoken.clans.structure.rank.enums.Result;

import java.util.UUID;

public class ClanScoreboard extends Scoreboard {
	
    public Player player;
    public ClanManager clanManager;
    public int points, credits;
    public String clan;
    public ClanRanks clanRanks; 
    
    public ClanScoreboard(Player player, ClanManager clanManager, ScoreboardManager scoreboardManager) {
        super(clanManager.cPlayer.getClan(player.getUniqueId()));
        this.player = player;
        this.clanManager = clanManager;
        
        if(clanManager.cPlayer.hasClan(player.getUniqueId()) == Result.FALSE) {
            this.clan = C.red + "NONE " + C.gray +"(/clan create)";
            this.clanRanks = ClanRanks.NONE;
        } else {
            this.clan = clanManager.cPlayer.getClan(player.getUniqueId());
            this.clanRanks = clanManager.cPlayer.getCRank(player.getUniqueId());
        } 
        int i = 15;
        for(String uuids : clanManager.cPlayer.getPlayersInAClan(clanManager.cPlayer.getClan(player.getUniqueId()))) {
            UUID uuid = UUID.fromString(uuids);
            try {
              
                add(C.yellow + "Points " + C.reset + "0", 15);
                add(C.yellow + "Credits " + C.reset + "0", 14);
                add(C.yellow + "Online " + C.reset + "0", 13);
                add(C.reset + "  ", 12);
                add(C.lpurple + clan, 11);
            } catch (Exception ignore) {}
        }
        build();
    } 
    /*
     * if(Bukkit.getPlayer(uuid).isOnline()) {
                    Player member = Bukkit.getPlayer(uuid);
                    if (member != player) {
                        if (i != 0) {
                            i--;
                            add(member.getName(), i);
                        }
                    }
                }
     */
}
