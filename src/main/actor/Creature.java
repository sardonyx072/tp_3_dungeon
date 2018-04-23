package main.actor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import main.items.Accessory;
import main.items.Armor;
import main.items.Weapon;

public class Creature extends Actor {
	private CreatureRace actorRace;
	private CreatureClass actorClass;
	private HashMap<AbilityScore.Type, AbilityScore> scores;
	private static final int MAX_LEVEL = 20, MAX_SCORE = 20;
	private int level, hp, damage, dc;
	private int[] hitdiceRolls;
	private Armor armor;
	private Weapon mainHand, offHand;
	private Accessory accessory1, accessory2, accessory3;
	public Creature(CreatureRace actorRace, CreatureClass actorClass, HashSet<AbilityScore> scores) {
		this.actorRace = actorRace;
		this.actorClass = actorClass;
		this.hitdiceRolls = new int[MAX_LEVEL];
		for (int i = 0; i < this.hitdiceRolls.length; i++) {
			this.actorClass.gethitDice().roll();
			this.hitdiceRolls[i] = this.actorClass.gethitDice().getValue();
		}
		this.armor = Armor.Archetype.UNARMORED.create();
		this.mainHand = null;
		this.offHand = null;
		this.accessory1 = null;
		this.accessory2 = null;
		this.accessory3 = null;
	}
	public CreatureRace getActorRace() {return this.actorRace;}
	public CreatureClass getActorClass() {return this.actorClass;}
	public int getLevel() {return this.level;}
	public int getProficiency() {return (this.getLevel()/4)+2;}
	public int getScore(AbilityScore.Type type) {return this.scores.get(type).getValue();}
	public int getModifier(AbilityScore.Type type) {return this.scores.get(type).getModifier();}
	public int getMaxHP() {return this.hp;}
	public int getHP() {return Math.max(0, this.hp-this.damage);}
	public int getArmorClass() {return this.armor.getBase() + Math.min(this.armor.getDexModMax(), Math.max(this.armor.getDexModMin(), this.getModifier(AbilityScore.Type.DEXTERITY)));}
	public int getDifficultyClass() {return this.dc;}
	// armor
	public Armor getArmor() {return this.armor;}
	public void equipArmor(Armor armor) {this.armor = this.armor != null ? this.armor : armor;}
	public Armor unequipArmor() {
		Armor armor = this.armor;
		this.armor = null;
		return armor;
	}
	// weapons
	public Weapon getMainHand() {return this.mainHand;}
	public void equipMainHand (Weapon weapon) {this.mainHand = this.mainHand != null ? this.mainHand : weapon;}
	public Weapon unequipMainHand() {
		Weapon weapon = this.mainHand;
		this.mainHand = null;
		return weapon;
	}
	public Weapon getOffhand() {return this.offHand;}
	public void equipOffHand (Weapon weapon) {this.offHand = this.offHand != null || Arrays.asList(this.mainHand.getAttributes()).contains(Weapon.Attribute.TWO_HANDED) ? this.offHand : weapon;}
	public Weapon unequipOffHand() {
		Weapon weapon = this.offHand;
		this.offHand = null;
		return weapon;
	}
	// accessories
	public Accessory getAccessory1() {return this.accessory1;}
	public void equipAccessory1(Accessory accessory) {this.accessory1 = this.accessory1 != null ? this.accessory1 : accessory;}
	public Accessory unequipAccessory1() {
		Accessory accessory = this.accessory1;
		this.accessory1 = null;
		return accessory;
	}
	public Accessory getAccessory2() {return this.accessory2;}
	public void equipAccessory2(Accessory accessory) {this.accessory2 = this.accessory2 != null ? this.accessory2 : accessory;}
	public Accessory unequipAccessory2() {
		Accessory accessory = this.accessory2;
		this.accessory2 = null;
		return accessory;
	}
	public Accessory getAccessory3() {return this.accessory3;}
	public void equipAccessory3(Accessory accessory) {this.accessory3 = this.accessory3 != null ? this.accessory3 : accessory;}
	public Accessory unequipAccessory3() {
		Accessory accessory = this.accessory3;
		this.accessory3 = null;
		return accessory;
	}
}
