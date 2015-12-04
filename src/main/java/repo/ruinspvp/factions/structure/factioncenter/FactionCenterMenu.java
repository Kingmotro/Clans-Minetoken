package repo.ruinspvp.factions.structure.factioncenter;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import repo.ruinspvp.factions.structure.faction.enums.FactionRanks;
import repo.ruinspvp.factions.structure.inventory.Menu;
import repo.ruinspvp.factions.structure.rank.enums.Result;
import repo.ruinspvp.factions.structure.scoreboard.scoreboards.FactionScoreboard;
import repo.ruinspvp.factions.structure.scoreboard.scoreboards.PlayerScoreboard;
import repo.ruinspvp.factions.utilities.Format;
import repo.ruinspvp.factions.utilities.ItemStackBuilder;

public class FactionCenterMenu extends Menu {

    public FactionCenterManager factionCenterManager;

    public FactionCenterMenu(FactionCenterManager factionCenterManager) {
        super("Wise One", 54);
        this.factionCenterManager = factionCenterManager;

        getInventory().setItem(0, new ItemStackBuilder(Material.WOOL).withData(13).withName(ChatColor.GREEN + "Enlist Faction").build());
        getInventory().setItem(8, new ItemStackBuilder(Material.WOOL).withData(14).withName(ChatColor.RED + "Remove Faction Enlistment").build());

        for(int i = 0; i < factionCenterManager.fCenter.getEnlistedFactions().size(); i++) {
            String faction = factionCenterManager.fCenter.getEnlistedFactions().get(i);
            for(int i1 = 18; i < 24; i++) {
                getInventory().setItem(i1, new ItemStackBuilder(Material.STAINED_CLAY).withData(14).withName(ChatColor.GREEN + faction).withLore("Left-Click to join this faction.").build());
            }
        }
    }

    @Override
    public void leftClick(Player player, ItemStack itemStack) {
        String itemName = getFriendlyName(itemStack);
        String name;

        if(factionCenterManager.factionManager.fPlayer.hasFaction(player.getUniqueId()) == Result.TRUE) {
            if(factionCenterManager.factionManager.fPlayer.getFRank(player.getUniqueId()) == FactionRanks.ADMIN) {
                name = factionCenterManager.factionManager.fPlayer.getFaction(player.getUniqueId());
            } else {
                name = null;
            }
        } else {
            name = null;
        }

        if(itemName == null) {
            return;
        }

        for(int i = 0; i < factionCenterManager.fCenter.getEnlistedFactions().size(); i++) {
            if (itemName.equalsIgnoreCase(factionCenterManager.fCenter.getEnlistedFactions().get(i))) {
                player.closeInventory();
                if(factionCenterManager.factionManager.fPlayer.hasFaction(player.getUniqueId()) == Result.FALSE) {
                    factionCenterManager.factionManager.fPlayer.joinFaction(player.getUniqueId(), player.getName(), itemName);
                    player.sendMessage(Format.main("Factions", "You have joined " + itemName + "."));
                } else {
                    player.sendMessage(Format.main("Factions", "You're already in a faction."));
                }
            }
        }

        switch (itemName) {
            case "Enlist Faction":
                player.closeInventory();
                if(name == null) {
                    player.sendMessage(Format.main("Faction Center", "You are either not in a faction, or you are not a admin in your faction."));
                    return;
                }
                factionCenterManager.fCenter.enlistFaction(name);
                player.sendMessage(Format.main("Faction Center", "You have enlisted your faction to the Faction Center."));
                break;
            case "Remove Faction Enlistment":
                player.closeInventory();
                if(name == null) {
                    player.sendMessage(Format.main("Faction Center", "You are either not in a faction, or you are not a admin in your faction."));
                    return;
                }
                factionCenterManager.fCenter.unenlistFaction(name);
                player.sendMessage(Format.main("Faction Center", "You have removed your faction from the Faction Center."));
                break;
        }
    }

    @Override
    public void rightClick(Player player, ItemStack itemStack) {

    }

    @Override
    public void sleftClick(Player player, ItemStack itemStack) {

    }

    @Override
    public void srightClick(Player player, ItemStack itemStack) {

    }
}
