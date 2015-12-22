package repo.minetoken.clans.utilities;


import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class UtilHologram {

    public static void createAllPlayerHologram(Location loc, String string) {
        WorldServer s = ((CraftWorld)loc.getWorld()).getHandle();
        EntityArmorStand stand = new EntityArmorStand(s);

        stand.setLocation(loc.getX(), loc.getY(), loc.getZ(), 0, 0);
        stand.setInvisible(true);
        stand.setCustomName(string);
        stand.setCustomNameVisible(true);
        LivingEntity ent = (LivingEntity) stand.getBukkitEntity();
        ent.setRemoveWhenFarAway(false);

        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(stand);
        for(Player player : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public static void createPlayerHologram(Location loc, Player player, String string) {
        WorldServer s = ((CraftWorld)loc.getWorld()).getHandle();
        EntityArmorStand stand = new EntityArmorStand(s);

        stand.setLocation(loc.getX(), loc.getY(), loc.getZ(), 0, 0);
        stand.setInvisible(true);
        stand.setCustomName(string);
        stand.setCustomNameVisible(true);
        LivingEntity ent = (LivingEntity) stand.getBukkitEntity();
        ent.setRemoveWhenFarAway(false);

        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(stand);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }


}