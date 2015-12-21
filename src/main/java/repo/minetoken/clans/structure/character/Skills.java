package repo.minetoken.clans.structure.character;

import java.io.File;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import repo.minetoken.clans.Clans;
import repo.minetoken.clans.structure.character.characters.Warrior;
import repo.minetoken.clans.utilities.Config;

public class Skills implements Listener {

	public String name;
	public String[] desc;
	public SkillType type;
	public Skills(String name, String[] desc, SkillType types) {
		this.name = name;
		this.desc = desc;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public String[] getDesc() {
		return desc;
	}

	public SkillType getType() {
		return type;
	}

	
	public static void saveSkill(Player player, String skill, String skillName){
		Config playerConfig = new Config(Clans.instance, "Players" + File.separator + player.getName());
		playerConfig.set(skill, skillName);
		playerConfig.save();
	}

	public static void loadSkills(Player player){
		Config playerConfig = new Config(Clans.instance, "Players" + File.separator + player.getName());
		
		String name = playerConfig.getString("Sword");
		if(playerConfig.equals(null)){
			saveSkill(player, "Sword", "None");
			saveSkill(player, "Axe", "None");
			return;
		}
		if (name.equals("Golem Toss")){
			Warrior.GolemToss.put(player.getName(), new Warrior());
		}
		if (name.equals("Blade Trail")){
			Warrior.BladeTrail.put(player.getName(), new Warrior());
		}
	}


}
