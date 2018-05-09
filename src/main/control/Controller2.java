package main.control;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import main.actor.Actor;
import main.actor.Attack;
import main.actor.Roll;
import main.area.Area2;
import main.area.BackdropElement;
import main.area.Direction;
import main.area.Dungeon;
import main.area.Orientation;
import main.area.World;
import main.dice.Constant;
import main.dice.Dice;
import main.dice.Die;

public class Controller2 {
	public enum Mode {
		ADVENTURE(new Dice(new Die[] {new Constant(10)})), // in adventure mode, all checks are made with a 10 instead of a roll (in a party, all other party actors follow leader in a snake formation)
		INITIATIVE(Die.Type.D20.create(1)), // in initiative mode, all checks are made with die rolls and all actors take turns
		SIMPLE_INITIATIVE(Die.Type.D20.create(1)); // move 1 square or action for turn
		private Dice dice;
		private Mode(Dice dice) {
			this.dice = dice;
		}
		public Dice getDice() {return this.dice;}
	}
	private Dungeon dungeon;
	private Mode mode;
	private List<Area2.OrientedActor> order;
	public Controller2 () {
		this.dungeon = null;
		this.mode = Mode.SIMPLE_INITIATIVE;
		this.order = new ArrayList<Area2.OrientedActor>();
	}
	public HashMap<Point,BackdropElement> getBackdrop() {return this.current.getElements();}
	public BackdropElement getBackdrop(Point point) {return this.current.getBackdrop(point);}
	public Area2.OrientedActor getActor(Point point) {return this.current.getActor(point);}
	public Area2.OrientedActor currentActor() {return this.order.get(0);}
	public void endTurn() {this.order.add(this.order.remove(0));}
	public void moveCurrentActor(Orientation direction) {
		this.dungeon.getCurrentArea().moveActor(this.currentActor().getLocation(), direction);
	}
	public void moveCurrentActor(Point point) {
		this.moveCurrentActor(Orientation.nearestOrientation(this.currentActor().getLocation(), point));
	}
	public void moveCurrentActor() {
		Area2.OrientedActor inSight = this.dungeon.getCurrentArea().getCone(this.currentActor().getLocation(), this.currentActor().getOrientation(), 6, false).getActorsNearest(this.currentActor().getLocation(), 1).get(0);
		if (inSight != null)
			this.moveCurrentActor(inSight.getLocation());
		else
			this.moveCurrentActor(Orientation.random());
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
	public void save(String file) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
			oos.writeObject(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void load(String file) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			Controller2 temp = (Controller2) ois.readObject();
			this.dungeon = temp.dungeon;
			this.mode = temp.mode;
			this.order = temp.order;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
