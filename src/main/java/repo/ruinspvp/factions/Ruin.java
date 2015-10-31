package repo.ruinspvp.factions;

public enum Ruin {

    HUB("Hub"),
    AZTEC_MOUNTAIN("Aztec Mountain"),
    TEMPLARS_CASCADE("Templar's Cascade");

    public String name;

    Ruin(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
