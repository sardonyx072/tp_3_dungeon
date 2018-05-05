package main.area;

public enum Orientation {
	NORTH(0),
	NORTHEAST(45),
	EAST(90),
	SOUTHEAST(135),
	SOUTH(180),
	SOUTHWEST(225),
	WEST(270),
	NORTHWEST(315);
	private int bearing;
	private Orientation(int bearing) {
		this.bearing = bearing;
	}
	public static Orientation fromBearing(int degrees) {
		while (degrees < 0) degrees+=360;
		degrees = degrees%360;
		return Orientation.values()[(int)((degrees+22.5)/45)];
	}
	public int getBearing() {return this.bearing;}
	public int dx() {return this.bearing%180 == 0 ? 0 : this.bearing > 180 ? -1 : 1;}
	public int dy() {return (this.bearing+90)%180==0 ? 0 : (this.bearing+90)>180 ? -1 : 1;}
	public Orientation next(int degrees) {return fromBearing(this.bearing+degrees);}
	public Orientation nextRight45() {return this.next(45);}
	public Orientation nextRight90() {return this.next(90);}
	public Orientation nextLeft45() {return this.next(-45);}
	public Orientation nextLeft90() {return this.next(-90);}
	public Orientation inverse() {return this.next(180);}
}
