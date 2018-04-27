package main.control;

import java.util.HashSet;

import main.area.World;
import main.dice.*;

public class Controller {
	public enum Mode {
		ADVENTURE(new Dice(new Die[] {new Constant(10)})), // in adventure mode, all checks are made with a 10 instead of a roll (in a party, all other party actors follow leader in a snake formation)
		INITIATIVE(Die.Type.D20.create(1)); // in initiative mode, all checks are made with die rolls and all actors take turns
		private Dice dice;
		private Mode(Dice dice) {
			this.dice = dice;
		}
		public Dice getDice() {return this.dice;}
	}
	// enum Allegiance {PARTY, FRIENDLY, NEUTRAL, ENEMY, NONE} in Actor
	private HashSet<World> worlds;
	private World current;
	private Mode mode;
	public Controller () {
		this.worlds = new HashSet<World>();
		this.current = null;
		this.mode = Mode.ADVENTURE;
	}
}
