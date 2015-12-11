package repo.minetoken.clans.structure.enchant.enchantments;

import repo.minetoken.clans.structure.enchant.Enchantment;
import repo.minetoken.clans.structure.enchant.EnchantManager;

public class Repair1 extends Enchantment {

    public Repair1(EnchantManager enchantManager) {
        super(enchantManager, "Repair", 1, enchantManager.plugin,
                new String[] {"SWORD", "CHESTPLATE", "LEGGINGS", "AXE", "PICKAXE", "BOOTS", "HELMET", "BOW"}, 12.0, 20, "ruinspvp.default");
    }

}
