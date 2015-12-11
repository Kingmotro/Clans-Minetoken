package repo.minetoken.clans.structure.clan.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import repo.minetoken.clans.structure.rank.enums.Result;
import repo.minetoken.clans.structure.command.SubCommand;
import repo.minetoken.clans.structure.clan.ClanManager;
import repo.minetoken.clans.utilities.Format;

import java.util.UUID;

public class KickCommand implements SubCommand {

    public ClanManager clanManager;

    public KickCommand(ClanManager clanManager) {
        this.clanManager = clanManager;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (args.length == 1) {
            String name = args[0];
            if (clanManager.cPlayer.getUUID(name) != null) {
                UUID uuid = clanManager.cPlayer.getUUID(name);
                if (clanManager.cPlayer.hasClan(uuid) == Result.TRUE) {
                    if (clanManager.cPlayer.getCRank(player.getUniqueId()).getPermLevel() > clanManager.cPlayer.getCRank(uuid).getPermLevel()) {
                        if (clanManager.cPlayer.getClan(player.getUniqueId()).equalsIgnoreCase(clanManager.cPlayer.getClan(uuid))) {
                            try {
                                for (String uuids : clanManager.cPlayer.getPlayersInAClan(clanManager.cPlayer.getClan(player.getUniqueId()))) {
                                    if (Bukkit.getPlayer(UUID.fromString(uuids)).isOnline()) {
                                        Player fPlayer = Bukkit.getPlayer(UUID.fromString(uuids));
                                        if (fPlayer != player) {
                                            fPlayer.sendMessage(Format.main("Clans", player.getName() + " has benn kick from the clan."));
                                        }
                                    }
                                }
                                if (Bukkit.getPlayer(uuid).isOnline()) {
                                    Bukkit.getPlayer(uuid).sendMessage(Format.main("Clans", "You have been kicked from " + clanManager.cPlayer.getClan(player.getUniqueId())));
                                }
                            } catch (Exception ignore) {}
                            clanManager.cPlayer.leaveFaction(uuid);
                        } else {
                            player.sendMessage(Format.main("Clans", "You must be in the same clan as the specified player."));
                        }
                    } else {
                        player.sendMessage(Format.main("Clans", "You cannot kick a player with a greater or equal to rank than yours."));
                    }
                } else {
                    player.sendMessage(Format.main("Clans", "You have no clan."));
                }
            } else {
                player.sendMessage(Format.main("Clans", "This player isn't on the database."));
            }
        } else {
            player.sendMessage(help());
        }
        return false;
    }

    @Override
    public String help() {
        return Format.info("/clan kick {player}");
    }

    @Override
    public String permission() {
        return "ruinspvp.default";
    }
}