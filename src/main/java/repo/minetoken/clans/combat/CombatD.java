package repo.minetoken.clans.combat;

public class CombatD
{
  private String name;
  private double dmg;
  
  public CombatD(String name, double dmg)
  {
    this.name = name;
    this.dmg = dmg;
  }
  
  public String GetName()
  {
    return this.name;
  }
  
  public double GetDamage()
  {
    return this.dmg;
  }
}

