package repo.ruinspvp.clans.structure.scoreboard;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import repo.ruinspvp.clans.structure.inventory.Menu;
import repo.ruinspvp.clans.structure.rank.enums.Result;
import repo.ruinspvp.clans.structure.scoreboard.scoreboards.ClanScoreboard;
import repo.ruinspvp.clans.structure.scoreboard.scoreboards.PlayerScoreboard;
import repo.ruinspvp.clans.utilities.Format;
import repo.ruinspvp.clans.utilities.ItemStackBuilder;

public class ScoreboardMenu extends Menu {

    public ScoreboardManager scoreboardManager;

    public ScoreboardMenu(ScoreboardManager scoreboardManager) {
        super("Scoreboards", 9);
        this.scoreboardManager = scoreboardManager;
        getInventory().setItem(2, new ItemStackBuilder(Material.DIAMOND_SWORD).withName(ChatColor.BLUE + "Clan Scoreboard").build());
        getInventory().setItem(5, new ItemStackBuilder(Material.NETHER_STAR).withName(ChatColor.GREEN + "Player Scoreboard").build());
    }

    @Override
    public void leftClick(Player player, ItemStack itemStack) {
        String itemName = getFriendlyName(itemStack);

        if(itemName == null) {
            return;
        }

        switch (itemName) {
            case "Clan Scoreboard":
                player.closeInventory();
                if(scoreboardManager.clanManager.cPlayer.hasClan(player.getUniqueId()) == Result.TRUE) {
                    ClanScoreboard clanScoreboard = new ClanScoreboard(player, scoreboardManager.clanManager);
                    clanScoreboard.send(player);
                    scoreboardManager.addClanScoreboard(player, clanScoreboard);
                    player.sendMessage(Format.main("Scoreboard", "Loading the clan scoreboard..."));
                } else {
                    player.sendMessage(Format.main("Scoreboard", "Sorry you don't have a clan. You may not use this scoreboard."));
                }
                break;
            case "Player Scoreboard":
                player.closeInventory();
                PlayerScoreboard playerScoreboard = new PlayerScoreboard(player, scoreboardManager);
                playerScoreboard.send(player);
                scoreboardManager.addPlayerScoreboard(player, playerScoreboard);
                player.sendMessage(Format.main("Scoreboard", "Loading your scoreboard..."));
                break;
        }
    }

    @Override
    public void rightClick(Player player, ItemStack itemStack) {
        String itemName = getFriendlyName(itemStack);

        if(itemName == null) {
            return;
        }

        switch (itemName) {
            case "Faction Scoreboard":
            case "Player Scoreboard":
        }
    }

    @Override
    public void sleftClick(Player player, ItemStack itemStack) {

    }

    @Override
    public void srightClick(Player player, ItemStack itemStack) {

    }
}
