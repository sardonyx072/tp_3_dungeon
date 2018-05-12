package main.actor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import main.dice.Dice;
import main.items.Accessory;
import main.items.Armor;
import main.items.Item;
import main.items.Weapon;

public class Actor implements Serializable {
	private static final long serialVersionUID = 6889241461449781654L;
	public enum Allegiance {
		PARTY,
		FRIENDLY,
		NEUTRAL,
		ENEMY,
		NONE;
	}
	private UUID id;
	private CreatureRace actorRace;
	private CreatureClass actorClass;
	private HashMap<Score.Type, Score> scores;
	private static final int MAX_LEVEL = 20;
	private int level, damage, temphp;
	private int[] hitdiceRolls;
	private Armor armor;
	private Weapon mainHand, offHand;
	private Accessory accessory1, accessory2, accessory3;
	private List<Item> inventory;
	private Allegiance allegiance;
	public Actor(CreatureRace actorRace, CreatureClass actorClass, HashMap<Score.Type,Score> scores) {
		this.id = UUID.randomUUID();
		this.actorRace = actorRace;
		this.actorClass = actorClass;
		this.hitdiceRolls = new int[MAX_LEVEL];
		Dice dice = this.actorClass.getHitDice().clone();
		for (int i = 0; i < this.hitdiceRolls.length; i++) {
			dice.roll();
			this.hitdiceRolls[i] = dice.getValue();
		}
		this.level = 1;
		this.scores = new HashMap<Score.Type,Score>();
		// TODO needs to sort scores for class
		for(Score.Type type : Score.Type.values())
			this.scores.put(type,new Score(type,scores.get(type).getValue()+Optional.ofNullable(this.actorRace.getScoreIncreases().get(type)).orElse(0)));
		this.armor = null;
		this.mainHand = null;
		this.offHand = null;
		this.accessory1 = null;
		this.accessory2 = null;
		this.accessory3 = null;
		this.inventory = new ArrayList<Item>();
		this.allegiance = Allegiance.NEUTRAL;
	}
	public Actor(Actor actor) {
		this.id = actor.id;
		this.actorRace = actor.actorRace;
		this.actorClass = actor.actorClass;
		this.scores = actor.scores;
		this.level = actor.level;
		this.damage = actor.damage;
		this.temphp = actor.temphp;
		this.hitdiceRolls = actor.hitdiceRolls;
		this.armor = actor.armor;
		this.mainHand = actor.mainHand;
		this.offHand = actor.offHand;
		this.accessory1 = actor.accessory1;
		this.accessory2 = actor.accessory2;
		this.accessory3 = actor.accessory3;
		this.inventory = actor.inventory;
		this.allegiance = actor.allegiance;
	}
	public CreatureRace getActorRace() {return this.actorRace;}
	public CreatureClass getActorClass() {return this.actorClass;}
	public int getLevel() {return this.level;}
	public void levelUp() {this.level++;}
	public int getProficiency() {return (this.getLevel()/4)+2;}
	public int getScore(Score.Type type) {return this.scores.get(type).getValue();}
	public int getModifier(Score.Type type) {return this.scores.get(type).getModifier();}
	public int getMaxHP() {return IntStream.of(Arrays.copyOfRange(this.hitdiceRolls,0,this.getLevel())).reduce(0, (sum,i)->sum+i+this.getModifier(Score.Type.CONSTITUTION));}
	public int getHP() {return Math.max(0, this.getMaxHP()-this.damage);}
	public int getTempHP() {return this.temphp;}
	public void takeDamage(Damage damage) {
		int amount = damage.getValue(), tempLost = Math.min(amount, this.getTempHP());
		this.temphp -= tempLost;
		this.damage += amount-tempLost;
	}
	public int getSpeed() {return this.actorRace.getSpeed();}
	public int getArmorClass() {return this.getArmor().getBase() + Math.min(this.getArmor().getDexModMax(), Math.max(this.getArmor().getDexModMin(), this.getModifier(Score.Type.DEXTERITY)));}
	public int getDifficultyClass() {return 8+this.getProficiency()+this.getModifier(this.actorClass.getSaveScore());}
	public int getAttackModifier(Score.Type type) {return this.getModifier(type) + (Arrays.asList(this.actorClass.getAttackProficiencies()).contains(type) ? this.getProficiency() : 0);}
	public int getSaveModifier(Score.Type type) {return this.getModifier(type) + (Arrays.asList(this.actorClass.getSaveProficiencies()).contains(type) ? this.getProficiency() : 0);}
	public int getCheckModifier(Score.Type type) {return this.getModifier(type) + (Arrays.asList(this.actorClass.getCheckProficiencies()).contains(type) ? this.getProficiency() : 0);}
	public Armor getArmor() {return Optional.ofNullable(this.armor).orElse(Armor.Archetype.UNARMORED.create());}
	public Armor getEquippedArmor() {return this.armor;}
	public void equipArmor(Armor armor) {this.armor = this.armor==null && Arrays.asList(this.actorClass.getArmorTypes()).contains(armor.getType()) ? armor : this.armor;}
	public Armor unequipArmor() {
		Armor armor = this.armor;
		this.armor = null;
		return armor;
	}
	public Weapon getMainHand() {return Optional.ofNullable(this.mainHand).orElse(Weapon.Archetype.UNARMED.create());}
	public Weapon getEquippedMainHand() {return this.mainHand;}
	public void equipMainHand (Weapon weapon) {this.mainHand = this.mainHand==null && Arrays.asList(this.actorClass.getWeaponTypes()).contains(weapon.getType()) ? weapon : this.mainHand;}
	public Weapon unequipMainHand() {
		Weapon weapon = this.mainHand;
		this.mainHand = null;
		return weapon;
	}
	public Weapon getOffhand() {return Optional.ofNullable(this.offHand).orElse(Weapon.Archetype.UNARMED.create());}
	public Weapon getEquippedOffHand() {return this.offHand;}
	public void equipOffHand (Weapon weapon) {this.offHand = this.offHand==null && Arrays.asList(this.actorClass.getWeaponTypes()).contains(weapon.getType()) ? weapon : this.offHand;}
	public Weapon unequipOffHand() {
		Weapon weapon = this.offHand;
		this.offHand = null;
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
	public Allegiance getAllegiance() {return this.allegiance;}
	public void setAllegiance(Allegiance allegiance) {this.allegiance = allegiance;}
	public UUID getID() {return this.id;}
	@Override
	public boolean equals(Object o) {
		return this.id.equals(((Actor)o).id);
	}
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}
	public String toString() {return this.actorRace.toString();}
}
