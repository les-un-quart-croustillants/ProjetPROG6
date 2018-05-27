package Vue.Pane;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import Modele.Moteur.Moteur.State;
import Utils.Position;
import Vue.Donnees;
import Vue.InterfaceGraphique;
import Vue.Listener.SauvegardeListener;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class BoutonSauvegarde extends Button implements SauvegardeListener {

	public BoutonSauvegarde() {
		super();
		setStyleDefault();
		setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (GamePane.moteur().currentState() == State.SELECTIONNER_DESTINATION) {
					Position lastSelection = GamePane.moteur().pingouinSelection().position();
					LinkedList<Position> lastaccessibles = GamePane.moteur().plateau().accessible(lastSelection);
					GamePane.moteur().selectionnerDestination(new Position(-1, -1));
					for (Position pos : lastaccessibles) {
						if(GamePane.getPlateauCadre().plateauGraphique.cases[pos.i()][pos.j()]!=null)
							GamePane.getPlateauCadre().plateauGraphique.cases[pos.i()][pos.j()].deselect();
					}
					GamePane.getPlateauCadre().plateauGraphique.cases[lastSelection.i()][lastSelection.j()].pingouinGraphique.transformer(false);
				}
				FileChooser fileChooser = new FileChooser();
				//fileChooser.setInitialDirectory(new File("rsc/save/"));
				fileChooser.setInitialFileName(new SimpleDateFormat("yyyy-MM-dd_HH-mm").format(new Date())+".save");
				File file = fileChooser.showSaveDialog(InterfaceGraphique.stage);
				if (file != null) {
					if (GamePane.moteur().sauvegarder(file)) {
						setStyleConfirmation();
						Timeline timeline = new Timeline(new KeyFrame(new Duration(750), ae -> setStyleDefault()));
						timeline.play();
					}
				}
			}
		});
		GamePane.getPlateauCadre().moteurGraphique.sauvegardeListener = this;
		setDisable(!GamePane.getPlateauCadre().moteurGraphique.getSauvegardeAutorise());
	}

	@Override
	public void action(boolean autorise) {
		setDisable(!autorise);
	}

	private void setStyleDefault() {
		setStyle(null);
		setTextFill(Color.BLACK);
		setText("Sauvegarder");
		setFont(Donnees.FONT_SCORES_FINAUX);
	}

	private void setStyleConfirmation() {
		this.setText("Sauvegarde ok");
		this.setTextFill(Color.GREEN);
		setStyle("-fx-background-color: transparent;");
	}

}
