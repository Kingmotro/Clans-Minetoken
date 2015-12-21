package repo.minetoken.clans.utilities;

import java.util.HashSet;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class utilWeapon
{
	private static HashSet<Material> axe = new HashSet<Material>();
	private static HashSet<Material> sword = new HashSet<Material>();
	private static HashSet<Material> staff = new HashSet<Material>();
	private static HashSet<Material> pick = new HashSet<Material>();
	private static HashSet<Material> diamond = new HashSet<Material>();
	private static HashSet<Material> gold = new HashSet<Material>();

	public static boolean hasSword(Player p)
	{
		Material item = p.getItemInHand().getType();
		if (item != null)
		{
			if (item == Material.IRON_SWORD) {
				return true;
			}
			if (item == Material.GOLD_SWORD) {
				return true;
			}
			if (item == Material.DIAMOND_SWORD) {
				return true;
			}

			return false;
		}
		return false;
	}

	public static boolean hasAxe(Player p)
	{
		Material item = p.getItemInHand().getType();
		if (item != null)
		{
			if (item == Material.IRON_AXE) {
				return true;
			}
			if (item == Material.GOLD_AXE) {
				return true;
			}
			if (item == Material.DIAMOND_AXE) {
				return true;
			}

			return false;
		}
		return false;
	}

	public static boolean isAxe(ItemStack item)
	{
		if (item == null) {
			return false;
		}
		if (axe.isEmpty())
		{
			axe.add(Material.WOOD_AXE);
			axe.add(Material.STONE_AXE);
			axe.add(Material.IRON_AXE);
			axe.add(Material.GOLD_AXE);
			axe.add(Material.DIAMOND_AXE);
		}
		return axe.contains(item.getType());
	}

	public static boolean isSword(ItemStack item)
	{
		if (item == null) {
			return false;
		}
		if (sword.isEmpty())
		{
			sword.add(Material.WOOD_SWORD);
			sword.add(Material.STONE_SWORD);
			sword.add(Material.IRON_SWORD);
			sword.add(Material.GOLD_SWORD);
			sword.add(Material.DIAMOND_SWORD);
		}
		return sword.contains(item.getType());
	}

	public static boolean isShovel(ItemStack item)
	{
		if (item == null) {
			return false;
		}
		if (staff.isEmpty())
		{
			staff.add(Material.WOOD_SPADE);
			staff.add(Material.STONE_SPADE);
			staff.add(Material.IRON_SPADE);
			staff.add(Material.GOLD_SPADE);
			staff.add(Material.DIAMOND_SPADE);
		}
		return staff.contains(item.getType());
	}

	public HashSet<Material> scytheSet = new HashSet<Material>();

	public boolean isHoe(ItemStack item)
	{
		if (item == null) {
			return false;
		}
		if (this.scytheSet.isEmpty())
		{
			this.scytheSet.add(Material.WOOD_HOE);
			this.scytheSet.add(Material.STONE_HOE);
			this.scytheSet.add(Material.IRON_HOE);
			this.scytheSet.add(Material.GOLD_HOE);
			this.scytheSet.add(Material.DIAMOND_HOE);
		}
		return this.scytheSet.contains(item.getType());
	}

	public boolean isPickaxe(ItemStack item)
	{
		if (item == null) {
			return false;
		}
		if (pick.isEmpty())
		{
			pick.add(Material.WOOD_PICKAXE);
			pick.add(Material.STONE_PICKAXE);
			pick.add(Material.IRON_PICKAXE);
			pick.add(Material.GOLD_PICKAXE);
			pick.add(Material.DIAMOND_PICKAXE);
		}
		return pick.contains(item.getType());
	}

	public boolean isDiamond(ItemStack item)
	{
		if (item == null) {
			return false;
		}
		if (diamond.isEmpty())
		{
			diamond.add(Material.DIAMOND_SWORD);
			diamond.add(Material.DIAMOND_AXE);
			diamond.add(Material.DIAMOND_SPADE);
			diamond.add(Material.DIAMOND_HOE);
		}
		return diamond.contains(item.getType());
	}

	public static boolean isGold(ItemStack item)
	{
		if (item == null) {
			return false;
		}
		if (gold.isEmpty())
		{
			gold.add(Material.GOLD_SWORD);
			gold.add(Material.GOLD_AXE);
		}
		return gold.contains(item.getType());
	}

	public static boolean isBow(ItemStack item)
	{
		if (item == null) {
			return false;
		}
		return item.getType() == Material.BOW;
	}

	public static boolean isWeapon(ItemStack item)
	{
		return (isAxe(item)) || (isSword(item));
	}

	public static boolean isMat(ItemStack item, Material mat)
	{
		if (item == null) {
			return false;
		}
		return item.getType() == mat;
	}

	public static boolean isRepairable(ItemStack item)
	{
		return item.getType().getMaxDurability() > 0;
	}
}
