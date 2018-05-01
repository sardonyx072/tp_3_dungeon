package main.dice;

import java.io.Serializable;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class Die implements Rollable, Comparable<Die>, Cloneable, Serializable {
	private static final long serialVersionUID = -2851019617127659771L;
	public static enum Type {
		D1 (1), D2 (2), D3 (3), D4 (4), D6 (6), D8 (8), D10 (10), D12 (12), D20 (20), D100 (100);
		private int sides;
		private Type(int sides) {this.sides = sides;}
		public Dice create(int n) {return new Dice(Stream.of(new Die[Math.max(0, n)]).map(die -> new FairRandomDie(this.sides)).toArray(Die[]::new));}
	}
	protected int[] values;
	protected int value;
	public Die(int[] values) {
		this.values = values == null || values.length == 0 || IntStream.of(values).anyMatch(i -> i<0) || IntStream.of(values).allMatch(i -> i==0) ? new int[] {0} : values;
		this.value = this.values.length-1;
	}
	public int compareTo(Die that) {return this.getValue()-that.getValue();}
	public boolean equals(Die that) {return this.values.equals(that.values) && this.value==that.value;}
	public Die[] getDie() {return new Die[] {this};}
	public abstract Die clone();
}
