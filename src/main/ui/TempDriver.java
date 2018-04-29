package main.ui;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.actor.Actor;
import main.area.BackdropElement;
import main.control.Controller;

public class TempDriver {
	public void run() {
		Interpreter interpreter = new Interpreter();
		interpreter.act("load");
		int in = (int)'x', in2 = (int)'x', in3 = (int)'x';
		while (in != (int)'`') {
			draw(interpreter.getController());
			try {
				in = System.in.read();
				in2 = System.in.read();
				in3 = System.in.read();
				//System.out.println(in + " " + in2 + " " + in3);
			} catch (Exception e) {
				in = (int)'x';
			}
			switch(in) {
			case (int)'w':
				interpreter.act("moveUp");
				break;
			case (int)'a':
				interpreter.act("moveLeft");
				break;
			case (int)'s':
				interpreter.act("moveDown");
				break;
			case (int)'d':
				interpreter.act("moveRight");
				break;
			case (int)'W':
				interpreter.act("attackUp");
				break;
			case (int)'A':
				interpreter.act("attackLeft");
				break;
			case (int)'S':
				interpreter.act("attackDown");
				break;
			case (int)'D':
				interpreter.act("attackRight");
				break;
			case (int)'q':
				interpreter.act("endTurn");
				break;
			}
		}
	}
	public void draw(Controller controller) {
		Rectangle bound = controller.getBounds();
		List<Actor> actors = new ArrayList<Actor>();
		for (int y = bound.y; y <= bound.y+bound.height; y++) {
			for (int x = bound.x; x <= bound.x+bound.width; x++) {
				Point point =new Point(x,y);
				BackdropElement element = controller.getBackdrop(point);
				Actor actor = controller.getActor(point);
				if (!element.isTraversible()) {
					System.out.print("WW");
				}
				else if (actor != null) {
					actors.add(actor);
					System.out.print(actor.getActorRace().toString().charAt(0) + " ");
				}
				else {
					System.out.print("  ");
				}
			}
			System.out.println();
		}
		for (Actor actor : actors)
			System.out.println(actor.getActorRace() + " [" + actor.getHP() + "/" + actor.getMaxHP() + "]");
	}
}
