package main.area;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Dungeon implements Serializable {
	private static final long serialVersionUID = 5440257946658946338L;
	public class AreaLink {
		private Point fromPoint, toPoint;
		private LinkedArea toArea;
		public AreaLink(Point fromPoint, LinkedArea toArea, Point toPoint) {
			this.fromPoint = fromPoint;
			this.toArea = toArea;
			this.toPoint = toPoint;
		}
		public Point getFromPoint() {return this.fromPoint;}
		public LinkedArea getToArea() {return this.toArea;}
		public Point getToPoint() {return this.toPoint;}
	}
	public class LinkedArea extends Area2 {
		private HashSet<AreaLink> links;
		public LinkedArea(HashMap<Point,Terrain> terrain, AreaLink...areaLinks) {
			super(terrain);
			this.links = new HashSet<AreaLink>();
			this.links.addAll(Arrays.asList(areaLinks));
		}
		public void addLinks(AreaLink...areaLinks) {
			this.links.addAll(Arrays.asList(areaLinks));
		}
		public AreaLink getLink(Point point) {
			AreaLink next = null;
			for (AreaLink link : this.links)
				if(link.fromPoint.equals(point)) {
					next = link;
					break;
				}
			return next;
		}
	}
	private LinkedArea currentArea;
	private HashSet<LinkedArea> areas;
	public Dungeon() {
		this.areas = new HashSet<LinkedArea>();
	}
	public Area2 getCurrentArea() {return this.currentArea;}
	public void addArea(LinkedArea area) {
		if (this.currentArea == null)
			this.currentArea = area;
		this.areas.add(area);
	}
	public void moveArea(Point point) {
		AreaLink link = this.currentArea.getLink(point);
		if (link != null) {
			Area2.OrientedActor actor = this.currentArea.removeActor(point);
			link.toArea.addActor(actor, link.toPoint);
			this.currentArea = link.toArea;
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
			Dungeon loaded = (Dungeon) ois.readObject();
			this.currentArea = loaded.currentArea;
			this.areas = loaded.areas;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
