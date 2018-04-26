package main.area;

public enum Direction {
	NORTH(0,-1),
	NORTHEAST(1,-1),
	EAST(1,0),
	SOUTHEAST(1,1),
	SOUTH(0,1),
	SOUTHWEST(-1,1),
	WEST(-1,0),
	NORTHWEST(-1,-1);
	private int dx, dy;
	private Direction(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}
	public Direction next45DegreesClockwise() {return Direction.values()[this.ordinal()+1==Direction.values().length ? 0 : this.ordinal()+1];}
	public Direction next45DegreesCounterclockwise() {return Direction.values()[this.ordinal()==0 ? Direction.values().length-1 : this.ordinal()-1];}
	public Direction next90DegreesClockwise() {return this.next45DegreesClockwise().next45DegreesClockwise();}
	public Direction next90DegreesCounterClockwise() {return this.next45DegreesCounterclockwise().next45DegreesCounterclockwise();}
	private Direction orient(Direction up) {
		int i = 0;
		while (up != NORTH) {
			i++;
			up = up.next45DegreesClockwise();
		}
		up = this;
		for (--i; i>=0; i--)
			up = up.next45DegreesClockwise();
		return up;
	}
	public int dx(Direction up) {
		return this.orient(up).dx;
	}
	public int dy(Direction up) {
		return this.orient(up).dy;
	}
}
