package repo.ruinspvp.factions.structure.faction.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import repo.ruinspvp.factions.structure.faction.enums.FactionRanks;

public class FactionDeleteEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private String faction;

    public FactionDeleteEvent(String faction) {
        this.faction = faction;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public String getFaction() {
        return this.faction;
    }
}