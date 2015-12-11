package repo.minetoken.clans.structure.shop.am;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import repo.minetoken.clans.structure.shop.Shop;
import repo.minetoken.clans.structure.shop.am.menus.ArmoryMenu;
import repo.minetoken.clans.structure.shop.ShopManager;

public class Armory extends Shop {

    public Armory(ShopManager shopManager) {
        super(shopManager, "Aztec Armory", new ArmoryMenu(shopManager), new Location(Bukkit.getWorld("world"), -1496.893, 44.0, 514.029, 86, 0));
        shopManager.menuManager.addMenu("Aztec Armory", new ArmoryMenu(shopManager));
        shopManager.createShopNPC(EntityType.VILLAGER, getLocation(), this);
    }
}
