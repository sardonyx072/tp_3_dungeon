package main.area;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import main.actor.Actor;

public class Area2 {
	private interface OrientedElement {
		public Point getLocation();
	}
	private class OrientedTerrain extends Terrain implements OrientedElement {
		private Point location;
		private boolean[] openSides;
		public OrientedTerrain(Terrain terrain, Point location, HashSet<Orientation> openSides) {
			super(terrain);
			this.location = location;
			this.openSides = new boolean[Orientation.values().length];
			for (Orientation o : openSides)
				this.openSides[o.ordinal()] = true;
		}
		public Point getLocation() {return this.location;}
		public boolean[] getOpenSides() {return this.openSides;}
		public boolean isOpenTo(Orientation orientation) {return this.openSides[orientation.ordinal()];}
		public void setOpenTo(Orientation side, boolean value) {this.openSides[side.ordinal()] = value;}
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
	private Shape shape;
	private HashMap<Point,OrientedTerrain> terrain;
	private HashMap<Point,OrientedActor> actor;
	public Area2(HashMap<Point,Terrain> terrain) {
		this.terrain = new HashMap<Point,OrientedTerrain>();
		int xmin=Integer.MAX_VALUE,xmax=Integer.MIN_VALUE,ymin=Integer.MAX_VALUE,ymax=Integer.MIN_VALUE;
		for (Point point : terrain.keySet()) {
			xmin = Math.min(xmin, point.x);
			xmax = Math.max(xmax, point.x);
			ymin = Math.min(ymin, point.y);
			ymax = Math.max(ymax, point.y);
			this.terrain.put(point, new OrientedTerrain(terrain.get(point),point,new HashSet<Orientation>()));
		}
		this.shape = new Rectangle(xmin,ymin,xmax-xmin,ymax-ymin);
		this.sculpt();
	}
	public void sculpt(Point point, Orientation orientation, boolean open) {this.terrain.get(point).setOpenTo(orientation, open);}
	public void sculpt() {
		Rectangle bound = this.shape.getBounds();
		for (int y=bound.y; y<bound.height; y++)
			for (int x=bound.x; x<bound.width; x++) {
				Point pointFrom = new Point(x,y);
				if (this.terrain.containsKey(pointFrom) && this.shape.contains(pointFrom))
					for (Orientation orientation: Orientation.values()) {
						Point pointTo = this.getNext(pointFrom, orientation);
						this.sculpt(pointFrom,orientation,this.terrain.get(pointTo)!=null && this.terrain.get(pointTo).getMovementMultiplier()!=0);
					}
			}
	}
	public Point getNext(Point point, Orientation orientation) {return new Point(point.x+orientation.dx(),point.y+orientation.dy());}
	public boolean canMove(Point point, Orientation orientation) {
		OrientedTerrain to = this.terrain.get(point);
		return to!=null && to.isOpenTo(orientation) && !this.actor.containsKey(this.getNext(point, orientation));
	}
	public Area2 getLine(Point origin, int width, int length) {
		
	}
	public Area2 getCone(Point origin, int length) {
		
	}
	public Area2 getCircle(Point origin, int radius) {
		
	}
}
