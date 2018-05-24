package Vue;

import javafx.scene.layout.*;
import Vue.TriSlider;
import javafx.event.ActionEvent;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.input.*;


public class TerrainMenu extends VBox {	
	private static TerrainMenu instance = null;
	Integer dim = 8;
	Button retour, jouer;
	TriSlider proportions_pingouins;
	Integer nbP1, nbP2, nbP3;
	Label lblS1, lblS2, lblS3;
	
	public static TerrainMenu getInstance() {
		if(instance == null)
			instance = new TerrainMenu();
		return instance;		
	}
	
	private TerrainMenu() {
		Font.loadFont(getClass().getResourceAsStream("LuckiestCuy.ttf"), 14);
		this.getStyleClass().add("menu");
		create_elements();
		update_proportions();
	}
	
	private void update_proportions() {
		Double minPosition = ConfigMenu.getInstance().create_config().nb_pingouins().doubleValue()/(dim*dim);
		if(proportions_pingouins.getDividers().get(0).getPosition() < minPosition)
			proportions_pingouins.getDividers().get(0).setPosition(minPosition);
		Double reste = .0;
		nbP1 = Double.valueOf(Math.round(proportions_pingouins.getDividers().get(0).getPosition()*(dim*dim))).intValue();
		reste = proportions_pingouins.getDividers().get(0).getPosition()*(dim*dim)- nbP1.doubleValue();
		nbP2 = Double.valueOf(Math.round(reste+(proportions_pingouins.getDividers().get(1).getPosition()-proportions_pingouins.getDividers().get(0).getPosition())*(dim*dim))).intValue();
		nbP3 = (dim*dim)-nbP2-nbP1;
		lblS1.setText(nbP1.toString());
		lblS2.setText(nbP2.toString());
		lblS3.setText(nbP3.toString());
	}
	
	private void create_elements() {
		proportions_pingouins = new TriSlider();
		Label mapDim = new Label("Dimensions");
		Label LblDim1 = new Label(dim.toString());
		Label x = new Label("x");
		Label LblDim2 = new Label(dim.toString());
		HBox dimBox = new HBox();
		Button plusDim = new Button();
		Button minusDim = new Button();
		retour = new Button("Retour");
		jouer = new Button("JOUER");		
		
		GridPane poissonsLyt = new GridPane();
		jouer.getStyleClass().add("textbutton");
		Label poissons1 = new Label("1 poissons");
		Label poissons2 = new Label("2 poissons");
		Label poissons3 = new Label("3 poissons");
		TextField tf_p1 = new TextField("");
		TextField tf_p2 = new TextField("");
		TextField tf_p3 = new TextField("");
		
		// Configuration
		retour.getStyleClass().add("textbutton");
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
				update_proportions();
			}
		});
		
		minusDim.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				dim = dim-1 <= 0 ? 1 : dim-1;
				LblDim1.setText(dim.toString());
				LblDim2.setText(dim.toString());
				update_proportions();
			}
		});
		
		// TriSlider
		lblS1 = new Label("3");
		lblS2 = new Label("3");
		lblS3 = new Label("3");
		
		lblS1.getStyleClass().add("smaller");
		lblS2.getStyleClass().add("smaller");
		lblS3.getStyleClass().add("smaller");
		
		proportions_pingouins.getDividers().get(0).positionProperty().addListener((observable, oldvalue, newvalue) -> {
			update_proportions();
		});
		
		proportions_pingouins.getDividers().get(1).positionProperty().addListener((observable, oldvalue, newvalue) -> {
			update_proportions();
		});
		
		proportions_pingouins.getFirstSlice().getChildren().add(lblS1);
		proportions_pingouins.getSecondSlice().getChildren().add(lblS2);
		proportions_pingouins.getThirdSlice().getChildren().add(lblS3);
		
		poissonsLyt.getChildren().addAll(poissons1, poissons2, poissons3, tf_p1, tf_p2, tf_p3);
		dimBox.getChildren().addAll(mapDim, new Label(), minusDim, LblDim1, x, LblDim2, plusDim);
		this.getChildren().addAll(dimBox, proportions_pingouins, jouer, retour);
	}
}