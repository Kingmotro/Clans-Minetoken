package repo.ruinspvp.factions;

import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.structure.console.ConsoleManager;
import repo.ruinspvp.factions.structure.economy.EconomyManager;
import repo.ruinspvp.factions.structure.enchant.EnchantManager;
import repo.ruinspvp.factions.structure.faction.FactionManager;
import repo.ruinspvp.factions.structure.factioncenter.FactionCenterManager;
import repo.ruinspvp.factions.structure.inventory.MenuManager;
import repo.ruinspvp.factions.structure.npc.NPCManager;
import repo.ruinspvp.factions.structure.rank.RankManager;
import repo.ruinspvp.factions.structure.shop.ShopManager;
import repo.ruinspvp.factions.structure.voting.VoteManager;

public class Factions extends JavaPlugin {

    public static JavaPlugin instance;

    private static MenuManager menuManager;

    public Ruin ruin;

    @Override
    public void onEnable() {
        instance = this;

        ruin = Ruin.HUB;

        getCommand("test").setExecutor(new TestCommand(this));

        FactionManager factionManager = new FactionManager(this, ruin);
        RankManager rankManager = new RankManager(this, factionManager, ruin);
        EconomyManager economyManager = new EconomyManager(this, ruin);
        NPCManager npcManager = new NPCManager(this);
        new VoteManager(this, rankManager, economyManager);
        new EnchantManager(this);
        new ConsoleManager(this, rankManager, economyManager);
        new ShopManager(this, economyManager, npcManager, ruin);
        new FactionCenterManager(this, factionManager, ruin);

        setupMenus();

        if(ruin == Ruin.HUB) {
            new Hub(this);
            System.out.println("HUB ENABLED!");
        }
    }

    @Override
    public void onDisable() {

    }

    public static JavaPlugin getInstance() {
        return instance;
    }

    public void setupMenus() {
        menuManager = new MenuManager(this);

        if(ruin == Ruin.AZTEC_MOUNTAIN) {

        } else if(ruin == Ruin.TEMPLARS_CASCADE) {

        }
    }
}
