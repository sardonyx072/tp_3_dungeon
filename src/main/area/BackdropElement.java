package main.area;

import java.io.Serializable;

public class BackdropElement implements Serializable {
	private static final long serialVersionUID = -2598774473998983626L;
	private MovementType type;
	public BackdropElement (MovementType type) {
		this.type = type;
	}
	public boolean isTraversible() {return this.type.isTraversible();}
	public double getMultiplier() {return this.type.gtMultiplier();}
}
