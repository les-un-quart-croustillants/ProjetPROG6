package Vue.Cadre;


import java.util.ArrayList;
import java.util.Iterator;

import Vue.GameObject.GameObject;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class Cadre extends Pane {
	private Canvas canvas;
	public GraphicsContext gc;
	public ArrayList<GameObject> gameObjects;
	
	public Cadre() {
		super();
		canvas = new Canvas();
		this.getChildren().add(canvas);
		canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());
        gc = canvas.getGraphicsContext2D();
        gameObjects = new ArrayList<GameObject>();
	}
	
	public Cadre(int wpref,int hpref) {
		this();
		this.setPrefSize(wpref, hpref);
	}
	
	/**
     * update : Appelle la fonction update de tous ses gameobjects.
     */
	public void update() {
		Iterator<GameObject> it = gameObjects.iterator();
		while(it.hasNext()) {
			GameObject go = it.next();
			go.update();

			if(go.estDetruit()) {
				it.remove();
				go.onDestroy();
			}

		}
	}
	
	 /**
     * draw : Appelle la fonction draw de tous ses gameobjects.
     */
	public void draw() {
		gc.clearRect(0, 0, this.getWidth(), this.getHeight());
		//gc.strokeRect(0, 0, this.getWidth(), this.getHeight());
		Iterator<GameObject> it = gameObjects.iterator();
		while(it.hasNext()) {
			GameObject go = it.next();
			go.draw(gc);
		}
	}
}
