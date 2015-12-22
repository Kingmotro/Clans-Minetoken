package repo.minetoken.clans.structure.character.skills;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import repo.minetoken.clans.structure.character.Characters;
import repo.minetoken.clans.structure.character.SkillType;
import repo.minetoken.clans.structure.character.Skills;
import repo.minetoken.clans.utilities.C;
import repo.minetoken.clans.utilities.Format;
import repo.minetoken.clans.utilities.UpdateEvent;
import repo.minetoken.clans.utilities.UpdateType;
import repo.minetoken.clans.utilities.utilAction;
import repo.minetoken.clans.utilities.utilAlgebra;
import repo.minetoken.clans.utilities.utilMath;
import repo.minetoken.clans.utilities.utilP;
import repo.minetoken.clans.utilities.utilWeapon;

public class BladeTrail extends Skills{
	public BladeTrail() {
		super("Blade Trail", new String[] {""}, SkillType.SWORD);
	}
	private HashSet<Player> active = new HashSet<Player>();
	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {
		Skills[] skill = Characters.getSkills();
		final Player player = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
			if(utilWeapon.hasSword(player)){
				if(!Characters.warrior.containsKey(player.getName())) {
					return;
				}

				active.add(player);
				utilP.message(player, Format.main("Wizard", "You prepared to use " + C.lpurple + "Evade" + ""));
			}
	}
	
	@EventHandler
	public void Energy(UpdateEvent event)
	{
		if (event.getType() != UpdateType.TICK) {
			return;
		}
		for (Player cur : Bukkit.getOnlinePlayers()) {
			if (active.contains(cur)) {

				if (!cur.isBlocking())
				{
					active.remove(cur);
				}       
				else
				{
					cur.getWorld().playEffect(cur.getLocation(), Effect.STEP_SOUND, 42);
					for (int i = 0; i <= 5; i++) {
						Pull(cur, cur.getEyeLocation().add(cur.getLocation().getDirection().multiply(i)));
					}
				}
			}

		}
	}

	public void Pull(Player player, Location loc)
	{
		for (Player other : utilP.getNearby(loc, 2.0D)) {
			if (!player.equals(other)) {
				if (utilMath.offset(player, other) >= 2.0D) {
					utilAction.velocity(other, utilAlgebra.getTrajectory2d(other, player), 
							0.2D, false, 0.0D, 0.0D, 1.0D, false);
				}
			}
		}
	}

	public void Reset(Player player)
	{
		active.remove(player);
	}
}
