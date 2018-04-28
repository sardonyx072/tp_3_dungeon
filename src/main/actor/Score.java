package main.actor;

public class Score {
	public static enum Type {
		STRENGTH,
		DEXTERITY,
		CONSTITUTION,
		INTELLIGENCE,
		WISDOM,
		CHARISMA;
	}
	private Type type;
	protected int value;
	public Score (Type type, int value) {
		this.value = value;
		this.type = type;
	}
	public int getValue() {return this.value;}
	public String getName() {return this.type.toString();}
	public String getAbbreviation() {return this.type.toString().substring(0, 3);}
	public int getModifier() {return (int)Math.floor((this.value-10)/2.0);}
}
