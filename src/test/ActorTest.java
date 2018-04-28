package test;

import static org.junit.Assert.*;

import org.junit.Test;

import main.actor.Actor;
import main.actor.CreatureClass;
import main.actor.CreatureRace;
import main.actor.Damage;
import main.actor.Score;
import main.dice.Die;
import main.items.Armor;
import main.items.Weapon;

import java.util.HashMap;

public class ActorTest {

	@SuppressWarnings("serial")
	@Test
	public void test() {
		Actor actor = new Actor(CreatureRace.DWARF, CreatureClass.BARD, new HashMap<Score.Type,Score>() {{
			put(Score.Type.CHARISMA,new Score(Score.Type.CHARISMA,10));
			put(Score.Type.CONSTITUTION,new Score(Score.Type.CONSTITUTION,10));
			put(Score.Type.DEXTERITY,new Score(Score.Type.DEXTERITY,10));
			put(Score.Type.INTELLIGENCE,new Score(Score.Type.INTELLIGENCE,10));
			put(Score.Type.STRENGTH,new Score(Score.Type.STRENGTH,10));
			put(Score.Type.WISDOM,new Score(Score.Type.WISDOM,10));
		}});
		try {
			actor.equipArmor(Armor.Archetype.LEATHER.create());
			actor.equipMainHand(Weapon.Archetype.DAGGER.create());
			actor.equipOffHand(Weapon.Archetype.DAGGER.create());
			actor.setAllegiance(Actor.Allegiance.PARTY);
			actor.getArmor();
			actor.getMainHand();
			actor.getOffhand();
			actor.getHP();
			actor.getMaxHP();
			actor.getArmorClass();
			actor.getActorRace();
			actor.getActorRace().getScoreIncreases();
			actor.getActorRace().getSpeed();
			actor.getActorClass();
			actor.getActorClass().getArmorTypes();
			actor.getActorClass().getAttackProficiencies();
			actor.getActorClass().getCheckProficiencies();
			actor.getActorClass().getSaveProficiencies();
			actor.getActorClass().getHitDice();
			actor.getActorClass().getSaveScore();
			actor.getActorClass().getScoreOrder();
			actor.getActorClass().getWeaponTypes();
			actor.getActorClass().isPlayable();
			actor.getAttackModifier(Score.Type.CHARISMA);
			actor.getCheckModifier(Score.Type.CHARISMA);
			actor.getSaveModifier(Score.Type.CHARISMA);
			actor.getAllegiance();
			actor.getID();
			actor.getInventory();
			actor.getLevel();
			actor.getProficiency();
			actor.levelUp();
			actor.takeDamage(new Damage(Damage.Type.ACID,Die.Type.D1.create(1)));
			assertTrue(true);
		} catch (Exception e) {
			fail("did nto contain functions");
		}
	}

}
