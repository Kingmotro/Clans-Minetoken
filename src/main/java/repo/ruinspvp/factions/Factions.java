package repo.ruinspvp.factions;

import org.bukkit.plugin.java.JavaPlugin;

public class Factions extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("test").setExecutor(new TestCommand(this));
    }

    @Override
    public void onDisable() {

    }
}
