package application;


import UI.GamePane;

//an experiment to see how much JavaFX code is required
//to build a game of reversi

//imports
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

//class defnition for reversi game
public class Reversi extends Application {
	public static final String VERSION = "1.0";
	
	private StackPane stackPane;
	private ReversiControl reversiControl;
	
	private GamePane root;

	// overridden init method
	public void init() {
		root = new GamePane();
//		stackPane = new StackPane();
//		reversiControl = new ReversiControl();
//		stackPane.getChildren().add(reversiControl);
	}
	
	// overridden start method
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Reversi");
		Scene scene = new Scene(root);
        scene.getStylesheets().add("/application/game.css");
        
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}
	
	// overridden stop method
	public void stop() {
		
	}
	
	// entry point into our program for launching our javafx applicaton
	public static void main(String[] args) {
		launch(args);
	}
	
	// private fields for a stack pane and a reversi control
	private StackPane sp_mainlayout;
	private ReversiControl rc_reversi;
	
}




