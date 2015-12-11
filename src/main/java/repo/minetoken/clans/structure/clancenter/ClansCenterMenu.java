package repo.minetoken.clans.structure.clancenter;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import repo.minetoken.clans.structure.clan.enums.ClanRanks;
import repo.minetoken.clans.structure.inventory.Menu;
import repo.minetoken.clans.structure.rank.enums.Result;
import repo.minetoken.clans.utilities.Format;
import repo.minetoken.clans.utilities.ItemStackBuilder;

public class ClansCenterMenu extends Menu {

    public ClansCenterManager clansCenterManager;

    public ClansCenterMenu(ClansCenterManager clansCenterManager) {
        super("Wise One", 54);
        this.clansCenterManager = clansCenterManager;

        getInventory().setItem(0, new ItemStackBuilder(Material.WOOL).withData(13).withName(ChatColor.GREEN + "Enlist Faction").build());
        getInventory().setItem(8, new ItemStackBuilder(Material.WOOL).withData(14).withName(ChatColor.RED + "Remove Faction Enlistment").build());

        for(int i = 0; i < clansCenterManager.cCenter.getEnlistedFactions().size(); i++) {
            String faction = clansCenterManager.cCenter.getEnlistedFactions().get(i);
            for(int i1 = 18; i < 24; i++) {
                getInventory().setItem(i1, new ItemStackBuilder(Material.STAINED_CLAY).withData(14).withName(ChatColor.GREEN + faction).withLore("Left-Click to join this clan.").build());
            }
        }
    }

    @Override
    public void leftClick(Player player, ItemStack itemStack) {
        String itemName = getFriendlyName(itemStack);
        String name;

        if(clansCenterManager.clanManager.cPlayer.hasClan(player.getUniqueId()) == Result.TRUE) {
            if(clansCenterManager.clanManager.cPlayer.getCRank(player.getUniqueId()) == ClanRanks.ADMIN) {
                name = clansCenterManager.clanManager.cPlayer.getClan(player.getUniqueId());
            } else {
                name = null;
            }
        } else {
            name = null;
        }

        if(itemName == null) {
            return;
        }

        for(int i = 0; i < clansCenterManager.cCenter.getEnlistedFactions().size(); i++) {
            if (itemName.equalsIgnoreCase(clansCenterManager.cCenter.getEnlistedFactions().get(i))) {
                player.closeInventory();
                if(clansCenterManager.clanManager.cPlayer.hasClan(player.getUniqueId()) == Result.FALSE) {
                    clansCenterManager.clanManager.cPlayer.joinFaction(player.getUniqueId(), player.getName(), itemName);
                    player.sendMessage(Format.main("Clans", "You have joined " + itemName + "."));
                } else {
                    player.sendMessage(Format.main("Clans", "You're already in a clan."));
                }
            }
        }

        switch (itemName) {
            case "Enlist Faction":
                player.closeInventory();
                if(name == null) {
                    player.sendMessage(Format.main("Faction Center", "You are either not in a clan, or you are not a admin in your clan."));
                    return;
                }
                clansCenterManager.cCenter.enlistFaction(name);
                player.sendMessage(Format.main("Faction Center", "You have enlisted your clan to the Faction Center."));
                break;
            case "Remove Faction Enlistment":
                player.closeInventory();
                if(name == null) {
                    player.sendMessage(Format.main("Faction Center", "You are either not in a clan, or you are not a admin in your clan."));
                    return;
                }
                clansCenterManager.cCenter.unenlistFaction(name);
                player.sendMessage(Format.main("Faction Center", "You have removed your clan from the Faction Center."));
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
