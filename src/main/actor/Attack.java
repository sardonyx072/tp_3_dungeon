package main.actor;

import main.dice.Dice;

public abstract class Attack extends Roll {
	protected Damage damage;
	protected Damage criticalDamage;
	protected AbilityScore.Type saveType;
	protected Save.OnSuccess onSaveSuccess;
	public Attack (Actor origin, Type type, AbilityScore.Type score, Dice dice, Damage damage, AbilityScore.Type saveType, Save.OnSuccess onSaveSuccess) {
		super(origin, type, score, dice);
		this.damage = damage;
		this.criticalDamage = damage.clone();
		this.saveType = saveType;
		this.onSaveSuccess = onSaveSuccess;
	}
	@Override
	public void roll() {
		this.dice.roll();
		this.damage.roll();
		this.criticalDamage.roll();
	}
	public Damage getDamage() {
		return this.damage;
	}
}
