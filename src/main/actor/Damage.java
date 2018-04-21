package main.actor;

import main.dice.Dice;
import main.dice.Rollable;

public class Damage implements Rollable {
	public static enum Type {
		ACID,
		BLUDGEONING,
		COLD,
		FIRE,
		FORCE,
		LIGHTNING,
		NECROTIC,
		PIERCING,
		POISON,
		RADIANT,
		SLASHING,
		THUNDER;
	}
	private Type type;
	private Dice dice;
	public Damage (Type type, Dice dice) {
		this.type = type;
		this.dice = dice;
	}
	public Type getType() {return this.type;}
	public Dice getDice() {return this.dice;}
	public void roll() {this.dice.roll();}
	public int getValue() {return this.dice.getValue();}
}
