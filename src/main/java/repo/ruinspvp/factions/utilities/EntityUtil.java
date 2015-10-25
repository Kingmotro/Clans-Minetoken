package repo.ruinspvp.factions.utilities;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;

public class EntityUtil {

    public static void noAI(Entity ent) {
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) ent).getHandle();
        NBTTagCompound tag = nmsEntity.getNBTTag();
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        nmsEntity.c(tag);
        tag.setInt("NoAI", 1);
        nmsEntity.f(tag);
    }

    public static void silent(Entity ent) {
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) ent).getHandle();
        NBTTagCompound tag = nmsEntity.getNBTTag();
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        nmsEntity.c(tag);
        tag.setInt("Silent", 1);
        nmsEntity.f(tag);
    }
}
