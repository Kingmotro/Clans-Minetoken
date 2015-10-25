
package repo.ruinspvp.factions.utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class HologramUtil implements Listener {

    String[] lines = null;
    Location loc = null;
    ArmorStand stand = null;
    ArrayList<ArmorStand> stands = new ArrayList<>();
    double distanceBetweenStands = 0.24;
    Boolean active = false;
    Entity entityToFollow = null;
    Plugin plugin = null;
    ArrayList<HologramUtil> holoList = new ArrayList<>();

    public HologramUtil(Location location, String[] text, Plugin plugin){
        this.loc = location;
        this.lines = text;
        this.plugin = plugin;
        active = true;
        startHologram();
        holoList.add(this);

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPassengerDeath(EntityDeathEvent e){
        if (active)
            if (e.getEntity() == entityToFollow){
                this.deleteHologram();
            }
    }

    @EventHandler
    public void onPassengerTeleport(EntityTeleportEvent e){
        if (active)
            if (e.getEntity() == entityToFollow){
                this.stopFollowingEntity();
                e.setCancelled(true);
                final Entity ent = e.getEntity();
                final Location loc = e.getTo();
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        ent.teleport(loc);
                    }
                }, 10L);
                this.followEntity(ent);
            }
    }

    public List<ArmorStand> getEntities(){
        return this.stands;
    }

    public void moveHologram(Location location){
        if (active){
            loc = location;
            updateHologram(this);
        }
    }

    public void followEntity(Entity entity){
        entityToFollow = entity;
        updateHologram(this);
    }

    public void stopFollowingEntity(){
        entityToFollow = null;
        updateHologram(this);
    }

    public void deleteHologram(){
        if (active){
            lines = null;
            for (ArmorStand stand : stands){
                stand.remove();
            }

            active = false;
            holoList.remove(this);
            stands.clear();
        }
    }

    private void startHologram(){
        if (active){
            Integer lineID = -1;
            Location spawnLoc = loc;
            spawnLoc.setY(spawnLoc.getY() + distanceBetweenStands);
            ArmorStand stand = null;
            ArmorStand prevStand = null;
            for (String line : lines){
                spawnLoc.setY(spawnLoc.getY() - distanceBetweenStands);
                stand = loc.getWorld().spawn(spawnLoc, ArmorStand.class);
                stand.setRemoveWhenFarAway(false);
                stand.setVisible(false);
                stand.setSmall(true);
                stand.setBasePlate(false);
                stand.setGravity(false);
                stand.setArms(false);
                stand.setCustomNameVisible(true);
                stand.setCustomName(colorString(line));
                stands.add(stand);
                LivingEntity ent = stand;
                ent.setRemoveWhenFarAway(false);
                lineID++;
            }
        }
    }

    public void updateHologram(HologramUtil holo){
        if (active){
            for (ArmorStand stand : stands){
                stand.remove();
            }
            stands.clear();
            Location spawnLoc = loc;
            spawnLoc.setY(spawnLoc.getY() + distanceBetweenStands);
            ArmorStand stand = null;
            ArmorStand prevStand = null;
            for (String line : lines){
                spawnLoc.setY(spawnLoc.getY() - distanceBetweenStands);
                stand = loc.getWorld().spawn(spawnLoc, ArmorStand.class);
                stand.setRemoveWhenFarAway(false);
                stand.setVisible(false);
                stand.setSmall(true);
                stand.setBasePlate(false);
                stand.setGravity(false);
                stand.setArms(false);
                stand.setCustomNameVisible(true);
                stand.setCustomName(colorString(line));
                LivingEntity ent = stand;
                if (prevStand != null){
                    if (entityToFollow != null) {
                        stand.setPassenger(prevStand);
                    }
                }
                ent.setRemoveWhenFarAway(false);
                stands.add(stand);
                prevStand = stand;
            }
            if (entityToFollow != null) {
                entityToFollow.setPassenger(prevStand);
            }

        }
    }

    private String colorString(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}