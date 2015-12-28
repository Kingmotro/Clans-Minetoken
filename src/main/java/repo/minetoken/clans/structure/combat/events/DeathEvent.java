package repo.minetoken.clans.structure.combat.events;

import repo.minetoken.clans.structure.combat.Combat;
import repo.minetoken.clans.structure.combat.CombatLog;
import repo.minetoken.clans.structure.combat.DeathMessageType;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDeathEvent;

public class DeathEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private EntityDeathEvent event;
    private Combat clientCombat;
    private CombatLog log;
    private DeathMessageType messageType = DeathMessageType.Detailed;

    public DeathEvent(EntityDeathEvent event, Combat clientCombat, CombatLog log) {
        this.event = event;
        this.clientCombat = clientCombat;
        this.log = log;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Combat GetClientCombat() {
        return clientCombat;
    }

    public CombatLog GetLog() {
        return log;
    }

    public EntityDeathEvent GetEvent() {
        return event;
    }

    public void SetBroadcastType(DeathMessageType value) {
        messageType = value;
    }

    public DeathMessageType GetBroadcastType() {
        return messageType;
    }
}
