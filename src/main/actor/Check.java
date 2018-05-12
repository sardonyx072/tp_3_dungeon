package main.actor;

import main.dice.Dice;

public class Check extends Roll {
	protected Score.Type contestType;
	protected Score.Type saveType;
	public Check (Actor origin, Type type, Score.Type checkType, Dice dice, Score.Type contestType, Score.Type saveType) {
		super(origin, type, checkType, dice);
		this.contestType = contestType;
		this.saveType = saveType;
	}
}
