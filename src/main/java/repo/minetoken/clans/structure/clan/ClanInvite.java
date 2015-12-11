package repo.minetoken.clans.structure.clan;

import org.bukkit.entity.Player;

public class ClanInvite {

    public Player player;
    public String faction;

    public ClanInvite(Player player, String faction) {
        this.player = player;
        this.faction = faction;
    }

    public Player getPlayer() {
        return player;
    }

    public String getFaction() {
        return faction;
    }
}
