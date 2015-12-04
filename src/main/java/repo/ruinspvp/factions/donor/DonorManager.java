package repo.ruinspvp.factions.donor;

import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.donor.perks.ChatPerk;
import repo.ruinspvp.factions.donor.perks.JoinPerk;

import java.util.HashMap;

public class DonorManager {

    public JavaPlugin plugin;

    public HashMap<String, DonorPerk> perks = new HashMap<>();

    public DonorManager(JavaPlugin plugin) {
        this.plugin = plugin;

        perks.put("Chat", new ChatPerk(plugin));
        perks.put("Join", new JoinPerk(plugin));

        for(DonorPerk perk : perks.values()) {
            plugin.getServer().getPluginManager().registerEvents(perk, plugin);
        }
    }
}
