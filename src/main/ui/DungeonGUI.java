package main.ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.*;

import com.sun.glass.events.KeyEvent;

import main.actor.Actor;
import main.actor.Score;
import main.area.Area2;
import main.area.Orientation;
import main.items.Item;

public class DungeonGUI {
	private static final int SQ_WIDTH = 48, SQ_COUNT = 25, DRAW_WIDTH = SQ_WIDTH*SQ_COUNT;
	private InterpretedController controller;
	private JFrame window;
	private JMenuBar bar;
	private JMenu file, help;
	private JMenuItem save, load, newg, howtoplay;
	private JPanel container, dungeon, party;
	private Actor displayActor;
	public DungeonGUI() {
		this.controller = new InterpretedController();
		this.controller.newGame();
		//JOptionPane.showOptionDialog(null, "What to do?", "Welcome to DungeonDelver!", null, null, null, new String[] {"Load Game", "New Game", "Quit"}, null);
		this.displayActor = this.controller.getPlayer();
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
					try {
						controller.act("save", fileChooser.getSelectedFile().getCanonicalPath());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				dungeon.repaint();
				party.repaint();
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
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.act("load", fileChooser.getSelectedFile().getCanonicalPath());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				dungeon.repaint();
				party.repaint();
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
				party.repaint();
			}}
		});
		this.file = new JMenu("File");
		this.file.add(this.save);
		this.file.add(this.load);
		this.file.add(this.newg);
		this.howtoplay = new JMenuItem("How To Play");
		this.howtoplay.getAccessibleContext().setAccessibleDescription("Get some help figuring out how to play Dungeon Delver!");
		this.howtoplay.addMouseListener(new MouseListener() {
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
				JOptionPane.showMessageDialog(null, "use WASD to move your character around\r\n" + 
						"use SHIFT + WASD to attack skeletons or the scary vampire!\r\n" + 
						"find the stairs on each floor and get to the vampire quickly!\r\n" + 
						"click on characters to see their stats");
				dungeon.repaint();
				party.repaint();
			}}
		});
		this.help = new JMenu("Help");
		this.help.add(this.howtoplay);
		this.bar = new JMenuBar();
		this.bar.add(this.file);
		this.bar.add(this.help);
		this.window.setJMenuBar(this.bar);
		//this.window.setVisible(true);
		this.dungeon = new JPanel() {
			private static final long serialVersionUID = -1937703836491254647L;

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
		this.dungeon.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				Point point = new Point(arg0.getX()/SQ_WIDTH,arg0.getY()/SQ_WIDTH);
				Actor actor = controller.getActor(point);
				System.out.println(String.format("clicked on point %s which translates to %s and contains actor %s", new Point(arg0.getX(),arg0.getY()), point, actor==null ? "null" : actor.getActorRace() + " " + actor.getActorClass()));
				if (actor != null)
					displayActor = actor;
				else
					displayActor = controller.getPlayer();
				party.repaint();
			}
		});
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
		this.party = new JPanel() {
			private static final long serialVersionUID = -4335800714697761880L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				System.out.println("repainting party");
				Graphics2D g2 = (Graphics2D)g;
				ImageIcon icon = displayActor.getActorRace().getImage(Orientation.SOUTH); //front-facing
				BufferedImage img = new BufferedImage(icon.getIconWidth(),icon.getIconHeight(),BufferedImage.TYPE_INT_RGB);
				icon.paintIcon(null, img.createGraphics(), 0, 0);
				icon = new ImageIcon(img.getScaledInstance(240, 240, BufferedImage.SCALE_DEFAULT));
				icon.paintIcon(null, g2, 120, 24);
				g2.drawString(displayActor.getActorRace() + " " + displayActor.getActorClass() + " level " + displayActor.getLevel(), 24, 288);
				g2.drawString(String.format("STRENGTH: %d, DEXTERITY: %d, CONSTITUTION: %d", displayActor.getScore(Score.Type.STRENGTH), displayActor.getScore(Score.Type.DEXTERITY), displayActor.getScore(Score.Type.CONSTITUTION)), 24, 312);
				g2.drawString(String.format("INTELLIGENCE: %d, WISDOM: %d, CHARISMA: %d", displayActor.getScore(Score.Type.INTELLIGENCE), displayActor.getScore(Score.Type.WISDOM), displayActor.getScore(Score.Type.CHARISMA)), 24, 336);
				g2.drawString("\tHP: " + displayActor.getHP() + "/" + displayActor.getMaxHP(), 24, 360);
				g2.drawString("\tAC: " + displayActor.getArmorClass() + " (" + displayActor.getArmor().getName() + ")", 24, 384);
				g2.drawString("\tWeapon: " + displayActor.getMainHand().getName() + " " + displayActor.getMainHand().get1HDamage().toString(), 24, 408);
				g2.drawString("Inventory:", 24, 432);
				List<Item> inventory = displayActor.getInventory();
				int i = 0;
				for (i = 0; i < inventory.size(); i++) {
					g2.drawString("    " + inventory.get(i).getName(),24,432+((i+1)*24));
				}
				g2.drawString("Initiative:",24,432+(++i*24));
				i++;
				for (int j = 0; j < controller.getOrder().size(); j++) {
					Actor actor = controller.getOrder().get(j);
					g2.drawString((j+1) + ". " + actor.getActorRace() + " " + actor.getActorClass() + " " + actor.getHP() + "/" + actor.getMaxHP(), 24, (432+((i+j)*24)));
				}
				if (controller.getPlayer().getHP() == 0 || controller.getBoss().getHP() == 0) {
					int result = JOptionPane.showOptionDialog(null, "What to do?", "Welcome to Dungeon Delver!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[] {"Load Game","New Game","Quit"}, null);
					if (result == JOptionPane.YES_OPTION) {
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
						party.repaint();
					}
					else if (result == JOptionPane.NO_OPTION) {
						controller.act("newg");
						dungeon.repaint();
						party.repaint();
					}
					else if (result == JOptionPane.CANCEL_OPTION) {
					    WindowEvent wev = new WindowEvent(window, WindowEvent.WINDOW_CLOSING);
					    Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
					    window.setVisible(false);
					    window.dispose();
					    System.exit(0); 
					}
				}
			}
		};
		this.party.setBackground(new Color(244,191,66));
		this.party.setPreferredSize(new Dimension(480,DRAW_WIDTH));
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
				party.repaint();
			}
			@Override
			public void keyReleased(java.awt.event.KeyEvent arg0) {
			}
			@Override
			public void keyTyped(java.awt.event.KeyEvent arg0) {
			}
		});
		this.window.pack();
		this.window.setVisible(true);
		dungeon.repaint();
		party.repaint();
		int result = JOptionPane.showOptionDialog(null, "What to do?", "Welcome to Dungeon Delver!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[] {"Load Game","New Game","Quit"}, null);
		if (result == JOptionPane.YES_OPTION) {
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
			party.repaint();
		}
		else if (result == JOptionPane.NO_OPTION) {
			controller.act("newg");
			dungeon.repaint();
			party.repaint();
		}
		else if (result == JOptionPane.CANCEL_OPTION) {
		    WindowEvent wev = new WindowEvent(this.window, WindowEvent.WINDOW_CLOSING);
		    Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
		    this.window.setVisible(false);
		    this.window.dispose();
		    System.exit(0); 
		}
	}
}
