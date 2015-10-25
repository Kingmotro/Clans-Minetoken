package repo.ruinspvp.factions.structure.shop;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class ShopEntity {

    private Entity ent;
    private Location location;
    private Shop shop;

    public ShopEntity(Entity ent, Location location, Shop shop) {
        this.ent = ent;
        this.location = location;
        this.shop = shop;
    }

    public Entity getEnt() {
        return ent;
    }

    public Location getLocation() {
        return location;
    }

    public Shop getShop() {
        return shop;
    }
}
