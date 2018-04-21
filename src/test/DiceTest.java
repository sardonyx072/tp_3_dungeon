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
	public void testInitialValues() {
		Dice dice;
		Die die;
		die = new SimDie(null);
		int rolls = 100000, i = 0;
		do {
			assertEquals("null param initialized die to always roll 0",0,die.getValue());
			die.roll();
		} while (i++ < rolls);
		die = new SimDie(new int[] {-1});
		i = 0;
		do {
			assertEquals("only param negative initialized die to always roll 0",0,die.getValue());
			die.roll();
		} while (i++ < rolls);
		die = new SimDie(new int[] {1, 1, -1, 1});
		i = 0;
		do {
			assertEquals("one param negative initialized die to always roll 0",0,die.getValue());
			die.roll();
		} while (i++ < rolls);
		die = new SimDie(new int[] {0, 1});
		i = 0;
		do {
			assertEquals("param zero creates die normally",1,die.getValue());
			die.roll();
			assertEquals("param zero creates die normally",0,die.getValue());
			die.roll();
		} while (i++ < rolls);
		die = new RandomDie(null);
		i = 0;
		do {
			assertEquals("null param initialized die to always roll 0",0,die.getValue());
			die.roll();
		} while (i++ < rolls);
		die = new RandomDie(new int[] {-1});
		i = 0;
		do {
			assertEquals("only param negative initialized die to always roll 0",0,die.getValue());
			die.roll();
		} while (i++ < rolls);
		die = new RandomDie(new int[] {1, 1, -1, 1});
		i = 0;
		do {
			assertEquals("one param negative initialized die to always roll 0",0,die.getValue());
			die.roll();
		} while (i++ < rolls);
		die = new RandomDie(new int[] {0, 1});
		i = 0;
		do {
			assertEquals("param zero creates die normally",1,die.getValue());
			die.roll();
		} while (i++ < rolls);
		dice = new Dice(new Die[] {new SimDie(null)});
		i = 0;
		do {
			assertEquals("null param initialized die to always roll 0",0,dice.getValue());
			dice.roll();
		} while (i++ < rolls);
		dice = new Dice(new Die[] {new SimDie(new int[] {-1})});
		i = 0;
		do {
			assertEquals("only param negative initialized die to always roll 0",0,dice.getValue());
			dice.roll();
		} while (i++ < rolls);
		dice = new Dice(new Die[] {new SimDie(new int[] {1, 1, -1, 1})});
		i = 0;
		do {
			assertEquals("one param negative initialized die to always roll 0",0,dice.getValue());
			dice.roll();
		} while (i++ < rolls);
		dice = new Dice(new Die[] {new SimDie(new int[] {0, 1})});
		i = 0;
		do {
			assertEquals("param zero creates die normally",1,dice.getValue());
			dice.roll();
			assertEquals("param zero creates die normally",0,dice.getValue());
			dice.roll();
		} while (i++ < rolls);
		dice = new Dice(new Die[] {new RandomDie(null)});
		i = 0;
		do {
			assertEquals("null param initialized die to always roll 0",0,dice.getValue());
			dice.roll();
		} while (i++ < rolls);
		dice = new Dice(new Die[] {new RandomDie(new int[] {-1})});
		i = 0;
		do {
			assertEquals("only param negative initialized die to always roll 0",0,dice.getValue());
			dice.roll();
		} while (i++ < rolls);
		dice = new Dice(new Die[] {new RandomDie(new int[] {1, 1, -1, 1})});
		i = 0;
		do {
			assertEquals("one param negative initialized die to always roll 0",0,dice.getValue());
			dice.roll();
		} while (i++ < rolls);
		dice = new Dice(new Die[] {new RandomDie(new int[] {0, 1})});
		i = 0;
		do {
			assertEquals("param zero creates die normally",1,dice.getValue());
			dice.roll();
		} while (i++ < rolls);
	}

	@Test
	public void testGetTotalValue() {
		Dice dice;
		//presorted
		dice = new Dice(new Die[] {new SimDie(new int[] {1}),new SimDie(new int[] {2}),new SimDie(new int[] {3}),new SimDie(new int[] {4})});
		assertEquals("values match expected",10,dice.getValue());
		assertEquals("values match expected",10,dice.getValue(4));
		assertEquals("values match expected",10,dice.getValue(-4));
		assertEquals("values match expected",9,dice.getValue(3));
		assertEquals("values match expected",6,dice.getValue(-3));
		assertEquals("values match expected",7,dice.getValue(2));
		assertEquals("values match expected",3,dice.getValue(-2));
		assertEquals("values match expected",4,dice.getValue(1));
		assertEquals("values match expected",1,dice.getValue(-1));
		assertEquals("values match expected",0,dice.getValue(0));
		dice.roll();
		assertEquals("values match expected",10,dice.getValue());
		assertEquals("values match expected",10,dice.getValue(4));
		assertEquals("values match expected",10,dice.getValue(-4));
		assertEquals("values match expected",9,dice.getValue(3));
		assertEquals("values match expected",6,dice.getValue(-3));
		assertEquals("values match expected",7,dice.getValue(2));
		assertEquals("values match expected",3,dice.getValue(-2));
		assertEquals("values match expected",4,dice.getValue(1));
		assertEquals("values match expected",1,dice.getValue(-1));
		assertEquals("values match expected",0,dice.getValue(0));
		//reverse sorted (worst case scenario)
		dice = new Dice(new Die[] {new SimDie(new int[] {4}),new SimDie(new int[] {3}),new SimDie(new int[] {2}),new SimDie(new int[] {1})});
		assertEquals("values match expected",10,dice.getValue());
		assertEquals("values match expected",10,dice.getValue(4));
		assertEquals("values match expected",10,dice.getValue(-4));
		assertEquals("values match expected",9,dice.getValue(3));
		assertEquals("values match expected",6,dice.getValue(-3));
		assertEquals("values match expected",7,dice.getValue(2));
		assertEquals("values match expected",3,dice.getValue(-2));
		assertEquals("values match expected",4,dice.getValue(1));
		assertEquals("values match expected",1,dice.getValue(-1));
		assertEquals("values match expected",0,dice.getValue(0));
		dice.roll();
		assertEquals("values match expected",10,dice.getValue());
		assertEquals("values match expected",10,dice.getValue(4));
		assertEquals("values match expected",10,dice.getValue(-4));
		assertEquals("values match expected",9,dice.getValue(3));
		assertEquals("values match expected",6,dice.getValue(-3));
		assertEquals("values match expected",7,dice.getValue(2));
		assertEquals("values match expected",3,dice.getValue(-2));
		assertEquals("values match expected",4,dice.getValue(1));
		assertEquals("values match expected",1,dice.getValue(-1));
		assertEquals("values match expected",0,dice.getValue(0));
	}
	
	@Test
	public void testRolls() {
		Die die;
		int[] values;
		int rolls = 100000;
		values = new int[] {0,1,0,2,2,Integer.MAX_VALUE,10,4,3,2,1,0,9}; 
		die = new SimDie(values);
		for (int j = 0; j < rolls; j++)
			for (int i = 0; i < values.length; i++) {
				die.roll();
				assertEquals("correct values",values[i],die.getValue());
			}
		values = new int[] {0, 1};
		die = new RandomDie(values);
		for (int i = 0; i < rolls; i++) {
			die.roll();
			assertEquals("correct value",1,die.getValue());
		}
		values = new int[] {0, 0, 0, 0, 0, 0, 0, 1};
		die = new RandomDie(values);
		for (int i = 0; i < rolls; i++) {
			die.roll();
			assertEquals("correct value",7,die.getValue());
		}
		values = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 222};
		die = new RandomDie(values);
		for (int i = 0; i < rolls; i++) {
			die.roll();
			assertEquals("correct value",9,die.getValue());
		}
		values = new int[] {0, 0, 1, 1, 0, 0};
		die = new RandomDie(values);
		for (int i = 0; i < rolls; i++) {
			die.roll();
			assertTrue("correct value",2 <= die.getValue() && die.getValue() <= 3);
		}
		values = new int[] {0, 0, 0, 5, 1, 0, 999, 11, 0};
		int[] count;
		die = new RandomDie(values);
		for (int j = 0; j < rolls; j++) {
			count = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
			for (int i = 0; i < rolls; i++) {
				die.roll();
				assertTrue("correct value",3 == die.getValue() || 4 == die.getValue() || 6 == die.getValue() || 7 == die.getValue());
				count[die.getValue()]++;
			}
			assertTrue("none",count[0]==0);
			assertTrue("none",count[1]==0);
			assertTrue("none",count[2]==0);
			assertTrue("at least one",count[3]>0);
			assertTrue("at least one",count[4]>0);
			assertTrue("none",count[5]==0);
			assertTrue("at least one",count[6]>0);
			assertTrue("at least one",count[7]>0);
			assertTrue("more 6 than any other value", count[6]>count[3] && count[6]>count[4] && count[6]>count[7]);
		}

		die = new FairRandomDie(2);
		for (int j = 0; j < rolls; j++) {
			count = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
			for (int i = 0; i < rolls; i++) {
				die.roll();
				assertTrue("correct value",1 == die.getValue() || 2 == die.getValue());
				count[die.getValue()]++;
			}
			assertTrue("none",count[0]==0);
			assertTrue("at least one",count[1]>0);
			assertTrue("at least one",count[2]>0);
			assertTrue("none",count[3]==0);
			assertTrue("none",count[4]==0);
			assertTrue("none",count[5]==0);
			assertTrue("none",count[6]==0);
			assertTrue("none",count[7]==0);
			assertTrue("more 6 than any other value", Math.abs(count[1]-count[2])<100);
		}
	}
	
	@Test
	public void testCreateDiceFromArchetypes() {
		int rolls = 10000000;
		int[] count;
		Dice dice;
		Die.Type[] types = new Die.Type[] {Die.Type.D4, Die.Type.D6, Die.Type.D8, Die.Type.D10, Die.Type.D12, Die.Type.D20, Die.Type.D100};
		int[] max = new int[] {            4,           6,           8,           10,           12,           20,           100};
		int[] nums = new int[] {-1, 0, 1, 2, 10};
		for (int j = 0; j < types.length; j++) {
			for (int num : nums) {
				dice = types[j].create(num);
				count = new int[101*100];
				for (int i = 0; i < rolls; i++) {
					dice.roll();
					if (num <= 0)
						assertTrue("correct value",0==dice.getValue());
					else
						assertTrue("correct value",(1*num)<=dice.getValue() && dice.getValue()<=(max[j]*num));
					count[dice.getValue()]++;
				}
				if (num <= 0)
					assertTrue("at least one",count[0]==rolls);
				else
					for (int i = 0; i < count.length; i++)
						if ((1*num)<=i&&i<=(max[j]*num))
							assertTrue("at least one",count[i]>0);
						else
							assertTrue("none",count[i]==0);
			}
		}
	}
}
