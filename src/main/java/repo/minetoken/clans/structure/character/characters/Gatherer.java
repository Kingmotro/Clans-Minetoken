package repo.minetoken.clans.structure.character.characters;

import repo.minetoken.clans.structure.character.Characters;
import repo.minetoken.clans.structure.character.Skills;
import repo.minetoken.clans.structure.character.skills.FireBall;
import repo.minetoken.clans.structure.character.skills.Rush;

public class Gatherer extends Characters {

    public Gatherer() {
        super("Gatherer", new Skills[] {new Rush(), new FireBall()}, new String[] {});
    }

}
