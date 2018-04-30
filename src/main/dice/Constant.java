package main.dice;

public class Constant extends SimDie {
	private static final long serialVersionUID = -1837741849316147749L;
	public Constant (int value) {
		super(new int[] {value});
	}
	@Override
	public Constant clone() {
		return new Constant(this.getValue());
	}
}
