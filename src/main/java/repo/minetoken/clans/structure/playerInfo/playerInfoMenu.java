package repo.minetoken.clans.structure.playerInfo;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import repo.minetoken.clans.structure.inventory.Menu;
import repo.minetoken.clans.utilities.C;
import repo.minetoken.clans.utilities.ItemStackBuilder;
import repo.minetoken.clans.utilities.UtilSound;
import repo.minetoken.clans.utilities.UtilSound.Pitch;

public class playerInfoMenu extends Menu{

	public playerInfoMenu(String title, int size, Player p) {
		super("User Profile", 45);
		getInventory().setItem(4, setOwner(p, new ItemStack(Material.SKULL_ITEM, 1, (short)3), C.yellow + p.getName() + "'s Profile"));
		getInventory().setItem(9, new ItemStackBuilder(Material.DIAMOND).withName(C.green + "" + C.bold + "Credits").build());
	}

	@Override
	public void leftClick(Player player, ItemStack itemStack) {
		String itemName = getFriendlyName(itemStack);

        if(itemName == null) {
            return;
        }

        switch (itemName) {
            case "'s Profile":
            	player.closeInventory();
         
            	UtilSound.play(player, Sound.NOTE_PLING, Pitch.VERY_HIGH);
            	break;
            case "Credits":
            	player.closeInventory();
                break;
            case "Guardian":
            	player.closeInventory();
                break;
            case "Gatherer":
            	player.closeInventory();
                break;
            case "Theif":
            	player.closeInventory();
            
                break;        
        }
	}

	@Override
	public void rightClick(Player player, ItemStack itemStack) {
		 String itemName = getFriendlyName(itemStack);

	        if(itemName == null) {
	            return;
	        }

	}

	@Override
	public void sleftClick(Player player, ItemStack itemStack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void srightClick(Player player, ItemStack itemStack) {
		// TODO Auto-generated method stub
		
	}
	
	public ItemStack setOwner(Player p, ItemStack item, String name) {
		  SkullMeta meta = (SkullMeta) item.getItemMeta();
		  meta.setOwner(p.getName());
		
		  meta.setDisplayName(name);
		  item.setItemMeta(meta);
		  return item;
		}

}
