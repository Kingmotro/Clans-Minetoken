package repo.minetoken.clans.combat.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CCombatEvent
  extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private Player player;
  
  public CCombatEvent(Player player)
  {
    this.player = player;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList()
  {
    return handlers;
  }
  
  public Player GetPlayer()
  {
    return player;
  }
}