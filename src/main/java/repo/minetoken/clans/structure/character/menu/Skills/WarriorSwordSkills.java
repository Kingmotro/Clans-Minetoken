package repo.minetoken.clans.structure.character.menu.Skills;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import repo.minetoken.clans.structure.character.Skills;
import repo.minetoken.clans.structure.character.characters.Warrior;
import repo.minetoken.clans.structure.character.characters.check.CheckCharacter;
import repo.minetoken.clans.structure.inventory.Menu;
import repo.minetoken.clans.utilities.C;
import repo.minetoken.clans.utilities.Format;
import repo.minetoken.clans.utilities.ItemStackBuilder;
import repo.minetoken.clans.utilities.UtilSound;
import repo.minetoken.clans.utilities.UtilSound.Pitch;

public class WarriorSwordSkills extends Menu{
	String character;
	public WarriorSwordSkills (String title, int size, Player p) {
		super("Warrior", 45);
        getInventory().setItem(2, new ItemStackBuilder(Material.IRON_AXE).withName(C.green + "" + C.bold + "Axe Skills").build()); 
        getInventory().setItem(4, new ItemStackBuilder(Material.BOW).withName(C.green + "" + C.bold + "Bow Skills").build());
        getInventory().setItem(5, new ItemStackBuilder(Material.EYE_OF_ENDER).withName(C.green + "" + C.bold + "Passive A").build());
        getInventory().setItem(6, new ItemStackBuilder(Material.FERMENTED_SPIDER_EYE).withName(C.green + "" + C.bold + "Passive B").build());
		
		getInventory().setItem(3, new ItemStackBuilder(Material.IRON_SWORD).withName(C.green + "" + C.bold + "Sword Skills").withEnchantment(Enchantment.WATER_WORKER).build());
        
        //swordskills
        if(!Warrior.BladeTrail.containsKey(p.getName())){
         getInventory().setItem(19, new ItemStackBuilder(Material.GLASS_BOTTLE).withName(C.red + "" + C.bold + "Blade Trail").withLore(
				new String[] {"" , C.white + "Description: " , "" ,
						C.white + "",
						C.gray + "Whirl in a target's direction " , C.gray + " slashing foes along your path.",
						"",
						C.aqua + "Free " + C.yellow + "(Temp)",
						""}).build());
        }  if(Warrior.BladeTrail.containsKey(p.getName())){
        	getInventory().setItem(19, new ItemStackBuilder(Material.EXP_BOTTLE).withName(C.green + "" + C.bold + "Blade Trail").withLore(
    				new String[] {"" , C.white + "Description: " , "" ,
    						C.white + "",
    						C.gray + "Whirl in a target's direction " , C.gray + " slashing foes along your path.",
    						"",
    						C.aqua + "Free " + C.yellow + "(Temp)",
    						""}).build());
        }
        if(!Warrior.GolemToss.containsKey(p.getName())){
        getInventory().setItem(20, new ItemStackBuilder(Material.GLASS_BOTTLE).withName(C.red + "" + C.bold + "Golem Toss").withLore(
				new String[] {"" , C.white + "Description: " , "" ,
						C.white + "",
						C.gray + "Toss player.",
						"",
						C.aqua + "Free " + C.yellow + "(Temp)",
						""}).build());
        } if(Warrior.GolemToss.containsKey(p.getName())){
        	 getInventory().setItem(20, new ItemStackBuilder(Material.EXP_BOTTLE).withName(C.green + "" + C.bold + "Golem Toss").withLore(
     				new String[] {"" , C.white + "Description: " , "" ,
     						C.white + "",
     						C.gray + "Toss player.",
     						"",
     						C.aqua + "Free " + C.yellow + "(Temp)",
     						""}).build());
        }
        
	}

	@Override
	public void leftClick(Player player, ItemStack itemStack) {
		int item = itemStack.getTypeId();
		ItemMeta name = itemStack.getItemMeta();
        if(item == 0) {
            return;
        }
       
        
        
      
        
        switch (item) {
        case 374: //GLASS BOTTLe
        	
            
        	if(name.getDisplayName().equals(C.red + "" + C.bold + "Blade Trail")){
        		removeSkills(player);
        		Skills.saveSkill(player, "Sword", "Blade Trail");
        		player.sendMessage(Format.main("Warrior", "Sword Skill set: " + C.lpurple + "Blade Trail"));
        		Warrior.BladeTrail.put(player.getName(), new Warrior());
        		new WarriorSwordSkills("Warrior", 0, player).show(player);
        	}if(name.getDisplayName().equals(C.red + "" + C.bold + "Golem Toss")){
        		removeSkills(player);
        		Skills.saveSkill(player, "Sword", "Golem Toss");
        		player.sendMessage(Format.main("Warrior", "Sword Skill set: " + C.lpurple + "Golem Toss"));
        		Warrior.BladeTrail.remove(player.getName());
        		Warrior.GolemToss.put(player.getName(), new Warrior());
        		new WarriorSwordSkills("Warrior", 0, player).show(player);
        	}
        	UtilSound.play(player, Sound.LEVEL_UP, Pitch.NORMAL);
        	UtilSound.play(player, Sound.NOTE_PLING, Pitch.NORMAL);
        	CheckCharacter.listSkills(player, "Warrior");
        	
        	break;
        case 384:
        	if(name.getDisplayName().equals(C.green + "" + C.bold + "Blade Trail")){
        		Skills.saveSkill(player, "Sword", "None");
        		player.sendMessage(Format.main("Warrior", "You Removed " + C.lpurple + "Blade Trail" + C.yellow + " from Sword Skill!"));
        		Warrior.BladeTrail.remove(player.getName());
        		new WarriorSwordSkills("Warrior", 0, player).show(player);
        	}if(name.getDisplayName().equals(C.green + "" + C.bold + "Golem Toss")){
        		Skills.saveSkill(player, "Sword", "None");
         		player.sendMessage(Format.main("Warrior", "You Removed " + C.lpurple + "Golem Toss" + C.yellow + " from Sword Skill!"));
        		Warrior.GolemToss.remove(player.getName());
        		new WarriorSwordSkills("Warrior", 0, player).show(player);
        		
        	}
        	UtilSound.play(player, Sound.NOTE_PLING, Pitch.NORMAL);
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
	
	public static void removeSkills(Player p){
		if(Warrior.BladeTrail.containsKey(p.getName())){
     		//p.sendMessage(Format.main("Characters", "You Removed " + C.lpurple + "Blade Trail" + C.yellow + " from Sword Skill!"));
			Warrior.BladeTrail.remove(p.getName());
			Skills.saveSkill(p, "Sword", "None");
		}
		if(Warrior.GolemToss.containsKey(p.getName())){
     		//p.sendMessage(Format.main("Characters", "You Removed " + C.lpurple + "Whirlwind" + C.yellow + " from Sword Skill!"));
			Warrior.GolemToss.remove(p.getName());
			Skills.saveSkill(p, "Sword", "None");
		}
	}

}
