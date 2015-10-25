package repo.ruinspvp.factions.structure.factioncenter;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import repo.ruinspvp.factions.structure.inventory.Menu;
import repo.ruinspvp.factions.utilities.ItemStackBuilder;

public class FactionCenterMenu extends Menu {

    public FactionCenterManager factionCenterManager;

    public FactionCenterMenu(FactionCenterManager factionCenterManager) {
        super("Wise One", 54);
        this.factionCenterManager = factionCenterManager;

        getInventory().setItem(0, new ItemStackBuilder(Material.WOOL).withData(14).withName(ChatColor.RED + "Remove Faction Enlistment").build());
        getInventory().setItem(8, new ItemStackBuilder(Material.WOOL).withData(13).withName(ChatColor.GREEN + "Enlist Faction").build());
    }

    @Override
    public void leftClick(Player player, ItemStack itemStack) {

    }

    @Override
    public void rightClick(Player player, ItemStack itemStack) {

    }

    @Override
    public void sleftClick(Player player, ItemStack itemStack) {

    }

    @Override
    public void srightClick(Player player, ItemStack itemStack) {

    }
}
