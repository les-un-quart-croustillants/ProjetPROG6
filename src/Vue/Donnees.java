package Vue;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public abstract class Donnees {
	
	public static Font FONT_TEXT = Font.loadFont(ClassLoader.getSystemClassLoader().getResourceAsStream("LuckiestGuy.ttf"), 42);
	public static Font FONT_PLAY = Font.loadFont(ClassLoader.getSystemClassLoader().getResourceAsStream("LuckiestGuy.ttf"), 72);
	public static Font FONT_SCORE = Font.loadFont(ClassLoader.getSystemClassLoader().getResourceAsStream("LuckiestGuy.ttf"), 18);
	public static Font FONT_SCORES_FINAUX = Font.loadFont(ClassLoader.getSystemClassLoader().getResourceAsStream("LuckiestGuy.ttf"), 32);

	private static Color[] COULEURS_JOUEURS = {Color.DARKBLUE,Color.GREEN,Color.MEDIUMPURPLE,Color.DARKGOLDENROD};
	public static Image IMG_GLACIER = new Image("bg_glacier.png");
	public static Image IMG_BLOC_GLACE = new Image("bloc_glace3.png");
	public static Image IMG_BLOC_GLACE_P1 = new Image("bloc_glace_p1.png");
	public static Image IMG_BLOC_GLACE_P2 = new Image("bloc_glace_p2.png");
	public static Image IMG_BLOC_GLACE_P3 = new Image("bloc_glace_p3.png");
	public static Image IMG_BLOC_GLACE_RIPPLE = new Image("blocRipple256x221.png");
	public static Image IMG_PINGOUIN_BD= new Image("pingouin_BD.png");
	public static Image IMG_PINGOUIN_BG= new Image("pingouin_BG.png");
	public static Image IMG_PINGOUIN_D= new Image("pingouin_D.png");
	public static Image IMG_PINGOUIN_G= new Image("pingouin_G.png");
	public static Image IMG_PINGOUIN_HD= new Image("pingouin_HD.png");
	public static Image IMG_PINGOUIN_HG= new Image("pingouin_HG.png");
	public static Image IMG_PINGOUIN_BOULE= new Image("pingouin_Boule.png");
	public static Image IMG_PINGOUIN_ANIM= new Image("animation.png");
	public static Image IMG_FOND_SCORE = new Image("fond_score.png");
	public static Image IMG_BG_NUAGES = new Image("fond2.png");
	public static Image IMG_PARTICLE = new Image("particle.png");
	public static Image IMG_BRUME = new Image("brume.png");
	public static Image IMG_SELECTEUR = new Image("selecteur.png");
	public static Image IMG_CADENAS = new Image("cadenas.png");
	public static Image IMG_POISSON = new Image("poisson_scores.png");
	public static Image IMG_PARTICLE_AURA = new Image("particule_flash.png");

	public static Color getCouleur(int index){
		return Donnees.COULEURS_JOUEURS[index%4];
	}
}
