package repo.minetoken.clans.utilities;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UpdateEvent
  extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private UpdateType type;
  
  public UpdateEvent(UpdateType example)
  {
    this.type = example;
  }
  
  public UpdateType getType()
  {
    return this.type;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList()
  {
    return handlers;
  }
}
