package main;

import main.ui.DungeonGUI;

import java.io.File;

import javafx.application.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Application extends javafx.application.Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		MediaPlayer player1 = new MediaPlayer(new Media(new File("./src/main/resources/dungeon.mp3").toURI().toString()));
		player1.setOnEndOfMedia(new Runnable() {
			public void run() {
				player1.seek(Duration.ZERO);
			}
		});
		player1.play();
		DungeonGUI ui = new DungeonGUI();
	}
}
