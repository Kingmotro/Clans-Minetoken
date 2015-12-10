package repo.minetoken.clans.structure.character.characters;

import repo.minetoken.clans.structure.character.Character;
import repo.minetoken.clans.structure.character.Skills;
import repo.minetoken.clans.structure.character.skills.FireBall;
import repo.minetoken.clans.structure.character.skills.Rush;

public class Warrior extends Character {

    public Warrior() {
        super("Warrior", new Skills[] {new Rush(), new FireBall()}, new String[] {});
    }

}
