package main.area;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;

import main.actor.Actor;

public class Area2 {
	private interface OrientedElement {
		public Point getLocation();
		public Orientation getOrientation();
	}
	private class OrientedTerrain extends Terrain implements OrientedElement {
		private Point location;
		private Orientation orientation;
		public OrientedTerrain(Terrain terrain, Point location, Orientation orientation) {
			super(terrain);
			this.location = location;
			this.orientation = orientation;
		}
		public Point getLocation() {return this.location;}
		public Orientation getOrientation() {return this.orientation;}
	}
	private class OrientedActor extends Actor implements OrientedElement {
		private Point location;
		private Orientation orientation;
		public OrientedActor(Actor actor, Point location, Orientation orientation) {
			super(actor);
			this.location = location;
			this.orientation = orientation;
		}
		public Point getLocation() {return this.location;}
		public Orientation getOrientation() {return this.orientation;}
	}
	private Rectangle bound;
	private HashMap<Point,OrientedTerrain> terrain;
	private HashMap<Point,OrientedActor> actor;
	public Area2(HashMap<Point,Oriented>)
}
