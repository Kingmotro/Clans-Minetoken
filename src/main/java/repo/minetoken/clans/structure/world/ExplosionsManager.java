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
import repo.minetoken.clans.structure.cooldowns.Cooldown;


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
	
		
	}
}


