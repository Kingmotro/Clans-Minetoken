package repo.minetoken.clans.structure.character.menu.Skills;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_8_R3.Enchantment;
import repo.minetoken.clans.structure.inventory.Menu;
import repo.minetoken.clans.utilities.C;
import repo.minetoken.clans.utilities.ItemStackBuilder;

public class DisplaySkills extends Menu{
	String character;
	public DisplaySkills (String title, int size, String character) {
		super("Edit " + character + " Skills", 45);
		getInventory().setItem(20, new ItemStackBuilder(Material.IRON_SWORD).withName(C.green + "" + C.bold + "Sword Skills").build());
        getInventory().setItem(21, new ItemStackBuilder(Material.IRON_AXE).withName(C.green + "" + C.bold + "Axe Skills").build()); 
        getInventory().setItem(22, new ItemStackBuilder(Material.BOW).withName(C.green + "" + C.bold + "Bow Skills").build());
        getInventory().setItem(23, new ItemStackBuilder(Material.EYE_OF_ENDER).withName(C.green + "" + C.bold + "Passive A").build());
        getInventory().setItem(24, new ItemStackBuilder(Material.FERMENTED_SPIDER_EYE).withName(C.green + "" + C.bold + "Passive B").build());
        this.character = character;
        
	}

	@Override
	public void leftClick(Player player, ItemStack itemStack) {
		int item = itemStack.getTypeId();

        if(item == 0) {
            return;
        }

        switch (item) {
        case 267:
        	if(character == "Warrior"){
        		new WarriorSwordSkills("Warrior", 0 , player).show(player);
        		
        	}
        
        	break;
        }
	}

	@Override
	public void rightClick(Player player, ItemStack itemStack) {
		 String itemName = getFriendlyName(itemStack);

	        if(itemName == null) {
	            return;
	        }

	        switch (itemName) {
	           
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
