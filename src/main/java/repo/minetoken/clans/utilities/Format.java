package repo.minetoken.clans.utilities;

import org.bukkit.ChatColor;

public class Format {

    public static String main(String head, String string) {
            return ChatColor.RED + "[" + head + "] " + ChatColor.YELLOW + string;
    }
    
    public static String cooldown(String ability, double d) {
		return ChatColor.RED + "[Cooldown] " + ChatColor.YELLOW + "You must wait: " + 
				ChatColor.GREEN + d + "s " + ChatColor.YELLOW + "to use " + ChatColor.LIGHT_PURPLE + ability + ChatColor.YELLOW + "!";
	}
    
    public static String cooldownUsed(String skill, String ability) {
  		return ChatColor.RED + "["+ skill + "] " + ChatColor.YELLOW + "You used: " + ChatColor.LIGHT_PURPLE + ability + ChatColor.YELLOW + "!";
  	}
	
	public static String cooldowndone(String ability) {
		return ChatColor.RED + "[Cooldown] " + ChatColor.YELLOW + "Recharged: " +  ChatColor.LIGHT_PURPLE + ability + ChatColor.YELLOW + "!";
	}

    public static String highlight(String string) {
        return ChatColor.GREEN + string + ChatColor.YELLOW;
    }

    public static String info(String string) {
        return ChatColor.RED + ">> " + ChatColor.YELLOW + string;
    }

    public static String help(String string, String desc) {
        return ChatColor.RED + ">> " + ChatColor.YELLOW + string + ChatColor.GRAY +", "+ desc;
    }

    public static String desc(String head, String body) {
      return ChatColor.RED + head + ": " + ChatColor.YELLOW + body;
    }
}
