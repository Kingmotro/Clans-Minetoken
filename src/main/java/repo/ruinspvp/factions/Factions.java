package repo.ruinspvp.factions;

import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.structure.inventory.MenuManager;

public class Factions extends JavaPlugin {

    public static JavaPlugin instance;

    private static MenuManager menuManager;

    @Override
    public void onEnable() {
        instance = this;

        getCommand("test").setExecutor(new TestCommand(this));

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
