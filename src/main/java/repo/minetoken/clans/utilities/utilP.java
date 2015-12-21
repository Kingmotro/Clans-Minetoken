package repo.minetoken.clans.utilities;


import java.util.HashMap;
import java.util.LinkedList;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class utilP
{	


	public static void message(Entity client, LinkedList<String> messageList)
	{
		message(client, messageList, false);
	}

	public static void message(Entity client, String message)
	{
		message(client, message, false);
	}

	public static void message(Entity client, LinkedList<String> messageList, boolean wiki)
	{
		for (String curMessage : messageList) {
			message(client, curMessage, wiki);
		}
	}

	public static void message(Entity client, String message, boolean wiki)
	{
		if (client == null) {
			return;
		}
		if (!(client instanceof Player)) {
			return;
		}
		((Player)client).sendMessage(message);
	}




	public static LinkedList<Player> getNearby(Location loc, double maxDist)
	{
		LinkedList<Player> nearbyMap = new LinkedList<Player>();
		for (Player cur : loc.getWorld().getPlayers()) {
			if (cur.getGameMode() != GameMode.CREATIVE) {
				if (!cur.isDead())
				{
					double dist = loc.toVector().subtract(cur.getLocation().toVector()).length();
					if (dist <= maxDist)
					{
						for (int i = 0; i < nearbyMap.size(); i++) {
							if (dist < loc.toVector().subtract(((Player)nearbyMap.get(i)).getLocation().toVector()).length())
							{
								nearbyMap.add(i, cur);
								break;
							}
						}
						if (!nearbyMap.contains(cur)) {
							nearbyMap.addLast(cur);
						}
					}
				}
			}
		}
		return nearbyMap;
	}


	public static Player getClosest(Location loc, Entity ignore)
	{
		Player best = null;
		double bestDist = 0.0D;
		for (Player cur : loc.getWorld().getPlayers()) {
			if (cur.getGameMode() != GameMode.CREATIVE) {
				if (!cur.isDead()) {
					if ((ignore == null) || (!ignore.equals(cur)))
					{
						double dist = utilMath.offset(cur.getLocation(), loc);
						if ((best == null) || (dist < bestDist))
						{
							best = cur;
							bestDist = dist;
						}
					}
				}
			}
		}
		return best;
	}

	public static void kick(Player player, String module, String message)
	{
		kick(player, module, message, true);
	}

	public static void kick(Player player, String module, String message, boolean log)
	{
		if (player == null) {
			return;
		}
		String out = ChatColor.RED + module + 
				ChatColor.WHITE + " - " + 
				ChatColor.YELLOW + message;
		player.kickPlayer(out);
		if (log) {
			System.out.println("Kicked Client [" + player.getName() + "] for [" + module + " - " + message + "]");
		}
	}

	public static HashMap<Player, Double> getInRadius(Location loc, double dR)
	{
		HashMap<Player, Double> players = new HashMap<Player, Double>();
		for (Player cur : loc.getWorld().getPlayers()) {
			if (cur.getGameMode() != GameMode.CREATIVE)
			{
				double offset = utilMath.offset(loc, cur.getLocation());
				if (offset < dR) {
					players.put(cur, Double.valueOf(1.0D - offset / dR));
				}
			}
		}
		return players;
	}

	public static void health(Player player, double mod)
	{
		if (player.isDead()) {
			return;
		}
		double health = player.getHealth() + mod;
		if (health < 0.0D) {
			health = 0.0D;
		}
		if (health > 20.0D) {
			health = 20.0D;
		}
		player.setHealth(health);
	}

	public static void hunger(Player player, int mod)
	{
		if (player.isDead()) {
			return;
		}
		int hunger = player.getFoodLevel() + mod;
		if (hunger < 0) {
			hunger = 0;
		}
		if (hunger > 20) {
			hunger = 20;
		}
		player.setFoodLevel(hunger);
	}


	public static String safeNameLength(String name)
	{
		if (name.length() > 16) {
			name = name.substring(0, 16);
		}
		return name;
	}
}
