package repo.ruinspvp.factions.structure.punish;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import repo.ruinspvp.factions.utilities.Format;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@SuppressWarnings("deprecation")
public class OffenceListener implements Listener {

	private static JavaPlugin plugin;
	private static Punish punish;

	@SuppressWarnings("static-access")
	public OffenceListener(Punish punish, JavaPlugin plugin) {
		this.plugin = plugin;
		this.punish = punish;
	}

	@EventHandler
	public void onPlayerChat(PlayerChatEvent event) {
		if (punish.mutedPlayers.contains(event.getPlayer().getUniqueId())) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(Format.main("Punish", "You can't talk in chat. You are currently muted!"));
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerLogin(final PlayerLoginEvent event) throws ParseException, SQLException {
		final Player player = event.getPlayer();

		ArrayList<String[]> punishments = new ArrayList<> ();
		punishments = punish.getPunishments(player.getUniqueId());
		if (punishments == null) {
			return;
		}
		for (final String[] punishment : punishments) {
			Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(punishment[5]);
			int Duration = Integer.valueOf(punishment[6]);

			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			if (Duration == -1) {
				cal.add(Calendar.YEAR, 100);
			} else {
				cal.add(Calendar.HOUR_OF_DAY, Duration);
			}
			Date endDate = cal.getTime();
			if (endDate.after(new Date()) && punishment[7].equals("")) {
				String type = punishment[1];
				System.out.println(event.getPlayer().getName() + " Punishment:" + type);
				if (type.equals("Chat Offense") || type.equals("Advertising") || type.equals("Permanent Mute")) {
					punish.mutedPlayers.add(player.getUniqueId());
					if (!(type.equals("Permanent Mute"))) {
						long seconds = (endDate.getTime() - new Date().getTime()) / 1000;
						BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
						scheduler.scheduleAsyncDelayedTask(plugin, new Runnable() {
							@Override
							public void run() {
								punish.mutedPlayers.remove(player);
							}
						}, seconds * 20L);
					}
				} else if (type.equals("Gameplay Offense") || type.equals("Hacking") || type.equals("Permanent Ban")) {
					if (!(type.equals("Permanent Ban"))) {
						if (endDate.getTime() <= new Date().getTime() / 1000) {
							event.allow();
						} else {
							event.disallow(Result.KICK_BANNED, Format.main("Punish", "You were banned by " + Format.highlight(punish.rankManager.fPlayer.getName(UUID.fromString(punishment[4])))
									+ ", because " + Format.highlight(punishment[3]) + "."
									+ "\n" + "Go to " + Format.highlight("http://www.thepyxel.com/forums")) + " to appeal.");
						}
					}
				}
			}

		}
	}

	@EventHandler
	public void onPlayerDisconnect(PlayerQuitEvent e) {
		punish.mutedPlayers.remove(e.getPlayer());
	}
}