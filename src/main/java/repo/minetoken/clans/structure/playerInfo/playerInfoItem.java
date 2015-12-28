package repo.minetoken.clans.structure.playerInfo;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import repo.minetoken.clans.utilities.UtilSound;
import repo.minetoken.clans.utilities.UtilSound.Pitch;

public class PlayerInfoItem implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Action a = event.getAction();
        ItemStack is = event.getItem();

        if (a == Action.PHYSICAL || is == null || is.getType() == Material.AIR)
            return;

        if (is.getType() == Material.ENCHANTED_BOOK) {
            event.setCancelled(true);

            new PlayerInfoMenu().show(event.getPlayer());
            UtilSound.play(event.getPlayer(), Sound.NOTE_BASS, Pitch.VERY_HIGH);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + event.getPlayer().getName() + "'s Profile" + ChatColor.GRAY + " - " + ChatColor.GREEN + "(Right Click)");
        item.setItemMeta(meta);
        event.getPlayer().getInventory().setItem(8, item);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Item is = event.getItemDrop();

        if (is.getItemStack().getType() == Material.ENCHANTED_BOOK) {
            event.setCancelled(true);
        }
    }
}