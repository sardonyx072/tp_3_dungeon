package main.area;

import java.awt.Point;

import main.actor.Actor;

public class Location extends Point {
	private Terrain terrain;
	private Actor actor;
	private Orientation terrainOrientation, actorOrientation;
	public Location (int x, int y, Terrain terrain, Orientation terrainOrientation, Actor actor, Orientation actorOrientation) {
		super(x,y);
		this.terrain = terrain;
		this.terrainOrientation = terrainOrientation;
		this.actor = actor;
		this.actorOrientation = actorOrientation;
		Point test = new Point(0,0);
	}
}
