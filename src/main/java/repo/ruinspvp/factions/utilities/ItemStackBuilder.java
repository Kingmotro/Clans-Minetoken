package repo.ruinspvp.factions.utilities;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class ItemStackBuilder {

    private final ItemStack itemStack;

    public ItemStackBuilder(Material mat) {
        this.itemStack = new ItemStack(mat);
    }

    public ItemStackBuilder(ItemStack item) {
        this.itemStack = item;
    }

    public ItemStackBuilder withAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemStackBuilder withName(String name) {
        final ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(color(name));
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder withLore(String name) {
        final ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }
        lore.add(color(name));
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder withDurability(int durability) {
        itemStack.setDurability((short) durability);
        return this;
    }

    public ItemStackBuilder withData(int data) {
        itemStack.setData(new MaterialData(itemStack.getType(), (byte) data));
        return this;
    }

    public ItemStackBuilder withEnchantment(Enchantment enchantment, final int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemStackBuilder withEnchantment(Enchantment enchantment) {
        itemStack.addUnsafeEnchantment(enchantment, 1);
        return this;
    }

    public ItemStackBuilder withType(Material material) {
        itemStack.setType(material);
        return this;
    }

    public ItemStackBuilder clearLore() {
        final ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(new ArrayList<String>());
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder clearEnchantments() {
        for (Enchantment enchantment : itemStack.getEnchantments().keySet()) {
            itemStack.removeEnchantment(enchantment);
        }
        return this;
    }

    public ItemStackBuilder withColor(Color color) {
        Material type = itemStack.getType();
        if (type == Material.LEATHER_BOOTS || type == Material.LEATHER_CHESTPLATE || type == Material.LEATHER_HELMET || type == Material.LEATHER_LEGGINGS) {
            LeatherArmorMeta meta = (LeatherArmorMeta) itemStack.getItemMeta();
            meta.setColor(color);
            itemStack.setItemMeta(meta);
            return this;
        } else {
            throw new IllegalArgumentException("withColor is only applicable for leather armor!");
        }
    }

    public ItemStack build() {
        return itemStack;
    }

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
