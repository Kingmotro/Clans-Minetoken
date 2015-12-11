package repo.ruinspvp.clans.structure.clan.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import repo.ruinspvp.clans.structure.command.SubCommand;
import repo.ruinspvp.clans.structure.clan.ClanManager;
import repo.ruinspvp.clans.structure.clan.enums.ClanRanks;
import repo.ruinspvp.clans.structure.rank.enums.Result;
import repo.ruinspvp.clans.utilities.Format;

import java.util.UUID;

public class RankCommand implements SubCommand {

    public ClanManager clanManager;

    public RankCommand(ClanManager clanManager) {
        this.clanManager = clanManager;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if(args.length == 2) {
            String name = args[0];
            String rankName = args[1];
            if(clanManager.cPlayer.getCRank(player.getUniqueId()).getPermLevel() >= ClanRanks.ADMIN.getPermLevel()) {
                if(!name.equalsIgnoreCase(player.getName())) {
                    if(clanManager.cPlayer.getRankFromString(rankName) != null) {
                        ClanRanks rank = clanManager.cPlayer.getRankFromString(rankName);
                        if(clanManager.cPlayer.getCRank(player.getUniqueId()).getPermLevel() >= rank.getPermLevel()) {
                            if(clanManager.cPlayer.getUUID(name) != null) {
                                if (clanManager.cPlayer.checkExists(clanManager.cPlayer.getUUID(name)) == Result.TRUE) {
                                    player.sendMessage(Format.main("Clans", "You have made " + name + "'s rank " + rank.getName() + "."));
                                    UUID uuid = clanManager.cPlayer.getUUID(name);
                                    clanManager.cPlayer.setCRank(uuid, rank);
                                    try {

                                        if (Bukkit.getPlayer(uuid).isOnline()) {
                                            Player target = Bukkit.getPlayer(uuid);
                                            target.sendMessage(Format.main("Clans", player.getName() + " has set your rank to " + rank.getName() + "."));
                                        }
                                    } catch (Exception ignore) {
                                    }
                                } else {
                                    player.sendMessage(Format.main("Clans", "According to the database this player doesn't exist, please contact staff."));
                                }
                            } else {
                                player.sendMessage(Format.main("Clans", "According to the database this player doesn't exist, please contact staff."));
                            }
                        } else {
                            player.sendMessage(Format.main("Clans", "You can't set a rank higher than yours."));
                        }
                    } else {
                        player.sendMessage(Format.main("Clans", "That isn't a clan rank, try Pleb, Mod, Admin, Leader."));
                    }
                } else {
                    player.sendMessage(Format.main("Clans", "You can't rank yourself."));
                }
            } else {
                player.sendMessage(Format.main("Clans", "You must a admin in your clan to set someone's rank."));
            }
        } else {
            player.sendMessage(help());
        }
        return false;
    }

    @Override
    public String help() {
        return Format.info("/clan rank {player} {rank}");
    }

    @Override
    public String permission() {
        return "ruinspvp.default";
    }
}
