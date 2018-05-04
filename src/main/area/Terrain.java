package main.area;

import java.util.Optional;

public class Terrain {
	public static enum Type {
		WALL_SHEER("Sheer Wall", Movement.NONE),
		FLOOR_DIRT("Dirt Floor", Movement.NORMAL),
		FLOOR_ROCK("Rocky Floor", Movement.DIFFICULT);
		private String name;
		private Movement movement;
		private Type(String name, Movement movement) {
			this.name = name;
			this.movement = movement;
		}
		public String getName() {return this.name;}
		public Movement getMovement() {return this.movement;}
		public Terrain create() {return new Terrain(this.name,this,null);}
	}
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
	private String name;
	private Type type;
	private Movement movement;
	public Terrain (String name, Type type, Movement overrideMovement) {
		type = Optional.ofNullable(type).orElse(Type.FLOOR_DIRT);
		this.name = Optional.ofNullable(name).orElse(type.getName());
		this.type = type;
		this.movement = Optional.ofNullable(overrideMovement).orElse(type.getMovement());
	}
	public Terrain (Terrain terrain) {
		this.movement = terrain.movement;
	}
	public String getName() {return this.name;}
	public Type getType() {return this.type;}
	public int getMovementMultiplier() {return this.movement.getMult();}
	public boolean equals(Terrain that) {return this.name.equals(that.name) && this.type.equals(that.type) && this.movement.equals(that.movement);}
}
