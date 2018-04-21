package main.actor;

import main.dice.Dice;

public abstract class Attack extends Roll {
	protected Damage damage;
	protected AbilityScore.Type saveType;
	protected Save.OnSuccess onSaveSuccess;
	public Attack (Actor origin, AbilityScore.Type attackType, Dice dice, Damage damage, AbilityScore.Type saveType, Save.OnSuccess onSaveSuccess) {
		super(origin, attackType, dice);
		this.damage = damage;
		this.saveType = saveType;
		this.onSaveSuccess = onSaveSuccess;
	}
}
