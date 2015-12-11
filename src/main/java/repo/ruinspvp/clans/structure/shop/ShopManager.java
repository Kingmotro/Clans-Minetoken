package repo.ruinspvp.clans.structure.shop;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.clans.structure.economy.EconomyManager;
import repo.ruinspvp.clans.structure.inventory.MenuManager;
import repo.ruinspvp.clans.structure.shop.am.Armory;
import repo.ruinspvp.clans.utilities.EntityUtil;
import repo.ruinspvp.clans.utilities.Format;

import java.util.HashMap;

public class ShopManager implements Listener {

    public JavaPlugin plugin;
    public EconomyManager economyManager;
    public MenuManager menuManager;

    public HashMap<Entity, ShopEntity> shopEntityMap;

    public ShopManager(JavaPlugin plugin, EconomyManager economyManager, MenuManager menuManager) {
        this.plugin = plugin;
        this.economyManager = economyManager;
        this.menuManager = menuManager;
        shopEntityMap = new HashMap<>();

        new Armory(this);

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void createShopNPC(EntityType entityType, Location location, Shop shop) {
        location.getChunk().load();
        Entity entity = location.getWorld().spawnEntity(location, entityType);
        LivingEntity livingEntity = (LivingEntity) entity;
        livingEntity.setRemoveWhenFarAway(false);
        livingEntity.setCanPickupItems(false);
        EntityEquipment entityEquipment = livingEntity.getEquipment();
        entityEquipment.setItemInHand(new ItemStack(Material.AIR));
        entityEquipment.setHelmet(new ItemStack(Material.AIR));
        entityEquipment.setChestplate(new ItemStack(Material.AIR));
        entityEquipment.setLeggings(new ItemStack(Material.AIR));
        entityEquipment.setLeggings(new ItemStack(Material.AIR));

        if (entity instanceof Ageable) {
            ((Ageable) entity).setAdult();
            ((Ageable) entity).setAgeLock(true);
        }

        EntityUtil.noAI(entity);
        EntityUtil.silent(entity);

        shopEntityMap.put(entity, new ShopEntity(entity, location, shop));
        Location hologramLoc = new Location(livingEntity.getEyeLocation().getWorld(), livingEntity.getEyeLocation().getX(), livingEntity.getEyeLocation().getY() - .7, livingEntity.getEyeLocation().getZ());
        //new HologramUtil(hologramLoc, new String[]{ChatColor.YELLOW + shop.getName()}, plugin);
    }

    @EventHandler
    public void onShopDamage(EntityDamageByEntityEvent event) {
        Entity ent = event.getEntity();

        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getDamager();

        if (!shopEntityMap.containsKey(ent)) {
            return;
        }

        if (!shopEntityMap.get(ent).getLocation().getBlock().equals(ent.getLocation().getBlock())) {
            return;
        }

        event.setCancelled(true);
        shopEntityMap.get(ent).getShop().getMenu().show(player);
    }


    @EventHandler
    public void onShopInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity ent = event.getRightClicked();

        if (!shopEntityMap.containsKey(ent)) {
            return;
        }

        if (!shopEntityMap.get(ent).getLocation().getBlock().equals(ent.getLocation().getBlock())) {
            return;
        }

        event.setCancelled(true);
        shopEntityMap.get(ent).getShop().getMenu().show(player);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (shopEntityMap.containsKey(event.getEntity())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onUnloadChunk(ChunkUnloadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            if (shopEntityMap.containsKey(entity)) {
                event.setCancelled(true);
                System.out.println(Format.info("Stopped the unloading " + event.getChunk() + ", cause there is a shop entity in that chunk."));
            }
        }
    }
}
