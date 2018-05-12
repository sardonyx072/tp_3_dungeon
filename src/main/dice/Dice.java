package main.dice;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Stream;

public class Dice implements Rollable, Cloneable, Serializable {
	private static final long serialVersionUID = 1024354024436110477L;
	protected Die[] dice;
	public Dice(Rollable... dice) {
		this.dice = dice == null || dice.length == 0 ? new Die[] {new Constant(0)} : Stream.of(dice).flatMap(r -> Arrays.asList(r.getDie()).stream()).toArray(Die[]::new);
	}
	public void roll() {Arrays.asList(this.dice).stream().forEach(die -> die.roll());}
	public int getValue() {return this.getValue(this.dice.length);}
	public int getValue(int n) {
		Arrays.sort(this.dice);
		return Arrays.asList(Arrays.copyOfRange(this.dice, n < 0 ? 0 : Math.max(0,this.dice.length - n), n >= 0 ? this.dice.length : Math.min(this.dice.length,Math.abs(n)))).stream().mapToInt(die -> die.getValue()).sum();
	}
	public Die[] getDie() {return this.dice.clone();}
	// public void crunch(); compress random dice into a single random die using convolution
	public int getMinValue() {return Stream.of(this.dice).mapToInt(die -> die.getMinValue()).sum();}
	public int getMaxValue() {return Stream.of(this.dice).mapToInt(die -> die.getMaxValue()).sum();}
	public Dice clone() {return new Dice(Stream.of(this.dice).map(die -> die.clone()).toArray(Die[]::new));}
	public String toString() {return String.format("%d - %d", this.getMinValue(), this.getMaxValue());}
}
