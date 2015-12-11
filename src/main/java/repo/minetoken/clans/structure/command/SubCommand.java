package repo.minetoken.clans.structure.command;

import org.bukkit.entity.Player;

public interface SubCommand {

    boolean onCommand(Player player, String[] args);

    String help();

    String permission();

}