package repo.ruinspvp.factions.structure.enchant.enchantments;

import repo.ruinspvp.factions.structure.enchant.EnchantManager;
import repo.ruinspvp.factions.structure.enchant.Enchantment;

public class Repair1 extends Enchantment {

    public Repair1(EnchantManager enchantManager) {
        super(enchantManager, "Repair", 1, enchantManager.plugin, new String[] {"SWORD", "CHESTPLATE", "LEGGINGS", "AXE", "PICKAXE", "BOOTS", "HELMET", "BOW"}, 10.0, 20);
    }

}
