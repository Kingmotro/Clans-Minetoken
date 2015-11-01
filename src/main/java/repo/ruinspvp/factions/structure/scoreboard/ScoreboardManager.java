package repo.ruinspvp.factions.structure.scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.structure.economy.EconomyManager;
import repo.ruinspvp.factions.structure.faction.FactionManager;
import repo.ruinspvp.factions.structure.inventory.MenuManager;
import repo.ruinspvp.factions.structure.rank.RankManager;
import repo.ruinspvp.factions.structure.scoreboard.commands.ScoreboardCommand;
import repo.ruinspvp.factions.structure.scoreboard.scoreboards.PlayerScoreboard;
import repo.ruinspvp.factions.structure.voting.VoteManager;

import java.util.HashMap;
import java.util.UUID;


public class ScoreboardManager implements Listener {

    public JavaPlugin plugin;
    public FactionManager factionManager;
    public RankManager rankManager;
    public EconomyManager economyManager;
    public VoteManager voteManager;
    public MenuManager menuManager;

    public HashMap<UUID, Scoreboard> playerScoreboard;

    public ScoreboardManager(JavaPlugin plugin, FactionManager factionManager, RankManager rankManager, EconomyManager economyManager, VoteManager voteManager, MenuManager menuManager) {
        this.plugin = plugin;
        this.factionManager = factionManager;
        this.rankManager = rankManager;
        this.economyManager = economyManager;
        this.voteManager = voteManager;
        this.menuManager = menuManager;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        plugin.getCommand("scoreboard").setExecutor(new ScoreboardCommand(plugin, this));

        menuManager.addMenu("Scoreboard Menu", new ScoreboardMenu(this));

        this.playerScoreboard = new HashMap<>();
    }

    public void addPlayerScoreboard(Player player, Scoreboard scoreboard) {
        if(playerScoreboard.containsKey(player.getUniqueId())) {
            playerScoreboard.remove(player.getUniqueId());
            playerScoreboard.put(player.getUniqueId(), scoreboard);
        } else {
            playerScoreboard.put(player.getUniqueId(), scoreboard);
        }
    }

}
