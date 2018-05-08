package main.actor;

import java.util.HashMap;

import javax.swing.ImageIcon;

import main.area.Orientation;

@SuppressWarnings("serial")
public enum CreatureRace {
	HALF_ORC (30, new HashMap<Score.Type,Integer>() {{put(Score.Type.STRENGTH,    1); put(Score.Type.CONSTITUTION, 1);}}, "./src/main/resources/adventurer.png"),
	HALFLING (25, new HashMap<Score.Type,Integer>() {{put(Score.Type.DEXTERITY,   1); put(Score.Type.CHARISMA,     1);}}, "./src/main/resources/adventurer.png"),
	HUMAN    (30, new HashMap<Score.Type,Integer>() {{put(Score.Type.STRENGTH,    1); put(Score.Type.INTELLIGENCE, 1);}}, "./src/main/resources/adventurer.png"),
	ELF      (30, new HashMap<Score.Type,Integer>() {{put(Score.Type.DEXTERITY,   1); put(Score.Type.WISDOM,       1);}}, "./src/main/resources/adventurer.png"),
	DWARF    (25, new HashMap<Score.Type,Integer>() {{put(Score.Type.CONSTITUTION,1); put(Score.Type.INTELLIGENCE, 1);}}, "./src/main/resources/adventurer.png"),
	HALF_ELF (30, new HashMap<Score.Type,Integer>() {{put(Score.Type.WISDOM,      1); put(Score.Type.CHARISMA,     1);}}, "./src/main/resources/adventurer.png"),
	SKELETON (25, new HashMap<Score.Type,Integer>() {{put(Score.Type.DEXTERITY,   1); put(Score.Type.CONSTITUTION, 1);}}, "./src/main/resources/skeleton.png");
	private int speed;
	private Sprite sprite;
	private HashMap<Score.Type,Integer> scoreIncreases;
	private CreatureRace(int speed, HashMap<Score.Type,Integer> scoreIncreases, String spriteFile) {
		this.speed = speed;
		this.scoreIncreases = scoreIncreases;
		this.sprite = new Sprite(spriteFile);
	}
	public int getSpeed() {return this.speed;}
	public HashMap<Score.Type,Integer> getScoreIncreases() {return this.scoreIncreases;}
	public ImageIcon getImage(Orientation orientation) {
		Sprite.Moment moment = null;
		switch(orientation) {
		case NORTH:
			moment = Sprite.Moment.MID_STEP_NORTH;
			break;
		case EAST:
			moment = Sprite.Moment.MID_STEP_EAST;
			break;
		case SOUTH:
			moment = Sprite.Moment.MID_STEP_SOUTH;
			break;
		case WEST:
			moment = Sprite.Moment.MID_STEP_WEST;
			break;
		default:
			moment = Sprite.Moment.NONE;
		}
		return this.sprite.getMoment(moment);
	}
}
