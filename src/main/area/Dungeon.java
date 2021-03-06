package main.area;

import java.awt.Point;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Dungeon implements Serializable {
	private static final long serialVersionUID = 5440257946658946338L;
	public class AreaLink implements Serializable {
		private static final long serialVersionUID = 6469133846394572368L;
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
	public class LinkedArea extends Area2 implements Serializable {
		private static final long serialVersionUID = -2390569893118658877L;
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
	public boolean moveArea(Point point) {
		boolean linked = false;
		AreaLink link = this.currentArea.getLink(point);
		if (link != null) {
			Area2.OrientedActor actor = this.currentArea.removeActor(point);
			if (actor != null) {
				actor.setLocation(link.toPoint);
				link.toArea.addActor(actor);
				this.currentArea = link.toArea;
				linked = true;
			}
		}
		return linked;
	}
	public Area2.OrientedTerrain getTerrain(Point point) {
		return this.currentArea.getTerrain(point);
	}
	public Area2.OrientedActor getActor(Point point) {
		return this.currentArea.getActor(point);
	}
}
