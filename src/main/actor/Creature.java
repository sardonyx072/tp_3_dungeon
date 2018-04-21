package main.actor;

import java.util.Arrays;
import java.util.HashMap;

import main.items.Accessory;
import main.items.Armor;
import main.items.Weapon;

public class Creature extends Actor {
	private CreatureRace actorRace;
	private CreatureClass actorClass;
	private HashMap<AbilityScore.Type, AbilityScore> scores;
	private Level level;
	private Score hitpoints, armorclass, savedifficulty;
	private Armor armor;
	private Weapon mainHand, offHand;
	private Accessory accessory1, accessory2, accessory3;
	public Creature(CreatureRace actorRace, CreatureClass actorClass) {
		this.actorRace = actorRace;
		this.actorClass = actorClass;
		
		this.mainHand = null;
		this.offHand = null;
	}
	public CreatureRace getActorRace() {return this.actorRace;}
	public CreatureClass getActorClass() {return this.actorClass;}
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
}
