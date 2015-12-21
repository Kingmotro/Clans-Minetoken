package repo.minetoken.clans.damage;

public class DamageChange
{
	private String source;
	private String reason;
	private double modifier;
	private boolean useReason;

	public DamageChange(String source, String reason, double modifier, boolean useReason)
	{
		this.source = source;
		this.reason = reason;
		this.modifier = modifier;
		this.useReason = useReason;
	}

	public String GetSource()
	{
		return this.source;
	}

	public String GetReason()
	{
		return this.reason;
	}

	public double GetDamage()
	{
		return this.modifier;
	}

	public boolean UseReason()
	{
		return this.useReason;
	}
}
