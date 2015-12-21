package repo.minetoken.clans.combat.events;

import repo.minetoken.clans.combat.CCombat;
import repo.minetoken.clans.combat.CombatLog;
import repo.minetoken.clans.combat.DeathMessageType;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDeathEvent;

public class CDeathEvent
  extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private EntityDeathEvent event;
  private CCombat clientCombat;
  private CombatLog log;
  private DeathMessageType messageType = DeathMessageType.Detailed;
  
  public CDeathEvent(EntityDeathEvent event, CCombat clientCombat, CombatLog log)
  {
    this.event = event;
    this.clientCombat = clientCombat;
    this.log = log;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList()
  {
    return handlers;
  }
  
  public CCombat GetClientCombat()
  {
    return clientCombat;
  }
  
  public CombatLog GetLog()
  {
    return log;
  }
  
  public EntityDeathEvent GetEvent()
  {
    return event;
  }
  
  public void SetBroadcastType(DeathMessageType value)
  {
    messageType = value;
  }
  
  public DeathMessageType GetBroadcastType()
  {
    return messageType;
  }
}
