package main.dice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public abstract class Die implements Rollable, Comparable<Die>, Cloneable, Serializable {
	private static final long serialVersionUID = -2851019617127659771L;
	public static enum Type {
		D1  (1),
		D2  (2),
		D3  (3),
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
		this.values = values == null || values.length == 0 || IntStream.of(values).anyMatch(i -> i<0) ? new int[] {0} : values;
		this.value = this.values.length-1;
	}
	public int compareTo(Die that) {return this.getValue()-that.getValue();}
	public Die[] getDie() {return new Die[] {this};}
	public abstract Die clone();
}
