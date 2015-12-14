package repo.minetoken.clans.structure.clan.command;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import repo.minetoken.clans.structure.command.SubCommand;
import repo.minetoken.clans.structure.rank.enums.Result;
import repo.minetoken.clans.utilities.C;
import repo.minetoken.clans.utilities.Format;
import repo.minetoken.clans.utilities.UtilSound;
import repo.minetoken.clans.utilities.UtilSound.Pitch;

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
                	if(name.length() < 3){
                		player.sendMessage(Format.main("Clans", "Sorry, that Clan name is too short."));
                        UtilSound.play(player, Sound.VILLAGER_HIT, Pitch.HIGH);
            			return true;
            		}
            		if(name.length() >= 12){
            			player.sendMessage(Format.main("Clans", "Sorry, that Clan name is too long."));
                        UtilSound.play(player, Sound.VILLAGER_HIT, Pitch.HIGH);
            			return true;
            		}
            		if(name.contains("/") || name.contains("\\") || name.contains(".") || name.contains("\"") 
            			|| name.contains(",") || name.contains("?") || name.contains("'") || name.contains("*") 
            			|| name.contains("|") || name.contains("<") || name.contains(":") || name.contains("$")){
            			player.sendMessage(Format.main("Clans", "Sorry, that Clan name contains illegal characters."));
                        UtilSound.play(player, Sound.VILLAGER_HIT, Pitch.HIGH);
            			return true;
            		}
                    if(name.length() <= 16) {
                    	String msg = name.trim(); 
            			String clan = ("" + msg.charAt(0)).toUpperCase() + msg.substring(1);
            			 clanCommand.clanManager.CClans.createClan(clan, player); 
                        player.sendMessage(Format.main("Clans", "Created Clan: " + C.green + clan));
                        UtilSound.play(player, Sound.LEVEL_UP, Pitch.HIGH);
                    } else {
                        player.sendMessage(Format.main("Clans", "Sorry, that Clan name is too long."));
                        UtilSound.play(player, Sound.VILLAGER_HIT, Pitch.HIGH);
                    }
                } else {
                    player.sendMessage(Format.main("Clans", "Sorry that Clan already exist."));
                    UtilSound.play(player, Sound.VILLAGER_HIT, Pitch.HIGH);
                }
            } else {
                player.sendMessage(Format.main("Clans", "Sorry, you're already in a Clan."));
                UtilSound.play(player, Sound.VILLAGER_HIT, Pitch.HIGH);
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
