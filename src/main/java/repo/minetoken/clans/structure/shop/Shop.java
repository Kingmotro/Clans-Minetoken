package repo.minetoken.clans.structure.shop;

import org.bukkit.Location;
import repo.minetoken.clans.structure.inventory.Menu;

public class Shop {

    public ShopManager shopManager;
    public String name;
    public Menu menu;
    public Location location;

    public Shop(ShopManager shopManager, String name, Menu menu, Location location) {
        this.shopManager = shopManager;
        this.name = name;
        this.menu = menu;
        this.location = location;
    }

    public ShopManager getShopManager() {
        return shopManager;
    }

    public String getName() {
        return name;
    }

    public Menu getMenu() {
        return menu;
    }

    public Location getLocation() {
        return location;
    }
}
