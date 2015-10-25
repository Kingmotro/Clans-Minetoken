package repo.ruinspvp.factions.structure.kits.kits;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import repo.ruinspvp.factions.structure.kits.Kit;

import java.util.ArrayList;
import java.util.List;

public class DefaultKit implements Kit {

    @Override
    public String getName() {
        return "Starter";
    }

    @Override
    public String getPermission() {
        return "ruinspvp.default";
    }

    @Override
    public ItemStack displayItem() {
        ItemStack itemStack = new ItemStack(Material.BOW);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + getName() + " Kit");
        List<String> lore = new ArrayList<>();
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public Inventory kitPreview() {
        Inventory inventory = Bukkit.createInventory(null, 9, getName() + " Kit Preview");

        inventory.addItem(new ItemStack(Material.IRON_SWORD));
        inventory.addItem(new ItemStack(Material.COOKED_BEEF, 16));
        inventory.addItem(new ItemStack(Material.IRON_HELMET));
        inventory.addItem(new ItemStack(Material.IRON_CHESTPLATE));
        inventory.addItem(new ItemStack(Material.IRON_LEGGINGS));
        inventory.addItem(new ItemStack(Material.IRON_BOOTS));

        return inventory;
    }

    @Override
    public void apply(Player player) {
        player.getInventory().clear();
        player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
        player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 16));
        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
    }
}