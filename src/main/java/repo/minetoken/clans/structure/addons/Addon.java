package repo.minetoken.clans.structure.addons;

import org.bukkit.event.Listener;
import repo.minetoken.clans.structure.clan.ClanManager;

/**
 * All rights reserved, any plugins made with this plugin
 * must be free, by downloading the plugin and using it
 * has an api you may not sell addons to this plugin.
 * Unless permission has been given to you, explictly saying
 * you have permission to sell addons for this plugin.
 **/
public class Addon implements Listener {

    public String name;
    public AddonManager manager;
    public ClanManager clanManager;

    public Addon(AddonManager manager, String name, ClanManager clanManager) {
        this.manager = manager;
        this.name = name;
    }

    public AddonManager getManager() {
        return manager;
    }

    public ClanManager getClanManager() {
        return clanManager;
    }
}
