package Vue.Pane;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PopupPane extends StackPane{
	
	public VBox pane;

	public PopupPane() {
		this.setStyle(" -fx-background-color: rgba(0,0,0,0.5);");
		pane = creer_pane_central();
		pane.setMaxWidth(400);
		pane.setMaxHeight(600);
		this.getChildren().add(pane);
	}
	
	private VBox creer_pane_central() {
		VBox v = new VBox();
		v.setStyle(" -fx-background-color: rgba(35,60,100,1); -fx-background-radius: 25; -fx-border-color: rgb(10,30,60);  -fx-border-radius: 20; -fx-border-width: 10;");
		v.setAlignment(Pos.CENTER);
		v.setSpacing(30);
		return v;
	}
	
}
