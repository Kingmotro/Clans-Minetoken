package repo.ruinspvp.clans.structure.character.skills;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import repo.ruinspvp.clans.structure.character.SkillType;
import repo.ruinspvp.clans.structure.character.Skills;

public class Rush extends Skills {

    public Rush() {
        super("Rush", new String[] {""}, SkillType.SWORD);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        if(player.getItemInHand().getType().toString().toLowerCase().contains("sword")) {
            player.setVelocity(player.getLocation().getDirection().multiply(5.0));
            for(Entity entity : player.getNearbyEntities(5.0, 5.0, 5.0)) {
                entity.setVelocity(entity.getLocation().getDirection().multiply(-2.0));
            }
        }

    }

}
