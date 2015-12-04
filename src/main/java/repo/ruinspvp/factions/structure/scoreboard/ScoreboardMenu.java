package repo.ruinspvp.factions.structure.scoreboard;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import repo.ruinspvp.factions.structure.inventory.Menu;
import repo.ruinspvp.factions.structure.scoreboard.scoreboards.FactionScoreboard;
import repo.ruinspvp.factions.structure.scoreboard.scoreboards.PlayerScoreboard;
import repo.ruinspvp.factions.utilities.ItemStackBuilder;

public class ScoreboardMenu extends Menu {

    public ScoreboardManager scoreboardManager;

    public ScoreboardMenu(ScoreboardManager scoreboardManager) {
        super("Scoreboards", 9);
        this.scoreboardManager = scoreboardManager;
        getInventory().setItem(2, new ItemStackBuilder(Material.DIAMOND_SWORD).withName(ChatColor.BLUE + "Faction Scoreboard").build());
        getInventory().setItem(5, new ItemStackBuilder(Material.NETHER_STAR).withName(ChatColor.GREEN + "Player Scoreboard").build());
    }

    @Override
    public void leftClick(Player player, ItemStack itemStack) {
        String itemName = getFriendlyName(itemStack);

        if(itemName == null) {
            return;
        }

        switch (itemName) {
            case "Faction Scoreboard":
                player.closeInventory();
                FactionScoreboard factionScoreboard = new FactionScoreboard(player, scoreboardManager.factionManager);
                factionScoreboard.send(player);
                scoreboardManager.addFactionScoreboard(player, factionScoreboard);
                break;
            case "Player Scoreboard":
                player.closeInventory();
                PlayerScoreboard playerScoreboard = new PlayerScoreboard(player, scoreboardManager);
                playerScoreboard.send(player);
                scoreboardManager.addPlayerScoreboard(player, playerScoreboard);
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
            //TODO: MAKE DATABASE TABLE, HANDLE DEFAULT SCOREBOARD.
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
