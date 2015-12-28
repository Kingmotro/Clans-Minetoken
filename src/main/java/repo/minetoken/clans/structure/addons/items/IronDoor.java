package repo.minetoken.clans.structure.addons.items;

import org.bukkit.ChatColor;
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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import repo.minetoken.clans.Clans;
import repo.minetoken.clans.structure.addons.Addon;
import repo.minetoken.clans.structure.addons.AddonManager;
import repo.minetoken.clans.structure.clan.ClanManager;
import repo.minetoken.clans.utilities.Format;

import java.util.List;

public class IronDoor extends Addon {

    public IronDoor(AddonManager manager, ClanManager clanManager) {
        super(manager, "IronDoor", clanManager);
    }

    @SuppressWarnings("deprecation")
    public static void craftDoor() {
        ItemStack bottle = new ItemStack(Material.IRON_DOOR, 1);
        ShapedRecipe expBottle = new ShapedRecipe(bottle);
        expBottle.shape("** ", "** ", "** ");
        expBottle.shape(" **", " **", " **");
        expBottle.setIngredient('*', Material.WOOD);
        Clans.getInstance().getServer().addRecipe(expBottle);
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent evt) {
        if (evt.useInteractedBlock() == Event.Result.ALLOW &&
                (evt.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            Block block = evt.getClickedBlock();
            if (block.getType() == Material.IRON_DOOR_BLOCK) {
                if (block.getData() >= 8) {
                    block = block.getRelative(BlockFace.DOWN);
                }
                if (block.getType() == Material.IRON_DOOR_BLOCK) {
                    //TODO: REPLACE "TEST" FOR CLAN NAME
                    //if (isPlayersInSameClan("TEST", evt.getPlayer())) {
                        if (block.getData() < 4) {
                            block.setData((byte) (block.getData() + 4));
                            block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, 0);
                        } else {
                            block.setData((byte) (block.getData() - 4));
                            block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, 0);
                        }
                        evt.setUseItemInHand(Event.Result.DENY);
                    } else {
                        evt.getPlayer().sendMessage(Format.main("Clans", "You can't use this door!"));
                    }
                //}
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent evt) {
        Block block = evt.getBlock();
        Player pl = evt.getPlayer();
        if (block.getType() == Material.IRON_DOOR || block.getType() == Material.IRON_DOOR_BLOCK) {
            pl.sendMessage(Format.main("Clans", "Door placed! " + ChatColor.GRAY + "(Only Clan members can open this door)"));
        }
    }

    @EventHandler
    public void onPlace(BlockBreakEvent evt) {
        Block block = evt.getBlock();
        Player pl = evt.getPlayer();
        if (block.getType() == Material.IRON_DOOR || block.getType() == Material.IRON_DOOR_BLOCK) {
            pl.sendMessage(Format.main("Clans", "Door destroyed!"));
        }
    }

    public boolean isPlayersInSameClan(String clan, Player player) {
        List<String> uuids = getClanManager().cPlayer.getPlayersInAClan(clan);

        for(int i = 0; i < uuids.size(); i++) {
            if(uuids.get(i).equalsIgnoreCase(player.getUniqueId().toString())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
