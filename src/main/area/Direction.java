package main.area;

public enum Direction {
	NORTH(0,-1,true),
	NORTHEAST(1,-1,false),
	EAST(1,0,true),
	SOUTHEAST(1,1,false),
	SOUTH(0,1,true),
	SOUTHWEST(-1,1,false),
	WEST(-1,0,true),
	NORTHWEST(-1,-1,false);
	private int dx, dy;
	private boolean cardinal;
	private Direction(int dx, int dy, boolean cardinal) {
		this.dx = dx;
		this.dy = dy;
		this.cardinal = cardinal;
	}
	public boolean isCardinal() {return this.cardinal;}
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
