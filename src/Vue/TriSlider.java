package Vue;

import javafx.scene.layout.*;
import javafx.scene.control.*;

public class TriSlider extends SplitPane {
	private VBox part1, part2, part3;
	
	TriSlider() {
		this.getStyleClass().add("trislider");
		
		part1 = new VBox();
		part2 = new VBox();
		part3 = new VBox();
		
		part1.getStyleClass().addAll("trislider-first", "slice");
		part2.getStyleClass().addAll("trislider-second", "slice");
		part3.getStyleClass().addAll("trislider-third", "slice");
		
		this.getItems().addAll(part1, part2, part3);
	}
	
	Pane getFirstSlice() {
		return part1;
	}
	
	Pane getSecondSlice() {
		return part2;
	}
	
	Pane getThirdSlice() {
		return part3;
	}
}