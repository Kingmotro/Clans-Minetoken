package repo.ruinspvp.factions.structure.factioncenter.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionUnenlistEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private String faction;

    public FactionUnenlistEvent(String faction) {
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
