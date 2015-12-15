package repo.minetoken.clans.structure.addons.items;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.Door;

import repo.minetoken.clans.Clans;
import repo.minetoken.clans.structure.addons.Addon;
import repo.minetoken.clans.structure.addons.AddonManager;
import repo.minetoken.clans.utilities.C;
import repo.minetoken.clans.utilities.Format;

public class IronDoor extends Addon {

	public IronDoor(AddonManager manager) {
		super(manager, "IronDoor");
	}

	@SuppressWarnings("deprecation")
	public static void craftDoor(){
		ItemStack bottle = new ItemStack(Material.IRON_DOOR, 1);
		ShapedRecipe expBottle = new ShapedRecipe(bottle);
		expBottle.shape("** ", "** ", "** ");
		expBottle.shape(" **", " **", " **");
		expBottle.setIngredient('*', Material.WOOD);
		Clans.getInstance().getServer().addRecipe(expBottle);
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onPlayerInteract(PlayerInteractEvent evt)
	{
		if (evt.useInteractedBlock() == Event.Result.ALLOW && 
				(evt.getAction() == Action.RIGHT_CLICK_BLOCK))
		{
			Block block = evt.getClickedBlock();
			if (block.getType() == Material.IRON_DOOR_BLOCK)
			{
				if (block.getData() >= 8) {
					block = block.getRelative(BlockFace.DOWN);
				}
				if (block.getType() == Material.IRON_DOOR_BLOCK)
				{
					if (block.getData() < 4)
					{
						block.setData((byte)(block.getData() + 4));
						block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, 0);
					}
					else
					{
						block.setData((byte)(block.getData() - 4));
						block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, 0);
					}
					evt.setUseItemInHand(Event.Result.DENY);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent evt){
		Block block = evt.getBlock();
		Player pl = evt.getPlayer();
		if(block.getType() == Material.IRON_DOOR || block.getType() == Material.IRON_DOOR_BLOCK){
			pl.sendMessage(Format.main("Clans", "Door placed! " + C.gray + "(Only Clan members can open this door)"));
		}
	}
	
	@EventHandler
	public void onPlace(BlockBreakEvent evt){
		Block block = evt.getBlock();
		Player pl = evt.getPlayer();
		if(block.getType() == Material.IRON_DOOR || block.getType() == Material.IRON_DOOR_BLOCK){
			pl.sendMessage(Format.main("Clans", "Door destroyed!"));
		}
	}
}
