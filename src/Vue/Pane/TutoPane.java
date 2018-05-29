package Vue.Pane;

import Vue.Donnees;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class TutoPane {
	
	private static PopupPane popup_courante = null;

	
	public static void lancer_tuto(){
		popup_courante = tutoDebut();
		GamePane.getInstance().getChildren().add(popup_courante);
	}
	
	private static PopupPane tutoDebut(){
		PopupPane p = new PopupPane();
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(20);
		Label l = new Label("Chaque joueur dispose de plusieurs\n pingouins. Le but est de déplacer\nses pingouins pour manger le plus\nde poissons possible afin de gagner\n la partie.");
		l.setFont(Donnees.FONT_SCORES_FINAUX);
		l.setTextFill(Color.WHITE);
		l.setTextAlignment(TextAlignment.CENTER);
		p.pane.setMaxWidth(600);
		p.pane.setMaxHeight(400);
		p.pane.getChildren().add(l);
		p.pane.getChildren().add(hbox);
		Button b = creer_bouton_suivant("Continuer");
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GamePane.getInstance().getChildren().remove(popup_courante);
				popup_courante = tutoPoser();
				GamePane.getInstance().getChildren().add(popup_courante);
			}
		});
		p.pane.getChildren().add(b);
		return p;
	}
	
	private static PopupPane tutoPoser(){
		PopupPane p = new PopupPane();
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(20);
		Label l = new Label("Pour commencer, cliquez sur une\n case avec 1 poissons pour poser vos\npingouins.");
		l.setFont(Donnees.FONT_SCORES_FINAUX);
		l.setTextFill(Color.WHITE);
		l.setTextAlignment(TextAlignment.CENTER);
		p.pane.setMaxWidth(600);
		p.pane.setMaxHeight(400);
		p.pane.getChildren().add(l);
		p.pane.getChildren().add(hbox);
		Button b = creer_bouton_suivant("Compris!");
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GamePane.getInstance().getChildren().remove(popup_courante);
				popup_courante = null;
			}
		});
		p.pane.getChildren().add(b);
		return p;
	}
	
	private static Button creer_bouton_suivant(String txt){
		Button b = new Button(txt);
		b.setFont(Donnees.FONT_SCORES_FINAUX);
		b.setTextFill(Color.BLACK);
		return b;
	}
	

}
