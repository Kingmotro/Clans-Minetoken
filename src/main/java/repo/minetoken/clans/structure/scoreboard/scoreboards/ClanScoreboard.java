package repo.minetoken.clans.structure.scoreboard.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import repo.minetoken.clans.structure.scoreboard.Scoreboard;
import repo.minetoken.clans.structure.scoreboard.ScoreboardManager;
import repo.minetoken.clans.structure.clan.ClanManager;

import java.util.UUID;

public class ClanScoreboard extends Scoreboard {

    public Player player;
    public ClanManager clanManager;

    public ClanScoreboard(Player player, ClanManager clanManager) {
        super(clanManager.cPlayer.getClan(player.getUniqueId()));
        this.player = player;

        int i = 15;
        for(String uuids : clanManager.cPlayer.getPlayersInAClan(clanManager.cPlayer.getClan(player.getUniqueId()))) {
            UUID uuid = UUID.fromString(uuids);
            try {
                if(Bukkit.getPlayer(uuid).isOnline()) {
                    Player member = Bukkit.getPlayer(uuid);
                    if (member != player) {
                        if (i != 0) {
                            i--;
                            add(member.getName(), i);
                        }
                    }
                }
            } catch (Exception ignore) {}
        }
        build();
    }
}
