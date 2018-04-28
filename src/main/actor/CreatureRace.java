package main.actor;

import java.util.HashMap;

@SuppressWarnings("serial")
public enum CreatureRace {
	HALF_ORC (30, new HashMap<Score.Type,Integer>() {{put(Score.Type.STRENGTH,    1); put(Score.Type.CONSTITUTION, 1);}}),
	HALFLING (25, new HashMap<Score.Type,Integer>() {{put(Score.Type.DEXTERITY,   1); put(Score.Type.CHARISMA,     1);}}),
	HUMAN    (30, new HashMap<Score.Type,Integer>() {{put(Score.Type.STRENGTH,    1); put(Score.Type.INTELLIGENCE, 1);}}),
	ELF      (30, new HashMap<Score.Type,Integer>() {{put(Score.Type.DEXTERITY,   1); put(Score.Type.WISDOM,       1);}}),
	DWARF    (25, new HashMap<Score.Type,Integer>() {{put(Score.Type.CONSTITUTION,1); put(Score.Type.INTELLIGENCE, 1);}}),
	HALF_ELF (30, new HashMap<Score.Type,Integer>() {{put(Score.Type.WISDOM,      1); put(Score.Type.CHARISMA,     1);}});
	private int speed;
	private HashMap<Score.Type,Integer> scoreIncreases;
	private CreatureRace(int speed, HashMap<Score.Type,Integer> scoreIncreases) {
		this.speed = speed;
		this.scoreIncreases = scoreIncreases;
	}
	public int getSpeed() {return this.speed;}
	public HashMap<Score.Type,Integer> getScoreIncreases() {return this.scoreIncreases;}
}
