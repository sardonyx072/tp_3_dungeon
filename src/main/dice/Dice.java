package main.dice;

import java.util.Arrays;

public class Dice implements Rollable {
	protected Die[] dice;
	public Dice(Die[] dice) { //TODO rollable
		this.dice = dice == null || dice.length == 0 ? new Die[] {new SimDie(new int[] {0})} : dice;
	}
	public void roll() {Arrays.asList(this.dice).stream().forEach(die -> die.roll());}
	public int getValue() {return this.getValue(this.dice.length);}
	public int getValue(int n) {
		Arrays.sort(this.dice);
		return Arrays.asList(Arrays.copyOfRange(this.dice, n < 0 ? 0 : Math.max(0,this.dice.length - n), n >= 0 ? this.dice.length : Math.min(this.dice.length,Math.abs(n)))).stream().mapToInt(die -> die.getValue()).sum();
	}
}
