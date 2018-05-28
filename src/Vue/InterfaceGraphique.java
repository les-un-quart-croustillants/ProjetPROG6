package Vue;


import javafx.application.Application;
import Vue.Menu;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Vue.Pane.GamePane;
import Modele.Moteurs.MoteurApp.*;
import Modele.Moteurs.MoteurApp;

public class InterfaceGraphique extends Application {	
	private static MoteurApp m;
	public static Stage stage;
	
	static public float dt = 1f/60f;
	
	public static void creer(String[] args, MoteurApp m) {
		InterfaceGraphique.m = m;
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		Menu.setInterfaceGraphique(this);
		Menu.setMoteurApp(m);
        stage.setScene(new Scene(Menu.getInstance(), 1000, 800));
		stage.show();
		stage.setMinHeight(400);
		stage.setMinWidth(600);
	}
	
	public static void transition(Action action) {
		m.transition(action);
	}
	
	public static void graphic_state() {
		switch(m.currentState()) {
		case MENU:
			//stage.setScene(new Scene(Menu.getInstance(), 1000, 800));
			stage.getScene().setRoot(Menu.getInstance());
			Menu.reload_css();
			Menu.getInstance().getChildren().removeAll(ConfigMenu.getInstance(), NewGameMenu.getInstance());
			break;
		case JEU:
			//stage.setScene(new Scene(GamePane.getInstance()));
			stage.getScene().setRoot(GamePane.getInstance());
			break;
		case QUITTER:
			stage.close();
			break;
		}
	}
}
