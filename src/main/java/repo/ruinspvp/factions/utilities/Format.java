package repo.ruinspvp.factions.utilities;

import org.bukkit.ChatColor;

public class Format {

    public static String main(String head, String string) {
        return ChatColor.YELLOW + "" + ChatColor.BOLD + head + ChatColor.GOLD + "" + ChatColor.BOLD + ">> "
                + ChatColor.RESET + string;
    }

    public static String highlight(String string) {
        return ChatColor.BLUE + string + ChatColor.GRAY;
    }

    public static String info(String string) {
        return ChatColor.GOLD + "" + ChatColor.BOLD + ">> " + ChatColor.RESET + string;
    }

    public static String help(String string, String desc) {
        return ChatColor.GOLD + "" + ChatColor.BOLD + ">> " + ChatColor.RESET + string + ChatColor.GRAY +", "+ desc;
    }
}
