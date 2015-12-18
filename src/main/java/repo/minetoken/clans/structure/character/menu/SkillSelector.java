package repo.minetoken.clans.structure.character.menu;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import repo.minetoken.clans.structure.character.menu.Skills.WarriorSkills;
import repo.minetoken.clans.structure.inventory.Menu;
import repo.minetoken.clans.utilities.C;
import repo.minetoken.clans.utilities.ItemStackBuilder;
import repo.minetoken.clans.utilities.UtilSound;
import repo.minetoken.clans.utilities.UtilSound.Pitch;

public class SkillSelector extends Menu{

	public SkillSelector() {
		super("Select a Character", 45);
		getInventory().setItem(18, new ItemStackBuilder(Material.BOOK_AND_QUILL).withName(C.green + "" + C.bold + "Warrior").build());
        getInventory().setItem(20, new ItemStackBuilder(Material.BOOK_AND_QUILL).withName(C.green + "" + C.bold + "Wizard").build()); 
        getInventory().setItem(22, new ItemStackBuilder(Material.BOOK_AND_QUILL).withName(C.green + "" + C.bold + "Guardian").build());
        getInventory().setItem(24, new ItemStackBuilder(Material.BOOK_AND_QUILL).withName(C.green + "" + C.bold + "Gatherer").build());
        getInventory().setItem(26, new ItemStackBuilder(Material.BOOK_AND_QUILL).withName(C.green + "" + C.bold + "Theif").build());
	
	}

	@Override
	public void leftClick(Player player, ItemStack itemStack) {
		String itemName = getFriendlyName(itemStack);

        if(itemName == null) {
            return;
        }

        switch (itemName) {
            case "Warrior":
            	player.closeInventory();
            	new WarriorSkills().show(player);
            	UtilSound.play(player, Sound.NOTE_PLING, Pitch.VERY_HIGH);
            	break;
            case "Wizard":
            	player.closeInventory();
            	player.setPlayerListName(C.white + "[" + C.gold + "Wizard" + C.white + "] " + C.yellow + player.getName());
                break;
            case "Guardian":
            	player.closeInventory();
            	player.setPlayerListName(C.white + "[" + C.gold + "Guardian" + C.white + "] " + C.yellow + player.getName());
                break;
            case "Gatherer":
            	player.closeInventory();
            	player.setPlayerListName(C.white + "[" + C.gold + "Gatherer" + C.white + "] " + C.yellow + player.getName());
                break;
            case "Theif":
            	player.closeInventory();
            	player.setPlayerListName(C.white + "[" + C.gold + "Theif" + C.white + "] " + C.yellow + player.getName());
                break;        
        }
	}

	@Override
	public void rightClick(Player player, ItemStack itemStack) {
		 String itemName = getFriendlyName(itemStack);

	        if(itemName == null) {
	            return;
	        }

	}

	@Override
	public void sleftClick(Player player, ItemStack itemStack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void srightClick(Player player, ItemStack itemStack) {
		// TODO Auto-generated method stub
		
	}

}
