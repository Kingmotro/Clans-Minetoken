package repo.minetoken.clans.structure.clan.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import repo.minetoken.clans.structure.clan.enums.ClanRanks;
import repo.minetoken.clans.structure.command.SubCommand;
import repo.minetoken.clans.structure.clan.ClanInvite;
import repo.minetoken.clans.structure.clan.ClanManager;
import repo.minetoken.clans.structure.rank.enums.Result;
import repo.minetoken.clans.utilities.Format;

import java.util.UUID;

public class InviteCommand implements SubCommand {

    public ClanManager clanManager;

    public InviteCommand(ClanManager clanManager) {
        this.clanManager = clanManager;
    }

    @Override
    public boolean onCommand(final Player player, String[] args) {
        if(args.length == 1) {
            final String name = args[0];
            String faction = clanManager.cPlayer.getClan(player.getUniqueId());
            if(name.equalsIgnoreCase(player.getName())) {
                return true;
            }
            try {
                if(Bukkit.getPlayer(name).isOnline()) {
                    final Player target = Bukkit.getPlayer(name);
                    final UUID uuid = target.getUniqueId();
                    if(clanManager.cPlayer.hasClan(uuid) == Result.FALSE) {
                        if (clanManager.cPlayer.getCRank(player.getUniqueId()).getPermLevel() >= ClanRanks.MOD.getPermLevel()) {
                            ClanInvite clanInvite = new ClanInvite(player, faction);
                            clanManager.invitedPlayer.put(uuid, clanInvite);
                            player.sendMessage(Format.main("Clans", name + " has 30 seconds to accept the invitation."));
                            target.sendMessage(Format.main("Clans", "You have 30 seconds to type /clan accept, or to deny by typing /clan deny."));
                            clanManager.plugin.getServer().getScheduler().scheduleSyncDelayedTask(clanManager.plugin, new Runnable() {
                                @Override
                                public void run() {
                                    if (clanManager.invitedPlayer.containsKey(uuid)) {
                                        clanManager.invitedPlayer.remove(uuid);
                                        player.sendMessage(Format.main("Clans", name + " didn't accept the clan invitation."));
                                    }
                                }
                            }, 600L);
                        } else {
                            player.sendMessage(Format.main("Clans", "Sorry you require Mod rank in your clan to invite a player, ask a mod about inviting, " + name));
                        }
                    } else {
                        player.sendMessage(Format.main("Clans", "That player is currently in a clan."));
                    }
                } else {
                    player.sendMessage(Format.main("Clans", "This player isn't online."));
                }
            } catch (Exception ignore) {player.sendMessage(Format.main("Clans", "This player isn't online."));}
        } else {
            player.sendMessage(help());
        }
        return false;
    }

    @Override
    public String help() {
        return Format.info("/clan invite {player}");
    }

    @Override
    public String permission() {
        return "ruinspvp.default";
    }
}
