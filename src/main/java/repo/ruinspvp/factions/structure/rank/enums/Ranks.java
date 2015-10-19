package repo.ruinspvp.factions.structure.rank.enums;

import org.bukkit.ChatColor;

public enum Ranks {

    /**
     * Staff Ranks
     */
    LEADER("Leader", ChatColor.DARK_RED, 100),
    ADMIN("Admin", ChatColor.RED, 90),
    MOD("MOD", ChatColor.YELLOW, 80),
    ASSISTANT("Assistant", ChatColor.AQUA, 70),
    /**
     * Player Ranks
     */
    YOUTUBE("Youtube", ChatColor.RED, 60),
    FORGOTTEN("Forgotten", ChatColor.DARK_GREEN, 60),
    LOST("Lost", ChatColor.GREEN, 50),
    EXCAVATOR("Excavator", ChatColor.GOLD, 40),
    HUNTER("Hunter", ChatColor.BLUE, 30),
    PIONEER("Pioneer", ChatColor.GRAY, 20),
    DEFAULT("", ChatColor.WHITE, 10);

    public String name;
    private ChatColor color;
    private int permLevel;

    Ranks(String name, ChatColor color, int permLevel) {
        this.name = name;
        this.color = color;
        this.permLevel = permLevel;
    }

    public String getName() {
        return this.name;
    }

    public int getPermLevel() {
        return this.permLevel;
    }

    public String getTag(boolean bold, boolean uppercase) {
        String name = this.name;
        if (uppercase) {
            name = this.name.toUpperCase();
        }
        if (bold) {
            return this.color +""+ ChatColor.BOLD + name;
        }
        return ChatColor.RESET + "[" + this.color + name + ChatColor.RESET + "]";
    }
}
