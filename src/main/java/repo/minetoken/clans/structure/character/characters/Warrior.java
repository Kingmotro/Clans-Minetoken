package repo.minetoken.clans.structure.character.characters;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import repo.minetoken.clans.structure.character.Characters;
import repo.minetoken.clans.structure.character.Skills;
import repo.minetoken.clans.structure.character.skills.FireBall;
import repo.minetoken.clans.structure.character.skills.Flash;
import repo.minetoken.clans.structure.character.skills.Fury;
import repo.minetoken.clans.structure.character.skills.Rush;
import repo.minetoken.clans.structure.character.skills.Swim;
import repo.minetoken.clans.structure.character.skills.BladeTrail;

public class Warrior extends Characters {

    public static HashMap<String, Warrior> GolemToss = new HashMap<String, Warrior>();
    public static HashMap<String, Warrior> BladeTrail = new HashMap<String, Warrior>();

    public static HashMap<String, Warrior> Earthshaker = new HashMap<String, Warrior>();
    public static HashMap<String, Warrior> Hammershock = new HashMap<String, Warrior>();
    public static HashMap<String, Warrior> Fierceblow = new HashMap<String, Warrior>();

    public Warrior() {
        super("Warrior", new Skills[]{new Rush(), new FireBall(), new Fury(), new Swim(), new Flash(), new BladeTrail()}, new String[]{});
    }

    public static String Sword(Player p) {
        if (GolemToss.containsKey(p.getName())) {
            name = ChatColor.WHITE + "Golem Toss";
        }
        if (BladeTrail.containsKey(p.getName())) {
            name = ChatColor.WHITE + "Blade Trail";
        }
        if (!BladeTrail.containsKey(p.getName()) && (!GolemToss.containsKey(p.getName()))) {
            name = ChatColor.WHITE + "None";
        }
        return name;
    }

    public static String Axe(Player p) {
        if (Earthshaker.containsKey(p.getName())) {
            name = ChatColor.WHITE + "Earthshaker";
        }
        if (Hammershock.containsKey(p.getName())) {
            name = ChatColor.WHITE + "Fury Slam";
        }
        if (Fierceblow.containsKey(p.getName())) {
            name = ChatColor.WHITE + "Fierce Blow";
        } else {
            name = ChatColor.WHITE + "None";
        }
        return name;
    }

}
