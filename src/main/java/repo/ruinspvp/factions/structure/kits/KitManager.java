package repo.ruinspvp.factions.structure.kits;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.structure.command.CommandManager;

public class KitManager extends CommandManager implements Listener {

    public KitManager(JavaPlugin plugin) {
        super(plugin, "kit", "ruinspvp.default");
    }

    @Override
    public void help(Player player) {

    }
}
