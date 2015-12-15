package repo.minetoken.clans.structure.shop.am.menus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import repo.minetoken.clans.structure.inventory.Menu;
import repo.minetoken.clans.structure.shop.ShopManager;
import repo.minetoken.clans.utilities.C;
import repo.minetoken.clans.utilities.ItemStackBuilder;

public class ArmoryMenu extends Menu {

    public ShopManager shopManager;

    public ArmoryMenu(ShopManager shopManager) {
        super("AZTEC ARMORY", 9);
        this.shopManager = shopManager;
        getInventory().addItem(new ItemStackBuilder(Material.IRON_SWORD).withName(ChatColor.GREEN + "Iron Sword").withLore(new String[] {"" , C.yellow + "Forged by the Aztecs by the finest material bs."}).build());
    }

    @Override
    public void leftClick(Player player, ItemStack itemStack) {
        String itemName = getFriendlyName(itemStack);

        if (itemName == null) {
            return;
        }

        switch (itemName) {
        }
    }

    @Override
    public void rightClick(Player player, ItemStack itemStack) {
        String itemName = getFriendlyName(itemStack);

        if (itemName == null) {
            return;
        }

        switch (itemName) {
        }
    }

    @Override
    public void sleftClick(Player player, ItemStack itemStack) {

    }

    @Override
    public void srightClick(Player player, ItemStack itemStack) {

    }
}
