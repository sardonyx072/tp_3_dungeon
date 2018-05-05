package main.area;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import javafx.scene.shape.Circle;
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
	private abstract class OrientedShape {
		protected Point origin;
		protected Orientation orientation;
		protected int distance;
		protected Shape bound;
		public OrientedShape(Point origin, Orientation orientation, int distance) {
			this.origin = origin;
			this.orientation = orientation;
			this.distance = distance;
			this.setShape();
		}
		public Rectangle getBounds() {return this.bound.getBounds();}
		public Shape getShape() {return this.bound;}
		//private abstract void setBound() {this.bound = AffineTransform.getRotateInstance(Math.toRadians(this.orientation.getBearing()+180),this.origin.x+0.5,this.origin.y+0.5).createTransformedShape(new Rectangle(origin.x-distance,origin.y-distance,2*distance,2*distance));}
		protected abstract void setShape();
		public Point getOrigin() {return this.origin;}
		public void setOrigin(Point origin) {
			this.origin = origin;
			this.setShape();
		}
		public Orientation getOrientation() {return this.orientation;}
		public void setOrientation(Orientation orientation) {
			this.orientation = orientation;
			this.setShape();
		}
		public int getDistance() {return this.distance;}
		public void setDistance(int distance) {
			this.distance = distance;
			this.setShape();
		}
		public boolean contains(Point point) {return this.bound.contains(point);}
	}
	public class OrientedRectangle extends OrientedShape {
		private int width;
		public OrientedRectangle(Point origin, Orientation orientation, int length, int width) {
			super(origin,orientation,length);
			this.width = width;
		}
		public void setShape() {this.bound = AffineTransform.getRotateInstance(Math.toRadians(this.orientation.getBearing()+180),this.origin.x+0.5,this.origin.y+0.5).createTransformedShape(new Rectangle(origin.x-(this.width/2),origin.y,width,distance));}
		public int getWidth() {return this.width;}
		public void setWidth(int width) {this.width = width;}
	}
	public class OrientedCircle extends OrientedShape {
		public OrientedCircle(Point origin, Orientation orientation, int radius) {
			super(origin, orientation, radius);
		}
		protected void setShape() {this.bound = AffineTransform.getRotateInstance(Math.toRadians(this.orientation.getBearing()+180),this.origin.x+0.5,this.origin.y+0.5).createTransformedShape(new Ellipse2D.Float(origin.x-distance,origin.y-distance,2*distance,2*distance));}
	}
	public class OrientedCone extends OrientedShape { //TODO extract Arc2D from Ellipse2D
		public OrientedCone(Point origin, Orientation orientation, int radius) {
			super(origin, orientation, radius);
		}
		protected void setShape() {this.bound = AffineTransform.getRotateInstance(Math.toRadians(this.orientation.getBearing()+180),this.origin.x+0.5,this.origin.y+0.5).createTransformedShape(new Ellipse2D.Float(origin.x-distance,origin.y-distance,2*distance,2*distance));}
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
	public Point getNext(Point point, Orientation orientation) {return new Point(point.x+orientation.dx(),point.y+orientation.dy());}
	public boolean canMove(Point point, Orientation orientation) {
		OrientedTerrain to = this.terrain.get(point);
		return to!=null && to.isOpenTo(orientation) && !this.actor.containsKey(this.getNext(point, orientation));
	}
	public Area2 getLine(Point origin, Orientation direction, int length, int width, boolean includeOrigin) {
		OrientedRectangle line = new OrientedRectangle(origin,direction,length,width);
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
		
	}
	public Area2 getCircle(Point origin, int radius, boolean includeOrigin) {
		
	}
}
