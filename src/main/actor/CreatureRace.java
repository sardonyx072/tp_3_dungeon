package main.actor;

import java.util.HashMap;

@SuppressWarnings("serial")
public enum CreatureRace {
	HALF_ORC (30, new HashMap<AbilityScore.Type,Integer>() {{put(AbilityScore.Type.STRENGTH,    1); put(AbilityScore.Type.CONSTITUTION, 1);}}),
	HALFLING (25, new HashMap<AbilityScore.Type,Integer>() {{put(AbilityScore.Type.DEXTERITY,   1); put(AbilityScore.Type.CHARISMA,     1);}}),
	HUMAN    (30, new HashMap<AbilityScore.Type,Integer>() {{put(AbilityScore.Type.STRENGTH,    1); put(AbilityScore.Type.INTELLIGENCE, 1);}}),
	ELF      (30, new HashMap<AbilityScore.Type,Integer>() {{put(AbilityScore.Type.DEXTERITY,   1); put(AbilityScore.Type.WISDOM,       1);}}),
	DWARF    (25, new HashMap<AbilityScore.Type,Integer>() {{put(AbilityScore.Type.CONSTITUTION,1); put(AbilityScore.Type.INTELLIGENCE, 1);}}),
	HALF_ELF (30, new HashMap<AbilityScore.Type,Integer>() {{put(AbilityScore.Type.WISDOM,      1); put(AbilityScore.Type.CHARISMA,     1);}});
	private int speed;
	private HashMap<AbilityScore.Type,Integer> scoreIncreases;
	private CreatureRace(int speed, HashMap<AbilityScore.Type,Integer> scoreIncreases) {
		this.speed = speed;
		this.scoreIncreases = scoreIncreases;
	}
	public int getSpeed() {return this.speed;}
	public HashMap<AbilityScore.Type,Integer> getScoreIncreases() {return this.scoreIncreases;}
}
