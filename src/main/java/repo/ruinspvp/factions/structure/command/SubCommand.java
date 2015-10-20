package repo.ruinspvp.factions.structure.command;

import org.bukkit.entity.Player;

public interface SubCommand {

    boolean onCommand(Player player, String[] args);

    String help(Player p);

    String permission();

}