package main.actor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Sprite {
	public static final int WIDTH = 48, HEIGHT = 48;
	public static enum Moment {
		RIGHT_STEP_SOUTH(0,0),
		MID_STEP_SOUTH(0,1),
		LEFT_STEP_SOUTH(0,2),
		RIGHT_STEP_WEST(1,0),
		MID_STEP_WEST(1,1),
		LEFT_STEP_WEST(1,2),
		LEFT_STEP_EAST(2,0),
		MID_STEP_EAST(2,1),
		RIGHT_STEP_EAST(2,2),
		LEFT_STEP_NORTH(3,0),
		MID_STEP_NORTH(3,1),
		RIGHT_STEP_NORTH(3,2),
		NONE(-1,-1);
		public int row, col;
		private Moment(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}
	private String fileName;
	private HashMap<Moment,ImageIcon> moments;
	public Sprite(String fileName) {
		this.fileName = fileName;
		this.moments = new HashMap<Moment,ImageIcon>();
		BufferedImage momentsbmp = null;
		try {
			momentsbmp = ImageIO.read(new File(this.fileName));
		} catch (IOException e) { //textures missing, get default
			int rows = Arrays.asList(Moment.values()).stream().mapToInt(moment -> moment.row).max().getAsInt()+1;
			int cols = Arrays.asList(Moment.values()).stream().mapToInt(moment -> moment.col).max().getAsInt()+1;
			momentsbmp = new BufferedImage(WIDTH*cols,HEIGHT*rows,BufferedImage.TYPE_INT_RGB);
			Graphics2D g = momentsbmp.createGraphics();
			for (int row=0; row<rows; row++)
				for (int col=0; col<cols; col++)
					this.drawDefaultIcon(g, col, row);
		}
		for (Moment moment : Moment.values()) {
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			BufferedImage momentbmp = null;
			if (moment != Moment.NONE)
				momentbmp = momentsbmp.getSubimage(moment.col*WIDTH, moment.row*HEIGHT, WIDTH, HEIGHT);
			else {
				momentbmp = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
				Graphics2D g = momentbmp.createGraphics();
				this.drawDefaultIcon(g, 0, 0);
			}
			try {
				ImageIO.write(momentbmp, "PNG", bytes);
			} catch (IOException e) {
				momentbmp = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
				Graphics2D g = momentsbmp.createGraphics();
				this.drawDefaultIcon(g, 0, 0);
			}
			this.moments.put(moment, new ImageIcon(bytes.toByteArray()));
		}
	}
	private void drawDefaultIcon (Graphics2D g, int col, int row) {
		g.setPaint(new Color(255,53,224));
		g.fillRect(col*WIDTH + 0, row*HEIGHT + 0, WIDTH/2, HEIGHT/2);
		g.fillRect(col*WIDTH + WIDTH/2, row*HEIGHT + HEIGHT/2, WIDTH/2, HEIGHT/2);
		g.setPaint(Color.LIGHT_GRAY);
		g.fillRect(col*WIDTH + WIDTH/2, row*HEIGHT + 0, WIDTH/2, HEIGHT/2);
		g.fillRect(col*WIDTH + 0, row*HEIGHT + HEIGHT/2, WIDTH/2, HEIGHT/2);
		g.setPaint(Color.GRAY);
		g.drawLine(col*WIDTH + 0, row*HEIGHT + 0, col*WIDTH + WIDTH, row*HEIGHT + 0);
		g.drawLine(col*WIDTH + 0, row*HEIGHT + 0, col*WIDTH + 0, row*HEIGHT + HEIGHT);
	}
	public String getFileName() {return this.fileName;}
	public ImageIcon getMoment(Moment moment) {return this.moments.get(moment);}
	
}
