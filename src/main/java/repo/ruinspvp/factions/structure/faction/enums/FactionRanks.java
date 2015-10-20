package repo.ruinspvp.factions.structure.faction.enums;

public enum FactionRanks {

    FOUNDER("Founder", 50),
    LEADER("Leader", 40),
    ADMIN("Admin", 30),
    MOD("Mod", 20),
    PLEB("Pleb", 10);

    public String name;
    private int permLevel;

    FactionRanks(String name, int permLevel) {
        this.name = name;
        this.permLevel = permLevel;
    }

    public String getName() {
        return name;
    }

    public int getPermLevel() {
        return permLevel;
    }
}
