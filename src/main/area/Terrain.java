package main.area;

public class Terrain {
	public static enum Movement {
		NONE(0),
		NORMAL(1),
		DIFFICULT(2);
		private int mult;
		private Movement(int mult) {
			this.mult = mult;
		}
		public int getMult() {return this.mult;}
	}
	private Movement movement;
	public Terrain (Movement movement) {
		this.movement = movement;
	}
	public Terrain (Terrain terrain) {
		this.movement = terrain.movement;
	}
	public int getMovementMultiplier() {return this.movement.getMult();}
}
