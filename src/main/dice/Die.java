package main.dice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public abstract class Die implements Rollable, Comparable<Die> {
	public static enum Type {
		D4  (4),
		D6  (6),
		D8  (8),
		D10 (10),
		D12 (12),
		D20 (20),
		D100(100);
		private int sides;
		private Type(int sides) {this.sides = sides;}
		public Dice create(int n) {
			n = n < 0 ? 0 : n;
			List<Die> dice = new ArrayList<Die>();
			for (int i = 0; i < n; i++)
				dice.add(new FairRandomDie(this.sides));
			return new Dice(dice.toArray(new Die[dice.size()]));
		}
	}
	protected int[] values;
	protected int value;
	public Die(int[] values) {
		if (IntStream.of(values).anyMatch(i -> i<0))
			throw new IllegalArgumentException("Dice cannot roll negative numbers or have negative chance to roll numbers");
		this.values = values == null || values.length == 0 ? new int[] {0} : values;
		this.value = this.values.length-1;
	}
	public int compareTo(Die that) {return this.getValue()-that.getValue();}
}
