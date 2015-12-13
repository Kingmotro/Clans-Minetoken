package repo.minetoken.clans.structure.addons;

import org.bukkit.event.Listener;

/**
 * All rights reserved, any plugins made with this plugin
 * must be free, by downloading the plugin and using it
 * has an api you may not sell addons to this plugin.
 * Unless permission has been given to you, explictly saying
 * you have permission to sell addons for this plugin.
 **/
public class Addon implements Listener {

    public String name;

    public Addon(String name) {
        this.name = name;
    }
}
