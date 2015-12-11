package repo.ruinspvp.clans.utilities;

import org.bukkit.ChatColor;

public class Format {

    public static String main(String head, String string) {
            return ChatColor.RED + "[" + head + "] " + ChatColor.YELLOW + string;
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
}
