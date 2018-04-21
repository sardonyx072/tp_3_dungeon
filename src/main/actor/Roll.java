package main.actor;

import main.dice.Dice;
import main.dice.Rollable;

public abstract class Roll implements Rollable {
	protected Actor origin;
	protected AbilityScore.Type type;
	protected Dice dice;
	public Roll (Actor origin, AbilityScore.Type type, Dice dice) {
		this.origin = origin;
		this.type = type;
		this.dice = dice;
	}
}
