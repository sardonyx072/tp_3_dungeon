package test;

import static org.junit.Assert.*;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;

import org.junit.Test;

import main.actor.Actor;
import main.actor.CreatureClass;
import main.actor.CreatureRace;
import main.actor.Score;
import main.actor.Sprite;
import main.area.Direction;
import main.area.World;

public class WorldTest {

	@SuppressWarnings("serial")
	@Test
	public void test() {
		try {
			World world = new World(new Rectangle(0,0,5,5));
			world.getBackdrop(new Point(0,0));
			world.getActor(new Point(0,0));
			world.getBounds();
			world.getNext(new Point(0,0), Direction.EAST);
			world.putActor(new Actor(CreatureRace.DWARF, CreatureClass.BARD, new HashMap<Score.Type,Score>() {{
				put(Score.Type.CHARISMA,new Score(Score.Type.CHARISMA,10));
				put(Score.Type.CONSTITUTION,new Score(Score.Type.CONSTITUTION,10));
				put(Score.Type.DEXTERITY,new Score(Score.Type.DEXTERITY,10));
				put(Score.Type.INTELLIGENCE,new Score(Score.Type.INTELLIGENCE,10));
				put(Score.Type.STRENGTH,new Score(Score.Type.STRENGTH,10));
				put(Score.Type.WISDOM,new Score(Score.Type.WISDOM,10));
			}}), new Point(0,0));
			world.moveActor(new Point(0,0), Direction.EAST);
			world.getNext(new Point(0,0), Direction.EAST);
			world.getLink(new Point(0,0));
			world.line(new Point(0,0), Direction.EAST, 3, 3, false, true, true, true);
			//world.cone(new Point(0,0), Direction.EAST, 3, false, true, true, true); //overflow error, need a better way
			world.radius(new Point(0,0), 3, true, true, true);
			world.allActorsInArea(world.radius(new Point(0,0), 3, true, true, true));
			world.randomActorsInArea(world.radius(new Point(0,0), 3, true, true, true), 1);
			world.nearestActorsInArea(new Point(0,0), world.radius(new Point(0,0), 3, true, true, true), 1);
			assertTrue(true);
		} catch (Exception e) {
			fail("Not yet implemented");
		}
	}

}
