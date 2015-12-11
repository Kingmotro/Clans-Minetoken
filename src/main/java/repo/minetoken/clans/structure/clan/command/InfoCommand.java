package repo.minetoken.clans.structure.clan.command;

import org.bukkit.entity.Player;
import repo.minetoken.clans.structure.command.SubCommand;
import repo.minetoken.clans.structure.clan.ClanManager;
import repo.minetoken.clans.utilities.Format;

public class InfoCommand implements SubCommand {

    public ClanManager clanManager;

    public InfoCommand(ClanManager clanManager) {
        this.clanManager = clanManager;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if(args.length == 0) {
            String faction = clanManager.cPlayer.getClan(player.getUniqueId());
            player.sendMessage(Format.main("Clans", faction + "'s Info"));
            player.sendMessage(Format.info("Created: " + clanManager.CClans.getDateCreated(faction)));
            player.sendMessage(Format.info("Members: " + clanManager.cPlayer.getPlayersInAFaction(faction).size()));
            player.sendMessage(Format.info("Founder: " + clanManager.CClans.getFounder(faction)));
        } else {
            player.sendMessage(help());
        }
        return false;
    }

    @Override
    public String help() {
        return Format.info("/clan info");
    }

    @Override
    public String permission() {
        return "ruinpvp.default";
    }
}
