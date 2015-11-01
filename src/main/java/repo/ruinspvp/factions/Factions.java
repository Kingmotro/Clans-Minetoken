package repo.ruinspvp.factions;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.structure.console.ConsoleManager;
import repo.ruinspvp.factions.structure.economy.EconomyManager;
import repo.ruinspvp.factions.structure.enchant.EnchantManager;
import repo.ruinspvp.factions.structure.faction.FactionManager;
import repo.ruinspvp.factions.structure.factioncenter.FactionCenterManager;
import repo.ruinspvp.factions.structure.inventory.MenuManager;
import repo.ruinspvp.factions.structure.npc.NPCManager;
import repo.ruinspvp.factions.structure.rank.RankManager;
import repo.ruinspvp.factions.structure.scoreboard.ScoreboardManager;
import repo.ruinspvp.factions.structure.shop.ShopManager;
import repo.ruinspvp.factions.structure.shop.am.menus.ArmoryMenu;
import repo.ruinspvp.factions.structure.voting.VoteManager;

public class Factions extends JavaPlugin {

    public static JavaPlugin instance;
    private static MenuManager menuManager;
    public Ruin ruin;

    @Override
    public void onEnable() {
        instance = this;

        ruin = Ruin.HUB;

        getCommand("test").setExecutor(new TestCommand(this));

        FactionManager factionManager = new FactionManager(this, ruin);
        RankManager rankManager = new RankManager(this, factionManager, ruin);
        EconomyManager economyManager = new EconomyManager(this, ruin);
        MenuManager menuManager = new MenuManager(this);
        VoteManager voteManager = new VoteManager(this, rankManager, economyManager);
        new ShopManager(this, economyManager, ruin, menuManager);
        new NPCManager(this);
        new EnchantManager(this);
        new ConsoleManager(this, rankManager, economyManager);
        new FactionCenterManager(this, factionManager, ruin, menuManager);
        new ScoreboardManager(this, factionManager, rankManager, economyManager, voteManager, menuManager);

        if (ruin == Ruin.HUB) {
            new Hub(this);
        }
    }

    @Override
    public void onDisable() {
        for (World world : Bukkit.getServer().getWorlds()) {
            for (Entity ent : world.getEntities()) {
                if (!(ent instanceof Player)) {
                    ent.remove();
                }
            }
        }
    }

    public static JavaPlugin getInstance() {
        return instance;
    }
}
