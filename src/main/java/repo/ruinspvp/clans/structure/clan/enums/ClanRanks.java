package repo.ruinspvp.clans.structure.clan.enums;

public enum ClanRanks {

    FOUNDER("Founder", 50, "F"),
    LEADER("Leader", 40, "L"),
    ADMIN("Admin", 30, "A"),
    MOD("Mod", 20, "M"),
    PLEB("Pleb", 10, "P"),
    NONE("None", 0, "N");

    public String name;
    private int permLevel;
    public String abv;

    ClanRanks(String name, int permLevel, String abv) {
        this.name = name;
        this.permLevel = permLevel;
        this.abv = abv;
    }

    public String getName() {
        return name;
    }

    public int getPermLevel() {
        return permLevel;
    }

    public String getAbv() {
        return abv;
    }
}
