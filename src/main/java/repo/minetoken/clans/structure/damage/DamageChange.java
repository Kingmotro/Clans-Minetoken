package repo.minetoken.clans.structure.damage;

public class DamageChange {

    private String source;
    private String reason;
    private double modifier;
    private boolean useReason;

    public DamageChange(String source, String reason, double modifier, boolean useReason) {
        this.source = source;
        this.reason = reason;
        this.modifier = modifier;
        this.useReason = useReason;
    }

    public String getSource() {
        return this.source;
    }

    public String getReason() {
        return this.reason;
    }

    public double getDamage() {
        return this.modifier;
    }

    public boolean useReason() {
        return this.useReason;
    }
}
