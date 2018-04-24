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
		Point next = null;
		switch(direction) {
		case NORTH:
			this.area.get(new Point(point.x,point.y+1));
			break;
		case NORTHEAST:
			this.area.get(new Point(point.x+1,point.y+1));
			break;
		case EAST:
			this.area.get(new Point(point.x+1,point.y));
			break;
		case SOUTHEAST:
			this.area.get(new Point(point.x+1,point.y-1));
			break;
		case SOUTH:
			this.area.get(new Point(point.x,point.y-1));
			break;
		case SOUTHWEST:
			this.area.get(new Point(point.x-1,point.y-1));
			break;
		case WEST:
			this.area.get(new Point(point.x-1,point.y));
			break;
		case NORTHWEST:
			this.area.get(new Point(point.x-1,point.y+1));
			break;
		}
		return next;
	}
	public HashSet<Point> line(Point point, Direction direction, int width, int length, boolean includeOrigin) {
		HashSet<Point> points = new HashSet<Point>();
		if (includeOrigin)
			points.add(point);
		while ((point = this.getNext(point, direction)) != null)
			points.add(point);
		return points;
	}
	public HashSet<Point> cone(Point point, Direction direction, int length, boolean includeOrigin) {
		HashSet<Point> points = new HashSet<Point>();
		return points;
	}
	public HashSet<Point> radius(Point point, Direction direction, int length, boolean includeOrigin) {
		HashSet<Point> points = new HashSet<Point>();
		return points;
	}
	public HashSet<Point> slice(Point point, Direction direction, int length, boolean includeOrigin) {
		HashSet<Point> points = new HashSet<Point>();
		return points;
	}
}
