package repo.ruinspvp.factions.structure.faction.command;

import org.bukkit.entity.Player;
import repo.ruinspvp.factions.structure.command.SubCommand;
import repo.ruinspvp.factions.structure.faction.FactionManager;
import repo.ruinspvp.factions.utilities.Format;

public class InfoCommand implements SubCommand {

    public FactionManager factionManager;

    public InfoCommand(FactionManager factionManager) {
        this.factionManager = factionManager;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if(args.length == 0) {
            String faction = factionManager.fPlayer.getFaction(player.getUniqueId());
            player.sendMessage(Format.main("Factions", faction + "'s Info"));
            player.sendMessage(Format.info("Created: " + factionManager.fFaction.getDateCreated(faction)));
            player.sendMessage(Format.info("Members: " + factionManager.fPlayer.getPlayersInAFaction(faction).size()));
            player.sendMessage(Format.info("Founder: " + factionManager.fFaction.getFounder(faction)));
        } else {
            player.sendMessage(help());
        }
        return false;
    }

    @Override
    public String help() {
        return Format.info("/faction info");
    }

    @Override
    public String permission() {
        return "ruinpvp.default";
    }
}
