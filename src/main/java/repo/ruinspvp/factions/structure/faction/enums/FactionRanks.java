package repo.ruinspvp.factions.structure.faction.enums;

public enum FactionRanks {

    FOUNDER("Founder", 50, "F"),
    LEADER("Leader", 40, "L"),
    ADMIN("Admin", 30, "A"),
    MOD("Mod", 20, "M"),
    PLEB("Pleb", 10, "P");

    public String name;
    private int permLevel;
    public String abv;

    FactionRanks(String name, int permLevel, String abv) {
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
