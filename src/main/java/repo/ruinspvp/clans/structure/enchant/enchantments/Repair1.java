package repo.ruinspvp.clans.structure.enchant.enchantments;

import repo.ruinspvp.clans.structure.enchant.EnchantManager;
import repo.ruinspvp.clans.structure.enchant.Enchantment;

public class Repair1 extends Enchantment {

    public Repair1(EnchantManager enchantManager) {
        super(enchantManager, "Repair", 1, enchantManager.plugin,
                new String[] {"SWORD", "CHESTPLATE", "LEGGINGS", "AXE", "PICKAXE", "BOOTS", "HELMET", "BOW"}, 12.0, 20, "ruinspvp.default");
    }

}
