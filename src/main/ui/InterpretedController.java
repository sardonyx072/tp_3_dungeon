package main.ui;

import main.actor.Actor;
import main.area.Orientation;
import main.control.Controller2;

public class InterpretedController extends Controller2 {
	private static final long serialVersionUID = 7112222854137316929L;

	public void act(String command, String... arg) {
		switch (command) {
		case "moveUp":
			this.moveCurrentActor(Orientation.NORTH);
			break;
		case "moveDown":
			this.moveCurrentActor(Orientation.SOUTH);
			break;
		case "moveLeft":
			this.moveCurrentActor(Orientation.WEST);
			break;
		case "moveRight":
			this.moveCurrentActor(Orientation.EAST);
			break;
		case "attackUp":
			this.attackCurrentActor(Orientation.NORTH);
			break;
		case "attackDown":
			this.attackCurrentActor(Orientation.SOUTH);
			break;
		case "attackLeft":
			this.attackCurrentActor(Orientation.WEST);
			break;
		case "attackRight":
			this.attackCurrentActor(Orientation.EAST);
			break;
		case "openUp":
			//TODO
			break;
		case "openDown":
			//TODO
			break;
		case "openLeft":
			//TODO
			break;
		case "openRight":
			//TODO
			break;
		case "wait":
			this.endTurn();
			break;
		case "load":
			this.load(arg[0]);
			break;
		case "save":
			this.save(arg[0]);
			break;
		case "newg":
			this.newGame();
			break;
		}
		if (this.mode == Mode.SIMPLE_INITIATIVE) {
			this.endTurn();
			while (this.getPlayer().getHP() > 0 && this.getCurrentActor().getAllegiance() != Actor.Allegiance.PARTY) {
				this.moveCurrentActor();
				this.endTurn();
			}
		}
	}
}
