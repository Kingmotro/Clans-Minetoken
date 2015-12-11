package repo.ruinspvp.clans.structure.character;

import org.bukkit.event.Listener;

public class Character implements Listener {

    public String name;
    public Skills[] skills;
    public String[] desc;

    public Character(String name, Skills[] skills, String[] desc) {
        this.name = name;
        this.skills = skills;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public Skills[] getSkills() {
        return skills;
    }

    public String[] getDesc() {
        return desc;
    }
}
