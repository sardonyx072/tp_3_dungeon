package main.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import com.sun.glass.events.KeyEvent;

import main.actor.Actor;
import main.actor.Sprite;
import main.area.Area2;
import main.area.Dungeon;
import main.area.Terrain;

public class DungeonGUI {
	private static final int SQ_WIDTH = 48, SQ_COUNT = 25, DRAW_WIDTH = SQ_WIDTH*SQ_COUNT;
	private InterpretedController controller;
	private JFrame window;
	private JMenuBar bar;
	private JMenu file;
	private JMenuItem save, load, newg;
	private JPanel container, dungeon, party;
	public DungeonGUI() {
		this.controller = new InterpretedController();
		this.controller.newGame();
//		for (int y = this.controller.getCurrentDungeon().getCurrentArea().getShape().getBounds().y; y < 25; y++)
//			for (int x = this.controller.getCurrentDungeon().getCurrentArea().getShape().getBounds().x; x < 25; x++)
//				System.out.println(this.controller.getTerrain(new Point(x,y)) == null);
		this.window = new JFrame();
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.window.setTitle("Dungeon");
		this.window.setLocationRelativeTo(null);
		this.window.setResizable(false);
		this.save = new JMenuItem("Save Game");
		this.save.setMnemonic(KeyEvent.VK_T);
		this.save.getAccessibleContext().setAccessibleDescription("Save your current game to file.");
		this.save.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) { {
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					//interpreter.act("save");
					try {
						controller.act("save", fileChooser.getSelectedFile().getCanonicalPath());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				dungeon.repaint();
			}}
		});
		this.load = new JMenuItem("Load Game");
		this.load.getAccessibleContext().setAccessibleDescription("Load a saved game from a file.");
		this.load.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) { {
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					//interpreter.act("load");
					try {
						controller.act("load", fileChooser.getSelectedFile().getCanonicalPath());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				dungeon.repaint();
			}}
		});
		this.newg = new JMenuItem("New Game");
		this.newg.getAccessibleContext().setAccessibleDescription("Start a new game.");
		this.newg.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) { {
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to start a new game? Any unsaved progress will be lost.") == JOptionPane.OK_OPTION) {
					//interpreter.act("load");
					controller.act("newg");
				}
				dungeon.repaint();
			}}
		});
		this.file = new JMenu("File");
		this.bar = new JMenuBar();
		this.file.add(this.save);
		this.file.add(this.load);
		this.file.add(this.newg);
		this.bar.add(this.file);
		this.window.setJMenuBar(this.bar);
		//this.window.setVisible(true);
		this.dungeon = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				System.out.println("repainting dungeon");
				Graphics2D g2 = (Graphics2D)g;
				Area2 area = controller.getCurrentDungeon().getCurrentArea();
				Rectangle bounds = area.getShape().getBounds();
				for (int y = bounds.y; y <= bounds.y+bounds.height; y++)
					for (int x = bounds.x; x <= bounds.x+bounds.width; x++) {
						Point point = new Point(x,y);
						Area2.OrientedTerrain terrain = area.getTerrain(point);
						Area2.OrientedActor actor = area.getActor(point);
						if (terrain != null) {
							terrain.getImage().paintIcon(this, g2, x*SQ_WIDTH, y*SQ_WIDTH);
						}
						if (actor != null) {
							actor.getAppearance().paintIcon(this, g2, x*SQ_WIDTH, y*SQ_WIDTH);
						}
					}
			}
		};
		this.dungeon.setBackground(Color.WHITE);
		this.dungeon.setPreferredSize(new Dimension(DRAW_WIDTH,DRAW_WIDTH));
		//this.dungeon.setMaximumSize(new Dimension(DRAW_WIDTH,DRAW_WIDTH));
		this.dungeon.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
		this.dungeon.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.SOUTH;
		constraints.gridy++;
		constraints.anchor = GridBagConstraints.CENTER;
		this.party = new JPanel();
		this.party.setBackground(new Color(244,191,66));
		this.party.setPreferredSize(new Dimension(500,250));
		this.party.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		this.container = new JPanel();
		this.container.setLayout(new BoxLayout(this.container,BoxLayout.X_AXIS));
		this.container.setBackground(Color.RED);
		this.container.add(this.dungeon, constraints);
		this.container.add(this.party);
		this.window.add(this.container);
		this.window.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent arg0) {
				switch(arg0.getKeyCode()) {
				case KeyEvent.VK_W:
					if(arg0.isShiftDown())
						controller.act("attackUp");
					else
						controller.act("moveUp");
					break;
				case KeyEvent.VK_A:
					if(arg0.isShiftDown())
						controller.act("attackLeft");
					else
						controller.act("moveLeft");
					break;
				case KeyEvent.VK_S:
					if(arg0.isShiftDown())
						controller.act("attackDown");
					else
						controller.act("moveDown");
					break;
				case KeyEvent.VK_D:
					if(arg0.isShiftDown())
						controller.act("attackRight");
					else
						controller.act("moveRight");
					break;
				}
				dungeon.repaint();
			}

			@Override
			public void keyReleased(java.awt.event.KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(java.awt.event.KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		this.window.pack();
		this.window.setVisible(true);
	}
	public void run() {
		
	}
}
