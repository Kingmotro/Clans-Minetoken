package repo.minetoken.clans.structure.enchant.enchantments.runnables;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import repo.minetoken.clans.structure.enchant.EnchantManager;
import repo.minetoken.clans.structure.enchant.enchantments.Repair1;

public class Repair1Runnable implements Runnable {

    EnchantManager enchantManager;

    public Repair1Runnable(EnchantManager enchantManager) {
        this.enchantManager = enchantManager;
        enchantManager.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(enchantManager.plugin, this, 0, 20);
    }

    public void run() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            for (ItemStack itemStacks : players.getInventory().getContents()) {

                if (itemStacks == null || itemStacks.getType() == Material.AIR || !itemStacks.hasItemMeta()) continue;

                if (!itemStacks.getItemMeta().hasLore()) continue;

                if (enchantManager.hasEnchant(new Repair1(enchantManager), itemStacks)) {
                    if (itemStacks.getDurability() > 0) {
                        if (enchantManager.percentChance(20.0) == true) {
                            itemStacks.setDurability((short) (itemStacks.getDurability() - 1));
                        }
                    }
                }
            }
        }
    }
}
