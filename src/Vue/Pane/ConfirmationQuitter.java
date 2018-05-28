package Vue.Pane;


import Vue.Donnees;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class ConfirmationQuitter extends PopupPane {

	public ConfirmationQuitter() {
		super();
		//VBox vbox = new VBox();
		HBox hbox = new HBox();
		hbox.getChildren().add(creer_oui());
		hbox.getChildren().add(creer_non());
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(20);
		Label l = new Label("Voulez vous vraiment\nquitter l'application?");
		l.setFont(Donnees.FONT_SCORES_FINAUX);
		l.setTextFill(Color.BLACK);
		pane.setMaxWidth(450);
		pane.setMaxHeight(300);
		pane.getChildren().add(l);
		pane.getChildren().add(hbox);
		//pane.getChildren().add(vbox);
	}
	
	private Button creer_oui() {
		Button b = new Button("oui");
		b.setFont(Donnees.FONT_SCORES_FINAUX);
		b.setTextFill(Color.BLACK);
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
		return b;
	}
	
	private Button creer_non() {
		Button b = new Button("non");
		b.setFont(Donnees.FONT_SCORES_FINAUX);
		b.setTextFill(Color.BLACK);
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GamePane.getInstance().getChildren().remove(ConfirmationQuitter.this);
			}
		});
		return b;
	}
	
}
