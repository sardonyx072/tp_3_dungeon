package main;

public class Effect {
	public static enum Type {
		ATTACK,
		CHECK,
		SAVE,
		DAMAGE;
	}
	public static enum Direction {
		OUTGOING,
		INCOMING;
	}
	public static enum Alteration {
		DOUBLE,
		HALF,
		PLUS_DICE,
		MINUS_DICE,
		AUTO_FAIL,
		AUTO_SUCCESS,
		ZERO;
	}
	public static enum Duration {
		INFINITE,
		ROLL,
		TURN_START,
		TURN_END,
		MINUTE,
		REST_SHORT,
		REST_LONG;
	}
	public static enum Qualifier {
		MELEE,
		RANGED,
		FRONT,
		BACK,
		FLANK,
		ACID,
		BLUDGEONING,
		COLD,
		FIRE,
		FORCE,
		LIGHTNING,
		NECROTIC,
		PIERCING,
		POISON,
		RADIANT,
		SLASHING,
		THUNDER
	}
	protected Type type;
	protected Direction direction;
	protected Alteration alteration;
	protected Duration duration;
	public Effect (Type type, Direction direction, Alteration alteration, Duration duration) {
		this.type = type;
		this.direction = direction;
		this.alteration = alteration;
		this.duration = duration;
	}
}
