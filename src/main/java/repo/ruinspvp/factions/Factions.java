package repo.ruinspvp.factions;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.donor.DonorManager;
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
    public Ruin ruin;

    FactionManager factionManager;
    RankManager rankManager;
    EconomyManager economyManager;
    MenuManager menuManager;
    VoteManager voteManager;
    ShopManager shopManager;
    NPCManager npcManager;
    EnchantManager enchantManager;
    ConsoleManager consoleManager;
    FactionCenterManager factionCenterManager;
    ScoreboardManager scoreboardManager;
    DonorManager donorManager;

    @Override
    public void onEnable() {
        instance = this;

        ruin = Ruin.HUB;

        getCommand("test").setExecutor(new TestCommand(this));

        factionManager = new FactionManager(this, ruin);
        rankManager = new RankManager(this, factionManager, ruin);
        economyManager = new EconomyManager(this, ruin);
        menuManager = new MenuManager(this);
        voteManager = new VoteManager(this, rankManager, economyManager);
        shopManager =new ShopManager(this, economyManager, ruin, menuManager);
        npcManager = new NPCManager(this);
        enchantManager = new EnchantManager(this);
        consoleManager = new ConsoleManager(this, rankManager, economyManager);
        factionCenterManager = new FactionCenterManager(this, factionManager, ruin, menuManager);
        scoreboardManager = new ScoreboardManager(this, factionManager, rankManager, economyManager, voteManager, menuManager);
        donorManager = new DonorManager(this);

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

    public Ruin getRuin() {
        return ruin;
    }

    public FactionManager getFactionManager() {
        return factionManager;
    }

    public RankManager getRankManager() {
        return rankManager;
    }

    public EconomyManager getEconomyManager() {
        return economyManager;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public VoteManager getVoteManager() {
        return voteManager;
    }

    public ShopManager getShopManager() {
        return shopManager;
    }

    public NPCManager getNpcManager() {
        return npcManager;
    }

    public EnchantManager getEnchantManager() {
        return enchantManager;
    }

    public ConsoleManager getConsoleManager() {
        return consoleManager;
    }

    public FactionCenterManager getFactionCenterManager() {
        return factionCenterManager;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public DonorManager getDonorManager() {
        return donorManager;
    }
}
