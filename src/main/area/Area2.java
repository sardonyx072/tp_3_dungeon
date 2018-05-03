package main.area;

import java.awt.Point;
import java.awt.Rectangle;

import main.actor.Actor;

public class Area2 {
	private interface OrientedElement {
		public Point getLocation();
		public Orientation getOrientation();
	}
	private class OrientedTerrain extends Terrain implements OrientedElement {
		private Point location;
		private Orientation orientation;
		
	}
	private class OrientedActor extends Actor implements OrientedElement {
		private Point location;
		private Orientation orientation;
		public OrientedActor(Actor actor, Orientation orientation) {
			super(actor);
			this.orientation = orientation;
		}
		public Point getLocation() {return this.location;}
		public Orientation getOrientation() {return this.orientation;}
	}
	private Rectangle bound;
	
}
