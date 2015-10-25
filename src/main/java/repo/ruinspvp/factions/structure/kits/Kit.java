package repo.ruinspvp.factions.structure.kits;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface Kit {

    String getName();

    String getPermission();

    ItemStack displayItem();

    Inventory kitPreview();

    void apply(Player player);
}
