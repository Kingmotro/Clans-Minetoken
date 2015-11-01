package repo.ruinspvp.factions.structure.scoreboard.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import repo.ruinspvp.factions.structure.faction.FactionManager;
import repo.ruinspvp.factions.structure.scoreboard.Scoreboard;

import java.util.UUID;

public class FactionScoreboard extends Scoreboard {

    public Player player;
    public FactionManager factionManager;

    public FactionScoreboard(Player player, FactionManager factionManager) {
        super(factionManager.fPlayer.getFaction(player.getUniqueId()));
        this.player = player;
        this.factionManager = factionManager;

        int i = 16;
        add(player.getName(), i);
        for(String uuids : factionManager.fPlayer.getPlayersInAFaction(factionManager.fPlayer.getFaction(player.getUniqueId()))) {
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
