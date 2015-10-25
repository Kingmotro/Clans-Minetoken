package repo.ruinspvp.factions.structure.shop.am.menus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import repo.ruinspvp.factions.structure.inventory.Menu;
import repo.ruinspvp.factions.structure.shop.ShopManager;
import repo.ruinspvp.factions.utilities.ItemStackBuilder;

public class ArmoryMenu extends Menu {

    public ShopManager shopManager;

    public ArmoryMenu(ShopManager shopManager) {
        super("AZTEC ARMORY", 54);
        this.shopManager = shopManager;
        getInventory().addItem(new ItemStackBuilder(Material.IRON_SWORD).withName(ChatColor.GREEN + "Iron Sword").withLore("Forged by the aztecs with the finest materials.").build());
    }

    @Override
    public void leftClick(Player player, ItemStack itemStack) {
        String itemName = getFriendlyName(itemStack);

        if(itemName == null) {
            return;
        }

        switch (itemName) {
            case "Iron Sword":
                shopManager.buy(player, 0, new ItemStack(Material.IRON_SWORD));
            case "":
        }
    }

    @Override
    public void rightClick(Player player, ItemStack itemStack) {
        String itemName = getFriendlyName(itemStack);

        if(itemName == null) {
            return;
        }

        switch (itemName) {
            case "Iron Sword":
                shopManager.sell(player, 10, new ItemStack(Material.IRON_SWORD));
            case "":
        }
    }

    @Override
    public void sleftClick(Player player, ItemStack itemStack) {

    }

    @Override
    public void srightClick(Player player, ItemStack itemStack) {

    }
}
