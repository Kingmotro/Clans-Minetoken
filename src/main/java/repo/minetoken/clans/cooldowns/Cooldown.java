package repo.minetoken.clans.cooldowns;

import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import repo.minetoken.clans.utilities.Format;
import repo.minetoken.clans.utilities.utilTime;
import repo.minetoken.clans.utilities.utilTime.TimeUnit;

public class Cooldown {
	
	public static HashMap<String, AbilityCooldown> cooldownPlayers = new HashMap<String, AbilityCooldown>();
	

	
	@SuppressWarnings({ "deprecation", "unused" })
	public static void add(String player, String ability, long seconds, long systime) {
        if(!cooldownPlayers.containsKey(player)) cooldownPlayers.put(player, new AbilityCooldown(player));
        Player cPlayer = Bukkit.getPlayer(player);
      
        if(isCooling(player, ability)) return;
        
        cooldownPlayers.get(player).cooldownMap.put(ability, new AbilityCooldown(player, seconds * 1000, System.currentTimeMillis()));
        cPlayer.sendMessage(Format.cooldownUsed(ability));
	}
	
	public static boolean isCooling(String player, String ability) {
		if(!cooldownPlayers.containsKey(player)) return false;
		if(!cooldownPlayers.get(player).cooldownMap.containsKey(ability)) return false;
		return true;
		}
	
	 public static double getRemaining(String player, String ability) {
	        if(!cooldownPlayers.containsKey(player)) return 0.0;
	        if(!cooldownPlayers.get(player).cooldownMap.containsKey(ability)) return 0.0;
	        return utilTime.convert((cooldownPlayers.get(player).cooldownMap.get(ability).seconds + cooldownPlayers.get(player).cooldownMap.get(ability).systime) - System.currentTimeMillis(), TimeUnit.SECONDS, 1);
	    }
	 
	   public static void coolDurMessage(Player player, String ability) {
	        if(player == null) {
	            return;
	        }
	        if(!isCooling(player.getName(), ability)) {
	            return;
	        }
	        player.sendMessage(Format.cooldown(ability, getRemaining(player.getName(), ability)));
	    }
	   
	   @SuppressWarnings("deprecation")
	public static void removeCooldown(String player, String ability) {
	        if(!cooldownPlayers.containsKey(player)) {
	            return;
	        }
	        if(player == null){
	        	return;
	        }
	        if(!cooldownPlayers.get(player).cooldownMap.containsKey(ability)) {
	            return;
	        }
	        cooldownPlayers.get(player).cooldownMap.remove(ability);
	        Player cPlayer = Bukkit.getPlayer(player);
	        if(player != null) {
	            cPlayer.sendMessage(Format.cooldowndone(ability));
	            cPlayer.playSound(cPlayer.getLocation(), Sound.NOTE_PLING, 1, 3);
	            
	            
	         
	        }
	    }
	   
	   public static void handleCooldowns() {
	        if(cooldownPlayers.isEmpty()) {
	            return;
	        }
	        
	        for(Iterator<String> it = cooldownPlayers.keySet().iterator(); it.hasNext();) {
	            String key = it.next();
	            for(Iterator<String> iter = cooldownPlayers.get(key).cooldownMap.keySet().iterator(); iter.hasNext();) {
	                String name = iter.next();
	           
	                		
	                if(getRemaining(key, name) <= 0.0) {
	                    removeCooldown(key, name);
	                }
	            }
	        }
	    }
	
}