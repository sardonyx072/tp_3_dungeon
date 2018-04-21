package main.actor;

public enum CreatureRace {
	HALF_ORC (30, new AbilityScore[] {new AbilityScore(AbilityScore.Type.STRENGTH,1), new AbilityScore(AbilityScore.Type.CONSTITUTION,1)}),
	HALFLING (25, new AbilityScore[] {new AbilityScore(AbilityScore.Type.DEXTERITY,1), new AbilityScore(AbilityScore.Type.CHARISMA,1)}),
	HUMAN (30, new AbilityScore[] {new AbilityScore(AbilityScore.Type.STRENGTH,1), new AbilityScore(AbilityScore.Type.INTELLIGENCE,1)}),
	ELF (30, new AbilityScore[] {new AbilityScore(AbilityScore.Type.DEXTERITY,1), new AbilityScore(AbilityScore.Type.WISDOM,1)}),
	DWARF (25, new AbilityScore[] {new AbilityScore(AbilityScore.Type.CONSTITUTION,1), new AbilityScore(AbilityScore.Type.INTELLIGENCE,1)}),
	HALF_ELF (30, new AbilityScore[] {new AbilityScore(AbilityScore.Type.WISDOM,1), new AbilityScore(AbilityScore.Type.CHARISMA,1)});
	private int speed;
	private AbilityScore[] scoreIncreases;
	private CreatureRace(int speed, AbilityScore[] scoreIncreases) {
		this.speed = speed;
		this.scoreIncreases = scoreIncreases;
	}
	public int getSpeed() {return this.speed;}
	public AbilityScore[] getScoreIncreases() {return this.scoreIncreases;}
}
