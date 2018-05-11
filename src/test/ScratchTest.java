package test;

import static org.junit.Assert.*;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.junit.Test;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import main.actor.Actor;
import main.actor.CreatureClass;
import main.actor.CreatureRace;
import main.actor.Score;
import main.area.BackdropElement;
import main.area.MovementType;
import main.area.World;
import main.dice.Dice;
import main.dice.Die;

public class ScratchTest {

	@Test
	public void test() {
		Integer test = 9;
		System.out.println(Optional.ofNullable(test).orElse(0));
		System.out.println(Optional.ofNullable(null).orElse(0));
		assertTrue(true);
		System.out.println("-------------------------------------------------------------");
		Rectangle rect = new Rectangle(-1,-1,2,2);
		Point point = new Point(-2,-2);
		System.out.println(String.format("rect: x=%d, y=%d, w=%d, h=%d", rect.x, rect.y, rect.width, rect.height));
		rect.add(point);
		System.out.println(String.format("rect: x=%d, y=%d, w=%d, h=%d", rect.x, rect.y, rect.width, rect.height));
		
		Dice dice1 = new Dice(Die.Type.D20.create(2),Die.Type.D20.create(2));
		Dice dice2 = dice1.clone();
		System.out.println(dice1.getDie() == dice2.getDie());
		for (int i = 0; i < dice1.getDie().length; i++)
			System.out.println(dice1.getDie()[i] == dice2.getDie()[i]);
		
		System.out.println(ThreadLocalRandom.current().nextInt(0,1));
		
//		World world = new World(new Rectangle(0,0,50,50));
//		for (int i = 0; i < 50; i++) {
//			world.putBackdropElement(new Point(i,0), new BackdropElement(MovementType.NONE));
//			world.putBackdropElement(new Point(i,49), new BackdropElement(MovementType.NONE));
//			world.putBackdropElement(new Point(0,i), new BackdropElement(MovementType.NONE));
//			world.putBackdropElement(new Point(49,i), new BackdropElement(MovementType.NONE));
//		}
//		Dice scoreDice = Die.Type.D6.create(4);
//		Actor player = new Actor(
//				CreatureRace.HALF_ORC,
//				CreatureClass.FIGHTER,
//				new HashMap<Score.Type,Score>() {{
//					put(Score.Type.STRENGTH,new Score(Score.Type.STRENGTH,15));
//					put(Score.Type.DEXTERITY,new Score(Score.Type.DEXTERITY,12));
//					put(Score.Type.CONSTITUTION,new Score(Score.Type.CONSTITUTION,13));
//					put(Score.Type.INTELLIGENCE,new Score(Score.Type.INTELLIGENCE,8));
//					put(Score.Type.WISDOM,new Score(Score.Type.WISDOM,10));
//					put(Score.Type.CHARISMA,new Score(Score.Type.CHARISMA,14));
//				}}
//		);
//		Actor enemy1 = new Actor(CreatureRace.ELF,CreatureClass.WIZARD, new HashMap<Score.Type,Score>() {{put(Score.Type.STRENGTH,new Score(Score.Type.STRENGTH,15));put(Score.Type.DEXTERITY,new Score(Score.Type.DEXTERITY,12));put(Score.Type.CONSTITUTION,new Score(Score.Type.CONSTITUTION,13));put(Score.Type.INTELLIGENCE,new Score(Score.Type.INTELLIGENCE,8));put(Score.Type.WISDOM,new Score(Score.Type.WISDOM,10));put(Score.Type.CHARISMA,new Score(Score.Type.CHARISMA,14));}});
//		Actor enemy2 = new Actor(CreatureRace.DWARF,CreatureClass.CLERIC, new HashMap<Score.Type,Score>() {{put(Score.Type.STRENGTH,new Score(Score.Type.STRENGTH,15));put(Score.Type.DEXTERITY,new Score(Score.Type.DEXTERITY,12));put(Score.Type.CONSTITUTION,new Score(Score.Type.CONSTITUTION,13));put(Score.Type.INTELLIGENCE,new Score(Score.Type.INTELLIGENCE,8));put(Score.Type.WISDOM,new Score(Score.Type.WISDOM,10));put(Score.Type.CHARISMA,new Score(Score.Type.CHARISMA,14));}});
//		player.setAllegiance(Actor.Allegiance.PARTY);
//		enemy1.setAllegiance(Actor.Allegiance.ENEMY);
//		enemy2.setAllegiance(Actor.Allegiance.ENEMY);
//		world.putActor(player, new Point(5,5));
//		world.putActor(enemy1, new Point(15,10));
//		world.putActor(enemy2, new Point(10,15));
//		world.save();
//		world.load();
	}

}
