package main.dice;

import java.util.stream.IntStream;

public class SimDie extends Die {
	private static final long serialVersionUID = 6384621667113280089L;
	public SimDie(int[] values) {
		super(values);
	}
	public void roll() {this.value = ++this.value == this.values.length ? 0 : this.value;}
	public int getValue() {return this.values[value];}
	public int getMinValue() {return IntStream.of(this.values).min().getAsInt();}
	public int getMaxValue() {return IntStream.of(this.values).max().getAsInt();}
	@Override
	public SimDie clone() {
		SimDie dup = new SimDie(this.values.clone());
		dup.value = this.value;
		return dup;
	}
}
