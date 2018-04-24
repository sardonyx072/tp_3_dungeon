package main.actor;

import main.dice.*;
import main.items.Armor;
import main.items.Weapon;

public enum CreatureClass {
	FIGHTER(
			true,
			Die.Type.D10.create(1),
			new AbilityScore.Type[] {AbilityScore.Type.INTELLIGENCE, AbilityScore.Type.WISDOM, AbilityScore.Type.DEXTERITY, AbilityScore.Type.CONSTITUTION, AbilityScore.Type.CHARISMA, AbilityScore.Type.STRENGTH},
			new Armor.Type[] {Armor.Type.UNARMORED, Armor.Type.LIGHT, Armor.Type.MEDIUM, Armor.Type.HEAVY},
			new Weapon.Type[] {Weapon.Type.SIMPLE, Weapon.Type.MARTIAL},
			new AbilityScore.Type[] {AbilityScore.Type.STRENGTH},
			new AbilityScore.Type[] {AbilityScore.Type.CHARISMA, AbilityScore.Type.STRENGTH},
			new AbilityScore.Type[] {AbilityScore.Type.CONSTITUTION, AbilityScore.Type.CHARISMA, AbilityScore.Type.STRENGTH},
			AbilityScore.Type.STRENGTH
		),
	ROGUE(
			true,
			Die.Type.D8.create(1),
			new AbilityScore.Type[] {AbilityScore.Type.CONSTITUTION, AbilityScore.Type.STRENGTH, AbilityScore.Type.CHARISMA, AbilityScore.Type.WISDOM, AbilityScore.Type.INTELLIGENCE, AbilityScore.Type.DEXTERITY},
			new Armor.Type[] {Armor.Type.UNARMORED,Armor.Type.LIGHT,Armor.Type.MEDIUM},
			new Weapon.Type[] {Weapon.Type.SIMPLE},
			new AbilityScore.Type[] {AbilityScore.Type.DEXTERITY},
			new AbilityScore.Type[] {AbilityScore.Type.INTELLIGENCE, AbilityScore.Type.DEXTERITY},
			new AbilityScore.Type[] {AbilityScore.Type.WISDOM, AbilityScore.Type.INTELLIGENCE, AbilityScore.Type.DEXTERITY},
			AbilityScore.Type.DEXTERITY
		),
	MONK(
			true,
			Die.Type.D12.create(1),
			new AbilityScore.Type[] {AbilityScore.Type.CHARISMA, AbilityScore.Type.INTELLIGENCE, AbilityScore.Type.WISDOM, AbilityScore.Type.DEXTERITY, AbilityScore.Type.STRENGTH, AbilityScore.Type.CONSTITUTION},
			new Armor.Type[] {Armor.Type.UNARMORED},
			new Weapon.Type[] {Weapon.Type.SIMPLE},
			new AbilityScore.Type[] {AbilityScore.Type.CONSTITUTION},
			new AbilityScore.Type[] {AbilityScore.Type.STRENGTH, AbilityScore.Type.CONSTITUTION},
			new AbilityScore.Type[] {AbilityScore.Type.DEXTERITY, AbilityScore.Type.STRENGTH, AbilityScore.Type.CONSTITUTION},
			AbilityScore.Type.CONSTITUTION
		),
	WIZARD(
			true,
			Die.Type.D6.create(1),
			new AbilityScore.Type[] {AbilityScore.Type.STRENGTH, AbilityScore.Type.DEXTERITY, AbilityScore.Type.CONSTITUTION, AbilityScore.Type.CHARISMA, AbilityScore.Type.WISDOM, AbilityScore.Type.INTELLIGENCE},
			new Armor.Type[] {Armor.Type.UNARMORED,Armor.Type.LIGHT},
			new Weapon.Type[] {Weapon.Type.MAGICAL},
			new AbilityScore.Type[] {AbilityScore.Type.INTELLIGENCE},
			new AbilityScore.Type[] {AbilityScore.Type.WISDOM, AbilityScore.Type.INTELLIGENCE},
			new AbilityScore.Type[] {AbilityScore.Type.CHARISMA, AbilityScore.Type.WISDOM, AbilityScore.Type.INTELLIGENCE},
			AbilityScore.Type.INTELLIGENCE
		),
	CLERIC(
			true,
			Die.Type.D10.create(1),
			new AbilityScore.Type[] {AbilityScore.Type.DEXTERITY, AbilityScore.Type.CHARISMA, AbilityScore.Type.INTELLIGENCE, AbilityScore.Type.STRENGTH, AbilityScore.Type.CONSTITUTION, AbilityScore.Type.WISDOM},
			new Armor.Type[] {Armor.Type.UNARMORED, Armor.Type.LIGHT, Armor.Type.MEDIUM, Armor.Type.HEAVY},
			new Weapon.Type[] {Weapon.Type.SIMPLE, Weapon.Type.MARTIAL, Weapon.Type.MAGICAL},
			new AbilityScore.Type[] {AbilityScore.Type.WISDOM},
			new AbilityScore.Type[] {AbilityScore.Type.CONSTITUTION, AbilityScore.Type.WISDOM},
			new AbilityScore.Type[] {AbilityScore.Type.STRENGTH, AbilityScore.Type.CONSTITUTION, AbilityScore.Type.WISDOM},
			AbilityScore.Type.WISDOM
		),
	BARD(
			true,
			Die.Type.D8.create(1),
			new AbilityScore.Type[] {AbilityScore.Type.WISDOM, AbilityScore.Type.CONSTITUTION, AbilityScore.Type.STRENGTH, AbilityScore.Type.INTELLIGENCE, AbilityScore.Type.DEXTERITY, AbilityScore.Type.CHARISMA},
			new Armor.Type[] {Armor.Type.UNARMORED,Armor.Type.LIGHT,Armor.Type.MEDIUM},
			new Weapon.Type[] {Weapon.Type.SIMPLE, Weapon.Type.MAGICAL},
			new AbilityScore.Type[] {AbilityScore.Type.CHARISMA},
			new AbilityScore.Type[] {AbilityScore.Type.DEXTERITY, AbilityScore.Type.CHARISMA},
			new AbilityScore.Type[] {AbilityScore.Type.INTELLIGENCE, AbilityScore.Type.DEXTERITY, AbilityScore.Type.CHARISMA},
			AbilityScore.Type.CHARISMA
		);
	private boolean isPlayable;
	private Dice hitDice;
	private AbilityScore.Type[] scoreOrder;
	private Armor.Type[] armorTypes;
	private Weapon.Type[] weaponTypes;
	private AbilityScore.Type[] attackProficiencies, saveProficiencies, checkProficiencies;
	private AbilityScore.Type saveScore;
	private CreatureClass(boolean isPlayable, Dice hitDice, AbilityScore.Type[] scoreOrder, Armor.Type[] armorTypes, Weapon.Type[] weaponTypes, AbilityScore.Type[] attackProficiencies, AbilityScore.Type[] saveProficiencies, AbilityScore.Type[] checkProficiencies, AbilityScore.Type saveScore) {
		this.isPlayable = isPlayable;
		this.hitDice = hitDice;
		this.scoreOrder = scoreOrder;
		this.armorTypes = armorTypes;
		this.weaponTypes = weaponTypes;
		this.attackProficiencies = attackProficiencies;
		this.saveProficiencies = saveProficiencies;
		this.checkProficiencies = checkProficiencies;
		this.saveScore = saveScore;
	}
	public boolean isPlayable() {return this.isPlayable;}
	public Dice getHitDice() {return this.hitDice;}
	public AbilityScore.Type[] getScoreOrder() {return this.scoreOrder;}
	public Armor.Type[] getArmorTypes() {return this.armorTypes;}
	public Weapon.Type[] getWeaponTypes() {return this.weaponTypes;}
	public AbilityScore.Type[] getAttackProficiencies() {return this.attackProficiencies;}
	public AbilityScore.Type[] getSaveProficiencies() {return this.saveProficiencies;}
	public AbilityScore.Type[] getCheckProficiencies() {return this.checkProficiencies;}
	public AbilityScore.Type getSaveScore() {return this.saveScore;}
}
