package main.items;

import main.Effect;
import main.actor.AbilityScore;
import main.actor.Damage;
import main.dice.*;

public class Weapon extends Item {
	public static enum Archetype {
		//archetype    name                  type              attribute                    score                    damage type 1h damage amount       2h damage amount       range weight cost
		UNARMED(       "Unarmed",       Type.SIMPLE, Attribute.ONE_HANDED,AbilityScore.Type.STRENGTH,    Damage.Type.BLUDGEONING,Die.Type.D1.create(1), Die.Type.D1.create(1), 5,    0,     0),
		DAGGER(        "Dagger",        Type.SIMPLE, Attribute.ONE_HANDED,AbilityScore.Type.DEXTERITY,   Damage.Type.PIERCING,   Die.Type.D4.create(1), Die.Type.D4.create(1), 15,   0,     0),
		GREATAXE(      "Greataxe",      Type.MARTIAL,Attribute.TWO_HANDED,AbilityScore.Type.STRENGTH,    Damage.Type.SLASHING,   Die.Type.D12.create(1),Die.Type.D12.create(1),5,    0,     0),
		GREATSWORD(    "Greatsword",    Type.MARTIAL,Attribute.TWO_HANDED,AbilityScore.Type.STRENGTH,    Damage.Type.SLASHING,   Die.Type.D6.create(2), Die.Type.D6.create(2), 5,    0,     0),
		HALBERD(       "Halberd",       Type.MARTIAL,Attribute.TWO_HANDED,AbilityScore.Type.STRENGTH,    Damage.Type.SLASHING,   Die.Type.D10.create(1),Die.Type.D10.create(1),10,   0,     0),
		LIGHT_CROSSBOW("Light Crossbow",Type.SIMPLE, Attribute.TWO_HANDED,AbilityScore.Type.DEXTERITY,   Damage.Type.PIERCING,   Die.Type.D8.create(1), Die.Type.D8.create(1), 320,  0,     0),
		LONGBOW(       "Longbow",       Type.MARTIAL,Attribute.TWO_HANDED,AbilityScore.Type.DEXTERITY,   Damage.Type.PIERCING,   Die.Type.D8.create(1), Die.Type.D8.create(1), 600,  0,     0),
		LONGSWORD(     "Longsword",     Type.MARTIAL,Attribute.VERSATILE, AbilityScore.Type.STRENGTH,    Damage.Type.SLASHING,   Die.Type.D8.create(1), Die.Type.D10.create(1),5,    0,     0),
		QUARTERSTAFF(  "Quarterstaff",  Type.SIMPLE, Attribute.VERSATILE, AbilityScore.Type.STRENGTH,    Damage.Type.BLUDGEONING,Die.Type.D4.create(1), Die.Type.D6.create(1), 5,    0,     0),
		SHIELD(        "Shield",        Type.MARTIAL,Attribute.ONE_HANDED,AbilityScore.Type.STRENGTH,    Damage.Type.BLUDGEONING,Die.Type.D1.create(1), Die.Type.D1.create(1), 5,    0,     0),
		SHORTBOW(      "Shortbow",      Type.SIMPLE, Attribute.TWO_HANDED,AbilityScore.Type.DEXTERITY,   Damage.Type.PIERCING,   Die.Type.D6.create(1), Die.Type.D6.create(1), 300,  0,     0),
		SHORTSWORD(    "Shortsword",    Type.SIMPLE, Attribute.ONE_HANDED,AbilityScore.Type.DEXTERITY,   Damage.Type.SLASHING,   Die.Type.D6.create(1), Die.Type.D6.create(1), 5,    0,     0),
		SPELLBOOK(     "Spellbook",     Type.MAGICAL,Attribute.VERSATILE, AbilityScore.Type.INTELLIGENCE,Damage.Type.NECROTIC,   Die.Type.D8.create(1), Die.Type.D10.create(2),30,   0,     0),
		WAND(          "Wand",          Type.MAGICAL,Attribute.ONE_HANDED,AbilityScore.Type.INTELLIGENCE,Damage.Type.FORCE,      Die.Type.D4.create(3), Die.Type.D4.create(3), 60,   0,     0);
		private String name;
		private Type type;
		private Attribute attribute;
		private Damage.Type damageType;
		private Dice damage1H, damage2H;
		private int range;
		private int weight, cost;
		private Archetype (String name, Type type, Attribute attribute, AbilityScore.Type score, Damage.Type damageType, Dice damage1H, Dice damage2H, int range, int weight, int cost) {
			this.name = name;
			this.type = type;
			this.attribute = attribute;
			this.damageType = damageType;
			this.damage1H = damage1H;
			this.damage2H = damage2H;
			this.range = range;
			this.weight = weight;
			this.cost = cost;
		}
		public String getName() {return this.name;}
		public Type getType() {return this.type;}
		public Attribute getAttribute() {return this.attribute;}
		public Damage get1HDamage() {return new Damage(this.damageType,this.damage1H);}
		public Damage get2HDamage() {return new Damage(this.damageType,this.damage2H);}
		public int getRange() {return this.range;}
		public int getWeight() {return this.weight;}
		public int getCost() {return this.cost;}
		public Weapon create() {return new Weapon(this,null,null,null,null,null,null,null,null,null,null);}
	}
	public static enum Type {
		MAGICAL,
		SIMPLE,
		MARTIAL,
		EXOTIC;
	}
	public static enum Attribute {
		            // wielded 1h   wielded 2h
		ONE_HANDED, // normal       advantage
		TWO_HANDED, // disadvantage normal
		VERSATILE;  // normal       normal
	}
	private String archetypeName;
	private Type type;
	private Attribute attribute;
	private Damage damage1H, damage2H;
	private int range;
	public Weapon (Archetype archetype, String overrideName, Type overrideType, Attribute overrideAttribute, AbilityScore.Type overrideScore, Damage override1HDamage, Damage override2HDamage, Integer overrideRange, Integer overrideWeight, Integer overrideCost, Effect[] effects) {
		super(overrideName != null ? overrideName : archetype.getName(), overrideWeight != null ? overrideWeight : archetype.getWeight(), overrideCost != null ? overrideCost : archetype.getCost(), effects);
		this.archetypeName = archetype.getName();
		this.type = overrideType != null ? overrideType : archetype.getType();
		this.attribute = overrideAttribute != null ? overrideAttribute : archetype.getAttribute();
		this.damage1H = override1HDamage != null ? override1HDamage : archetype.get1HDamage();
		this.damage2H = override2HDamage != null ? override2HDamage : archetype.get2HDamage();
		this.range = overrideRange != null ? overrideRange : archetype.getRange();
	}
	public String getArchetypeName() {return this.archetypeName;}
	public Type getType() {return this.type;}
	public Attribute getAttributes() {return this.attribute;}
	public Damage get1HDamage() {return this.damage1H;}
	public Damage get2HDamage() {return this.damage2H;}
	public int getRange() {return this.range;}
}
