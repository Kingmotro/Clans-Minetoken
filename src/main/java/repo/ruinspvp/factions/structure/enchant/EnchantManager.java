package repo.ruinspvp.factions.structure.enchant;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.structure.enchant.enchantments.*;
import repo.ruinspvp.factions.structure.enchant.enchantments.runnables.Repair1Runnable;
import repo.ruinspvp.factions.structure.enchant.enchantments.runnables.Repair2Runnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class EnchantManager implements Listener {

    public JavaPlugin plugin;
    public HashMap<String, Enchantment> enchantments = new HashMap<>();

    public EnchantManager(JavaPlugin plugin) {
        this.plugin = plugin;

        enchantments.put("Repair 1", new Repair1(this));
        enchantments.put("Repair 2", new Repair2(this));
        enchantments.put("Smelt 1", new Smelt1(this));
        enchantments.put("Smelt 2", new Smelt2(this));
        enchantments.put("Smelt 3", new Smelt3(this));

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        for (Enchantment enchantment : enchantments.values()) {
            plugin.getServer().getPluginManager().registerEvents(enchantment, plugin);
        }
        new Repair1Runnable(this);
        new Repair2Runnable(this);
    }

    @EventHandler
    public void onEnchant(EnchantItemEvent event) {
        ItemStack itemStack = event.getItem();
        int levelCost = event.getExpLevelCost();

        for (Enchantment enchantment : enchantments.values()) {
            for (String string : enchantment.getItemType()) {
                if (itemStack.getType().name().endsWith(string)) {
                    if (levelCost >= enchantment.getLevelRequirement()) {
                        if (levelCost > enchantment.getLevelRequirement()) {
                            if (percentChance(enchantment.getChance()) == true) {
                                if (hasEnchant(enchantment, itemStack) == false) {
                                    if(event.getEnchanter().hasPermission(enchantment.getPermission())) {
                                        addEnchantment(enchantment, itemStack);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void addEnchantment(Enchantment enchantment, ItemStack item) {
        ItemMeta im = item.getItemMeta();
        List<String> newLore;
        if (!im.hasLore()) {
            newLore = new ArrayList<>();
        } else {
            newLore = im.getLore();
            for (int i = 0; i < newLore.size(); i++) {
                if (newLore.get(i).contains(ChatColor.GRAY + enchantment.getName())) {
                    String[] lore = newLore.get(i).split(" ");
                    if (enchantment.getLevel() > romanToNumber(lore[1])) {
                        newLore.remove(i);
                    }
                }
            }
        }
        newLore.add(ChatColor.GRAY + enchantment.getName() + " " + enchantment.getRomanNumber(enchantment.getLevel()));
        im.setLore(newLore);
        item.setItemMeta(im);
    }

    public boolean hasEnchant(Enchantment enchantment, ItemStack itemStack) {
        if (!itemStack.getItemMeta().hasLore()) {
            return false;
        }
        for (String s : itemStack.getItemMeta().getLore()) {
            if (s.contains(ChatColor.GRAY + enchantment.getName() + " " + enchantment.getRomanNumber(enchantment.level)))
                return true;
        }
        return false;
    }

    public boolean percentChance(double percent) {
        if (percent > 100 || percent < 0) {
            throw new IllegalArgumentException("Percentage cannot be greater than 100 or less than 0!");
        }
        double result = new Random().nextDouble() * 100;
        return result <= percent;
    }

    public int romanToNumber(String level) {
        if (level.equalsIgnoreCase("I")) return 1;
        if (level.equalsIgnoreCase("II")) return 2;
        if (level.equalsIgnoreCase("III")) return 3;
        if (level.equalsIgnoreCase("IV")) return 4;
        if (level.equalsIgnoreCase("V")) return 5;
        if (level.equalsIgnoreCase("VI")) return 6;
        return 0;
    }

}
