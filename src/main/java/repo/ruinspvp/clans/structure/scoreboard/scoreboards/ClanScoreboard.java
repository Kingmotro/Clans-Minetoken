package repo.ruinspvp.clans.structure.scoreboard.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import repo.ruinspvp.clans.structure.clan.ClanManager;
import repo.ruinspvp.clans.structure.scoreboard.Scoreboard;

import java.util.UUID;

public class ClanScoreboard extends Scoreboard {

    public Player player;
    public ClanManager clanManager;

    public ClanScoreboard(Player player, ClanManager clanManager) {
        super(clanManager.cPlayer.getClan(player.getUniqueId()));
        this.player = player;
        this.clanManager = clanManager;

        int i = 16;
        add(player.getName(), i);
        for(String uuids : clanManager.cPlayer.getPlayersInAFaction(clanManager.cPlayer.getClan(player.getUniqueId()))) {
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
