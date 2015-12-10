package repo.minetoken.clans.addons;

import repo.minetoken.clans.Clans;
import repo.minetoken.clans.addons.items.Soup;

public class AddonManager{

	public Clans clans;
	public AddonManager(Clans clans) {
		register();
	}
	
	public void register() {
		Clans.instance.getServer().getPluginManager().registerEvents(new Soup(this), Clans.instance);
	}

}
