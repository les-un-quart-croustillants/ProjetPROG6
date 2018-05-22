package Vue;

import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.text.*;

public class TerrainMenu extends VBox {	
	private static TerrainMenu instance = null;
	Integer dim = 8;
	Button retour;
	
	public static TerrainMenu getInstance() {
		if(instance == null)
			instance = new TerrainMenu();
		return instance;
	}
	
	private TerrainMenu() {
		Font.loadFont(getClass().getResourceAsStream("LuckiestCuy.ttf"), 14);
		this.getStyleClass().add("menu");
		create_elements();
	}
	
	private void create_elements() {
		Label mapDim = new Label("Dimensions");
		Label LblDim1 = new Label(dim.toString());
		Label x = new Label("x");
		Label LblDim2 = new Label(dim.toString());
		HBox dimBox = new HBox();
		Button plusDim = new Button();
		Button minusDim = new Button();
		
		GridPane poissonsLyt = new GridPane();
		Label poissons1 = new Label("1 poissons");
		Label poissons2 = new Label("2 poissons");
		Label poissons3 = new Label("3 poissons");
		TextField tf_p1 = new TextField("");
		TextField tf_p2 = new TextField("");
		TextField tf_p3 = new TextField("");
		
		// Configuration
		mapDim.getStyleClass().addAll("smaller");
		LblDim1.getStyleClass().addAll("smaller");
		LblDim2.getStyleClass().addAll("smaller");
		x.getStyleClass().addAll("smaller");
		poissonsLyt.setConstraints(poissons1, 0, 0);
		poissonsLyt.setConstraints(tf_p1, 0, 1);
		poissonsLyt.setConstraints(poissons2, 1, 0);
		poissonsLyt.setConstraints(tf_p2, 1, 1);
		poissonsLyt.setConstraints(poissons3, 2, 0);
		poissonsLyt.setConstraints(tf_p3, 2, 1);
		poissons1.getStyleClass().addAll("smaller");
		poissons2.getStyleClass().addAll("smaller");
		poissons3.getStyleClass().addAll("smaller");
		poissonsLyt.getStyleClass().add("center");
		dimBox.getStyleClass().add("center");
		plusDim.getStyleClass().addAll("iconbutton", "rightbuttonsmall");
		minusDim.getStyleClass().addAll("iconbutton", "leftbuttonsmall");
		
		plusDim.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				dim++;
				LblDim1.setText(dim.toString());
				LblDim2.setText(dim.toString());
			}
		});
		
		minusDim.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				dim = dim-1 <= 0 ? 1 : dim-1;
				LblDim1.setText(dim.toString());
				LblDim2.setText(dim.toString());
			}
		});
		
		poissonsLyt.getChildren().addAll(poissons1, poissons2, poissons3, tf_p1, tf_p2, tf_p3);
		dimBox.getChildren().addAll(mapDim, new Label(), minusDim, LblDim1, x, LblDim2, plusDim);
		this.getChildren().addAll(dimBox, poissonsLyt);
	}
}