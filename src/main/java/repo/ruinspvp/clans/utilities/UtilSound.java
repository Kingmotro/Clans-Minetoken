package repo.ruinspvp.clans.utilities;

import net.minecraft.server.v1_8_R3.PacketPlayOutNamedSoundEffect;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.CraftSound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class UtilSound {

    public static void play(Player player, Sound sound) {
        play(player, sound, Pitch.NORMAL);
    }

    public static void play(Player player, Sound sound, Pitch pitch) {

        PacketPlayOutNamedSoundEffect packetPlayOutNamedSoundEffect = new PacketPlayOutNamedSoundEffect(
                CraftSound.getSound(sound),
                player.getLocation().getBlockX() + 0.5,
                player.getLocation().getBlockY() + 0.5,
                player.getLocation().getBlockZ() + 0.5,
                1f,
                pitch.getPitch());

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutNamedSoundEffect);

    }


    public enum Pitch {

        NORMAL(1f),
        HIGH(1.5f),
        VERY_HIGH(2f),
        LOW(0.5f),
        VERY_LOW(0.1f);

        private float pitch;

        Pitch(float pitch) {
            this.pitch = pitch;
        }

        public float getPitch() {
            return pitch;
        }
    }

}
