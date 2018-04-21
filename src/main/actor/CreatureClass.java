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
			new Weapon.Type[] {Weapon.Type.SIMPLE, Weapon.Type.MARTIAL}
		),
	ROGUE(
			true,
			Die.Type.D8.create(1),
			new AbilityScore.Type[] {AbilityScore.Type.CONSTITUTION, AbilityScore.Type.STRENGTH, AbilityScore.Type.CHARISMA, AbilityScore.Type.WISDOM, AbilityScore.Type.INTELLIGENCE, AbilityScore.Type.DEXTERITY},
			new Armor.Type[] {Armor.Type.UNARMORED,Armor.Type.LIGHT,Armor.Type.MEDIUM},
			new Weapon.Type[] {Weapon.Type.SIMPLE}
		),
	MONK(
			true,
			Die.Type.D12.create(1),
			new AbilityScore.Type[] {AbilityScore.Type.CHARISMA, AbilityScore.Type.INTELLIGENCE, AbilityScore.Type.WISDOM, AbilityScore.Type.DEXTERITY, AbilityScore.Type.STRENGTH, AbilityScore.Type.CONSTITUTION},
			new Armor.Type[] {Armor.Type.UNARMORED},
			new Weapon.Type[] {Weapon.Type.SIMPLE}
		),
	WIZARD(
			true,
			Die.Type.D6.create(1),
			new AbilityScore.Type[] {AbilityScore.Type.STRENGTH, AbilityScore.Type.DEXTERITY, AbilityScore.Type.CONSTITUTION, AbilityScore.Type.CHARISMA, AbilityScore.Type.WISDOM, AbilityScore.Type.INTELLIGENCE},
			new Armor.Type[] {Armor.Type.UNARMORED,Armor.Type.LIGHT},
			new Weapon.Type[] {Weapon.Type.MAGICAL}
		),
	CLERIC(
			true,
			Die.Type.D10.create(1),
			new AbilityScore.Type[] {AbilityScore.Type.DEXTERITY, AbilityScore.Type.CHARISMA, AbilityScore.Type.INTELLIGENCE, AbilityScore.Type.STRENGTH, AbilityScore.Type.CONSTITUTION, AbilityScore.Type.WISDOM},
			new Armor.Type[] {Armor.Type.UNARMORED, Armor.Type.LIGHT, Armor.Type.MEDIUM, Armor.Type.HEAVY},
			new Weapon.Type[] {Weapon.Type.SIMPLE, Weapon.Type.MARTIAL, Weapon.Type.MAGICAL}
		),
	BARD(
			true,
			Die.Type.D8.create(1),
			new AbilityScore.Type[] {AbilityScore.Type.WISDOM, AbilityScore.Type.CONSTITUTION, AbilityScore.Type.STRENGTH, AbilityScore.Type.INTELLIGENCE, AbilityScore.Type.DEXTERITY, AbilityScore.Type.CHARISMA},
			new Armor.Type[] {Armor.Type.UNARMORED,Armor.Type.LIGHT,Armor.Type.MEDIUM},
			new Weapon.Type[] {Weapon.Type.SIMPLE, Weapon.Type.MAGICAL}
		);
	private boolean isPlayable;
	private Dice hitDice;
	private AbilityScore.Type[] scoreOrder;
	private Armor.Type[] armorTypes;
	private Weapon.Type[] weaponTypes;
	private CreatureClass(boolean isPlayable, Dice hitDice, AbilityScore.Type[] scoreOrder, Armor.Type[] armorTypes, Weapon.Type[] weaponTypes) {
		this.isPlayable = isPlayable;
		this.hitDice = hitDice;
		this.scoreOrder = scoreOrder;
		this.armorTypes = armorTypes;
		this.weaponTypes = weaponTypes;
	}
	public boolean isPlayable() {return this.isPlayable;}
	public Dice gethitDice() {return this.hitDice;}
	public AbilityScore.Type[] getScoreOrder() {return this.scoreOrder;}
	public Armor.Type[] getArmorTypes() {return this.armorTypes;}
	public Weapon.Type[] getWeaponTypes() {return this.weaponTypes;}
}
