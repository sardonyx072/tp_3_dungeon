package main.dice;

public class SimDie extends Die {
	public SimDie(int[] values) {
		super(values);
	}
	public void roll() {this.value = ++this.value == this.values.length ? 0 : this.value;}
	public int getValue() {return this.values[value];}
}
