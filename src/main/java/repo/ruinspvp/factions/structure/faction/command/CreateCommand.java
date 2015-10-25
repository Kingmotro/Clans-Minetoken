package repo.ruinspvp.factions.structure.faction.command;

import org.bukkit.entity.Player;
import repo.ruinspvp.factions.structure.command.SubCommand;
import repo.ruinspvp.factions.structure.rank.enums.Result;
import repo.ruinspvp.factions.utilities.Format;

public class CreateCommand implements SubCommand {

    public FactionCommand factionCommand;

    public CreateCommand(FactionCommand factionCommand) {
        this.factionCommand = factionCommand;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if(args.length == 1) {
            String name = args[0];
            if (factionCommand.factionManager.fPlayer.hasFaction(player.getUniqueId()) == Result.FALSE) {
                if (factionCommand.factionManager.fFaction.checkExists(name) == Result.FALSE) {
                    if(name.length() <= 16) {
                        factionCommand.factionManager.fFaction.createFaction(name, player);
                        player.sendMessage(Format.main("Factions", "You have successfully created the faction " + name + "."));
                    } else {
                        player.sendMessage(Format.main("Factions", "Sorry that faction name is too long."));
                    }
                } else {
                    player.sendMessage(Format.main("Factions", "Sorry that faction already exist."));
                }
            } else {
                player.sendMessage(Format.main("Factions", "Sorry your already in a faction."));
            }
        } else {
           player.sendMessage(help());
        }
        return false;
    }

    @Override
    public String help() {
        return Format.info("/faction create {name}");
    }

    @Override
    public String permission() {
        return "ruinspvp.default";
    }
}
