package main.area;

public class BackdropElement {
	private MovementType type;
	public BackdropElement (MovementType type) {
		this.type = type;
	}
	public boolean isTraversible() {return this.type.isTraversible();}
	public double getMultiplier() {return this.type.gtMultiplier();}
}
