package repo.ruinspvp.clans.structure.rank.enums;

import org.bukkit.ChatColor;

public enum Ranks {

    /**
     * Staff Ranks
     */
    LEADER("Leader", "Leader", ChatColor.DARK_RED, 100),
    ADMIN("Admin", "Admin", ChatColor.RED, 90),
    MOD("MOD", "Mod", ChatColor.YELLOW, 80),
    ASSISTANT("Assistant", "Assistant", ChatColor.AQUA, 70),
    /**
     * Player Ranks
     */
    YOUTUBE("Youtube", "Youtube", ChatColor.RED, 60),
    DONOR5("Donor5", "D5", ChatColor.DARK_GREEN, 60),
    DONOR4("Donor4", "D4", ChatColor.GREEN, 50),
    DONOR3("Donor3", "D3", ChatColor.DARK_AQUA, 40),
    DONOR2("Donor2", "D2", ChatColor.DARK_BLUE, 30),
    DONOR1("Donor1", "D1", ChatColor.GRAY, 20),
    DEFAULT("", "None", ChatColor.WHITE, 10);

    public String name;
    public String commandName;
    private ChatColor color;
    private int permLevel;

    Ranks(String name, String commandName, ChatColor color, int permLevel) {
        this.name = name;
        this.commandName = commandName;
        this.color = color;
        this.permLevel = permLevel;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommandName() {
        return commandName;
    }

    public int getPermLevel() {
        return this.permLevel;
    }

    public String getTag(String name, boolean bold, boolean uppercase) {
        if (uppercase) {
            name = name.toUpperCase();
        }
        if (bold) {
            return this.color +""+ ChatColor.BOLD + name;
        }
        return ChatColor.RESET + "[" + this.color + name + ChatColor.RESET + "]";
    }
}
