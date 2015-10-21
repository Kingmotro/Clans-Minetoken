package repo.ruinspvp.factions.structure.faction.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import repo.ruinspvp.factions.structure.command.SubCommand;
import repo.ruinspvp.factions.structure.faction.FactionInvite;
import repo.ruinspvp.factions.structure.faction.FactionManager;
import repo.ruinspvp.factions.structure.faction.enums.FactionRanks;
import repo.ruinspvp.factions.structure.rank.enums.Result;
import repo.ruinspvp.factions.utilities.Format;

import java.util.UUID;

public class InviteCommand implements SubCommand {

    public FactionManager factionManager;

    public InviteCommand(FactionManager factionManager) {
        this.factionManager = factionManager;
    }

    @Override
    public boolean onCommand(final Player player, String[] args) {
        if(args.length == 1) {
            final String name = args[0];
            String faction = factionManager.fPlayer.getFaction(player.getUniqueId());
            if(name.equalsIgnoreCase(player.getName())) {
                return true;
            }
            try {
                if(Bukkit.getPlayer(name).isOnline()) {
                    final Player target = Bukkit.getPlayer(name);
                    final UUID uuid = target.getUniqueId();
                    if(factionManager.fPlayer.hasFaction(uuid) == Result.FALSE) {
                        if (factionManager.fPlayer.getFRank(player.getUniqueId()).getPermLevel() >= FactionRanks.MOD.getPermLevel()) {
                            FactionInvite factionInvite = new FactionInvite(player, faction);
                            factionManager.invitedPlayer.put(uuid, factionInvite);
                            player.sendMessage(Format.main("Factions", name + " has 30 seconds to accept the invitation."));
                            factionManager.plugin.getServer().getScheduler().scheduleSyncDelayedTask(factionManager.plugin, new Runnable() {
                                @Override
                                public void run() {
                                    if (factionManager.invitedPlayer.containsKey(uuid)) {
                                        factionManager.invitedPlayer.remove(uuid);
                                        player.sendMessage(Format.main("Factions", name + " didn't accept the faction invitation."));
                                    }
                                }
                            }, 600L);
                        } else {
                            player.sendMessage(Format.main("Factions", "Sorry you require Mod rank in your faction to invite a player, ask a mod about inviting, " + name));
                        }
                    } else {
                        player.sendMessage(Format.main("Factions", "That player is currently in a faction."));
                    }
                } else {
                    player.sendMessage(Format.main("Factions", "This player isn't online."));
                }
            } catch (Exception ignore) {player.sendMessage(Format.main("Factions", "This player isn't online."));}
        } else {
            player.sendMessage(help());
        }
        return false;
    }

    @Override
    public String help() {
        return Format.help("/faction invite {player}");
    }

    @Override
    public String permission() {
        return "ruinspvp.default";
    }
}
