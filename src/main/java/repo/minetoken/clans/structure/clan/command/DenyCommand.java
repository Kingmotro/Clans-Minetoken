package repo.minetoken.clans.structure.clan.command;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import repo.minetoken.clans.structure.clan.ClanManager;
import repo.minetoken.clans.structure.command.SubCommand;
import repo.minetoken.clans.structure.rank.enums.Result;
import repo.minetoken.clans.utilities.C;
import repo.minetoken.clans.utilities.Format;
import repo.minetoken.clans.utilities.UtilSound;
import repo.minetoken.clans.utilities.UtilSound.Pitch;

public class DenyCommand implements SubCommand {

    ClanManager clanManager;

    public DenyCommand(ClanManager clanManager) {
        this.clanManager = clanManager;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (args.length == 0) {
            if (clanManager.invitedPlayer.containsKey(player.getUniqueId())) {
                if (clanManager.cPlayer.hasClan(player.getUniqueId()) == Result.FALSE) {
                    player.sendMessage(Format.main("Clans", "You have denied the Clan " + C.red + clanManager.invitedPlayer.get(player.getUniqueId()).getFaction()) + C.yellow +"!");
                    UtilSound.play(player, Sound.IRONGOLEM_DEATH, Pitch.LOW);
                    try {
                        if(clanManager.invitedPlayer.get(player.getUniqueId()).getPlayer().isOnline()) {
                            clanManager.invitedPlayer.get(player.getUniqueId()).getPlayer().sendMessage(Format.main("Clans", player.getName() + " has "+ C.yellow + "DENIED "+ C.yellow + "to join the Clan."));
                            UtilSound.play(clanManager.invitedPlayer.get(player.getUniqueId()).getPlayer(), Sound.WITHER_DEATH, Pitch.LOW);
                        }
                    } catch (Exception ignore) {}
                    clanManager.invitedPlayer.remove(player.getUniqueId());
                } else {
                    player.sendMessage(Format.main("Clans", "You must leave your current Clan to join."));
                    UtilSound.play(player, Sound.VILLAGER_HIT, Pitch.LOW);
                }
            } else {
                player.sendMessage(Format.main("Clans", "You have no pending Clan request."));
                UtilSound.play(player, Sound.VILLAGER_HIT, Pitch.LOW);
            }
        } else {
            player.sendMessage(help());
        }
        return false;
    }

    @Override
    public String help() {
        return Format.info("/clan deny");
    }

    @Override
    public String permission() {
        return "ruinspvp.default";
    }
}
