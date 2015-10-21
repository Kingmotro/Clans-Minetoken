package repo.ruinspvp.factions.structure.faction;

import org.bukkit.entity.Player;

public class FactionInvite {

    public Player player;
    public String faction;

    public FactionInvite(Player player, String faction) {
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
