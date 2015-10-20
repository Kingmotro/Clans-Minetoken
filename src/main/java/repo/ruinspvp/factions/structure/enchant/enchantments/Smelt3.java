package repo.ruinspvp.factions.structure.enchant.enchantments;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import repo.ruinspvp.factions.structure.enchant.EnchantManager;
import repo.ruinspvp.factions.structure.enchant.Enchantment;

public class Smelt3 extends Enchantment {

    public Smelt3(EnchantManager enchantManager) {
        super(enchantManager, "Smelt", 3, enchantManager.plugin, new String[]{"PICKAXE", "SHOVEL", "AXE"}, 5.0, 25);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getItemInHand();

        if (itemStack == null || itemStack.getType() == Material.AIR || !itemStack.hasItemMeta()) {
            return;
        }

        if (!itemStack.getItemMeta().hasLore()) {
            return;
        }

        if (enchantManager.hasEnchant(this, itemStack) == true) {
            if(enchantManager.percentChance(75.0)) {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), smeltedItem(event.getBlock()));
                event.getBlock().setType(Material.AIR);
            }
        }
    }

    public ItemStack smeltedItem(Block block) {
        Material type = block.getType();
        Byte data = block.getData();
        if (type == Material.IRON_ORE) return new ItemStack(Material.IRON_INGOT, 1);
        if (type == Material.GOLD_ORE) return new ItemStack(Material.GOLD_INGOT, 1);
        if (type == Material.SAND) return new ItemStack(Material.GLASS, 1);
        if (type == Material.COBBLESTONE) return new ItemStack(Material.STONE, 1);
        if (type == Material.CLAY) return new ItemStack(Material.HARD_CLAY, 1);
        if (type == Material.NETHERRACK) return new ItemStack(Material.NETHER_BRICK_ITEM, 1);
        if (type == Material.LOG) return new ItemStack(Material.COAL, 1, (short) 1);
        if (type == Material.CACTUS) return new ItemStack(Material.INK_SACK, 1, (short) 2);
        if (type == Material.SPONGE && data == (byte) 1) return new ItemStack(Material.SPONGE, 1);
        if (type == Material.LEAVES) return new ItemStack(Material.DEAD_BUSH, 1);
        if (type == Material.LEAVES_2) return new ItemStack(Material.DEAD_BUSH, 1);
        if (type == Material.SMOOTH_BRICK && data == (byte) 1) return new ItemStack(Material.SMOOTH_BRICK, 1);
        return new ItemStack(block.getType() , 1 , block.getData());
    }
}
