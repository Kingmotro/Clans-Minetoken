package repo.minetoken.clans.structure.character;

import java.util.HashMap;

import org.bukkit.event.Listener;

import repo.minetoken.clans.structure.character.characters.Gatherer;
import repo.minetoken.clans.structure.character.characters.Guardian;
import repo.minetoken.clans.structure.character.characters.Thief;
import repo.minetoken.clans.structure.character.characters.Warrior;
import repo.minetoken.clans.structure.character.characters.Wizard;

public class Characters implements Listener {

    public static String name;
    public static Skills[] skills;
    public String[] desc;
    
    public static HashMap<String, Warrior> warrior = new HashMap<String, Warrior>();
    public static HashMap<String, Wizard> wizard = new HashMap<String, Wizard>();
    public static HashMap<String, Guardian> guardian = new HashMap<String, Guardian>();
    public static HashMap<String, Gatherer> gatherer = new HashMap<String, Gatherer>();
    public static HashMap<String, Thief> thief = new HashMap<String, Thief>();
    public static HashMap<String, Characters> none = new HashMap<String, Characters>();
    
    public Characters(String name, Skills[] skills, String[] desc) {
        Characters.name = name;
        Characters.skills = skills;
        this.desc = desc;
    }

    public static String getName() {
        return name;
    }

    public static Skills[] getSkills() {
        return skills;
    }

    public String[] getDesc() {
        return desc;
    }
}