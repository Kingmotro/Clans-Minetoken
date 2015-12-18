package repo.minetoken.clans.structure.addons;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import repo.minetoken.clans.Clans;
import repo.minetoken.clans.structure.addons.items.IronDoor;
import repo.minetoken.clans.structure.addons.items.Soup;
import repo.minetoken.clans.structure.clan.ClanManager;

public class AddonManager {

	public JavaPlugin plugin = Clans.getInstance();

	Addon[] addons;

	public AddonManager(ClanManager clanManager) {
		addons = new Addon[]{new Soup(this), new IronDoor(this)};
		register();
	}

	public void register() {
		for (Addon addon : addons) {
			plugin.getServer().getPluginManager().registerEvents(addon, plugin);
			IronDoor.craftDoor();
		}
	}
}
