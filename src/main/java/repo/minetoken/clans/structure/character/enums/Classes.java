package repo.minetoken.clans.structure.character.enums;

public enum Classes {

    Thief("Thief", "F"),
    Wizard("Wizard", "L"),
    Warrior("Warrior", "A"),
    Guardian("Guardian", "M"),
    Gatherer("Gatherer", "P"),
    NONE("None", "N");

    public String name;
    public String abv;

    Classes(String name, String abv) {
        this.name = name;
        this.abv = abv;
    }

    public String getName() {
        return name;
    }

    public String getAbv() {
        return abv;
    }
}
