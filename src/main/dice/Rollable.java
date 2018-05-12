package main.dice;

public interface Rollable {
	public void roll();
	public int getValue();
	public int getMinValue();
	public int getMaxValue();
	public Die[] getDie();
}
