package repo.ruinspvp.factions.structure.faction.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import repo.ruinspvp.factions.structure.faction.enums.FactionRanks;

public class FactionRankChangeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private FactionRanks ranks;


    public FactionRankChangeEvent(Player player, FactionRanks rank) {
        this.player = player;
        this.ranks = rank;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getPlayerName() {
        return this.player.getName();
    }

    public FactionRanks getRank() {
        return this.ranks;
    }
}
