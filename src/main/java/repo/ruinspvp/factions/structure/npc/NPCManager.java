package repo.ruinspvp.factions.structure.npc;

import net.minecraft.server.v1_8_R3.EntityAgeable;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftAgeable;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import repo.ruinspvp.factions.utilities.Format;

import java.io.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;

public class NPCManager implements Listener {

    private HashMap<String, NPC> npcs;
    private HashMap<String, Integer> failedAttempts;
    private HashMap<String, NPC> addTempList;
    private HashSet<String> delTempList;

    public NPCManager(JavaPlugin plugin) {
        this.npcs = new HashMap();
        this.failedAttempts = new HashMap();
        this.addTempList = new HashMap();
        this.delTempList = new HashSet();

        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                NPCManager.this.UpdateNpcLocations();
            }
        }, 0L, 5L);

        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                NPCManager.this.ReattachNpcs();
            }
        }, 100L);

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        LoadNpcs();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void OnEntityDamage(EntityDamageEvent event) {
        if (this.npcs.containsKey(event.getEntity().getUniqueId().toString())) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void OnEntityTarget(EntityTargetEvent event) {
        if (this.npcs.containsKey(event.getEntity().getUniqueId().toString())) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void OnEntityCombust(EntityCombustEvent event) {
        if (this.npcs.containsKey(event.getEntity().getUniqueId().toString())) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void OnChunkLoad(ChunkLoadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            if (this.npcs.containsKey(entity.getUniqueId().toString())) {
                ((NPC) this.npcs.get(entity.getUniqueId().toString())).name = ((LivingEntity) entity).getCustomName();
                ((NPC) this.npcs.get(entity.getUniqueId().toString())).entity = entity;
                NPC.silent(entity);
                NPC.noAI(entity);

                if (((NPC) this.npcs.get(entity.getUniqueId().toString())).radius == 0) {
                    NPC.noAI(entity);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if ((event.getRightClicked() instanceof LivingEntity)) {
            if (this.addTempList.containsKey(event.getPlayer().getName())) {
                if (event.getRightClicked().getType() == EntityType.PLAYER) {
                    event.getPlayer().sendMessage(Format.main("NPC", "Failed to add npc.  Can't attach to player."));
                } else {
                    LivingEntity npc = (LivingEntity) event.getRightClicked();

                    if (((NPC) this.addTempList.get(event.getPlayer().getName())).name != null) {
                        npc.setCustomName(((NPC) this.addTempList.get(event.getPlayer().getName())).name);
                        npc.setCustomNameVisible(true);
                    }

                    npc.getEquipment().setArmorContents(event.getPlayer().getInventory().getArmorContents());
                    npc.getEquipment().setItemInHand(event.getPlayer().getItemInHand());
                    npc.setCanPickupItems(false);
                    ((EntityInsentient) ((CraftLivingEntity) npc).getHandle()).persistent = true;

                    createNPC(npc, (NPC) this.addTempList.get(event.getPlayer().getName()), true);
                    event.getPlayer().sendMessage(Format.main("NPC", "Added npc"));
                }

                this.addTempList.remove(event.getPlayer().getName());
            } else if (this.delTempList.contains(event.getPlayer().getName())) {
                if (DeleteNpc(event.getRightClicked())) {
                    event.getPlayer().sendMessage(Format.main("NPC", "Deleted npc."));
                } else {
                    event.getPlayer().sendMessage(Format.main("NPC", "Failed to delete npc.  That one isn't in the list."));
                }

                this.delTempList.remove(event.getPlayer().getName());
            }

            if (this.npcs.containsKey(event.getRightClicked().getUniqueId().toString())) {
                event.setCancelled(true);
                return;
            }
        }
    }

    public void SetNpcInfo(Player admin, int radius, String name, Location location) {
        this.addTempList.put(admin.getName(), new NPC(null, name, location, radius));
    }

    public Entity createNPC(EntityType entityType, int radius, String name, Location location) {
        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, entityType);

        entity.setCustomName(name);
        entity.setCustomNameVisible(true);

        entity.setCanPickupItems(false);
        entity.setRemoveWhenFarAway(false);
        ((EntityInsentient) ((CraftLivingEntity) entity).getHandle()).persistent = true;

        if ((((CraftLivingEntity) entity).getHandle() instanceof EntityAgeable)) {
            ((CraftAgeable) entity).getHandle().ageLocked = true;
        }

        return createNPC(entity, new NPC(entity, name, location, radius), true);
    }

    public Entity createNPC(LivingEntity entity, NPC npc, boolean save) {
        npc.entity = entity;
        this.npcs.put(entity.getUniqueId().toString(), npc);

        if (npc.radius == 0) {
            NPC.noAI(entity);
            NPC.silent(entity);
        }

        if (save) {
            SaveNpcs();
        }

        return entity;
    }

    public boolean DeleteNpc(Entity entity) {
        if ((entity instanceof LivingEntity)) {
            if (this.npcs.containsKey(entity.getUniqueId().toString())) {
                entity.remove();
                this.npcs.remove(entity.getUniqueId().toString());

                return true;
            }
        }

        return false;
    }

    public void PrepDeleteNpc(Player admin) {
        this.delTempList.add(admin.getName());
    }

    public void ClearNpcs() {
        Iterator<String> npcIterator = this.npcs.keySet().iterator();

        while (npcIterator.hasNext()) {
            String id = (String) npcIterator.next();

            if (((NPC) this.npcs.get(id)).entity != null) {
                ((NPC) this.npcs.get(id)).entity.remove();
            }
            npcIterator.remove();
        }

        SaveNpcs();
    }

    private void UpdateNpcLocations() {
        for (NPC npc : this.npcs.values()) {
            if (npc.entity != null) {

                npc.entity.setTicksLived(1);
                ((EntityInsentient) ((CraftLivingEntity) npc.entity).getHandle()).persistent = true;
                NPC.silent(npc.entity);

                if ((IsNpcChunkLoaded(npc.entity)) && ((npc.entity instanceof org.bukkit.craftbukkit.v1_8_R3.entity.CraftCreature))) {
                    if ((!npc.entity.isDead()) && (npc.entity.isValid())) {
                        String uuid = npc.entity.getUniqueId().toString();

                        ((LivingEntity) npc.entity).getEquipment().getArmorContents()[0].setDurability((short) 0);
                        ((LivingEntity) npc.entity).getEquipment().getArmorContents()[1].setDurability((short) 0);
                        ((LivingEntity) npc.entity).getEquipment().getArmorContents()[2].setDurability((short) 0);
                        ((LivingEntity) npc.entity).getEquipment().getArmorContents()[3].setDurability((short) 0);

                        if (!this.failedAttempts.containsKey(uuid)) {
                            this.failedAttempts.put(uuid, Integer.valueOf(0));
                        }
                        if (((Integer) this.failedAttempts.get(uuid)).intValue() >= 10) {
                            npc.entity.teleport(npc.location);
                            this.failedAttempts.put(uuid, Integer.valueOf(0));
                        } else if (!npc.isInRadius()) {
                            npc.returnWithinRaidus();
                            this.failedAttempts.put(uuid, Integer.valueOf(((Integer) this.failedAttempts.get(uuid)).intValue() + 1));
                        } else {
                            if (npc.isReturning()) {
                                npc.clearGoals();
                            }

                            this.failedAttempts.put(uuid, Integer.valueOf(0));
                        }
                    }
                }
            }
        }
    }

    public void TeleportNpcsHome() {
        for (NPC npc : this.npcs.values()) {
            if (npc.entity != null) {


                if (IsNpcChunkLoaded(npc.entity)) {


                    if ((!npc.entity.isDead()) && (npc.entity.isValid())) {
                        npc.entity.teleport(npc.location);
                        this.failedAttempts.put(npc.entity.getUniqueId().toString(), Integer.valueOf(0));
                    }
                }
            }
        }
    }

    public void ReattachNpcs() {
        for (Entity entity : getWorldType(World.Environment.NORMAL).getEntities()) {
            if (this.npcs.containsKey(entity.getUniqueId().toString())) {
                ((NPC) this.npcs.get(entity.getUniqueId().toString())).name = ((LivingEntity) entity).getCustomName();
                ((NPC) this.npcs.get(entity.getUniqueId().toString())).entity = entity;
            }
        }
    }

    public boolean IsNpcChunkLoaded(Entity entity) {
        return entity.getWorld().isChunkLoaded(entity.getLocation().getBlockX() >> 4, entity.getLocation().getBlockZ() >> 4);
    }

    public void LoadNpcs() {
        FileInputStream fstream = null;
        BufferedReader br = null;

        try {
            File npcFile = new File("npcs.dat");

            if (npcFile.exists()) {
                fstream = new FileInputStream(npcFile);
                br = new BufferedReader(new InputStreamReader(fstream));

                String line = br.readLine();

                while (line != null) {
                    UUID uuid = UUID.fromString(line.split(" ")[0]);
                    String location = line.split(" ")[1];
                    Integer radius = Integer.valueOf(Integer.parseInt(line.split(" ")[2]));

                    this.npcs.put(uuid.toString(), new NPC(null, null, strToLoc(location), radius.intValue()));

                    line = br.readLine();
                }
            }
        } catch (Exception e) {
            System.out.println(Format.main("NPC", "Error parsing npc file."));


            if (br != null) {
                try {
                    br.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            if (fstream != null) {
                try {
                    fstream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fstream != null) {
                try {
                    fstream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void SaveNpcs() {
        FileWriter fstream = null;
        BufferedWriter out = null;

        try {
            fstream = new FileWriter("npcs.dat");
            out = new BufferedWriter(fstream);

            for (String key : this.npcs.keySet()) {
                out.write(key + " " + locToStr(((NPC) this.npcs.get(key)).location) + " " + ((NPC) this.npcs.get(key)).radius);
                out.newLine();
            }

            out.close();
        } catch (Exception e) {
            System.err.println("Npc Save Error: " + e.getMessage());


            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            if (fstream != null) {
                try {
                    fstream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fstream != null) {
                try {
                    fstream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public NPC GetNpcByUUID(UUID uniqueId) {
        return (NPC) this.npcs.get(uniqueId.toString());
    }

    public static String locToStr(Location loc) {
        if (loc == null) {
            return "";
        }
        return loc.getWorld().getName() + "," + trim(1, loc.getX()) + "," + trim(1, loc.getY()) + "," + trim(1, loc.getZ());
    }

    public static String locToStrClean(Location loc) {
        if (loc == null) {
            return "Null";
        }
        return "(" + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + ")";
    }

    public static Location strToLoc(String string) {
        if (string.length() == 0) {
            return null;
        }
        String[] tokens = string.split(",");
        try {
            for (World cur : Bukkit.getServer().getWorlds()) {
                if (cur.getName().equalsIgnoreCase(tokens[0])) {
                    return new Location(cur, Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3]));
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public static World getWorldType(World.Environment env) {
        for (World cur : Bukkit.getServer().getWorlds()) {
            if (cur.getEnvironment() == env)
                return cur;
        }
        return null;
    }

    public static double trim(int degree, double d) {
        String format = "#.#";

        for (int i = 1; i < degree; i++) {
            format = format + "#";
        }
        DecimalFormat twoDForm = new DecimalFormat(format);
        return Double.valueOf(twoDForm.format(d));
    }

}
