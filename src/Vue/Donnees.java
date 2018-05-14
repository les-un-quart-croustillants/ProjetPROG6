package Vue;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public abstract class Donnees {
	
	public static Font FONT_TEXT = Font.loadFont(ClassLoader.getSystemClassLoader().getResourceAsStream("LuckiestGuy.ttf"), 42);
	public static Font FONT_PLAY = Font.loadFont(ClassLoader.getSystemClassLoader().getResourceAsStream("LuckiestGuy.ttf"), 72);
	
	public static Color[] COULEURS_JOUEURS = {Color.ORANGERED, Color.GREENYELLOW,Color.CORNFLOWERBLUE,Color.YELLOW};
	public static Image IMG_GLACIER = new Image("bg_glacier.png");
	public static Image IMG_BLOC_GLACE = new Image("bloc_glace.png");
	public static Image IMG_PINGOUIN_FACE= new Image("pingouin_fusee_face_mini.png");


}
