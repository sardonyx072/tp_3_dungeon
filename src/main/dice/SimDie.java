package main.dice;

public class SimDie extends Die {
	public SimDie(int[] values) {
		super(values);
	}
	public void roll() {this.value = ++this.value == this.values.length ? 0 : this.value;}
	public int getValue() {return this.values[value];}
	@Override
	public SimDie clone() {
		SimDie dup = new SimDie(this.values.clone());
		dup.value = this.value;
		return dup;
	}
}
