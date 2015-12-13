package repo.minetoken.clans.structure.cooldowns;

import org.bukkit.Bukkit;

import repo.minetoken.clans.Clans;

public class HandleCooldowns {
	
	public static void handlecooldowns(){
	Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Clans.instance, new Runnable() { 
		public void run() {
			Cooldown.handleCooldowns(); 
		}
	}, 1L, 1L);
	}
}
