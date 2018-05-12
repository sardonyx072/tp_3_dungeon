package main.control;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import main.actor.Actor;
import main.actor.Attack;
import main.actor.Check;
import main.actor.CreatureClass;
import main.actor.CreatureRace;
import main.actor.Damage;
import main.actor.Roll;
import main.actor.Score;
import main.area.Area2;
import main.area.Dungeon;
import main.area.Orientation;
import main.area.Terrain;
import main.dice.Constant;
import main.dice.Dice;
import main.dice.Die;
import main.items.Armor;
import main.items.Weapon;

public class Controller2 {
	public enum Mode {
		ADVENTURE(new Dice(new Die[] {new Constant(10)})), // in adventure mode, all checks are made with a 10 instead of a roll (in a party, all other party actors follow leader in a snake formation)
		INITIATIVE(Die.Type.D20.create(1)), // in initiative mode, all checks are made with die rolls and all actors take turns
		SIMPLE_INITIATIVE(Die.Type.D20.create(1)); // move 1 square or action for turn
		private Dice dice;
		private Mode(Dice dice) {
			this.dice = dice;
		}
		public Dice getDice() {return this.dice;}
	}
	protected Dungeon dungeon;
	protected Mode mode;
	protected Area2.OrientedActor player;
	protected Area2.OrientedActor boss;
	protected List<Area2.OrientedActor> order;
	public Controller2 () {
		this.dungeon = null;
		this.mode = Mode.ADVENTURE;
		this.order = new ArrayList<Area2.OrientedActor>();
	}
	public Terrain getTerrain(Point point) {return this.dungeon.getCurrentArea().getTerrain(point);}
	public Area2.OrientedActor getActor(Point point) {return this.dungeon.getCurrentArea().getActor(point);}
	public Area2.OrientedActor getCurrentActor() {return this.order.get(0);}
	public Area2.OrientedActor getPlayer() {return this.player;}
	public Area2.OrientedActor getBoss() {return this.boss;}
	public void endTurn() {
		this.order.add(this.order.remove(0));
	}
	public void moveCurrentActor(Orientation direction) {
		this.order.get(0).setOrientation(direction);
		this.dungeon.getCurrentArea().moveActor(this.getCurrentActor().getLocation(), direction);
		this.nextArea();
	}
	public void moveCurrentActor(Point point) {
		this.moveCurrentActor(Orientation.nearestOrientation(this.getCurrentActor().getLocation(), point));
		this.nextArea();
	}
	public void moveCurrentActor() {
		List<Area2.OrientedActor> nearestActorsInSight = this.dungeon.getCurrentArea().getCone(this.getCurrentActor().getLocation(), this.getCurrentActor().getOrientation(), 6, false).getActorsNearest(this.getCurrentActor().getLocation(), 1);
		List<Area2.OrientedActor> nearestPlayersInSight = nearestActorsInSight.stream().filter(actor -> actor.getAllegiance() != Actor.Allegiance.ENEMY).collect(Collectors.toCollection(ArrayList::new));
		if (nearestPlayersInSight.size() > 0) {
			Area2.OrientedActor nearest = nearestPlayersInSight.get(0);
			if (Math.hypot(nearest.getLocation().x-this.getCurrentActor().getLocation().x, nearest.getLocation().y-this.getCurrentActor().getLocation().y) <= this.getCurrentActor().getMainHand().getRange()/this.dungeon.getCurrentArea().getScale()) { //can hit
				this.attackCurrentActor(Orientation.nearestOrientation(this.getCurrentActor().getLocation(), nearest.getLocation()));
			}
			else {
				this.moveCurrentActor(nearest.getLocation());
			}
		}
		else
			this.moveCurrentActor(Orientation.random());
		this.nextArea();
	}
	public void nextArea() {
		if(this.getCurrentActor().getAllegiance() == Actor.Allegiance.PARTY && this.dungeon.moveArea(this.getCurrentActor().getLocation())) {
			this.getCurrentActor().levelUp();
			this.rollInitiative();
		}
	}
	public void rollInitiative() {
		this.order = new ArrayList<Area2.OrientedActor>();
		List<Check> checks = new ArrayList<Check>();
		for (Area2.OrientedActor actor : this.dungeon.getCurrentArea().getActorsAll()) {
			//(Actor origin, Type type, Score.Type checkType, Dice dice, Score.Type contestType, Score.Type saveType)
			Check check = new Check(actor,Roll.Type.NORMAL,Score.Type.DEXTERITY,this.mode.getDice(),null,null);
			check.roll();
			int i = 0;
			while (i < checks.size() && checks.get(i).getValue() > check.getValue()) {
				i++;
			}
			this.order.add(i, actor);
			checks.add(i, check);
		}
	}
	public void attackCurrentActor (Orientation direction) {
		Area2.OrientedActor current = this.getCurrentActor();
		current.setOrientation(direction);
		System.out.println(current.getActorRace() + " " + current.getActorClass() + " chose to attack " + direction.toString());
		List<Area2.OrientedActor> actors = this.dungeon.getCurrentArea().getLine(current.getLocation(), current.getOrientation(), 1, current.getMainHand().getRange()/this.dungeon.getCurrentArea().getScale(), false).getActorsNearest(current.getLocation(), 1);
		if (actors.size() > 0) {
			Area2.OrientedActor target = actors.get(0);
			Attack attack = new Attack(this.order.get(0),Roll.Type.NORMAL,this.order.get(0).getMainHand().getScore(),this.mode.getDice(),this.order.get(0).getMainHand().get1HDamage(),null,null);
			attack.roll();
			System.out.println("\trolled a " + attack.getValue() + " to hit AC " + target.getArmorClass() + " of target " + target.getActorRace() + " " + target.getActorClass());
			if (attack.getValue() >= target.getArmorClass()) {
				System.out.println("\tit hits, target takes " + attack.getDamage().getValue() + " points of " + attack.getDamage().getType() + " damage.");
				target.takeDamage(attack.getDamage());
				if (target.getHP() == 0) {
					if (target.getEquippedArmor() != null)
						current.getInventory().add(target.unequipArmor());
					if (target.getEquippedMainHand() != null)
						current.getInventory().add(target.unequipMainHand());
					if (target.getEquippedOffHand() != null)
						current.getInventory().add(target.unequipOffHand());
					if (target.getInventory().size() > 0) {
						current.getInventory().addAll(target.getInventory());
						target.getInventory().removeAll(target.getInventory());
					}
					this.order.remove(this.order.indexOf(target));
					this.dungeon.getCurrentArea().removeActor(target.getLocation());
				}
			}
		}
		if (this.mode == Mode.SIMPLE_INITIATIVE) {
			this.endTurn();
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
			Controller2 temp = (Controller2) ois.readObject();
			this.dungeon = temp.dungeon;
			this.mode = temp.mode;
			this.order = temp.order;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void newGame() {
		System.out.println("----------------------------------- starting a new game ----------------------------------");
		Actor player = new Actor(CreatureRace.HUMAN, CreatureClass.FIGHTER, Score.getScores());
		player.equipArmor(Armor.Archetype.LEATHER.create());
		player.equipMainHand(Weapon.Archetype.LONGSWORD.create());
		player.setAllegiance(Actor.Allegiance.PARTY);
		this.dungeon = new Dungeon();
		Dungeon.LinkedArea previousArea = null;
		for (int i = 0; i < 20; i++) {
			System.out.println("generating area " + (i+1));
			HashMap<Point,Terrain> terrain = new HashMap<Point,Terrain>();
			String floormap = ""
					// 0123456789012345678901234
					+ "0000000000000000000000000\n" //0
					+ "0111111111111111111111110\n" //1
					+ "0101000000000000001001010\n" //2
					+ "0101000000000000001001010\n" //3
					+ "0101111111111111111001010\n" //4
					+ "0100000010000001001001010\n" //5
					+ "0100000010111001001001010\n" //6
					+ "0111110010101111111111010\n" //7
					+ "0100010010101000011100010\n" //8
					+ "0100010011101000011100010\n" //9
					+ "0100010010001111111111110\n" //10
					+ "0100011110001010011100010\n" //11
					+ "0111110011111011011100010\n" //12
					+ "0100000000001011011100010\n" //13
					+ "0111111111111011011100010\n" //14
					+ "0111111000000011011100010\n" //15
					+ "0100000000000010011100010\n" //16
					+ "0111111111111111111111110\n" //17
					+ "0111111111111111111101110\n" //18
					+ "0111111111111111111101110\n" //19
					+ "0100000000010010010000010\n" //20
					+ "0111111110010010011101110\n" //21
					+ "0100000000010010011101110\n" //22
					+ "0111111111111111111111110\n" //23
					+ "0000000000000000000000000\n" //24
					+ "";
			System.out.println("\tparsing");
			int x = 0, y = 0;
			for (int j = 0; j < floormap.length(); j++) {
				switch (floormap.charAt(j)) {
				case '\n':
					y++;
					x = 0;
					break;
				case '1':
					terrain.put(new Point(x++,y), Terrain.Type.FLOOR_ROCK.create());
					break;
				case '0':
					terrain.put(new Point(x++,y), Terrain.Type.WALL_SHEER.create());
					break;
				}
			}
			System.out.println("\tterraforming");
			Dungeon.LinkedArea area = this.dungeon.new LinkedArea(terrain);
			System.out.println("\tplacing actors");
			for (int j = 0; j <= i; j++) {
				Actor skeleton = new Actor(CreatureRace.SKELETON, CreatureClass.FIGHTER, Score.getScores());
				skeleton.setAllegiance(Actor.Allegiance.ENEMY);
				for (int k = 0; k < i; k++) {
					skeleton.levelUp();
				}
				skeleton.equipMainHand(new Weapon(Weapon.Archetype.SHORTSWORD,"Rusty Shortsword",null,null,null,new Damage(Damage.Type.SLASHING,Die.Type.D4.create(1)),new Damage(Damage.Type.SLASHING,Die.Type.D4.create(1)),null,null,null,null));
				skeleton.equipArmor(new Armor(Armor.Archetype.HIDE,"Tattered Armor Scraps",null,11,null,3,null,null,null,null));
				area.addActor(area.new OrientedActor(skeleton, area.getRandomUnoccupiedLocation(), Orientation.random()));
				if (j!=0 && j%4==0) {
					//chest
				}
			}
			if (i == 19) { //last area
				Area2.OrientedActor vampire = area.new OrientedActor(new Actor(CreatureRace.VAMPIRE, CreatureClass.FIGHTER, Score.getScores()), area.getRandomUnoccupiedLocation(), Orientation.random());
				vampire.setAllegiance(Actor.Allegiance.ENEMY);
				for (int k = 0; k < i; k++) {
					vampire.levelUp();
				}
				vampire.equipMainHand(new Weapon(Weapon.Archetype.UNARMED,"Bite",null,Weapon.Attribute.TWO_HANDED,null,new Damage(Damage.Type.PIERCING,Die.Type.D8.create(2)),new Damage(Damage.Type.PIERCING,Die.Type.D8.create(2)),null,null,null,null));
				vampire.equipArmor(new Armor(Armor.Archetype.LEATHER,"Shadow Cloak",null,14,null,3,null,null,null,null));
				area.addActor(vampire);
				this.boss = vampire;
			}
			System.out.println("\tlinking");
			if (previousArea != null) {
				Point linkPointFrom = previousArea.getRandomUnoccupiedLocation();
				Point linkPointTo = area.getRandomUnoccupiedLocation();
				HashSet<Orientation> newOpenSides = new HashSet<Orientation>();
				boolean[] oldOpenSides = previousArea.getTerrain(linkPointFrom).getOpenSides();
				for (int j = 0; j < oldOpenSides.length; j++) {
					if (oldOpenSides[j])
						newOpenSides.add(Orientation.values()[j]);
				}
				previousArea.addTerrain(previousArea.new OrientedTerrain(Terrain.Type.STAIRS_DOWN.create(),linkPointFrom,newOpenSides));
				previousArea.addLinks(this.dungeon.new AreaLink(linkPointFrom,area,linkPointTo));
			}
			this.dungeon.addArea(area);
			previousArea = area;
		}
		Area2 current = this.dungeon.getCurrentArea();
		Area2.OrientedActor orientedPlayer = current.new OrientedActor(player,current.getRandomUnoccupiedLocation(),Orientation.random());
		current.addActor(orientedPlayer);
		this.player = orientedPlayer;
		this.setMode(Mode.SIMPLE_INITIATIVE);
		System.out.println("done making new game");
	}
	public void setMode(Controller2.Mode mode) {
		this.mode = mode;
		if (mode == Mode.INITIATIVE || mode == Mode.SIMPLE_INITIATIVE) {
			this.rollInitiative();
		}
		else {
			this.order.addAll(this.getCurrentDungeon().getCurrentArea().getActorsAll());
		}
	}
	public boolean isPlayerDead() {return this.player.getHP() == 0;}
	public boolean isBossDead() {return this.boss.getHP() == 0;}
	public Dungeon getCurrentDungeon() {return this.dungeon;}
	public Mode getMode() {return this.mode;}
	public List<Area2.OrientedActor> getOrder() {return this.order;}
	public boolean isPartyTurn() {return this.getCurrentActor().getAllegiance() == Actor.Allegiance.PARTY;}
}
