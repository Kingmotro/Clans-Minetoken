package repo.minetoken.clans.structure.combat;

public class CombatD {

    private String name;
    private double dmg;

    public CombatD(String name, double dmg) {
        this.name = name;
        this.dmg = dmg;
    }

    public String getName() {
        return this.name;
    }

    public double getDamage() {
        return this.dmg;
    }
}

