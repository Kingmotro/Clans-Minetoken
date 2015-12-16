package repo.minetoken.clans.structure.character.menu.Skills;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import repo.minetoken.clans.structure.inventory.Menu;
import repo.minetoken.clans.utilities.C;
import repo.minetoken.clans.utilities.ItemStackBuilder;

public class WarriorSkills extends Menu{
	
	public WarriorSkills (String title, int size) {
		super("Warrior", 45);
		getInventory().setItem(0, new ItemStackBuilder(Material.IRON_SWORD).withName(C.green + "" + C.bold + "Sword Skills:").build());
        getInventory().setItem(9, new ItemStackBuilder(Material.IRON_AXE).withName(C.green + "" + C.bold + "Axe Skills:").build()); 
        getInventory().setItem(18, new ItemStackBuilder(Material.BOW).withName(C.green + "" + C.bold + "Bow Skills:").build());
        getInventory().setItem(27, new ItemStackBuilder(Material.EYE_OF_ENDER).withName(C.green + "" + C.bold + "Passive A:").build());
        getInventory().setItem(36, new ItemStackBuilder(Material.FERMENTED_SPIDER_EYE).withName(C.green + "" + C.bold + "Passive B:").build());
        //swordskills
        getInventory().setItem(1, new ItemStackBuilder(Material.GLASS_BOTTLE).withName(C.lpurple + "" + C.bold + "Whirlwind").withLore(
				new String[] {"" , C.white + "Description: " , "" ,
						C.white + "",
						C.gray + "Whirl in a target's direction " , C.gray + " slashing foes along your path.",
						"",
						C.aqua + "Free " + C.yellow + "(Temp)",
						""}).build());
        getInventory().setItem(2, new ItemStackBuilder(Material.GLASS_BOTTLE).withName(C.lpurple + "" + C.bold + "Blade Trail").withLore(
				new String[] {"" , C.white + "Description: " , "" ,
						C.white + "",
						C.gray + "Throw your greatsword at your foe so that it" , C.gray + "returns to you, crippling foes along the way.",
						"",
						C.aqua + "Free " + C.yellow + "(Temp)",
						""}).build());
        
	}

	@Override
	public void leftClick(Player player, ItemStack itemStack) {
		String itemName = getFriendlyName(itemStack);

        if(itemName == null) {
            return;
        }

        switch (itemName) {
           
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
