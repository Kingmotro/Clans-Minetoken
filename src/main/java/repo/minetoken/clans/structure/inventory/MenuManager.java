package repo.minetoken.clans.structure.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MenuManager implements Listener {

    private Map<Object, Menu> menus = new HashMap<>();
    public MenuManager(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin); 
    }

    public void addMenu(Object key, Menu menu) {
        menus.put(key, menu);
    }

    public Collection<Menu> getMenus() {
        return menus.values();
    }

    public Menu getMenu(Object key) {
        return menus.get(key);
    }

    @EventHandler
    public void onClick(InventoryDragEvent e) {
        Inventory inv = e.getWhoClicked().getOpenInventory().getTopInventory();
        for (Menu menu : menus.values()) {
            if (menu.getInventory().getName().equals(inv.getName())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Inventory inv = player.getOpenInventory().getTopInventory();
        for (Menu menu : menus.values()) {
            if (menu.getTitle().equals(inv.getName())) {
                if(e.getCurrentItem().getType() == Material.AIR || e.getCursor() == null || e.getCurrentItem() == null) {
                    return;
                }
                e.setCancelled(true);
                if (e.isLeftClick()) {
                    if (e.isShiftClick()) {
                        menu.sleftClick(player, e.getCurrentItem());
                    } else {
                        menu.leftClick(player, e.getCurrentItem());
                    }
                }
                if (e.isRightClick()) {
                    if (e.isShiftClick()) {
                        menu.srightClick(player, e.getCurrentItem());
                    } else {
                        menu.rightClick(player, e.getCurrentItem());
                    }
                }
            }
        }
    }

}