package repo.ruinspvp.factions.structure.shop.am;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import repo.ruinspvp.factions.structure.shop.Shop;
import repo.ruinspvp.factions.structure.shop.ShopManager;
import repo.ruinspvp.factions.structure.shop.am.menus.ArmoryMenu;

public class Armory extends Shop {

    public Armory(ShopManager shopManager) {
        super(shopManager, "Aztec Armory", new ArmoryMenu(shopManager), new Location(Bukkit.getWorld("world"), -1496.893, 44.0, 514.029, 86, 0));
        shopManager.menuManager.addMenu("Aztec Armory", new ArmoryMenu(shopManager));
        shopManager.createShopNPC(EntityType.VILLAGER, getLocation(), this);
        shopManager.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(shopManager.plugin, new Runnable() {
            @Override
            public void run() {
                getLocation().getChunk().load();
            }
        }, 0L, 0L);
    }
}
