package repo.minetoken.clans.structure.world;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import repo.minetoken.clans.Clans;
import repo.minetoken.clans.cooldowns.Cooldown;


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
		e.setCancelled(true);
		if (!e.blockList().isEmpty()) {
			final List<BlockState> blocks = new ArrayList<BlockState>();
			for (final Block b : e.blockList()) {
			
					if (b.getType() == Material.SMOOTH_BRICK && b.getData() != 5) {
						blocks.add(b.getState());
						b.setTypeIdAndData(98, (byte) 2, true);
						Cooldown.add("block", "PROTECTED", 10, 10); 
					}
					if (b.getType() != Material.AIR || b.getType() != Material.SMOOTH_BRICK || b.getData() == 2){
				
						Clans.instance.getServer().getScheduler().scheduleSyncDelayedTask(Clans.instance, new Runnable() {
							public void run() {
								if (!blocks.contains(b.getState())) {  
									if(Cooldown.isCooling("block", "PROTECTED")) { 
										b.setTypeIdAndData(98, (byte) 2, true);
									}
									blocks.add(b.getState());
									b.setType(Material.AIR);
								}
							}
						}, 20);
						//b.setType(Material.AIR);
					}
				}
			
			
		}
	}
}


