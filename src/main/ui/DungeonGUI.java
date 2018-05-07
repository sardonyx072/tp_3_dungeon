package main.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import com.sun.glass.events.KeyEvent;

import main.actor.Sprite;

public class DungeonGUI {
	private static final int SQ_WIDTH = 48, SQ_COUNT = 25, DRAW_WIDTH = SQ_WIDTH*SQ_COUNT;
	private Interpreter interpreter;
	private JFrame window;
	private JMenuBar bar;
	private JMenu file;
	private JMenuItem save, load;
	private JPanel container, dungeon, party;
	public DungeonGUI() {
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
				}
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
				}
			}}
		});
		this.file = new JMenu("File");
		this.bar = new JMenuBar();
		this.file.add(this.save);
		this.file.add(this.load);
		this.bar.add(this.file);
		this.window.setJMenuBar(this.bar);
		//this.window.setVisible(true);
		this.dungeon = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D)g;
				g2.setPaint(Color.GRAY);
				for (int i = 1; i < SQ_COUNT; i++) {
					int x = i*SQ_WIDTH;
					g2.drawLine(x, 0, x, getSize().height);
				}
				for (int i = 1; i < SQ_COUNT; i++) {
					int y = i*SQ_WIDTH;
					g2.drawLine(0, y, getSize().width, y);
				}
				ImageIcon test0 = new Sprite("./src/main/resources/adventurer.png").getMoment(Sprite.Moment.MID_STEP_SOUTH);
				test0.paintIcon(this, g2, 0*SQ_WIDTH, 0*SQ_WIDTH);
				ImageIcon test1 = new Sprite("./src/main/resources/skeleton.png").getMoment(Sprite.Moment.MID_STEP_SOUTH);
				test1.paintIcon(this, g2, 1*SQ_WIDTH, 0*WIDTH);
				ImageIcon test2 = new Sprite("./src/main/resources/skeleton.png").getMoment(Sprite.Moment.MID_STEP_SOUTH);
				test2.paintIcon(this, g2, 2*SQ_WIDTH, 0*WIDTH);
				ImageIcon test3 = new Sprite("./src/main/resources/false.png").getMoment(Sprite.Moment.MID_STEP_SOUTH);
				test3.paintIcon(this, g2, 3*SQ_WIDTH, 0*WIDTH);
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
		this.window.pack();
		this.window.setVisible(true);
	}
	public void run() {
		
	}
}
