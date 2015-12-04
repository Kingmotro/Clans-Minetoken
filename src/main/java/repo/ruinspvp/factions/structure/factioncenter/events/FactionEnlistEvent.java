package repo.ruinspvp.factions.structure.factioncenter.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FactionEnlistEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private String faction;


    public FactionEnlistEvent(String faction) {
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
