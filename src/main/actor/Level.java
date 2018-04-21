package main.actor;

public class Level {
	private int level;
	public Level(int level) {
		this.level = level;
	}
	public int getLevel() {return this.level;}
	public int getProficiencyBonus() {return (this.level/5)+2;}
	public void levelUp() {this.level++;}
}
