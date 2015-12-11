package repo.minetoken.clans.structure.enchant.enchantments;

import repo.minetoken.clans.structure.enchant.EnchantManager;
import repo.minetoken.clans.structure.enchant.Enchantment;

public class Repair2 extends Enchantment {

    public Repair2(EnchantManager enchantManager) {
        super(enchantManager, "Repair", 2, enchantManager.plugin,
                new String[] {"SWORD", "CHESTPLATE", "LEGGINGS", "AXE", "PICKAXE", "BOOTS", "HELMET", "BOW"}, 6.0, 25, "ruinspvp.repair2");
    }

}
