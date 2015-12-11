package repo.minetoken.clans;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import repo.minetoken.clans.structure.addons.AddonManager;
import repo.minetoken.clans.donor.DonorManager;
import repo.minetoken.clans.structure.clan.ClanManager;
import repo.minetoken.clans.structure.console.ConsoleManager;
import repo.minetoken.clans.structure.disguise.DisguiseManager;
import repo.minetoken.clans.structure.economy.EconomyManager;
import repo.minetoken.clans.structure.enchant.EnchantManager;
import repo.minetoken.clans.structure.inventory.MenuManager;
import repo.minetoken.clans.structure.npc.NPCManager;
import repo.minetoken.clans.structure.punish.Punish;
import repo.minetoken.clans.structure.rank.RankManager;
import repo.minetoken.clans.structure.scoreboard.ScoreboardManager;
import repo.minetoken.clans.structure.voting.VoteManager;
import repo.minetoken.clans.structure.world.ExplosionsManager;
import repo.minetoken.clans.structure.character.CharacterManager;
import repo.minetoken.clans.structure.clancenter.ClansCenterManager;
import repo.minetoken.clans.structure.shop.ShopManager;

public class Clans extends JavaPlugin {

    public static JavaPlugin instance;

    ClanManager clanManager;
    RankManager rankManager;
    EconomyManager economyManager;
    MenuManager menuManager;
    VoteManager voteManager;
    ShopManager shopManager;
    NPCManager npcManager;
    EnchantManager enchantManager;
    ConsoleManager consoleManager;
    ClansCenterManager clansCenterManager;
    ScoreboardManager scoreboardManager;
    DonorManager donorManager;
    Punish punish;
    CharacterManager characterManager;
    ExplosionsManager explosionsManager;
	AddonManager addonManager;
    DisguiseManager disguiseManager;

    @Override
    public void onEnable() {
        instance = this;

        getCommand("test").setExecutor(new TestCommand(this));

        clanManager = new ClanManager(this);
        rankManager = new RankManager(this, clanManager);
        economyManager = new EconomyManager(this);
        menuManager = new MenuManager(this);
        voteManager = new VoteManager(this, rankManager, economyManager);
        shopManager =new ShopManager(this, economyManager, menuManager);
        npcManager = new NPCManager(this);
        enchantManager = new EnchantManager(this);
        consoleManager = new ConsoleManager(this, rankManager, economyManager);
        clansCenterManager = new ClansCenterManager(this, clanManager, menuManager);
        scoreboardManager = new ScoreboardManager(this, clanManager, rankManager, economyManager, voteManager, menuManager);
        donorManager = new DonorManager(this);
        punish = new Punish(this, rankManager);
        characterManager = new CharacterManager(this);
        explosionsManager = new ExplosionsManager(this);
        
        addonManager = new AddonManager(this);

        disguiseManager = new DisguiseManager(this);
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

    public ClanManager getClanManager() {
        return clanManager;
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

    public ClansCenterManager getClansCenterManager() {
        return clansCenterManager;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public DonorManager getDonorManager() {
        return donorManager;
    }
}
