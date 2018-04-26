package main.area;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;

public class Area {
	private Direction up;
	private HashMap<Point,Setpiece> area;
	private HashMap<Point,Area> links;
	public Area() {
		this.up = Direction.NORTH;
		this.area = new HashMap<Point,Setpiece>();
		this.links = new HashMap<Point,Area>();
	}
	public Point getNext(Point point, Direction direction) {
		return new Point(point.x+direction.dx(this.up),point.y+direction.dy(this.up));
	}
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
		boolean include = (includeOrigin) && (includeNull || this.area.containsKey(point)) && (includeNonTraversible || this.area.get(point).isTraversible());
		if (include || forceLimit) {
			if (include)
				points.add(point);
			points.addAll(this.cone(this.getNext(point, direction.next45DegreesCounterclockwise()), direction, length-1, true, includeNonTraversible, includeNull, forceLimit));
			points.addAll(this.cone(this.getNext(point, direction), direction, length-1, true, includeNonTraversible, includeNull, forceLimit));
			points.addAll(this.cone(this.getNext(point, direction.next45DegreesClockwise()), direction, length-1, true, includeNonTraversible, includeNull, forceLimit));
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
