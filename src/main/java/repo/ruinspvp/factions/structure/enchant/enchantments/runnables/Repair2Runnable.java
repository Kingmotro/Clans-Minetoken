package repo.ruinspvp.factions.structure.enchant.enchantments.runnables;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import repo.ruinspvp.factions.structure.enchant.EnchantManager;
import repo.ruinspvp.factions.structure.enchant.enchantments.Repair2;

public class Repair2Runnable implements Runnable {

    EnchantManager enchantManager;

    public Repair2Runnable(EnchantManager enchantManager) {
        this.enchantManager = enchantManager;
        enchantManager.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(enchantManager.plugin, this, 0, 40);
    }

    public void run() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            for (ItemStack itemStacks : players.getInventory().getContents()) {

                if (itemStacks == null || itemStacks.getType() == Material.AIR || !itemStacks.hasItemMeta()) continue;

                if (!itemStacks.getItemMeta().hasLore()) continue;

                if (enchantManager.hasEnchant(new Repair2(enchantManager), itemStacks)) {
                    if (itemStacks.getDurability() > 0) {
                        if (enchantManager.percentChance(40.0) == true) {
                            itemStacks.setDurability((short) (itemStacks.getDurability() - 1));
                        }
                    }
                }
            }
        }
    }
}
