package Vue.GameObject;

import Modele.Plateau.Pingouin;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PingouinGraphique extends GameObject {
	Pingouin pingouin;
	PlateauGraphique pg;
	Color couleur;

	public PingouinGraphique(Pingouin pingouin, PlateauGraphique pg, Color c) {
		this.pingouin = pingouin;
		this.pg = pg;
		this.couleur = c;
	}

	@Override
	public void update() {
		position.x = pg.cases[pingouin.position().i()][pingouin.position().j()].position().x;
		position.y = pg.cases[pingouin.position().i()][pingouin.position().j()].position().y;
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(couleur);
		gc.fillOval(position.x + pg.tailleCase * 0.1, position.y + pg.tailleCase * 0.1, pg.tailleCase * 0.7,
				pg.tailleCase * 0.7);
	}
}
