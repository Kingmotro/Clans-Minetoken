package repo.minetoken.clans.structure.addons;

import org.bukkit.plugin.java.JavaPlugin;
import repo.minetoken.clans.Clans;
import repo.minetoken.clans.structure.addons.items.Soup;

public class AddonManager{

	public JavaPlugin plugin = Clans.getInstance();

	Addon[] addons;

	public AddonManager() {
		addons = new Addon[] {new Soup()};
		register();
	}
	
	public void register() {
		for(Addon addon : addons) {
			plugin.getServer().getPluginManager().registerEvents(addon, plugin);
		}
	}

}
