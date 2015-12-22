package repo.minetoken.clans.structure.character.menu;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import repo.minetoken.clans.structure.inventory.Menu;
import repo.minetoken.clans.utilities.C;
import repo.minetoken.clans.utilities.ItemStackBuilder;

public class ShopSelector extends Menu{

	String sell = C.yellow + "Right-Click to " + C.bold + C.underline +"SELL";
	String buy = C.yellow + "Left-Click to " + C.bold + C.underline +"BUY";

	public ShopSelector() {
		super("Shop", 45);
		getInventory().setItem(4, new ItemStackBuilder(Material.EMERALD).withName(C.green + "" + C.bold + "Points " + C.yellow + "0").build());
		//Soup
		getInventory().setItem(10, new ItemStackBuilder(Material.MUSHROOM_SOUP).withName(C.green + "" + C.bold + "Soup").withLore(
				new String[] {"" , C.white + "Description: " , "" ,
						C.white + "- Regeneration " + C.lpurple + "6 Seconds...",
						C.white + "- Hunger " + C.lpurple +"+3",
						C.white + "- Cooldown " + C.lpurple + "4 Seconds...",
						C.white + "",
						C.gray + "From a land of soups, where" , C.gray + " all soup is life saving...",
						"",
						C.aqua + "100p " + C.yellow + "or " + C.aqua +"10c",
						"",
						sell,
						buy}).build());
		//WEB
		getInventory().setItem(11, new ItemStackBuilder(Material.WEB).withName(C.green + "" + C.bold + "Web").withLore(
				new String[] {"" , C.white + "Description: " , "" ,
						C.white + "- Slowness " + C.lpurple + "3 Seconds...",
						C.white + "- Cooldown " + C.red + "NONE",
						C.white + "",
						C.gray + "Slow down players with the ability to " , C.gray + "throw these anywhere you want.",
						"",
						C.aqua + "150p " + C.yellow + "or " + C.aqua +"15c",
						"",
						sell,
						buy}).build()); 
		//SkillSelector
		getInventory().setItem(12, new ItemStackBuilder(Material.ENCHANTMENT_TABLE).withName(C.green + "" + C.bold + "Skill Selector").withLore(
				new String[] {"" , C.white + "Description: " , "" ,
						C.white + "- Ability " + C.red + "NONE",
						C.white + "- Cooldown " + C.red + "NONE",
						C.white + "",
						C.gray + "Placing these and right clicking them will" , C.gray + "allow you to select your skill from" , C.gray + "anywhere you want." ,
						"",
						C.aqua + "300p " + C.yellow + "or " + C.aqua +"30c",
						"",
						sell,
						buy}).build());
		//GoldenApple
		getInventory().setItem(13, new ItemStackBuilder(Material.GOLDEN_APPLE).withName(C.green + "" + C.bold + "Golden Apple").withLore(
				new String[] {"" , C.white + "Description: " , "" ,
						C.white + "- Regeneration " + C.red + "15 Seconds...",
						C.white + "- Speed " + C.red + "5 Seconds...",
						C.white + "- Cooldown " + C.lpurple + "15 Seconds...",
						C.white + "",
						C.gray + "Golden Apple's can only be found here or " , C.gray + "from breaking leaves from a tree.",
						"",
						C.aqua + "170p " + C.yellow + "or " + C.aqua +"17c",
						"",
						sell,
						buy}).build());
		//Throwing Axe
		getInventory().setItem(14, new ItemStackBuilder(Material.STONE_AXE).withName(C.green + "" + C.bold + "Throwing Axe").withLore(
				new String[] {"" , C.white + "Description: " , "" ,
						C.white + "- Damage " + C.lpurple + "6.5HP",
						C.white + "- Cooldown " + C.red + "NONE",
						C.white + "",
						C.gray + "You can throw this axe and as long as you " , C.gray + "pick it up, you won't loose it.",
						"",
						C.aqua + "2000p " + C.yellow + "or " + C.aqua +"200c",
						"",
						sell,
						buy}).build());
		//TNT
		getInventory().setItem(15, new ItemStackBuilder(Material.TNT).withName(C.green + "" + C.bold + "Rare TNT").withLore(
				new String[] {"" , C.white + "Description: " , "" ,
						C.white + "- Explosion Size " + C.lpurple + "5x5R",
						C.white + "- Cooldown " + C.red + "NONE",
						C.white + "",
						C.gray + "TNT cannot be crafted, so the only way to get it" , C.gray + "is from buying it here.",
						"",
						C.aqua +"5000p " + C.yellow + "or " + C.aqua +"500c",
						"",
						sell,
						buy}).build());
		//STONEBRICK
		getInventory().setItem(16, new ItemStackBuilder(Material.SMOOTH_BRICK).withName(C.green + "" + C.bold + "Stonebrick").withLore(
				new String[] {"" , C.white + "Description: " , "" ,
						C.white + "- Durability " + C.red + "2",
						C.white + "- Cooldown " + C.red + "NONE",
						C.white + "",
						C.gray + "Stonebrick is the only block that protects from" , C.gray + "explosions until it's durability wears out.",
						"",
						C.aqua + "100p " + C.yellow + "or " + C.aqua +"10c",
						"",
						sell,
						buy}).build());
		//IronDoor
		getInventory().setItem(19, new ItemStackBuilder(Material.IRON_DOOR).withName(C.green + "" + C.bold + "Door").withLore(
				new String[] {"" , C.white + "Description: " , "" ,
						C.white + "- Durability " + C.red + "4",
						C.white + "- Cooldown " + C.red + "NONE",
						C.white + "",
						C.gray + "Protects living environment from explosions & " , C.gray + " other Clans.",
						"",
						C.aqua + "10p " + C.yellow + "or " + C.aqua +"1c",
						"",
						sell,
						buy}).build());
		//Lightning
		getInventory().setItem(20, new ItemStackBuilder(Material.DIAMOND_BLOCK).withName(C.green + "" + C.bold + "Lightning Shock").withLore(
				new String[] {"" , C.white + "Description: " , "" ,
						C.white + "- Damage " + C.lpurple + "3.0HP",
						C.white + "- Cooldown " + C.lpurple + "15 Seconds",
						C.white + "",
						C.gray + "Spawns lightning where ever it's thrown.",
						"",
						C.aqua + "10p " + C.yellow + "or " + C.aqua +"1c",
						"",
						sell,
						buy}).build());




	}

	@Override
	public void leftClick(Player player, ItemStack itemStack) {
		String itemName = getFriendlyName(itemStack);

		if(itemName == null) {
			return;
		}

		switch (itemName) {
		case "Soup":
			player.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
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
		case "Faction Scoreboard":
		case "Player Scoreboard":
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
