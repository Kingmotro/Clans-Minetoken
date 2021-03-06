package repo.minetoken.clans.structure.character.characters.check.version;


import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import repo.minetoken.clans.Clans;
import repo.minetoken.clans.utilities.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>This is a utility class for handling getting offline player IDs
 * without having to slowly query the Minecraft servers. This is
 * limited to only players who have played since MCCore has been
 * installed, but that shouldn't be a problem since it only takes one
 * login.</p>
 * <p/>
 * <p>The main usage of this class is the static methods.</p>
 */
public class PlayerUUIDs implements Listener
{

    private static final HashMap<String, UUID> ids   = new HashMap<String, UUID>();
    private static final HashMap<UUID, String> names = new HashMap<UUID, String>();

    private Config config;

    /**
     * <p>Sets up the listener to update player UUIDs</p>
     * <p>This class should not be instantiated on older servers
     * where UUIDs do not exist.</p>
     * <p/>
     * <p>MCCore sets this up by default so you should not
     * instantiate this yourself.</p>
     *
     * @param plugin plugin reference
     */
    public PlayerUUIDs(Clans plugin)
    {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        config = new Config(plugin, "uuid");

        // Load previous data
        ConfigurationSection section = config.getConfig();
        for (String key : section.getKeys(false))
        {
            UUID id = UUID.fromString(section.getString(key));
            ids.put(key.toLowerCase(), id);
            names.put(id, key);
        }
    }

    /**
     * <p>Saves the UUID data to the config</p>
     */
    public void save()
    {
        ConfigurationSection section = config.getConfig();
        for (Map.Entry<UUID, String> id : names.entrySet())
        {
            section.set(id.getValue(), id.getKey().toString());
        }
        config.save();
    }

    /**
     * <p>Updates the UUID of a player when they join</p>
     *
     * @param event event details
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        ids.put(event.getPlayer().getName().toLowerCase(), event.getPlayer().getUniqueId());
        names.put(event.getPlayer().getUniqueId(), event.getPlayer().getName());
    }

    /**
     * <p>Gets the UUID of the player with the given name</p>
     * <p>This is not case sensitive</p>
     *
     * @param name name of the player
     *
     * @return UUID of the player or null if not found
     */
    public static UUID getUUID(String name)
    {
        return ids.get(name.toLowerCase());
    }

    /**
     * <p>Gets the name of a player via their UUID</p>
     * <p>If the player hasn't played since MCCore was installed,
     * this will return null</p>
     *
     * @param id player UUID
     *
     * @return player name or null if not found
     */
    public static String getName(UUID id)
    {
        return names.get(id);
    }

    /**
     * <p>Gets an offline player by name</p>
     * </p>If the player hasn't played since MCCore was installed,
     * this returns null</p>
     *
     * @param name name of the player
     *
     * @return offline player or null if not found
     */
    public static OfflinePlayer getOfflinePlayer(String name)
    {
        UUID id = getUUID(name);
        if (id != null) return Bukkit.getServer().getOfflinePlayer(id);
        else return null;
    }

    /**
     * <p>Gets an offline player by name</p>
     * </p>If the player is not online, this returns null</p>
     *
     * @param name name of the player
     *
     * @return offline player or null if not found
     */
    public static Player getPlayer(String name)
    {
        UUID id = getUUID(name);
        if (id != null) return Bukkit.getServer().getPlayer(id);
        else return null;
    }
}