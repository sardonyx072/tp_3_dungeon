package main.ui;

import main.area.Direction;
import main.control.Controller;

public class Interpreter {
	private Controller controller;
	public Interpreter() {
		this.controller = new Controller();
	}
	public void act(String command) {
		switch (command) {
		case "load":
			controller.loadWorld();
			break;
		case "moveUp":
			controller.move(controller.currentActor(), Direction.NORTH);
			break;
		case "moveDown":
			controller.move(controller.currentActor(), Direction.SOUTH);
			break;
		case "moveLeft":
			controller.move(controller.currentActor(), Direction.WEST);
			break;
		case "moveRight":
			controller.move(controller.currentActor(), Direction.EAST);
			break;
		case "attackUp":
			controller.attack(controller.currentActor(), Direction.NORTH);
			break;
		case "attackDown":
			controller.attack(controller.currentActor(), Direction.SOUTH);
			break;
		case "attackLeft":
			controller.attack(controller.currentActor(), Direction.WEST);
			break;
		case "attackRight":
			controller.attack(controller.currentActor(), Direction.EAST);
			break;
		case "endTurn":
			this.controller.endTurn();
			break;
		}
	}
	public Controller getController() {
		return this.controller;
	}
}
