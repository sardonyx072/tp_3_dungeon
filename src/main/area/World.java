package main.area;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import main.actor.Actor;

public class World extends Area {
	protected HashMap<Point,Actor> actors;
	public World (Rectangle size) {
		super(size);
		this.actors = new HashMap<Point,Actor>();
	}
	public Actor getActor(Point point) {
		return this.actors.get(point);
	}
	public void putActor(Actor actor, Point point) {
		this.actors.putIfAbsent(point, actor);
	}
	public Point locationOfActor(Actor actor) {
		//System.out.println("searching for " + actor.getID());
		for (Point point : this.actors.keySet()) {
			//System.out.println("\tfound " + this.actors.get(point).getID());
			if (actor.equals(this.actors.get(point)))
				return point;
		}
		return null;
	}
	public void moveActor(Point point, Direction direction) {
		Point next = this.getNext(point, direction);
		if (this.actors.get(point) != null && this.actors.get(next) == null)
			this.actors.put(next, this.actors.remove(point));
	}
	public HashSet<Actor> allActorsInArea(HashSet<Point> area) {
		HashSet<Actor> actors = new HashSet<Actor>();
		for (Point point : area)
			if (this.actors.containsKey(point))
				actors.add(this.actors.get(point));
		return actors;
	}
	public HashSet<Actor> nearestActorsInArea(Point origin, HashSet<Point> area, int n) {
		HashSet<Actor> actors = new HashSet<Actor>();
		class Temp implements Comparable<Temp> {
			Actor actor;
			double dist2;
			public Temp(Actor actor, double dist2) {
				this.actor = actor;
				this.dist2 = dist2;
			}
			@Override
			public int compareTo(Temp o) {
				if (this.dist2<o.dist2)
					return -1;
				else if(this.dist2>o.dist2)
					return 1;
				else
					return 0;
			}
		}
		List<Temp> temps = new ArrayList<Temp>();
		for (Point point : area)
			if (this.actors.containsKey(point))
				temps.add(new Temp(this.actors.get(point),Math.pow(Math.abs(point.x-origin.x),2)+Math.pow(Math.abs(point.y-origin.y),2)));
		System.out.println("origin checking for actors " + origin);
		System.out.println("area checking for actors " + Arrays.toString(area.toArray()));
		System.out.println("actors found " + Arrays.toString(temps.toArray()));
		Temp[] temps2 = temps.toArray(new Temp[temps.size()]);
		System.out.println("actors found " + Arrays.toString(temps2));
		Arrays.sort(temps2);
		System.out.println("actors found " + Arrays.toString(temps2));
		temps2 = Arrays.copyOfRange(temps2, 0, n);
		System.out.println("actors found " + Arrays.toString(temps2));
		for (Temp t : temps2)
			actors.add(t.actor);
		return actors;
	}
	public HashSet<Actor> randomActorsInArea(HashSet<Point> area, int n) {
		HashSet<Actor> actors = new HashSet<Actor>();
		List<Actor> list = new ArrayList<Actor>(this.allActorsInArea(area));
		for (int i = 0; i < n; i++)
			actors.add(list.remove(ThreadLocalRandom.current().nextInt(0,list.size())));
		return actors;
	}
	public void save() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./testworld.world"))) {
			oos.writeObject(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void load() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./testworld.world"))) {
			World test = (World) ois.readObject();
			this.up = test.up;
			this.area = test.area;
			this.links = test.links;
			this.actors = test.actors;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
