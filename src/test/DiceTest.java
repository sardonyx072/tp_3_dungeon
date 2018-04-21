package test;

import static org.junit.Assert.*;

import org.junit.Test;

import main.dice.*;

public class DiceTest {
	
	@SuppressWarnings("unused")
	@Test
	public void testInheritance() {
		boolean ok = false;
		try {
			Rollable t1 = new SimDie(new int[] {0});
			Rollable t2 = new RandomDie(new int[] {0});
			Rollable t3 = new FairRandomDie(1);
			Die t4 = new SimDie(new int[] {0});
			Die t5 = new RandomDie(new int[] {0});
			Die t6 = new FairRandomDie(1);
			SimDie t7 = new SimDie(new int[] {0});
			RandomDie t8 = new RandomDie(new int[] {0});
			RandomDie t9 = new FairRandomDie(1);
			FairRandomDie t10 = new FairRandomDie(1);
			ok = true;
		} catch (Exception e) {}
		assertTrue(ok);
	}

	@Test
	public void testSimDie() {
		Dice dice = new Dice(new Die[] {new SimDie(new int[] {1}),new SimDie(new int[] {2}),new SimDie(new int[] {3}),new SimDie(new int[] {4})});
		System.out.println(dice.getValue(4));
		System.out.println(dice.getValue(3));
		System.out.println(dice.getValue(-3));
	}

}
