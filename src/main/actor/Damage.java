package main.actor;

import java.io.Serializable;

import main.dice.Dice;

public class Damage implements Cloneable, Serializable {
	private static final long serialVersionUID = -464423475176141679L;
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
	public Damage (Damage... damages) {
		
	}
	public Type getType() {return this.type;}
	public Dice getDice() {return this.dice;}
	public void roll() {this.dice.roll();}
	public int getValue() {return this.dice.getValue();}
	public Damage clone() {return new Damage(this.type,this.dice.clone());}
	public String toString() {return this.dice.toString() + " " + this.type.toString();}
}
