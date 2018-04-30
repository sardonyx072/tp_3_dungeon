package main.actor;

import main.dice.Dice;
import main.dice.Rollable;

public abstract class Roll {
	public enum Type {
		NORMAL (1),
		ADVANTAGE (1),
		DISADVANTAGE (-1);
		private int num;
		private Type(int num) {
			this.num = num;
		}
		public int getNum() {return this.num;}
	}
	protected Actor origin;
	protected Type type;
	protected Score.Type score;
	protected Dice dice;
	public Roll (Actor origin, Type type, Score.Type score, Dice dice) {
		this.origin = origin;
		this.type = type;
		this.score = score;
		this.dice = dice;
	}
	public void roll() {
		this.dice.roll();
	}
	public int getValue() {
		return this.dice.getValue() + this.origin.getAttackModifier(this.score);
	}
}
