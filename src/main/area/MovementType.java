package main.area;

public enum MovementType {
	NONE (false, 0),
	ROUGH (true, 0.5),
	CLIMBING (true, 0.5),
	SQUEEZING (true, 0.5),
	NORMAL (true, 1),
	SLIPPERY (true, 1);
	private boolean traversible;
	private double  multiplier;
	private MovementType(boolean traversible, double multiplier) {
		this.traversible = traversible;
		this.multiplier = multiplier;
	}
	public boolean isTraversible() {return this.traversible;}
	public double gtMultiplier() {return this.multiplier;}
}
