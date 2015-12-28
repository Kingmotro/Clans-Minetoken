package repo.minetoken.clans.structure.character.characters.check;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import repo.minetoken.clans.Clans;
import repo.minetoken.clans.structure.character.Characters;
import repo.minetoken.clans.structure.character.Skills;
import repo.minetoken.clans.structure.character.characters.Gatherer;
import repo.minetoken.clans.structure.character.characters.Guardian;
import repo.minetoken.clans.structure.character.characters.Thief;
import repo.minetoken.clans.structure.character.characters.Warrior;
import repo.minetoken.clans.structure.character.characters.Wizard;
import repo.minetoken.clans.structure.character.characters.check.version.VersionPlayer;
import repo.minetoken.clans.utilities.Format;
import repo.minetoken.clans.utilities.UtilSound;
import repo.minetoken.clans.utilities.UtilSound.Pitch;

import java.util.HashMap;

public class CheckCharacter implements Listener {

    private final HashMap<String, ItemStack[]> equipment = new HashMap<>();

    public CheckCharacter() {
        for (Player player : Clans.instance.getServer().getOnlinePlayers()) {
            equipment.put(player.getName(), player.getEquipment().getArmorContents());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onClick(InventoryClickEvent event) {
        if (event.getInventory().getType() == InventoryType.CRAFTING || event.getInventory().getType() == InventoryType.PLAYER) {
            if (event.getSlotType() == InventoryType.SlotType.ARMOR || event.isShiftClick()) {
                Player player = (Player) event.getWhoClicked();
                checkEquips(player);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            String name = event.getPlayer().getItemInHand().getType().name();
            if (name.contains("_CHESTPLATE") || name.contains("_LEGGINGS") || name.contains("_BOOTS") || name.contains("_HELMET"))
                checkEquips(event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDeath(PlayerDeathEvent event) {
        checkEquips(event.getEntity());
    }

    private void checkEquips(final Player player) {
        Bukkit.getServer().getScheduler().runTaskLater(Clans.getInstance(), new Runnable() {
            @Override
            public void run() {
                String id = new VersionPlayer(player).getIdString();
                ItemStack[] equips = player.getEquipment().getArmorContents();
                ItemStack[] previous = equipment.get(id);
                for (int i = 0; i < equips.length; i++) {
                    if (equips[i] == null && (previous != null && previous[i] != null)) {
                        Bukkit.getServer().getPluginManager().callEvent(new PlayerUnequipEvent(player, previous[i]));
                        checkCharacter(player);
                    } else if (equips[i] != null && (previous == null || previous[i] == null)) {
                        Bukkit.getServer().getPluginManager().callEvent(new PlayerEquipEvent(player, equips[i]));
                        checkCharacter(player);

                    } else if (previous != null && !equips[i].toString().equalsIgnoreCase(previous[i].toString())) {
                        Bukkit.getServer().getPluginManager().callEvent(new PlayerUnequipEvent(player, previous[i]));
                        Bukkit.getServer().getPluginManager().callEvent(new PlayerEquipEvent(player, equips[i]));
                        checkCharacter(player);
                    }
                }
                equipment.put(id, equips);
            }
        }, 1);
    }

    public static void checkCharacter(Player player) {
        if (player.getInventory().getBoots() == null ||
                player.getInventory().getLeggings() == null ||
                player.getInventory().getChestplate() == null ||
                player.getInventory().getHelmet() == null) {


            if (Characters.thief.containsKey(player.getName())) {
                none(player);
                Characters.thief.remove(player.getName());
            }


            if (Characters.wizard.containsKey(player.getName())) {

                none(player);
                Characters.wizard.remove(player.getName());
            }

            if (Characters.gatherer.containsKey(player.getName())) {

                none(player);
                Characters.gatherer.remove(player.getName());
            }

            if (Characters.guardian.containsKey(player.getName())) {

                none(player);
                Characters.guardian.remove(player.getName());
            }

            if (Characters.warrior.containsKey(player.getName())) {

                none(player);
                Characters.warrior.remove(player.getName());

            }
        }
        if (player.getInventory().getBoots() == null ||
                player.getInventory().getLeggings() == null ||
                player.getInventory().getChestplate() == null ||
                player.getInventory().getHelmet() == null) {
            return;
        }
        if (player.getInventory().getBoots().getType() == Material.LEATHER_BOOTS &&
                player.getInventory().getLeggings().getType() == Material.LEATHER_LEGGINGS &&
                player.getInventory().getChestplate().getType() == Material.LEATHER_CHESTPLATE &&
                player.getInventory().getHelmet().getType() == Material.LEATHER_HELMET && (!Characters.thief.containsKey(player.getName()))) {

            Skills.loadSkills(player);
            player.sendMessage(Format.main("Character", "You've put on: " + Format.highlight("Thief")));
            Characters.thief.put(player.getName(), new Thief());
            UtilSound.play(player, Sound.HORSE_ARMOR, Pitch.HIGH);
            UtilSound.play(player, Sound.NOTE_PLING, Pitch.HIGH);
        }
        if (player.getInventory().getBoots().getType() == Material.CHAINMAIL_BOOTS &&
                player.getInventory().getLeggings().getType() == Material.CHAINMAIL_LEGGINGS &&
                player.getInventory().getChestplate().getType() == Material.CHAINMAIL_CHESTPLATE &&
                player.getInventory().getHelmet().getType() == Material.CHAINMAIL_HELMET && (!Characters.gatherer.containsKey(player.getName()))) {

            Skills.loadSkills(player);
            player.sendMessage(Format.main("Character", "You've put on: " + Format.highlight("Gatherer")));
            Characters.gatherer.put(player.getName(), new Gatherer());
            UtilSound.play(player, Sound.HORSE_ARMOR, Pitch.HIGH);
            UtilSound.play(player, Sound.NOTE_PLING, Pitch.HIGH);
        }
        if (player.getInventory().getBoots().getType() == Material.IRON_BOOTS &&
                player.getInventory().getLeggings().getType() == Material.IRON_LEGGINGS &&
                player.getInventory().getChestplate().getType() == Material.IRON_CHESTPLATE &&
                player.getInventory().getHelmet().getType() == Material.IRON_HELMET) {
            if (Characters.guardian.containsKey(player.getName())) {
                return;
            }
            Skills.loadSkills(player);
            player.sendMessage(Format.main("Character", "You've put on: " + Format.highlight("Guardian")));
            Characters.guardian.put(player.getName(), new Guardian());
            UtilSound.play(player, Sound.HORSE_ARMOR, Pitch.HIGH);
            UtilSound.play(player, Sound.NOTE_PLING, Pitch.HIGH);
        }
        if (player.getInventory().getBoots().getType() == Material.GOLD_BOOTS &&
                player.getInventory().getLeggings().getType() == Material.GOLD_LEGGINGS &&
                player.getInventory().getChestplate().getType() == Material.GOLD_CHESTPLATE &&
                player.getInventory().getHelmet().getType() == Material.GOLD_HELMET) {
            if (Characters.wizard.containsKey(player.getName())) {
                return;
            }
            Skills.loadSkills(player);
            player.sendMessage(Format.main("Character", "You've put on: " + Format.highlight("Wizard")));
            Characters.wizard.put(player.getName(), new Wizard());
            UtilSound.play(player, Sound.HORSE_ARMOR, Pitch.HIGH);
            UtilSound.play(player, Sound.NOTE_PLING, Pitch.HIGH);
        }
        if (player.getInventory().getBoots().getType() == Material.DIAMOND_BOOTS &&
                player.getInventory().getLeggings().getType() == Material.DIAMOND_LEGGINGS &&
                player.getInventory().getChestplate().getType() == Material.DIAMOND_CHESTPLATE &&
                player.getInventory().getHelmet().getType() == Material.DIAMOND_HELMET) {
            if (Characters.warrior.containsKey(player.getName())) {
                return;
            }
            Skills.loadSkills(player);
            player.sendMessage(Format.main("Character", "You've put on: " + Format.highlight("Warrior")));
            listSkills(player, "Warrior");
            Characters.warrior.put(player.getName(), new Warrior());
            UtilSound.play(player, Sound.HORSE_ARMOR, Pitch.HIGH);
            UtilSound.play(player, Sound.NOTE_PLING, Pitch.HIGH);
        }

    }

    public static void listSkills(Player p, String name) {
        p.sendMessage(Format.main(name, ChatColor.GRAY + "Loading list of selected skills..."));
        p.sendMessage("    " + ChatColor.GREEN + ChatColor.ITALIC + name + "");
        if (name.equals("Warrior")) {
            p.sendMessage(" " + ChatColor.YELLOW + "Sword: " + Warrior.Sword(p));
            p.sendMessage(" " + ChatColor.YELLOW + "Axe: " + Warrior.Axe(p));
            p.sendMessage(" " + ChatColor.YELLOW + "Bow: " + "");
            p.sendMessage(" " + ChatColor.YELLOW + "Physical: " + "");
            p.sendMessage(" " + ChatColor.YELLOW + "Energy: " + "");
            return;
        }
    }

    private static void none(Player player) {
        Skills.loadSkills(player);
        player.sendMessage(Format.main("Character", "Status: " + ChatColor.RED + "NONE"));
        UtilSound.play(player, Sound.IRONGOLEM_DEATH, Pitch.VERY_HIGH);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent evt) {
        Player player = evt.getPlayer();
        if (Characters.thief.containsKey(player.getName())) {
            Characters.thief.remove(player.getName());
            return;
        }
        if (Characters.wizard.containsKey(player.getName())) {
            Characters.wizard.remove(player.getName());
            return;
        }
        if (Characters.gatherer.containsKey(player.getName())) {
            Characters.gatherer.remove(player.getName());
            return;
        }
        if (Characters.guardian.containsKey(player.getName())) {
            Characters.guardian.remove(player.getName());
            return;
        }
        if (Characters.warrior.containsKey(player.getName())) {
            Characters.warrior.remove(player.getName());
            return;
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent evt) {
        Player player = evt.getPlayer();
        checkEquips(player);

    }
}
