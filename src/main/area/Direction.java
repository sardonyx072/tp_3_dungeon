package main.area;

public enum Direction {
	NORTH,
	NORTHEAST,
	EAST,
	SOUTHEAST,
	SOUTH,
	SOUTHWEST,
	WEST,
	NORTHWEST;
	public Direction nextClockwise() {return Direction.values()[this.ordinal()+1==Direction.values().length ? 0 : this.ordinal()+1];}
	public Direction nextCounterclockwise() {return Direction.values()[this.ordinal()==0 ? Direction.values().length-1 : this.ordinal()-1];}
	public int dx(Direction up) {
		
	}
	public int dy(Direction up) {
		
	}
}
