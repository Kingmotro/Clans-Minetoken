package repo.minetoken.clans.blockFix;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

import repo.minetoken.clans.utilities.UpdateEvent;
import repo.minetoken.clans.utilities.UpdateType;
import repo.minetoken.clans.utilities.utilBlock;

public class blockFix implements Listener
{
  private static HashMap<Block, blockFixData> b = new HashMap<Block, blockFixData>();
  
  public blockFix(JavaPlugin plugin)
  {
	   Bukkit.getPluginManager().registerEvents(this, plugin); 
  }
  
  @EventHandler(priority=EventPriority.LOW)
  public void BlockBreak(BlockBreakEvent event)
  {
    if (Contains(event.getBlock())) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler(priority=EventPriority.LOW)
  public void BlockPlace(BlockPlaceEvent event)
  {
    if (Contains(event.getBlockPlaced())) {
      event.setCancelled(true);
    }
  }
  
  @SuppressWarnings("deprecation")
@EventHandler(priority=EventPriority.LOW)
  public void Piston(BlockPistonExtendEvent event)
  {
    if (event.isCancelled()) {
      return;
    }
    Block push = event.getBlock();
    for (int i = 0; i < 13; i++)
    {
      push = push.getRelative(event.getDirection());
      if (push.getType() == Material.AIR) {
        return;
      }
      if (Contains(push))
      {
        push.getWorld().playEffect(push.getLocation(), Effect.STEP_SOUND, push.getTypeId());
        event.setCancelled(true);
        return;
      }
    }
  }
  
  @EventHandler
  public void ExpireBlocks(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    ArrayList<Block> toRemove = new ArrayList<Block>();
    for (blockFixData cur : blockFix.b.values()) {
      if (cur.expire()) {
        toRemove.add(cur.block);
      }
    }
    for (Block cur : toRemove) {
      blockFix.b.remove(cur);
    }
  }
  
  public void Restore(Block block)
  {
    if (!Contains(block)) {
      return;
    }
    ((blockFixData)blockFix.b.remove(block)).restore();
  }
  
  public static void Add(Block block, int toID, byte toData, long expireTime)
  {
    if (!Contains(block)) {
      GetBlocks().put(block, new blockFixData(block, toID, toData, expireTime, 0L));
    } else {
      GetData(block).update(toID, toData, expireTime);
    }
  }
  
  @SuppressWarnings("deprecation")
public void Snow(Block block, byte heightAdd, byte heightMax, long expireTime, long meltDelay, int heightJumps)
  {
    if (((block.getTypeId() == 78) && (block.getData() >= 7)) || ((block.getTypeId() == 80) && (GetData(block) != null)))
    {
      GetData(block).update(78, heightAdd, expireTime, meltDelay);
      if (heightJumps > 0) {
        Snow(block.getRelative(BlockFace.UP), heightAdd, heightMax, expireTime, meltDelay, heightJumps - 1);
      }
      if (heightJumps == -1) {
        Snow(block.getRelative(BlockFace.UP), heightAdd, heightMax, expireTime, meltDelay, -1);
      }
      return;
    }
    if ((!utilBlock.solid(block.getRelative(BlockFace.DOWN))) && (block.getRelative(BlockFace.DOWN).getTypeId() != 78)) {
      return;
    }
    if ((block.getRelative(BlockFace.DOWN).getTypeId() == 78) && (block.getRelative(BlockFace.DOWN).getData() < 7)) {
      return;
    }
    if (block.getRelative(BlockFace.DOWN).getTypeId() == 79) {
      return;
    }
    if ((block.getRelative(BlockFace.DOWN).getTypeId() == 44) || (block.getRelative(BlockFace.DOWN).getTypeId() == 126)) {
      return;
    }
    if (block.getRelative(BlockFace.DOWN).getType().toString().contains("STAIRS")) {
      return;
    }
    if ((block.getRelative(BlockFace.DOWN).getTypeId() == 85) || 
      (block.getRelative(BlockFace.DOWN).getTypeId() == 139)) {
      return;
    }
    if ((!utilBlock.airFoliage(block)) && (block.getTypeId() != 78) && (block.getType() != Material.CARPET)) {
      return;
    }
    if ((block.getTypeId() == 78) && 
      (block.getData() >= (byte)(heightMax - 1))) {
      heightAdd = 0;
    }
    if (!Contains(block)) {
      GetBlocks().put(block, new blockFixData(block, 78, (byte)Math.max(0, heightAdd - 1), expireTime, meltDelay));
    } else {
      GetData(block).update(78, heightAdd, expireTime, meltDelay);
    }
  }
  
  public static boolean Contains(Block block)
  {
    if (GetBlocks().containsKey(block)) {
      return true;
    }
    return false;
  }
  
  public static blockFixData GetData(Block block)
  {
    if (b.containsKey(block)) {
      return (blockFixData)b.get(block);
    }
    return null;
  }
  
  public static HashMap<Block, blockFixData> GetBlocks()
  {
    return b;
  }
}