package repo.minetoken.clans.structure.crate;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class CrateManager {

    public JavaPlugin plugin;

    Location point1 = new Location(Bukkit.getWorld("world"), 0, 0, 0);
    Location point2 = new Location(Bukkit.getWorld("world"), 0, 0, 0);

    public CrateManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    int task;

    public void scrollBlocks() {
        final long[] ticks = {0L};
        task = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            int tick = 0;
            @Override
            public void run() {
                tick++;



                if(tick == 40) {
                    ticks[0] = 20L;
                }
            }
        }, 0L, ticks[0]);
    }

    public void getBlocks() {

    }

    public enum BlockType {
        BLUE(5),
        PURPLE(4),
        PINK(3),
        RED(2),
        YELLOW(1);

        private int width;

        BlockType(int width) {
            this.width = width;
        }
    }

}
