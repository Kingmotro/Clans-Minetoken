package repo.minetoken.clans.structure.character.characters;

import repo.minetoken.clans.structure.character.Characters;
import repo.minetoken.clans.structure.character.Skills;
import repo.minetoken.clans.structure.character.skills.FireBall;
import repo.minetoken.clans.structure.character.skills.Rush;

public class Thief extends Characters {

    public Thief() {
        super("Thief", new Skills[] {new Rush(), new FireBall()}, new String[] {});
    }

}
