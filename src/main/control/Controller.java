package main.control;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import main.area.BackdropElement;
import main.area.Direction;
import main.area.World;
import main.dice.*;
import main.actor.*;

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
	private HashSet<World> worlds;
	private World current;
	private Mode mode;
	private List<Actor> order;
	public Controller () {
		this.worlds = new HashSet<World>();
		this.current = null;
		this.loadWorld();
		this.mode = Mode.INITIATIVE;
		this.order = new ArrayList<Actor>(this.current.allActorsInArea(this.current.radius(new Point(0,0), 50, true, false, false)));
	}
	public void loadWorld() {
		this.current = new World(new Rectangle(0,0,50,50));
		this.current.load();
	}
	public Rectangle getBounds() {return this.current.getBounds();}
	public HashMap<Point,BackdropElement> getBackdrop() {return this.current.getElements();}
	public BackdropElement getBackdrop(Point point) {return this.current.getBackdrop(point);}
	public Actor getActor(Point point) {return this.current.getActor(point);}
	public Actor currentActor() {return this.order.get(0);}
	public void endTurn() {this.order.add(this.order.remove(0));}
	public void move(Actor actor, Direction direction) {
		Point point = this.current.locationOfActor(actor);
		//System.out.println(Arrays.toString(this.order.toArray()) + " " + point);
		this.current.moveActor(point, direction);
	}
	public void attack (Actor actor, Direction direction) {
		System.out.println(1+(actor.getMainHand().getRange()/this.current.SCALE));
		System.out.println(Arrays.toString(this.current.line(this.current.locationOfActor(actor), direction, 1, 1+(actor.getMainHand().getRange()/this.current.SCALE), false, false, false, false).toArray()));
		Actor target = (Actor) this.current.nearestActorsInArea(this.current.locationOfActor(actor), this.current.line(this.current.locationOfActor(actor), direction, 1, 1+(actor.getMainHand().getRange()/this.current.SCALE), false, false, false, false), 1).toArray()[0];
		Attack attack = new Attack(actor,Roll.Type.NORMAL,actor.getMainHand().getScore(),this.mode.getDice(),actor.getMainHand().get1HDamage(),null,null);
		attack.roll();
		if (attack.getValue() >= target.getArmorClass()) {
			target.takeDamage(attack.getDamage());
		}
	}
}
