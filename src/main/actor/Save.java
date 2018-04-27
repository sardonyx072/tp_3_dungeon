package main.actor;

import main.dice.Dice;

public abstract class Save extends Roll {
	public static enum OnSuccess {
		NOTHING,
		HALF_DAMAGE;
	}
	public Save (Actor origin, Type type, AbilityScore.Type saveType, Dice dice) {
		super(origin, type, saveType, dice);
	}
}
