package repo.ruinspvp.factions.utilities;

import org.bukkit.ChatColor;

public class Format {

    public static String main(String head, String string) {
        return ChatColor.YELLOW + "" + ChatColor.BOLD + head + ChatColor.GOLD + ">> "
                + ChatColor.RESET + "" + ChatColor.BOLD + string;
    }

    public static String highlight(String string) {
        return ChatColor.BLUE + string + ChatColor.GRAY;
    }
}
