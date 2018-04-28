package main.dice;

public class Constant extends SimDie {
	public Constant (int value) {
		super(new int[] {value});
	}
	@Override
	public Constant clone() {
		return new Constant(this.getValue());
	}
}
