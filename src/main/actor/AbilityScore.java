package main.actor;

public class AbilityScore extends Score {
	public static enum Type {
		STRENGTH,
		DEXTERITY,
		CONSTITUTION,
		INTELLIGENCE,
		WISDOM,
		CHARISMA;
	}
	private Type type;
	public AbilityScore (Type type, int value) {
		super(value);
		this.type = type;
	}
	public String getName() {return this.type.toString();}
	public String getAbbreviation() {return this.type.toString().substring(0, 3);}
	public int getModifier() {return (int)Math.floor((this.value-10)/2.0);}
}
