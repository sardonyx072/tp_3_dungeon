package main.actor;

import main.dice.Dice;

public abstract class Save extends Roll {
	public static enum OnSuccess {
		NOTHING,
		HALF_DAMAGE;
	}
	public Save (Actor origin, AbilityScore.Type saveType, Dice dice) {
		super(origin, saveType, dice);
	}
}
