package Vue;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InterfaceGraphique extends Application {	
	final static float dt = 0.0166f; //temps entre 2 frames en s (60 fps)
	
	public static void creer(String[] args) {
		launch(args);
	}
	
	static public PlateauCadre pc; //TODO: à supprimer

	@Override
	public void start(Stage primaryStage) {
		pc = new PlateauCadre(800,600);
		pc.plateauGraphique.start();
        Scene menu = new Scene(pc, 1000, 800);
        primaryStage.setScene(menu);  
		primaryStage.show();
		primaryStage.setMinHeight(400);
		primaryStage.setMinWidth(600);
		new AnimationTimer() {
			@Override
			public void handle(long currentNanoTime) {
				pc.update();
				pc.draw();
			}
		}.start();
	}
	
	
}
