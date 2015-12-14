package repo.minetoken.clans.structure.clan.command;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import repo.minetoken.clans.structure.command.SubCommand;
import repo.minetoken.clans.structure.clan.ClanManager;
import repo.minetoken.clans.structure.clan.enums.ClanRanks;
import repo.minetoken.clans.structure.rank.enums.Result;
import repo.minetoken.clans.utilities.C;
import repo.minetoken.clans.utilities.Format;
import repo.minetoken.clans.utilities.UtilSound;
import repo.minetoken.clans.utilities.UtilSound.Pitch;

import java.util.UUID;

public class LeaveCommand implements SubCommand {

    public ClanManager clanManager;

    public LeaveCommand(ClanManager clanManager) {
        this.clanManager = clanManager;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if(args.length == 0) {
            if(clanManager.cPlayer.hasClan(player.getUniqueId()) == Result.TRUE) {
                if(clanManager.cPlayer.getCRank(player.getUniqueId()) == ClanRanks.FOUNDER) {
                    Bukkit.broadcastMessage(Format.main("Clans", C.aqua + player.getName() + C.yellow + " has disbanded the Clan " + C.red + clanManager.cPlayer.getClan(player.getUniqueId())));
                    clanManager.CClans.deleteClan(clanManager.cPlayer.getClan(player.getUniqueId()));
                    UtilSound.play(player, Sound.ITEM_BREAK, Pitch.HIGH);
                    UtilSound.play(player, Sound.VILLAGER_HIT, Pitch.HIGH);
                } else {
                    try {
                        for(String uuid : clanManager.cPlayer.getPlayersInAClan(clanManager.cPlayer.getClan(player.getUniqueId()))) {
                            if(Bukkit.getPlayer(UUID.fromString(uuid)).isOnline()) {
                                Player fPlayer = Bukkit.getPlayer(UUID.fromString(uuid));
                                if(fPlayer != player) {
                                    fPlayer.sendMessage(Format.main("Clans", player.getName() + " has left the Clan."));
                                    UtilSound.play(player, Sound.VILLAGER_HIT, Pitch.HIGH);
                                }
                            }
                        }
                    } catch (Exception ignore) {}
                    player.sendMessage(Format.main("Clans", "You have left " + clanManager.cPlayer.getClan(player.getUniqueId())));
                    clanManager.cPlayer.leaveFaction(player.getUniqueId());
                    UtilSound.play(player, Sound.LEVEL_UP, Pitch.HIGH);
                }
            } else {
                player.sendMessage(Format.main("Clans", "You have no clan."));
                UtilSound.play(player, Sound.VILLAGER_HIT, Pitch.HIGH);
            }
        } else {
            player.sendMessage(help());
        }
        return false;
    }

    @Override
    public String help() {
        return Format.info("/clan leave");
    }

    @Override
    public String permission() {
        return "ruinspvp.default";
    }
}
