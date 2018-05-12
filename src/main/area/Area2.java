package main.area;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;

import main.actor.Actor;

public class Area2 {
	private interface OrientedElement {
		public Point getLocation();
	}
	public class OrientedTerrain extends Terrain implements OrientedElement {
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
	public class OrientedActor extends Actor implements OrientedElement {
		private Point location;
		private Orientation orientation;
		public OrientedActor(Actor actor, Point location, Orientation orientation) {
			super(actor);
			this.location = location;
			this.orientation = orientation;
		}
		public Point getLocation() {return this.location;}
		public void setLocation(Point location) {this.location = location;}
		public Orientation getOrientation() {return this.orientation;}
		public void setOrientation(Orientation orientation) {this.orientation = orientation;}
		public ImageIcon getAppearance() {return this.getActorRace().getImage(this.getOrientation());}
	}
	private Shape shape;
	private HashMap<Point,OrientedTerrain> terrain;
	private HashMap<Point,OrientedActor> actor;
	private int scale;
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
		this.actor = new HashMap<Point,OrientedActor>();
		this.shape = new Rectangle(xmin,ymin,xmax-xmin,ymax-ymin);
		System.out.println(String.format("shape being set at x=%d, y=%d, w=%d, h=%d", xmin, ymin, xmax-xmin, ymax-ymin));
		this.scale = 5;
		this.sculpt();
	}
	public void sculpt(Point point, Orientation orientation, boolean open) {this.terrain.get(point).setOpenTo(orientation, open);}
	public void sculpt() {
		Rectangle bound = this.shape.getBounds();
		for (int y=bound.y; y<bound.y+bound.height; y++)
			for (int x=bound.x; x<bound.x+bound.width; x++) {
				Point pointFrom = new Point(x,y);
				if (this.terrain.containsKey(pointFrom) && this.shape.contains(pointFrom))
					for (Orientation orientation: Orientation.values()) {
						Point pointTo = this.getNext(pointFrom, orientation);
						this.sculpt(pointFrom,orientation,this.terrain.get(pointTo)!=null && this.terrain.get(pointTo).getMovementMultiplier()!=0);
					}
			}
	}
	public Shape getShape() {return this.shape;}
	public int getScale() {return this.scale;}
	public Point getNext(Point point, Orientation orientation) {return new Point(point.x+orientation.dx(),point.y+orientation.dy());}
	public boolean canMove(Point point, Orientation orientation) {
		OrientedTerrain to = this.terrain.get(point);
		return to!=null && to.isOpenTo(orientation) && !this.actor.containsKey(this.getNext(point, orientation));
	}
	public Point getRandomUnoccupiedLocation() {
		HashSet<Point> unoccupied = new HashSet<Point>(this.terrain.keySet());
		unoccupied.removeIf(key -> this.terrain.get(key).getMovementMultiplier()==0 || this.actor.containsKey(key));
		return unoccupied.toArray(new Point[unoccupied.size()])[ThreadLocalRandom.current().nextInt(unoccupied.size())];
	}
	public Area2 getLine(Point origin, Orientation direction, int length, int width, boolean includeOrigin) {
		Shape line = AffineTransform.getRotateInstance(Math.toRadians(direction.getBearing()+180),origin.x+0.5,origin.y+0.5).createTransformedShape(new Rectangle(origin.x-(width/2),origin.y,width,length));
		HashMap<Point,OrientedTerrain> terrainWithin = new HashMap<Point,OrientedTerrain>();
		HashMap<Point,OrientedActor> actorsWithin = new HashMap<Point,OrientedActor>();
		Rectangle bound = line.getBounds();
		for (int y=bound.y; y<bound.y+bound.height; y++)
			for (int x=bound.x; x<bound.x+bound.width; x++) {
				Point point = new Point(x,y);
				if (line.contains(point) && this.terrain.containsKey(point)) {
					terrainWithin.put(point, this.terrain.get(point));
					if (this.actor.containsKey(point))
						actorsWithin.put(point, this.actor.get(point));
				}
			}
		Area2 result = new Area2(new HashMap<Point,Terrain>());
		result.shape = line;
		result.terrain = terrainWithin;
		result.actor = actorsWithin;
		return result;
	}
	public Area2 getCone(Point origin, Orientation direction, int length, boolean includeOrigin) {
		Shape cone = AffineTransform.getRotateInstance(Math.toRadians(direction.getBearing()+180),origin.x+0.5,origin.y+0.5).createTransformedShape(new Ellipse2D.Float(origin.x-length,origin.y-length,2*length,2*length));
		HashMap<Point,OrientedTerrain> terrainWithin = new HashMap<Point,OrientedTerrain>();
		HashMap<Point,OrientedActor> actorsWithin = new HashMap<Point,OrientedActor>();
		Rectangle bound = cone.getBounds();
		for (int y=bound.y; y<bound.y+bound.height; y++)
			for (int x=bound.x; x<bound.x+bound.width; x++) {
				Point point = new Point(x,y);
				if (cone.contains(point) && this.terrain.containsKey(point)) {
					terrainWithin.put(point, this.terrain.get(point));
					if (this.actor.containsKey(point))
						actorsWithin.put(point, this.actor.get(point));
				}
			}
		Area2 result = new Area2(new HashMap<Point,Terrain>());
		result.shape = cone;
		result.terrain = terrainWithin;
		result.actor = actorsWithin;
		return result;
	}
	public Area2 getCircle(Point origin, int radius, boolean includeOrigin) {
		Shape circle = AffineTransform.getRotateInstance(0,origin.x+0.5,origin.y+0.5).createTransformedShape(new Ellipse2D.Float(origin.x-radius,origin.y-radius,2*radius,2*radius));
		HashMap<Point,OrientedTerrain> terrainWithin = new HashMap<Point,OrientedTerrain>();
		HashMap<Point,OrientedActor> actorsWithin = new HashMap<Point,OrientedActor>();
		Rectangle bound = circle.getBounds();
		for (int y=bound.y; y<bound.y+bound.height; y++)
			for (int x=bound.x; x<bound.x+bound.width; x++) {
				Point point = new Point(x,y);
				if (circle.contains(point) && this.terrain.containsKey(point)) {
					terrainWithin.put(point, this.terrain.get(point));
					if (this.actor.containsKey(point))
						actorsWithin.put(point, this.actor.get(point));
				}
			}
		Area2 result = new Area2(new HashMap<Point,Terrain>());
		result.shape = circle;
		result.terrain = terrainWithin;
		result.actor = actorsWithin;
		return result;
	}
	public List<OrientedActor> getActorsAll() {
		List<OrientedActor> actors = new ArrayList<OrientedActor>();
		Rectangle bounds = this.shape.getBounds();
		for (int y=bounds.y; y<bounds.y+bounds.height; y++)
			for (int x=bounds.x; x<bounds.x+bounds.width; x++) {
				Point point = new Point(x,y);
				if (this.shape.contains(point) && this.actor.containsKey(point))
					actors.add(this.actor.get(point));
			}
		return actors;
	}
	public List<OrientedActor> getActorsNearest(Point point, int n) {
		List<OrientedActor> actors = this.getActorsAll();
		Collections.sort(actors, new Comparator<OrientedActor>() {
			public int compare(OrientedActor a1, OrientedActor a2) {
				double diff = Math.hypot(a1.getLocation().x-point.x,a1.getLocation().y-point.y)-Math.hypot(a2.getLocation().x-point.x,a2.getLocation().y-point.y);
				return  diff==0 ? 0 : diff>0 ? 1 : -1;
			}
		});
		return actors.subList(0, Math.min(actors.size(), n));
	}
	public List<OrientedActor> getActorsRandom(int n) {
		List<OrientedActor> actors = this.getActorsAll();
		Collections.shuffle(actors);
		return actors.subList(0, Math.min(actors.size(), n));
	}
	public void moveActor(Point point, Orientation direction) {
		OrientedActor actor = this.actor.remove(point);
		if (actor != null) {
			actor.setOrientation(direction);
			System.out.println(String.format("from point x=%d, y=%d can move north=%s, northeast=%s, east=%s, southeast=%s, south=%s, southwest=%s, west=%s, northwest=%s", actor.location.x, actor.location.y, this.terrain.get(point).isOpenTo(Orientation.NORTH), this.terrain.get(point).isOpenTo(Orientation.NORTHEAST), this.terrain.get(point).isOpenTo(Orientation.EAST), this.terrain.get(point).isOpenTo(Orientation.SOUTHEAST), this.terrain.get(point).isOpenTo(Orientation.SOUTH), this.terrain.get(point).isOpenTo(Orientation.SOUTHWEST), this.terrain.get(point).isOpenTo(Orientation.WEST), this.terrain.get(point).isOpenTo(Orientation.NORTHWEST)));
			if(this.canMove(point, direction)) {
				Point next = this.getNext(point, direction);
				this.actor.put(next, actor);
				actor.setLocation(next);
			}
		}
	}
	public void addActor(OrientedActor actor) {
		this.actor.put(actor.getLocation(), actor);
	}
	public OrientedActor removeActor(Point point) {
		return this.actor.get(point);
	}
	public OrientedTerrain getTerrain(Point point) {
		return this.terrain.get(point);
	}
	public void addTerrain(OrientedTerrain terrain) {
		this.terrain.put(terrain.getLocation(), terrain);
	}
	public Area2.OrientedActor getActor(Point point) {
		return this.actor.get(point);
	}
	public Point getLocationOfActor(Actor actor) {
		for (Point point : this.actor.keySet()) {
			if (this.actor.get(point) == actor) {
				return point;
			}
		}
		return null;
	}
}
