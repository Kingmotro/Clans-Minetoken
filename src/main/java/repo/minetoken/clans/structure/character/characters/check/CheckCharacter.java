package repo.minetoken.clans.structure.character.characters.check;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
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
import org.bukkit.plugin.Plugin;

import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import repo.minetoken.clans.Clans;
import repo.minetoken.clans.structure.character.CharacterManager;
import repo.minetoken.clans.structure.character.Characters;
import repo.minetoken.clans.structure.character.Skills;
import repo.minetoken.clans.structure.character.characters.Gatherer;
import repo.minetoken.clans.structure.character.characters.Guardian;
import repo.minetoken.clans.structure.character.characters.Thief;
import repo.minetoken.clans.structure.character.characters.Warrior;
import repo.minetoken.clans.structure.character.characters.Wizard;
import repo.minetoken.clans.structure.character.characters.check.Version.VersionPlayer;
import repo.minetoken.clans.utilities.C;
import repo.minetoken.clans.utilities.CHashMap;
import repo.minetoken.clans.utilities.Format;
import repo.minetoken.clans.utilities.UtilSound;
import repo.minetoken.clans.utilities.UtilSound.Pitch;

import java.util.HashMap;

/**
 * <p>A repeating task to check when players equip and unequip items</p>
 * <p>This is set up by MCCore already and should not be instantiated
 * by another plugin as it will cause duplicate results leading to
 * undesired behavior.</p>
 */
public class CheckCharacter implements Listener
{

    private final CHashMap<String, ItemStack[]> equipment = new CHashMap<String, ItemStack[]>();

    /**
     * <p>Creates a new listener for player equipment</p>
     * <p>You should not be instantiating this class as MCCore
     * handles it already.</p>
     *
     * @param plugin plugin reference
     */
    public CheckCharacter(CharacterManager characterManager)
    {

        // Load player equipment for equip events
        for (Player player : Clans.instance.getServer().getOnlinePlayers())
        {
            equipment.put(player.getName(), player.getEquipment().getArmorContents());
        }
    }

    /**
     * Listens for inventory events for changing equipment
     *
     * @param event event details
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onClick(InventoryClickEvent event)
    {
        if (event.getInventory().getType() == InventoryType.CRAFTING || event.getInventory().getType() == InventoryType.PLAYER)
        {
            if (event.getSlotType() == InventoryType.SlotType.ARMOR || event.isShiftClick())
            {
                Player player = (Player) event.getWhoClicked();
                checkEquips(player);
            }
        }
    }

    /**
     * Listens for right clicking with armor for changing equipment
     *
     * @param event event details
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event)
    {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            String name = event.getPlayer().getItemInHand().getType().name();
            if (name.contains("_CHESTPLATE") || name.contains("_LEGGINGS") || name.contains("_BOOTS") || name.contains("_HELMET"))
                checkEquips(event.getPlayer());
        }
    }

    /**
     * Listens for the player dying and losing their equipment
     *
     * @param event event details
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDeath(PlayerDeathEvent event)
    {
        checkEquips(event.getEntity());
    }

    /**
     * Runs a task one tick later that evaluates the player's equipment for any changes
     *
     * @param player player to evaluate
     */
    private void checkEquips(final Player player)
    {
    	Bukkit.getServer().getScheduler().runTaskLater(Clans.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
            	String id = new VersionPlayer(player).getIdString();
                ItemStack[] equips = player.getEquipment().getArmorContents();
                ItemStack[] previous = equipment.get(id);
                for (int i = 0; i < equips.length; i++)
                {
                    if (equips[i] == null && (previous != null && previous[i] != null)){
                        Bukkit.getServer().getPluginManager().callEvent(new PlayerUnequipEvent(player, previous[i]));
                        checkCharacter(player);
                    }else if (equips[i] != null && (previous == null || previous[i] == null)){
                    	Bukkit.getServer().getPluginManager().callEvent(new PlayerEquipEvent(player, equips[i]));
                    	checkCharacter(player);
                    
                    }else if (previous != null && !equips[i].toString().equalsIgnoreCase(previous[i].toString()))
                    {
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
		if(player.getInventory().getBoots() == null ||
				player.getInventory().getLeggings() == null ||
				player.getInventory().getChestplate() == null ||
				player.getInventory().getHelmet() == null){




			if(Characters.thief.containsKey(player.getName())){ 
				none(player);
				Characters.thief.remove(player.getName());
			}



			if(Characters.wizard.containsKey(player.getName())){  

				none(player);
				Characters.wizard.remove(player.getName());
			}

			if(Characters.gatherer.containsKey(player.getName())){  

				none(player);
				Characters.gatherer.remove(player.getName());
			}

			if(Characters.guardian.containsKey(player.getName())){ 

				none(player);
				Characters.guardian.remove(player.getName());
			}

			if(Characters.warrior.containsKey(player.getName())){ 

				none(player);
				Characters.warrior.remove(player.getName());

			}
		}
		if(player.getInventory().getBoots() == null ||
				player.getInventory().getLeggings() == null ||
				player.getInventory().getChestplate() == null ||
				player.getInventory().getHelmet() == null){
			return;
		}
		if(player.getInventory().getBoots().getType() == Material.LEATHER_BOOTS &&
				player.getInventory().getLeggings().getType() == Material.LEATHER_LEGGINGS &&
				player.getInventory().getChestplate().getType() == Material.LEATHER_CHESTPLATE && 
				player.getInventory().getHelmet().getType() == Material.LEATHER_HELMET && (!Characters.thief.containsKey(player.getName()))){

			Skills.loadSkills(player);
			player.sendMessage(Format.main("Character", "You've put on: " + C.green + "Thief"));
			Characters.thief.put(player.getName(), new Thief());
			UtilSound.play(player, Sound.HORSE_ARMOR, Pitch.HIGH);
			UtilSound.play(player, Sound.NOTE_PLING, Pitch.HIGH);
			player.setPlayerListName(C.white + "[" + C.gold + "Thief" + C.white + "] " + C.yellow + player.getName());
		}
		if(player.getInventory().getBoots().getType() == Material.CHAINMAIL_BOOTS &&
				player.getInventory().getLeggings().getType() == Material.CHAINMAIL_LEGGINGS &&
				player.getInventory().getChestplate().getType() == Material.CHAINMAIL_CHESTPLATE && 
				player.getInventory().getHelmet().getType() == Material.CHAINMAIL_HELMET && (!Characters.gatherer.containsKey(player.getName()))){

			Skills.loadSkills(player);
			player.sendMessage(Format.main("Character", "You've put on: " + C.green + "Gatherer"));
			Characters.gatherer.put(player.getName(), new Gatherer());
			UtilSound.play(player, Sound.HORSE_ARMOR, Pitch.HIGH);
			UtilSound.play(player, Sound.NOTE_PLING, Pitch.HIGH);
			player.setPlayerListName(C.white + "[" + C.gold + "Gatherer" + C.white + "] " + C.yellow + player.getName());
		}
		if(player.getInventory().getBoots().getType() == Material.IRON_BOOTS &&
				player.getInventory().getLeggings().getType() == Material.IRON_LEGGINGS &&
				player.getInventory().getChestplate().getType() == Material.IRON_CHESTPLATE && 
				player.getInventory().getHelmet().getType() == Material.IRON_HELMET){
			if(Characters.guardian.containsKey(player.getName())){
				return;
			}
			Skills.loadSkills(player);
			player.sendMessage(Format.main("Character", "You've put on: " + C.green + "Guardian"));
			Characters.guardian.put(player.getName(), new Guardian());
			UtilSound.play(player, Sound.HORSE_ARMOR, Pitch.HIGH);
			UtilSound.play(player, Sound.NOTE_PLING, Pitch.HIGH);
			player.setPlayerListName(C.white + "[" + C.gold + "Guardian" + C.white + "] " + C.yellow + player.getName());
		}
		if(player.getInventory().getBoots().getType() == Material.GOLD_BOOTS &&
				player.getInventory().getLeggings().getType() == Material.GOLD_LEGGINGS &&
				player.getInventory().getChestplate().getType() == Material.GOLD_CHESTPLATE && 
				player.getInventory().getHelmet().getType() == Material.GOLD_HELMET){
			if(Characters.wizard.containsKey(player.getName())){
				return;
			}
			Skills.loadSkills(player);
			player.sendMessage(Format.main("Character", "You've put on: " + C.green + "Wizard"));
			Characters.wizard.put(player.getName(), new Wizard());
			UtilSound.play(player, Sound.HORSE_ARMOR, Pitch.HIGH);
			UtilSound.play(player, Sound.NOTE_PLING, Pitch.HIGH);
			player.setPlayerListName(C.white + "[" + C.gold + "Wizard" + C.white + "] " + C.yellow + player.getName());
		}
		if(player.getInventory().getBoots().getType() == Material.DIAMOND_BOOTS &&
				player.getInventory().getLeggings().getType() == Material.DIAMOND_LEGGINGS &&
				player.getInventory().getChestplate().getType() == Material.DIAMOND_CHESTPLATE && 
				player.getInventory().getHelmet().getType() == Material.DIAMOND_HELMET){
			if(Characters.warrior.containsKey(player.getName())){
				return;
			}
			Skills.loadSkills(player);
			player.sendMessage(Format.main("Character", "You've put on: " + C.green + "Warrior"));
			listSkills(player, "Warrior");
			Characters.warrior.put(player.getName(), new Warrior());
			UtilSound.play(player, Sound.HORSE_ARMOR, Pitch.HIGH);
			UtilSound.play(player, Sound.NOTE_PLING, Pitch.HIGH);
			player.setPlayerListName(C.white + "[" + C.gold + "Warrior" + C.white + "] " + C.yellow + player.getName());
		}

	}
	
	public static void listSkills(Player p, String name){
		p.sendMessage(Format.main(name, C.gray + "Loading list of selected skills..."));
		p.sendMessage("    " + C.green + C.italic +name + "");
		if(name.equals("Warrior")){
		p.sendMessage(" " + C.yellow +"Sword: " + Warrior.Sword(p));
		p.sendMessage(" " + C.yellow +"Axe: " + Warrior.Axe(p));
		p.sendMessage(" " + C.yellow +"Bow: " + "");
		p.sendMessage(" " + C.yellow +"Physical: " + "");
		p.sendMessage(" " + C.yellow +"Energy: " + "");
		return;
		}
	}
	private static void none(Player player) {
		Skills.loadSkills(player);
		player.sendMessage(Format.main("Character", "Status: " + C.red + "NONE"));
		UtilSound.play(player, Sound.IRONGOLEM_DEATH, Pitch.VERY_HIGH); 
		player.setPlayerListName(C.white + "[" + C.red + "None" + C.white + "] " + C.yellow + player.getName());
	}

	@EventHandler//you can move later //nvm don't
	public void onLeave(PlayerQuitEvent evt){
		Player player = evt.getPlayer();
		if(Characters.thief.containsKey(player.getName())){
			Characters.thief.remove(player.getName());
			return;
		}
		if(Characters.wizard.containsKey(player.getName())){ 
			Characters.wizard.remove(player.getName());
			return;
		}
		if(Characters.gatherer.containsKey(player.getName())){ 
			Characters.gatherer.remove(player.getName());
			return;
		}
		if(Characters.guardian.containsKey(player.getName())){ 
			Characters.guardian.remove(player.getName());
			return;
		}
		if(Characters.warrior.containsKey(player.getName())){ 
			Characters.warrior.remove(player.getName());
			return;
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent evt){
		Player player = evt.getPlayer();
		checkEquips(player);
		
	}

	
}
