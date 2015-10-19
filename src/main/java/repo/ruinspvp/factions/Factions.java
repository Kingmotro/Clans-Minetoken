package repo.ruinspvp.factions;

import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.structure.enchant.EnchantManager;
import repo.ruinspvp.factions.structure.inventory.MenuManager;
import repo.ruinspvp.factions.structure.rank.RankManager;
import repo.ruinspvp.factions.structure.voting.VoteManager;

public class Factions extends JavaPlugin {

    public static JavaPlugin instance;

    private static MenuManager menuManager;

    @Override
    public void onEnable() {
        instance = this;

        getCommand("test").setExecutor(new TestCommand(this));

        RankManager rankManager = new RankManager(this);
        new VoteManager(rankManager, this);
        new EnchantManager(this);

        setupMenus();
    }

    @Override
    public void onDisable() {

    }

    public static JavaPlugin getInstance() {
        return instance;
    }

    public void setupMenus() {
        menuManager = new MenuManager(this);
    }
}
