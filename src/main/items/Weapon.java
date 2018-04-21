package main.items;

import main.Effect;
import main.dice.*;

public class Weapon extends Item {
	public static enum Archetype {
		UNARMED("Unarmed", Type.SIMPLE, new Attribute[] {}, new Dice(new Die[] {new SimDie(new int[] {1})}), null, 5, 0, 0, 0),
		DAGGER("Dagger", Type.SIMPLE, new Attribute[] {Attribute.FINESSE,Attribute.THROWN}, Die.Type.D4.create(1), null, 5, 40, 1, 2),
		GREATAXE("Greataxe", Type.MARTIAL, new Attribute[] {Attribute.TWO_HANDED}, Die.Type.D12.create(1), null, 5, 0, 0, 0),
		GREATSWORD("Greatsword", Type.MARTIAL, new Attribute[] {Attribute.TWO_HANDED}, Die.Type.D6.create(2), null, 5, 0, 0, 0),
		HALBERD("Halberd", Type.MARTIAL, new Attribute[] {Attribute.TWO_HANDED}, Die.Type.D10.create(1), null, 5, 0, 0, 0),
		LIGHT_CROSSBOW("Crossbow", Type.SIMPLE, new Attribute[] {Attribute.AMMUNITION, Attribute.TWO_HANDED}, Die.Type.D8.create(1), null, 0, 320, 0, 0),
		LONGBOW("Longbow", Type.MARTIAL, new Attribute[] {Attribute.AMMUNITION, Attribute.TWO_HANDED}, Die.Type.D8.create(1), null, 0, 600, 0, 0),
		LONGSWORD("Longsword", Type.MARTIAL, new Attribute[] {Attribute.VERSATILE}, Die.Type.D8.create(1), Die.Type.D10.create(1), 5, 0, 0, 0),
		QUARTERSTAFF("Quarterstaff", Type.SIMPLE, new Attribute[] {Attribute.VERSATILE}, Die.Type.D4.create(1), Die.Type.D6.create(1), 5, 0, 0, 0),
		SHIELD("Shield", Type.MARTIAL, new Attribute[] {}, new Dice(new Die[] {new SimDie(new int[] {1})}), null, 5, 0, 10, 10),
		SHORTBOW("Shortbow", Type.SIMPLE, new Attribute[] {Attribute.AMMUNITION, Attribute.TWO_HANDED}, Die.Type.D6.create(1), null, 0, 300, 0, 0),
		SHORTSWORD("Shortsword", Type.SIMPLE, new Attribute[] {Attribute.FINESSE}, Die.Type.D6.create(1), null, 5, 0, 0, 0),
		SPELLBOOK("Spellbook", Type.MAGICAL, new Attribute[] {Attribute.VERSATILE}, Die.Type.D8.create(2), Die.Type.D10.create(2), 0, 20, 0, 0),
		WAND("Wand", Type.MAGICAL, new Attribute[] {}, Die.Type.D4.create(3), null, 60, 60, 0, 0);
		private String name;
		private Type type;
		private Attribute[] attributes;
		private Dice damageNormal, damageVersatile;
		private int meleeDistance, rangeDistance;
		private int weight, cost;
		private Archetype (String name, Type type, Attribute[] attributes, Dice damageNormal, Dice damageVersatile, int meleeDistance, int rangeDistance, int weight, int cost) {
			this.name = name;
			this.type = type;
			this.attributes = attributes;
			this.damageNormal = damageNormal;
			this.damageVersatile = damageVersatile;
			this.meleeDistance = meleeDistance;
			this.rangeDistance = rangeDistance;
			this.weight = weight;
			this.cost = cost;
		}
		public String getName() {return this.name;}
		public Type getType() {return this.type;}
		public Attribute[] getAttributes() {return this.attributes;}
		public Dice getNormalDamage() {return this.damageNormal;}
		public Dice getVersatileDamage() {return this.damageVersatile;}
		public int getMeleeDistance() {return this.meleeDistance;}
		public int getRangedDistance() {return this.rangeDistance;}
		public int getWeight() {return this.weight;}
		public int getCost() {return this.cost;}
	}
	public static enum Type {
		MAGICAL,
		SIMPLE,
		MARTIAL,
		EXOTIC;
	}
	public static enum Attribute { //TODO ranges for all ranged types
		AMMUNITION,
		FINESSE,
		REACH,
		THROWN,
		TWO_HANDED,
		VERSATILE;
	}
	private String archetypeName;
	private Type type;
	private Attribute[] attributes;
	private Dice damageNormal, damageVersatile;
	private int meleeDistance, rangeDistance;
	public Weapon (Archetype archetype, String overrideName, Type overrideType, Attribute[] overrideAttributes, Dice overrideDamageNormal, Dice overrideDamageVersatile, Integer overrideMeleeDistance, Integer overrideRangeDistance, Integer overrideWeight, Integer overrideCost, Effect[] effects) {
		super(overrideName != null ? overrideName : archetype.getName(), overrideWeight != null ? overrideWeight : archetype.getWeight(), overrideCost != null ? overrideCost : archetype.getCost(), effects);
		this.archetypeName = archetype.getName();
		this.type = overrideType != null ? overrideType : archetype.getType();
		this.attributes = overrideAttributes != null ? overrideAttributes : archetype.getAttributes();
		this.damageNormal = overrideDamageNormal != null ? overrideDamageNormal : archetype.getNormalDamage();
		this.damageVersatile = overrideDamageVersatile != null ? overrideDamageVersatile : archetype.getVersatileDamage();
		this.meleeDistance = overrideMeleeDistance != null ? overrideMeleeDistance : archetype.getMeleeDistance();
		this.rangeDistance = overrideRangeDistance != null ? overrideRangeDistance : archetype.getRangedDistance();
	}
	public String getArchetypeName() {return this.archetypeName;}
	public Type getType() {return this.type;}
	public Attribute[] getAttributes() {return this.attributes;}
	public Dice getNormalDamage() {return this.damageNormal;}
	public Dice getVersatileDamage() {return this.damageVersatile;}
	public int getMeleeDistance() {return this.meleeDistance;}
	public int getRangeDsitance() {return this.rangeDistance;}
}
