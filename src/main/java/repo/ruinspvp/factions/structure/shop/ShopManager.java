package repo.ruinspvp.factions.structure.shop;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.Ruin;
import repo.ruinspvp.factions.structure.economy.EconomyManager;
import repo.ruinspvp.factions.structure.inventory.MenuManager;
import repo.ruinspvp.factions.structure.shop.am.Armory;
import repo.ruinspvp.factions.utilities.EntityUtil;
import repo.ruinspvp.factions.utilities.Format;
import repo.ruinspvp.factions.utilities.HologramUtil;

import java.sql.SQLException;
import java.util.HashMap;

public class ShopManager implements Listener {

    public JavaPlugin plugin;
    public EconomyManager economyManager;
    public Ruin ruin;
    public MenuManager menuManager;

    public HashMap<Entity, ShopEntity> shopEntityMap;

    public ShopManager(JavaPlugin plugin, EconomyManager economyManager, Ruin ruin, MenuManager menuManager) {
        this.plugin = plugin;
        this.economyManager = economyManager;
        this.ruin = ruin;
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
        new HologramUtil(hologramLoc, new String[]{ChatColor.YELLOW + shop.getName()}, plugin);
    }

    public void buy(Player player, int price, ItemStack itemStack) {
        int playerBal = economyManager.fEco.getMoney(player.getUniqueId());

        if (playerBal < price) {
            player.sendMessage(Format.main("Shop", "You don't have enough money for this item."));
            return;
        }

        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(Format.main("Shop", "Your inventory is full, please comeback when you have space."));
            return;
        }

        player.sendMessage(Format.main("Shop", "You have bought " + itemStack.getType().name() + " for " + price + "."));
        economyManager.fEco.removeMoney(player.getUniqueId(), price);
        player.getInventory().addItem(itemStack);
    }

    public void sell(Player player, int price, ItemStack itemStack) {
        for (ItemStack stacks : player.getInventory().getContents()) {
            if (stacks == null || stacks.getType() == Material.AIR || !stacks.hasItemMeta()) continue;

            if (stacks.getType() != itemStack.getType()) {
                player.sendMessage(Format.main("Shop", "Seems like you don't you have this item."));
                return;
            }

            if (stacks.getDurability() != 0) {
                player.sendMessage(Format.main("Shop", "You cannot sell a used item."));
                return;
            }

            if (stacks.getAmount() != itemStack.getAmount()) {
                player.sendMessage(Format.main("Shop", "You don't have the same amount of items required to sell this."));
                return;
            }
            player.getInventory().remove(stacks);
        }

        player.sendMessage(Format.main("Shop", "You have sold " + itemStack.getType().name() + " for " + price + "."));
        economyManager.fEco.addMoney(player.getUniqueId(), price);
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
        if(shopEntityMap.containsKey(event.getEntity())) {
            event.setCancelled(true);
        }
    }
}
