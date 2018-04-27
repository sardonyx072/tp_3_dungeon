package main.area;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.HashSet;

public class Area {
	private static final MovementType DEFAULT_MOVEMENT_TYPE = MovementType.NORMAL;
	public static final int SCALE = 5;
	protected Direction up;
	protected HashMap<Point,BackdropElement> area;
	protected HashMap<Point,Area> links;
	public Area(Rectangle bound) {
		this.up = Direction.NORTH;
		this.area = new HashMap<Point,BackdropElement>();
		this.links = new HashMap<Point,Area>();
		for (int y = bound.y; y < bound.height; y++)
			for (int x = bound.x; x < bound.width; x++)
				this.area.put(new Point(x,y), new BackdropElement(DEFAULT_MOVEMENT_TYPE));
	}
	public Rectangle getBounds() {
		int xmin, xmax, ymin, ymax;
		xmin = this.area.keySet().stream().mapToInt(point -> point.x).min().getAsInt();
		xmax = this.area.keySet().stream().mapToInt(point -> point.x).max().getAsInt();
		ymin = this.area.keySet().stream().mapToInt(point -> point.y).min().getAsInt();
		ymax = this.area.keySet().stream().mapToInt(point -> point.y).max().getAsInt();
		return new Rectangle(xmin,ymin,xmax-xmin,ymax-ymin);
	}
	public void putBackdropElement(Point point, BackdropElement element) {
		Rectangle bound = this.getBounds();
		if (!bound.contains(point)) {
			bound.add(point);
			for (int y = bound.y; y < bound.height; y++)
				for (int x = bound.x; x < bound.width; x++)
					this.area.putIfAbsent(new Point(x,y), new BackdropElement(DEFAULT_MOVEMENT_TYPE));
		}
		this.area.put(point, element);
	}
	public Point getNext(Point point, Direction direction) {return new Point(point.x+direction.dx(this.up),point.y+direction.dy(this.up));}
	public Area getLink(Point point) {return this.links.get(point);}
	public HashSet<Point> line(Point point, Direction direction, int width, int length, boolean includeOrigin, boolean includeNonTraversible, boolean includeNull, boolean forceLimit) {
		HashSet<Point> points = new HashSet<Point>();
		Point temp = point;
		for (int i = 0; i < length; i++) {
			if ((includeOrigin || i>0) && (includeNull || this.area.containsKey(point)) && (includeNonTraversible || this.area.get(temp).isTraversible()))
				points.add(temp);
			else if(!forceLimit)
				break;
			temp = this.getNext(temp, direction);
		}
		temp = point;
		for (int i = 0; i < width/2; i++)
			points.addAll(this.line(temp = this.getNext(temp, direction.next90DegreesCounterClockwise()), direction, 1, length, true, includeNonTraversible, includeNull, forceLimit));
		temp = point;
		for (int i = 0; i < width/2; i++)
			points.addAll(this.line(temp = this.getNext(temp, direction.next90DegreesClockwise()), direction, 1, length, true, includeNonTraversible, includeNull, forceLimit));
		return points;
	}
	public HashSet<Point> cone(Point point, Direction direction, int length, boolean includeOrigin, boolean includeNonTraversible, boolean includeNull, boolean forceLimit) {
		HashSet<Point> points = new HashSet<Point>();
		Point point2 = this.getNext(point, direction);
		boolean include1 = (includeOrigin) && (includeNull || this.area.containsKey(point)) && (includeNonTraversible || this.area.get(point).isTraversible());
		boolean include2 = (includeNull || this.area.containsKey(point2)) && (includeNonTraversible || this.area.get(point2).isTraversible());
		if (include1 || include2 || forceLimit) {
			if (include1)
				points.add(point);
			if (include2)
				points.add(point2);
			points.addAll(this.cone(this.getNext(point2, direction.next45DegreesCounterclockwise()), direction, length-1, true, includeNonTraversible, includeNull, forceLimit));
			points.addAll(this.cone(this.getNext(point2, direction), direction, length-1, true, includeNonTraversible, includeNull, forceLimit));
			points.addAll(this.cone(this.getNext(point2, direction.next45DegreesClockwise()), direction, length-1, true, includeNonTraversible, includeNull, forceLimit));
		}
		return points;
	}
	public HashSet<Point> radius(Point point, int length, boolean includeOrigin, boolean includeNonTraversible, boolean includeNull) {
		HashSet<Point> points = new HashSet<Point>();
		Point temp = point;
		for (int i = 0; i < length; i++)
			temp = this.getNext(temp, this.up);
		for (Point p : this.line(temp, this.up.next90DegreesClockwise().next90DegreesClockwise(), length*2, length*2, true, true, true, true))
			if ((includeNull || this.area.containsKey(p)) && (Math.pow(Math.abs(point.x-p.x),2)+Math.pow(Math.abs(point.y-p.y),2) <= Math.pow(length+0.5,2)) && (includeOrigin || !p.equals(point)) && (includeNonTraversible || this.area.get(p).isTraversible()))
				points.add(p);
		return points;
	}
}
