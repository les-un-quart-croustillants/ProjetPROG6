package Vue;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public abstract class Donnees {
	
	public enum Niveau{
		BANQUISE(0),
		ENFER(1);
		
		private final int num;
		private Niveau(int n){
			num = n;
		}
		public int getNiveau() {
			return num;
		}
	}
	
	public static Font FONT_TEXT = Font.loadFont(ClassLoader.getSystemClassLoader().getResourceAsStream("LuckiestGuy.ttf"), 42);
	public static Font FONT_PLAY = Font.loadFont(ClassLoader.getSystemClassLoader().getResourceAsStream("LuckiestGuy.ttf"), 72);
	public static Font FONT_SCORE = Font.loadFont(ClassLoader.getSystemClassLoader().getResourceAsStream("LuckiestGuy.ttf"), 18);
	public static Font FONT_SCORES_FINAUX = Font.loadFont(ClassLoader.getSystemClassLoader().getResourceAsStream("LuckiestGuy.ttf"), 32);

	private static Color[] COULEURS_JOUEURS = {Color.DARKBLUE,Color.GREEN,Color.MEDIUMPURPLE,Color.DARKGOLDENROD,Color.FUCHSIA,Color.GRAY,Color.CHOCOLATE,Color.GREENYELLOW};
	public static Color[] COULEURS_NIVEAUX = {new Color(70.0/255.0,190.0/255.0,255.0/255.0,1),new Color(170.0/255.0,70.0/255.0,0.0/255.0,1)};
	public static Color COULEUR_SUGGESTION = Color.BLACK;
	public static Color[] COULEURS_TEXTES_NIVEAUX = {Color.CORNFLOWERBLUE, Color.ANTIQUEWHITE };
	
	public static Image IMG_GLACIER = new Image("bg_glacier.png");	
	public static Image[] IMG_BLOC = {new Image("bloc_glace3.png"),new Image("bloc_lave.png")};
	public static Image[] IMG_BLOC_P1 = {new Image("bloc_glace_p1.png"), new Image("bloc_lave_p1.png")};
	public static Image[] IMG_BLOC_P2 = {new Image("bloc_glace_p2.png"), new Image("bloc_lave_p2.png")};
	public static Image[] IMG_BLOC_P3 = {new Image("bloc_glace_p3.png"), new Image("bloc_lave_p3.png")};
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
	public static Image[] IMG_BG_HUD = {new Image("fond2.png"), new Image("bg_hud_enfer.png")};
	public static Image IMG_PARTICLE = new Image("particle.png");
	public static Image IMG_PARTICLE_ENFER = new Image("particle_enfer.png");
	public static Image[] IMG_BRUME = {new Image("brume.png"),new Image("brume_enfer.png")};
	public static Image IMG_SELECTEUR = new Image("selecteur.png");
	public static Image IMG_CADENAS = new Image("cadenas.png");
	public static Image IMG_POISSON = new Image("poisson_scores.png");
	public static Image IMG_PARTICLE_AURA = new Image("particle_feu.png");
	public static Image[] IMG_REDO = {new Image("redo.png"),new Image("redo_enfer.png")};
	public static Image[] IMG_UNDO = {new Image("undo.png"),new Image("undo_enfer.png")};
	public static Image[] IMG_PARAMETRE = {new Image("bouton_parametre.png"),new Image("bouton_parametre_enfer.png")};
	public static Image[] IMG_PARAMETRE_HOVER = {new Image("bouton_parametre_hover.png"),new Image("bouton_parametre_hover_enfer.png")};
	public static Image IMG_SELECTEUR_PINGOUIN = new Image("selecteur_pingouin.png");
	
	public static Color getCouleur(int index){
		return Donnees.COULEURS_JOUEURS[index%8];
	}
}
