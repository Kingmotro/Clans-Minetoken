package repo.ruinspvp.factions.structure.kits.commands;

import org.bukkit.entity.Player;
import repo.ruinspvp.factions.structure.command.SubCommand;

public class KitCommand implements SubCommand {
    @Override
    public boolean onCommand(Player player, String[] args) {
        return false;
    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public String permission() {
        return null;
    }
}
