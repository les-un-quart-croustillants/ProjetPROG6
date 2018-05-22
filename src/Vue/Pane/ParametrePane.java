package Vue.Pane;

import Vue.Donnees;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ParametrePane extends PopupPane{

	public ParametrePane() {
		super();
		remplir_pane_central();
	}
	
	private void remplir_pane_central() {	
		pane.getChildren().add(creer_retour_jeu());
		pane.getChildren().add(creer_charger());
		pane.getChildren().add(creer_sauvegarder());
		pane.getChildren().add(creer_retour_menu());
		pane.getChildren().add(creer_quitter());	
	}
	
	private Button creer_sauvegarder() {
		Button b = new Button("Sauvegarder");
		b.setFont(Donnees.FONT_SCORES_FINAUX);
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("TODO: sauvegarder");
			}
		});
		return b;
	}
	
	private Button creer_charger() {
		Button b = new Button("charger");
		b.setFont(Donnees.FONT_SCORES_FINAUX);
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("TODO: charger");
			}
		});
		return b;
	}
	
	private Button creer_retour_jeu() {
		Button b = new Button("Revenir au jeu");
		b.setFont(Donnees.FONT_TEXT);
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GamePane.getInstance().getChildren().remove(ParametrePane.this);
			}
		});
		return b;
	}
	
	private Button creer_quitter() {
		Button b = new Button("Quitter");
		b.setFont(Donnees.FONT_SCORES_FINAUX);
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
		return b;
	}
	
	private Button creer_retour_menu() {
		Button b = new Button("Retour au Menu");
		b.setFont(Donnees.FONT_SCORES_FINAUX);
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("TODO: Retour au menu");
			}
		});
		return b;
	}
}
