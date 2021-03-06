package repo.minetoken.clans.structure.character.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import repo.minetoken.clans.structure.inventory.Menu;
import repo.minetoken.clans.utilities.ItemStackBuilder;
import repo.minetoken.clans.utilities.UtilSound;
import repo.minetoken.clans.utilities.UtilSound.Pitch;

public class SkillSelector extends Menu {

    public SkillSelector() {
        super("Select a Character", 45);
    }

    @Override
    public void playerInventory(Player player) {
        getInventory().setItem(18, new ItemStackBuilder(Material.BOOK_AND_QUILL).withName(ChatColor.GREEN + "" + ChatColor.BOLD + "Warrior").build());
        getInventory().setItem(20, new ItemStackBuilder(Material.BOOK_AND_QUILL).withName(ChatColor.GREEN + "" + ChatColor.BOLD + "Wizard").build());
        getInventory().setItem(22, new ItemStackBuilder(Material.BOOK_AND_QUILL).withName(ChatColor.GREEN + "" + ChatColor.BOLD + "Guardian").build());
        getInventory().setItem(24, new ItemStackBuilder(Material.BOOK_AND_QUILL).withName(ChatColor.GREEN + "" + ChatColor.BOLD + "Gatherer").build());
        getInventory().setItem(26, new ItemStackBuilder(Material.BOOK_AND_QUILL).withName(ChatColor.GREEN + "" + ChatColor.BOLD + "Thief").build());

    }

    @Override
    public void leftClick(Player player, ItemStack itemStack) {
        String itemName = getFriendlyName(itemStack);

        if (itemName == null) {
            return;
        }

        switch (itemName) {
            case "Warrior":
                player.closeInventory();
                UtilSound.play(player, Sound.NOTE_PLING, Pitch.VERY_HIGH);
                UtilSound.play(player, Sound.SILVERFISH_KILL, Pitch.VERY_LOW);
                break;
            case "Wizard":
                player.closeInventory();
                UtilSound.play(player, Sound.NOTE_PLING, Pitch.VERY_HIGH);
                UtilSound.play(player, Sound.SILVERFISH_KILL, Pitch.VERY_LOW);
                break;
            case "Guardian":
                player.closeInventory();
                UtilSound.play(player, Sound.NOTE_PLING, Pitch.VERY_HIGH);
                UtilSound.play(player, Sound.SILVERFISH_KILL, Pitch.VERY_LOW);
                break;
            case "Gatherer":
                player.closeInventory();
                UtilSound.play(player, Sound.NOTE_PLING, Pitch.VERY_HIGH);
                UtilSound.play(player, Sound.SILVERFISH_KILL, Pitch.VERY_LOW);
                break;
            case "Thief":
                player.closeInventory();
                UtilSound.play(player, Sound.NOTE_PLING, Pitch.VERY_HIGH);
                UtilSound.play(player, Sound.SILVERFISH_KILL, Pitch.VERY_LOW);
                break;
        }
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
