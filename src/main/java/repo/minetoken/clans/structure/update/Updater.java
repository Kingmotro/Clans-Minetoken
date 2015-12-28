package repo.minetoken.clans.structure.update;

import org.bukkit.Bukkit;

import repo.minetoken.clans.Clans;

public class Updater
  implements Runnable
{
  private Clans plugin;
  
  public Updater(Clans plugin)
  {
    this.plugin = plugin;
    this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this, 0L, 1L);
  }
  
  public void run()
  {
    UpdateType[] arrayOfUpdateType;
    int j = (arrayOfUpdateType = UpdateType.values()).length;
    for (int i = 0; i < j; i++)
    {
      UpdateType updateType = arrayOfUpdateType[i];
      if (updateType.Elapsed()) {
    	  Bukkit.getServer().getPluginManager().callEvent(new UpdateEvent(updateType));
      }
    }
  }
}