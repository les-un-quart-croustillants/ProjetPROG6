package Vue.Pane;

import Vue.Donnees;
import Vue.InterfaceGraphique;
import Vue.MainMenu;
import Vue.Menu;
import Vue.Donnees.Niveau;

import java.io.File;

import Modele.Moteur.Moteur;
import Modele.Moteurs.MoteurApp.Action;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class ParametrePane extends PopupPane {

	public ParametrePane() {
		super();
		this.setAlignment(Pos.TOP_RIGHT);
		this.setPadding(new Insets(20));
		remplir_pane_central();
	}

	private void remplir_pane_central() {
		pane.getChildren().add(creer_retour_jeu());
		pane.getChildren().add(creer_charger());
		pane.getChildren().add(new BoutonSauvegarde());
		pane.getChildren().add(creer_retour_menu());
		pane.getChildren().add(creer_quitter());
	}

	private Button creer_charger() {
		Button b = new Button("charger");
		b.setFont(Donnees.FONT_SCORES_FINAUX);
		b.setTextFill(Color.BLACK);
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				//fileChooser.setInitialDirectory(new File("rsc/save/"));
				File file = fileChooser.showOpenDialog(InterfaceGraphique.stage);
				if (file != null) {
					GamePane.getInstance().getChildren().remove(ParametrePane.this);
					GamePane.newInstance(Moteur.charger(file), GamePane.getPlateauCadre().niveau);
					InterfaceGraphique.stage.getScene().setRoot(GamePane.getInstance());
				}
			}
		});
		return b;
	}

	private Button creer_retour_jeu() {
		Button b = new Button("Revenir au jeu");
		b.setFont(Donnees.FONT_TEXT);
		b.setTextFill(Color.BLACK);
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
		b.setTextFill(Color.BLACK);
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//System.exit(0);
				GamePane.getInstance().getChildren().add(new ConfirmationQuitter());
			}
		});
		return b;
	}

	private Button creer_retour_menu() {
		Button b = new Button("Retour au Menu");
		b.setFont(Donnees.FONT_SCORES_FINAUX);
		b.setTextFill(Color.BLACK);
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				InterfaceGraphique.transition(Action.RETOUR_MENU);
				InterfaceGraphique.graphic_state();
			}
		});
		return b;
	}
}
