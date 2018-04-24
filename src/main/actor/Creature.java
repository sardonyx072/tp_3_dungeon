package main.actor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import main.dice.Dice;
import main.items.Accessory;
import main.items.Armor;
import main.items.Item;
import main.items.Weapon;

public class Creature extends Actor {
	private CreatureRace actorRace;
	private CreatureClass actorClass;
	private HashMap<AbilityScore.Type, AbilityScore> scores;
	private static final int MAX_LEVEL = 20;
	private int level, damage, temphp;
	private int[] hitdiceRolls;
	private Armor armor;
	private Weapon mainHand, offHand;
	private Accessory accessory1, accessory2, accessory3;
	private List<Item> inventory;
	public Creature(CreatureRace actorRace, CreatureClass actorClass, HashMap<AbilityScore.Type,AbilityScore> scores) {
		this.actorRace = actorRace;
		this.actorClass = actorClass;
		this.hitdiceRolls = new int[MAX_LEVEL];
		Dice dice = this.actorClass.getHitDice().clone();
		for (int i = 0; i < this.hitdiceRolls.length; i++) {
			dice.roll();
			this.hitdiceRolls[i] = dice.getValue();
		}
		this.scores = new HashMap<AbilityScore.Type,AbilityScore>();
		for(AbilityScore.Type type : AbilityScore.Type.values())
			this.scores.put(type,new AbilityScore(type,scores.get(type).getValue()+Optional.ofNullable(this.actorRace.getScoreIncreases().get(type)).orElse(0)));
		this.armor = Armor.Archetype.UNARMORED.create();
		this.mainHand = null;
		this.offHand = null;
		this.accessory1 = null;
		this.accessory2 = null;
		this.accessory3 = null;
		this.inventory = new ArrayList<Item>();
	}
	public CreatureRace getActorRace() {return this.actorRace;}
	public CreatureClass getActorClass() {return this.actorClass;}
	public int getLevel() {return this.level;}
	public void levelUp() {this.level++;}
	public int getProficiency() {return (this.getLevel()/4)+2;}
	public int getScore(AbilityScore.Type type) {return this.scores.get(type).getValue();}
	public int getModifier(AbilityScore.Type type) {return this.scores.get(type).getModifier();}
	public int getMaxHP() {return IntStream.of(Arrays.copyOfRange(this.hitdiceRolls,0,this.getLevel())).reduce(0, (sum,i)->sum+i+this.getModifier(AbilityScore.Type.CONSTITUTION));}
	public int getHP() {return Math.max(0, this.getMaxHP()-this.damage);}
	public int getTempHP() {return this.temphp;}
	public int getSpeed() {return this.actorRace.getSpeed();}
	public int getArmorClass() {return this.armor.getBase() + Math.min(this.armor.getDexModMax(), Math.max(this.armor.getDexModMin(), this.getModifier(AbilityScore.Type.DEXTERITY)));}
	public int getDifficultyClass() {return 8+this.getProficiency()+this.getModifier(this.actorClass.getSaveScore());}
	public int getAttackModifier(AbilityScore.Type type) {return this.getModifier(type) + (Arrays.asList(this.actorClass.getAttackProficiencies()).contains(type) ? this.getProficiency() : 0);}
	public int getSaveModifier(AbilityScore.Type type) {return this.getModifier(type) + (Arrays.asList(this.actorClass.getSaveProficiencies()).contains(type) ? this.getProficiency() : 0);}
	public int getCheckModifier(AbilityScore.Type type) {return this.getModifier(type) + (Arrays.asList(this.actorClass.getCheckProficiencies()).contains(type) ? this.getProficiency() : 0);}
	public Armor getArmor() {return this.armor;}
	public void equipArmor(Armor armor) {this.armor = this.armor==null && Arrays.asList(this.actorClass.getArmorTypes()).contains(armor.getType()) ? armor : this.armor;}
	public Armor unequipArmor() {
		Armor armor = this.armor;
		this.armor = Armor.Archetype.UNARMORED.create();
		return armor;
	}
	public Weapon getMainHand() {return this.mainHand;}
	public void equipMainHand (Weapon weapon) {this.mainHand = this.mainHand==null && Arrays.asList(this.actorClass.getWeaponTypes()).contains(weapon.getType()) ? weapon : this.mainHand;}
	public Weapon unequipMainHand() {
		Weapon weapon = this.mainHand;
		this.mainHand = Weapon.Archetype.UNARMED.create();
		return weapon;
	}
	public Weapon getOffhand() {return this.offHand;}
	public void equipOffHand (Weapon weapon) {this.offHand = this.offHand==null && Arrays.asList(this.actorClass.getWeaponTypes()).contains(weapon.getType()) ? weapon : this.offHand;}
	public Weapon unequipOffHand() {
		Weapon weapon = this.offHand;
		this.offHand = Weapon.Archetype.UNARMED.create();
		return weapon;
	}
	public Accessory getAccessory1() {return this.accessory1;}
	public void equipAccessory1(Accessory accessory) {this.accessory1 = this.accessory1==null ? accessory : this.accessory1;}
	public Accessory unequipAccessory1() {
		Accessory accessory = this.accessory1;
		this.accessory1 = null;
		return accessory;
	}
	public Accessory getAccessory2() {return this.accessory2;}
	public void equipAccessory2(Accessory accessory) {this.accessory2 = this.accessory2==null ? accessory : this.accessory2;}
	public Accessory unequipAccessory2() {
		Accessory accessory = this.accessory2;
		this.accessory2 = null;
		return accessory;
	}
	public Accessory getAccessory3() {return this.accessory3;}
	public void equipAccessory3(Accessory accessory) {this.accessory3 = this.accessory3==null ? accessory : this.accessory3;}
	public Accessory unequipAccessory3() {
		Accessory accessory = this.accessory3;
		this.accessory3 = null;
		return accessory;
	}
	public List<Item> getInventory() {return this.inventory;}
}