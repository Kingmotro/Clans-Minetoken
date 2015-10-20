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
                    //TODO: Replace "" for ruin that they are in.
                    factionCommand.factionManager.fFaction.createFaction(name, "", player);
                } else {
                    player.sendMessage(Format.main("Error", "Sorry that faction already exist."));
                }
            } else {
                player.sendMessage(Format.main("Error", "Sorry your already in a faction."));
            }
        } else {
            help(player);
        }
        return false;
    }

    @Override
    public String help(Player player) {
        return Format.help("/faction create {name}");
    }

    @Override
    public String permission() {
        return "ruinspvp.default";
    }
}
