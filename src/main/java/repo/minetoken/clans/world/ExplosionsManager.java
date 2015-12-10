package repo.minetoken.clans.world;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;


public class ExplosionsManager implements Listener{

	public JavaPlugin plugin;
	private static final Set<Material> brick = new HashSet<Material>();
	static {
		brick.add(Material.SMOOTH_BRICK);
	}
	public ExplosionsManager(JavaPlugin plugin) {
		this.plugin = plugin;
		registerCharacters();
	}

	public void registerCharacters() {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	//TODO - Fix 

	@EventHandler
	public void explode(EntityExplodeEvent e) {
		if (!e.blockList().isEmpty()) {
			final List<BlockState> blocks = new ArrayList<BlockState>();
			for (Block b : e.blockList()) {
				if (b.getType() != Material.AIR) {
			
					if (b.getType() == Material.SMOOTH_BRICK) {
						b.setTypeIdAndData(98, (byte) 2, true);
					
					}
					if (!blocks.contains(b.getState())) {
						blocks.add(b.getState());
						FallingBlock fb = b.getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
						fb.setDropItem(true);
						b.setType(Material.AIR);
					}
				}
			
			}
		}
	}
}


