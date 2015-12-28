package repo.minetoken.clans.structure.combat.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CombatEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;

    public CombatEvent(Player player) {
        this.player = player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player GetPlayer() {
        return player;
    }
}