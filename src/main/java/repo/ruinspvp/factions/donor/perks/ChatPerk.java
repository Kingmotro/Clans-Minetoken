package repo.ruinspvp.factions.donor.perks;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.donor.DonorPerk;
import repo.ruinspvp.factions.structure.rank.enums.Ranks;
import repo.ruinspvp.factions.utilities.Format;

public class ChatPerk extends DonorPerk {

    public ChatPerk(JavaPlugin plugin) {
        super(plugin, "Chat Perk", "ruinspvp.d1", Ranks.DONOR1);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if(!player.hasPermission(getPermssion())) {
            player.sendMessage(Format.main("Perk", "Sorry only donors have this perk."));
            return;
        }

        String message = event.getMessage();
        String coloredMessage;

        if(message.contains("&0") || message.contains("&1") || message.contains("&2") || message.contains("&3") || message.contains("&4")
                || message.contains("&5") || message.contains("&6") || message.contains("&7") || message.contains("&8") || message.contains("&9")
                || message.contains("&a") || message.contains("&b") || message.contains("&c") || message.contains("&d") || message.contains("&e")
                || message.contains("&f") || message.contains("&m") || message.contains("&n") || message.contains("&l") || message.contains("&k")
                || message.contains("&o")) {
            coloredMessage = ChatColor.translateAlternateColorCodes('&', message);
            event.setMessage(coloredMessage);
        }

    }
}
