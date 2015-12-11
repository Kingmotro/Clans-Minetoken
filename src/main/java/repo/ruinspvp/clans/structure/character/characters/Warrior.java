package repo.ruinspvp.clans.structure.character.characters;

import repo.ruinspvp.clans.structure.character.Character;
import repo.ruinspvp.clans.structure.character.Skills;
import repo.ruinspvp.clans.structure.character.skills.Rush;

public class Warrior extends Character {

    public Warrior() {
        super("Warrior", new Skills[] {new Rush()}, new String[] {});
    }

}
