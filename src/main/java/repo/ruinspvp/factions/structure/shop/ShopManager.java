package repo.ruinspvp.factions.structure.shop;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.Ruin;
import repo.ruinspvp.factions.structure.economy.EconomyManager;
import repo.ruinspvp.factions.structure.npc.NPCManager;

public class ShopManager implements Listener {

    public JavaPlugin plugin;

    public EconomyManager economyManager;

    public NPCManager npcManager;

    public Ruin ruin;

    public ShopManager(JavaPlugin plugin, EconomyManager economyManager, NPCManager npcManager, Ruin ruin) {
        this.plugin = plugin;
        this.economyManager = economyManager;
        this.npcManager = npcManager;
        this.ruin = ruin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

}
