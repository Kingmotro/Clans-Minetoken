package repo.minetoken.clans.structure.clan.command;

import org.bukkit.entity.Player;
import repo.minetoken.clans.structure.command.SubCommand;
import repo.minetoken.clans.structure.rank.enums.Result;
import repo.minetoken.clans.utilities.Format;

public class CreateCommand implements SubCommand {

    public ClanCommand clanCommand;

    public CreateCommand(ClanCommand clanCommand) {
        this.clanCommand = clanCommand;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if(args.length == 1) {
            String name = args[0];
            if (clanCommand.clanManager.cPlayer.hasClan(player.getUniqueId()) == Result.FALSE) {
                if (clanCommand.clanManager.CClans.checkExists(name) == Result.FALSE) {
                    if(name.length() <= 16) {
                        clanCommand.clanManager.CClans.createClan(name, player);
                        player.sendMessage(Format.main("Clans", "You have successfully created the clan " + name + "."));
                    } else {
                        player.sendMessage(Format.main("Clans", "Sorry that clan name is too long."));
                    }
                } else {
                    player.sendMessage(Format.main("Clans", "Sorry that clan already exist."));
                }
            } else {
                player.sendMessage(Format.main("Clans", "Sorry your already in a clan."));
            }
        } else {
           player.sendMessage(help());
        }
        return false;
    }

    @Override
    public String help() {
        return Format.info("/clan create {name}");
    }

    @Override
    public String permission() {
        return "ruinspvp.default";
    }
}
