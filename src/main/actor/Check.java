package main.actor;

import main.dice.Dice;

public abstract class Check extends Roll {
	protected AbilityScore.Type contestType;
	protected AbilityScore.Type saveType;
	public Check (Actor origin, AbilityScore.Type checkType, Dice dice, AbilityScore.Type contestType, AbilityScore.Type saveType) {
		super(origin, checkType, dice);
		this.contestType = contestType;
		this.saveType = saveType;
	}
}