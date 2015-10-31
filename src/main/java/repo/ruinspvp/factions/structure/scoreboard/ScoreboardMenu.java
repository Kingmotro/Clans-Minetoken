package repo.ruinspvp.factions.structure.scoreboard;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import repo.ruinspvp.factions.structure.inventory.Menu;
import repo.ruinspvp.factions.utilities.ItemStackBuilder;

public class ScoreboardMenu extends Menu {

    public ScoreboardMenu() {
        super("Scoreboards", 9);
        getInventory().setItem(2, new ItemStackBuilder(Material.DIAMOND_SWORD).withName(ChatColor.BLUE + "Faction Scoreboard").build());
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
