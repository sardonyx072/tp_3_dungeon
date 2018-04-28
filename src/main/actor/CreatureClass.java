package main.actor;

import main.dice.*;
import main.items.Armor;
import main.items.Weapon;

public enum CreatureClass {
	FIGHTER(
			true,
			Die.Type.D10.create(1),
			new Score.Type[] {Score.Type.INTELLIGENCE, Score.Type.WISDOM, Score.Type.DEXTERITY, Score.Type.CONSTITUTION, Score.Type.CHARISMA, Score.Type.STRENGTH},
			new Armor.Type[] {Armor.Type.UNARMORED, Armor.Type.LIGHT, Armor.Type.MEDIUM, Armor.Type.HEAVY},
			new Weapon.Type[] {Weapon.Type.SIMPLE, Weapon.Type.MARTIAL},
			new Score.Type[] {Score.Type.STRENGTH},
			new Score.Type[] {Score.Type.CHARISMA, Score.Type.STRENGTH},
			new Score.Type[] {Score.Type.CONSTITUTION, Score.Type.CHARISMA, Score.Type.STRENGTH},
			Score.Type.STRENGTH
		),
	ROGUE(
			true,
			Die.Type.D8.create(1),
			new Score.Type[] {Score.Type.CONSTITUTION, Score.Type.STRENGTH, Score.Type.CHARISMA, Score.Type.WISDOM, Score.Type.INTELLIGENCE, Score.Type.DEXTERITY},
			new Armor.Type[] {Armor.Type.UNARMORED,Armor.Type.LIGHT,Armor.Type.MEDIUM},
			new Weapon.Type[] {Weapon.Type.SIMPLE},
			new Score.Type[] {Score.Type.DEXTERITY},
			new Score.Type[] {Score.Type.INTELLIGENCE, Score.Type.DEXTERITY},
			new Score.Type[] {Score.Type.WISDOM, Score.Type.INTELLIGENCE, Score.Type.DEXTERITY},
			Score.Type.DEXTERITY
		),
	MONK(
			true,
			Die.Type.D12.create(1),
			new Score.Type[] {Score.Type.CHARISMA, Score.Type.INTELLIGENCE, Score.Type.WISDOM, Score.Type.DEXTERITY, Score.Type.STRENGTH, Score.Type.CONSTITUTION},
			new Armor.Type[] {Armor.Type.UNARMORED},
			new Weapon.Type[] {Weapon.Type.SIMPLE},
			new Score.Type[] {Score.Type.CONSTITUTION},
			new Score.Type[] {Score.Type.STRENGTH, Score.Type.CONSTITUTION},
			new Score.Type[] {Score.Type.DEXTERITY, Score.Type.STRENGTH, Score.Type.CONSTITUTION},
			Score.Type.CONSTITUTION
		),
	WIZARD(
			true,
			Die.Type.D6.create(1),
			new Score.Type[] {Score.Type.STRENGTH, Score.Type.DEXTERITY, Score.Type.CONSTITUTION, Score.Type.CHARISMA, Score.Type.WISDOM, Score.Type.INTELLIGENCE},
			new Armor.Type[] {Armor.Type.UNARMORED,Armor.Type.LIGHT},
			new Weapon.Type[] {Weapon.Type.MAGICAL},
			new Score.Type[] {Score.Type.INTELLIGENCE},
			new Score.Type[] {Score.Type.WISDOM, Score.Type.INTELLIGENCE},
			new Score.Type[] {Score.Type.CHARISMA, Score.Type.WISDOM, Score.Type.INTELLIGENCE},
			Score.Type.INTELLIGENCE
		),
	CLERIC(
			true,
			Die.Type.D10.create(1),
			new Score.Type[] {Score.Type.DEXTERITY, Score.Type.CHARISMA, Score.Type.INTELLIGENCE, Score.Type.STRENGTH, Score.Type.CONSTITUTION, Score.Type.WISDOM},
			new Armor.Type[] {Armor.Type.UNARMORED, Armor.Type.LIGHT, Armor.Type.MEDIUM, Armor.Type.HEAVY},
			new Weapon.Type[] {Weapon.Type.SIMPLE, Weapon.Type.MARTIAL, Weapon.Type.MAGICAL},
			new Score.Type[] {Score.Type.WISDOM},
			new Score.Type[] {Score.Type.CONSTITUTION, Score.Type.WISDOM},
			new Score.Type[] {Score.Type.STRENGTH, Score.Type.CONSTITUTION, Score.Type.WISDOM},
			Score.Type.WISDOM
		),
	BARD(
			true,
			Die.Type.D8.create(1),
			new Score.Type[] {Score.Type.WISDOM, Score.Type.CONSTITUTION, Score.Type.STRENGTH, Score.Type.INTELLIGENCE, Score.Type.DEXTERITY, Score.Type.CHARISMA},
			new Armor.Type[] {Armor.Type.UNARMORED,Armor.Type.LIGHT,Armor.Type.MEDIUM},
			new Weapon.Type[] {Weapon.Type.SIMPLE, Weapon.Type.MAGICAL},
			new Score.Type[] {Score.Type.CHARISMA},
			new Score.Type[] {Score.Type.DEXTERITY, Score.Type.CHARISMA},
			new Score.Type[] {Score.Type.INTELLIGENCE, Score.Type.DEXTERITY, Score.Type.CHARISMA},
			Score.Type.CHARISMA
		);
	private boolean isPlayable;
	private Dice hitDice;
	private Score.Type[] scoreOrder;
	private Armor.Type[] armorTypes;
	private Weapon.Type[] weaponTypes;
	private Score.Type[] attackProficiencies, saveProficiencies, checkProficiencies;
	private Score.Type saveScore;
	private CreatureClass(boolean isPlayable, Dice hitDice, Score.Type[] scoreOrder, Armor.Type[] armorTypes, Weapon.Type[] weaponTypes, Score.Type[] attackProficiencies, Score.Type[] saveProficiencies, Score.Type[] checkProficiencies, Score.Type saveScore) {
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
	public Score.Type[] getScoreOrder() {return this.scoreOrder;}
	public Armor.Type[] getArmorTypes() {return this.armorTypes;}
	public Weapon.Type[] getWeaponTypes() {return this.weaponTypes;}
	public Score.Type[] getAttackProficiencies() {return this.attackProficiencies;}
	public Score.Type[] getSaveProficiencies() {return this.saveProficiencies;}
	public Score.Type[] getCheckProficiencies() {return this.checkProficiencies;}
	public Score.Type getSaveScore() {return this.saveScore;}
}
