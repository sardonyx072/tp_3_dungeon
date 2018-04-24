package main.area;

import java.awt.Point;
import java.util.HashMap;

public class Area {
	private HashMap<Point,Setpiece> area;
	private HashMap<Point,Area> links;
	public Area() {
		area = new HashMap<Point,Setpiece>();
	}
}
