package repo.minetoken.clans.structure.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Menu implements InventoryHolder {

    private int size;
    private String title;
    private Inventory inventory;

    public Menu(String title, int size) {
        this.title = title;
        this.size = size;
    }

    public void show(Player player) {
        inventory = Bukkit.createInventory(player, size, ChatColor.translateAlternateColorCodes('&', title));
        playerInventory(player);
        player.openInventory(inventory);
    }

    public abstract void playerInventory(Player player);

    public abstract void leftClick(Player player, ItemStack itemStack);

    public abstract void rightClick(Player player, ItemStack itemStack);

    public abstract void sleftClick(Player player, ItemStack itemStack);

    public abstract void srightClick(Player player, ItemStack itemStack);

    protected String getFriendlyName(ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null || !itemMeta.hasDisplayName()) {
            return null;
        }

        return ChatColor.stripColor(itemMeta.getDisplayName());
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public int getSize() {
        return size;
    }

    public String getTitle() {
        return title;
    }
}