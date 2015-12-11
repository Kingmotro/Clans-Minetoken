package repo.ruinspvp.clans.structure.character;

import org.bukkit.Material;
import org.bukkit.event.Listener;

public class Skills implements Listener {

    public String name;
    public String[] desc;
    public SkillType type;

    public Skills(String name, String[] desc, SkillType type) {
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
}
