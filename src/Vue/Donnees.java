package Vue;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public abstract class Donnees {

	public static Font FONT_TEXT = Font
			.loadFont(ClassLoader.getSystemClassLoader().getResourceAsStream("LuckiestGuy.ttf"), 42);
	public static Font FONT_PLAY = Font
			.loadFont(ClassLoader.getSystemClassLoader().getResourceAsStream("LuckiestGuy.ttf"), 72);

	public static Color[] COULEURS_JOUEURS = { Color.INDIANRED, Color.DARKSEAGREEN, Color.CORNFLOWERBLUE,
			Color.YELLOW };

}
