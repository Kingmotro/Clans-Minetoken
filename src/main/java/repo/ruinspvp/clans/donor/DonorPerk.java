package repo.ruinspvp.clans.donor;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.clans.structure.rank.enums.Ranks;

public abstract class DonorPerk implements Listener {

    public JavaPlugin plugin;
    public String name;
    public String permssion;
    public Ranks rank;

    public DonorPerk(JavaPlugin plugin, String name, String permssion, Ranks rank) {
        this.plugin = plugin;
        this.name = name;
        this.permssion = permssion;
        this.rank = rank;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public String getName() {
        return name;
    }

    public String getPermssion() {
        return permssion;
    }

    public Ranks getRank() {
        return rank;
    }
}
