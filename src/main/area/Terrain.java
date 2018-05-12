package main.area;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Terrain {
	public static enum Type {
		WALL_SHEER("Sheer Wall", Movement.NONE, "./src/main/resources/wall.png"),
		FLOOR_DIRT("Dirt Floor", Movement.NORMAL, "./src/main/resources/floor.png"),
		FLOOR_ROCK("Rocky Floor", Movement.DIFFICULT, "./src/main/resources/floor.png"),
		STAIRS_DOWN("Stairs", Movement.NORMAL, "./src/main/resources/stairs.png");
		private String name;
		private Movement movement;
		private String imageFile;
		private Type(String name, Movement movement, String imageFile) {
			this.name = name;
			this.movement = movement;
			this.imageFile = imageFile;
		}
		public String getName() {return this.name;}
		public Movement getMovement() {return this.movement;}
		public String getImageFile() {return this.imageFile;}
		public Terrain create() {return new Terrain(null,this,null,null);}
	}
	public static enum Movement {
		NONE(0),
		NORMAL(1),
		DIFFICULT(2);
		private int mult;
		private Movement(int mult) {
			this.mult = mult;
		}
		public int getMult() {return this.mult;}
	}
	private String name;
	private Type type;
	private Movement movement;
	private ImageIcon image;
	public Terrain (String name, Type type, Movement overrideMovement, String imageFile) {
		this.type = Optional.ofNullable(type).orElse(Type.FLOOR_DIRT);
		this.name = Optional.ofNullable(name).orElse(this.type.getName());
		this.movement = Optional.ofNullable(overrideMovement).orElse(this.type.getMovement());
		try {
			this.image = new ImageIcon(Optional.ofNullable(imageFile).orElse(this.type.getImageFile()));
		} catch (Exception e) {
			BufferedImage img = new BufferedImage(48,48,BufferedImage.TYPE_INT_RGB);
			Graphics2D g = img.createGraphics();
			g.setPaint(new Color(255,53,224));
			g.fillRect(0, 0, 24, 24);
			g.fillRect(24, 24, 24, 24);
			g.setPaint(Color.LIGHT_GRAY);
			g.fillRect(24, 0, 24, 24);
			g.fillRect(0, 24, 24, 24);
			g.setPaint(Color.GRAY);
			g.drawLine(0, 0, 48, 0);
			g.drawLine(0, 0, 0, 48);
			this.image = new ImageIcon(img);
		}
		if (this.image == null) {
			JOptionPane.showMessageDialog(null, "there was a null image");
		}
	}
	public Terrain (Terrain terrain) {
		this.name = terrain.name;
		this.type = terrain.type;
		this.movement = terrain.movement;
		this.image = terrain.image;
	}
	public String getName() {return this.name;}
	public Type getType() {return this.type;}
	public int getMovementMultiplier() {return this.movement.getMult();}
	public ImageIcon getImage() {return this.image;}
	//public boolean equals(Terrain that) {return this.name.equals(that.name) && this.type.equals(that.type) && this.movement.equals(that.movement);}
}
