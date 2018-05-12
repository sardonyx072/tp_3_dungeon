package main.items;

import java.util.Arrays;
import java.util.List;

import main.Effect;

public class Armor extends Item {
	public static enum Archetype {
		//archetype name                  type      base mindexmod maxdexmod attributes                                                          wight, cost
		UNARMORED("Unarmored", Armor.Type.UNARMORED,10,  -100,     100,      new Attribute[] {},                                                 0,     0),
		LEATHER(  "Leather",   Armor.Type.LIGHT,    11,  -100,     100,      new Attribute[] {},                                                 10,    25),
		HIDE(     "Hide",      Armor.Type.MEDIUM,   13,  -100,     2,        new Attribute[] {},                                                 45,    100), 
		CHAINMAIL("Chain Mail",Armor.Type.HEAVY,    15,  0,        0,        new Attribute[] {Armor.Attribute.GAUCHE},                           60,    500),
		PLATE(    "Plate",     Armor.Type.HEAVY,    18,  0,        0,        new Attribute[] {Armor.Attribute.CUMBERSOME,Armor.Attribute.GAUCHE},100,   1500);
		private String name;
		private int base, dexModMin, dexModMax, weight, cost;
		private Type type;
		private Attribute[] attributes;
		private Archetype(String name, Type type, int base, int dexModMin, int dexModMax, Attribute[] attributes, int weight, int cost) {
			this.name = name;
			this.type = type;
			this.base = base;
			this.dexModMin = dexModMin;
			this.dexModMax = dexModMax;
			this.attributes = attributes;
			this.weight = weight;
			this.cost = cost;
		}
		public String getName() {return this.name;}
		public int getBase() {return this.base;}
		public int getDexModMin() {return this.dexModMin;}
		public int getDexModMax() {return this.dexModMax;}
		public int getWeight() {return this.weight;}
		public int getCost() {return this.cost;}
		public Type getType() {return this.type;}
		public Attribute[] getAttributes() {return this.attributes;}
		public Armor create() {return new Armor(this,null,null,null,null,null,null,null,null,null);}
	}
	public static enum Type {
		UNARMORED,
		NATURAL,
		LIGHT,
		MEDIUM,
		HEAVY,
		EXOTIC;
	}
	public static enum Attribute {
		CUMBERSOME,
		GAUCHE; //disadvantage dex checks
	}
	private String archetypeName, name;
	private Type type;
	private Integer base, dexModMin, dexModMax, weight, cost;
	private Attribute[] overrideAttributes;
	private Effect[] effects;
	public Armor(Archetype archetype, String overrideName, Type overrideType, Integer overrideBase, Integer overrideDexModMin, Integer overrideDexModMax, Attribute[] overrideAttributes, Integer overrideWeight, Integer overrideCost, Effect[] effects) {
		super(overrideName != null ? overrideName : archetype.getName(), overrideWeight != null ? overrideWeight : archetype.getWeight(), overrideCost != null ? overrideCost : archetype.getCost(), effects);
		this.archetypeName = archetype.getName();
		this.type = overrideType != null ? overrideType : archetype.getType();
		this.base = overrideBase != null ? overrideBase : archetype.getBase();
		this.dexModMin = overrideDexModMin != null ? overrideDexModMin : archetype.getDexModMin();
		this.dexModMax = overrideDexModMax != null ? overrideDexModMax : archetype.getDexModMax();
		this.overrideAttributes = overrideAttributes != null ? overrideAttributes : archetype.getAttributes();
	}
	public String getArchetypeName() {return this.archetypeName;}
	public int getBase() {return this.base;}
	public int getDexModMin() {return this.dexModMin;}
	public int getDexModMax() {return this.dexModMax;}
	public int getWeight() {return this.weight;}
	public int getCost() {return this.cost;}
	public Type getType() {return this.type;}
	public Attribute[] getAttributes() {return this.overrideAttributes;}
	public Effect[] getEffects() {return this.effects;}
}
