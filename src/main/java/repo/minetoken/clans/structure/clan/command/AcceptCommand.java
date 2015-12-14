package repo.minetoken.clans.structure.clan.command;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import repo.minetoken.clans.structure.command.SubCommand;
import repo.minetoken.clans.structure.clan.ClanManager;
import repo.minetoken.clans.structure.rank.enums.Result;
import repo.minetoken.clans.utilities.C;
import repo.minetoken.clans.utilities.Format;
import repo.minetoken.clans.utilities.UtilSound;
import repo.minetoken.clans.utilities.UtilSound.Pitch;

import java.util.UUID;

public class AcceptCommand implements SubCommand {

    ClanManager clanManager;

    public AcceptCommand(ClanManager clanManager) {
        this.clanManager = clanManager;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if(args.length == 0) {
            if(clanManager.invitedPlayer.containsKey(player.getUniqueId())) {
                if(clanManager.cPlayer.hasClan(player.getUniqueId()) == Result.FALSE) {
                    clanManager.cPlayer.joinFaction(player.getUniqueId(), player.getName(), clanManager.invitedPlayer.get(player.getUniqueId()).getFaction());
                    player.sendMessage(Format.main("Clans", "You have successfully joined " + clanManager.invitedPlayer.get(player.getUniqueId()).getFaction()));
                    UtilSound.play(player, Sound.NOTE_PLING, Pitch.HIGH);
                    try {
                        for(String uuid : clanManager.cPlayer.getPlayersInAClan(clanManager.invitedPlayer.get(player.getUniqueId()).getFaction())) {
                            if(Bukkit.getPlayer(UUID.fromString(uuid)).isOnline()) {
                                Player fPlayer = Bukkit.getPlayer(UUID.fromString(uuid));
                                fPlayer.sendMessage(Format.main("Clans", C.green + player.getName() + C.yellow + " joined the clan!"));
                                UtilSound.play(fPlayer, Sound.LEVEL_UP, Pitch.HIGH);
                            }
                        }
                    } catch (Exception ignore) {}
                    clanManager.invitedPlayer.remove(player.getUniqueId());
                } else {
                    player.sendMessage(Format.main("Clans", "You must leave your current clan to join."));
                    UtilSound.play(player, Sound.VILLAGER_HIT, Pitch.HIGH);
                }
            } else {
                player.sendMessage(Format.main("Clans", "You have no pending clan request."));
                UtilSound.play(player, Sound.WOLF_PANT, Pitch.HIGH);
            }
        } else {
            player.sendMessage(help());
        }
        return false;
    }

    @Override
    public String help() {
        return Format.info("/clan accept");
    }

    @Override
    public String permission() {
        return "ruinspvp.default";
    }
}
