package Default;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import Modele.Joueurs.Joueur;
import Modele.Joueurs.Joueur.Difficulte;
import Modele.Joueurs.JoueurIA;
import Modele.Joueurs.JoueurPhysique;
import Modele.Moteur.Moteur;
import Modele.Moteur.Moteur.State;
import Modele.Plateau.Pingouin;
import Modele.Plateau.Plateau;
import Utils.Couple;
import Utils.Position;

public class JeuConsole {

	/* CONSTANTES */
	/* Inputs */
	private static Position error = new Position(-1, -1);
	private static Position undo = new Position(-1, 0);
	private static Position redo = new Position(-1, 1);
	/* Couleurs */
	public static final String RESET = "\033[0m";
	public static final String BLACK = "\033[30m";
	public static final String RED = "\033[31m";
	public static final String GREEN = "\033[32m";
	public static final String YELLOW = "\033[33m";
	public static final String BLUE = "\033[34m";
	public static final String PURPLE = "\033[35m";
	public static final String CYAN = "\033[36m";
	public static final String WHITE = "\033[37m";
	public static final String BLACK_BG = "\033[40m";
	public static final String RED_BG = "\033[41m";
	public static final String GREEN_BG = "\033[42m";
	public static final String YELLOW_BG = "\033[43m";
	public static final String BLUE_BG = "\033[44m";
	public static final String PURPLE_BG = "\033[45m";
	public static final String CYAN_BG = "\033[46m";
	public static final String WHITE_BG = "\033[47m";
	/* format texte */
	public static final String BOLD = "\033[1m";
	public static final String UNDERLINE = "\033[4m";
	public static final String BLINK = "\033[5m";
	/* affichage */
	public static final String CLEAR = "\033[2Jm";

	public static Moteur m;
	private static String[] color_joueurs = { YELLOW, BLUE, PURPLE, CYAN };
	private static String[] colorbg_joueurs = { YELLOW_BG, BLUE_BG, PURPLE_BG, CYAN_BG };
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws Exception {

		Position res;
		Plateau p = new Plateau(8);
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
		boolean fin_partie = false;

		/* Initialisation moteur */
		//joueurs.add(new JoueurPhysique(0, 3, "Loukavocat"));
		joueurs.add(new JoueurIA(1, 3, "Henry", Difficulte.FACILE));
		joueurs.add(new JoueurIA(2, 3, "Chris. P. Beacon", Difficulte.FACILE));
		joueurs.add(new JoueurIA(3, 3, "Johny cash", Difficulte.FACILE));
		m = new Moteur(p, joueurs);
		m.setCurrentState(State.POSER_PINGOUIN);

		prettyTitle();
		while (!fin_partie) {
			System.out.println("_______________________________________________________________");
			System.out.println();
			if (m.currentState() != State.RESULTATS) {
				System.out.println(colorbg_joueurs[m.indexJoueurCourant()] + m.joueurCourant().nom() + RESET);
			} else {
				System.out.println("L'IA pose pingouin...");
			}
			prettyPlateau();
			if (m.currentState() == State.POSER_PINGOUIN) {
				if (!m.joueurCourant().estIA()) {
					System.out.println("Poser un pingouin:");
				}
				res = getInput(sc);
				processInput(m, res);
			} else if (m.currentState() == State.SELECTIONNER_PINGOUIN) {
				if (!m.joueurCourant().estIA()) {
					System.out.println("Selectionner un pingouin:");
				} else {
					System.out.println("L'IA joue un coup...");

				}
				res = getInput(sc);
				processInput(m, res);
			} else if (m.currentState() == State.SELECTIONNER_DESTINATION) {
				if (!m.joueurCourant().estIA()) {
					System.out.println("Selectionner une destination pour le pingouin en ("
							+ m.pingouinSelection().position().i() + "," + m.pingouinSelection().position().j() + "):");
				}
				res = getInput(sc);
				processInput(m, res);
			} else if (m.currentState() == State.RESULTATS) {
				int i = 0;
				System.out.println();
				System.out.println("                 SCORES");
				System.out.println(" ________________________________________");
				System.out.println("|rang |joueur |poissons |cases detruites |");
				System.out.println("|_____|_______|_________|________________|");
				try {
					for (ArrayList<Integer> j : m.podium()) {
						i++;
						System.out.println("|" + i + "    |" + colorbg_joueurs[j.get(0)] + "       " + RESET + "|"
								+ j.get(1) + "       |" + j.get(2) + "              |");
					}
				} catch (Exception e) {
					System.out.println("La partie n'est pas fini les resultats ne sont pas disponibles");
				}
				System.out.println("|________________________________________|");
				return;
			} else {
				System.out.println(m.currentState());
				return;
			}
		}

		sc.close();
	}

	/**
	 * Lis l'entree standard et la traduis en position : (-1,-1) : coup IA (pas de
	 * lecture) (-1,0) : undo (-1,1) : redo (a,b) : coup joueur physique
	 * 
	 * @param sc
	 *            scanner de l'entree standard
	 * @return
	 * @throws InterruptedException
	 */
	private static Position getInput(Scanner sc) throws InterruptedException {
		Integer a, b;
		Boolean error = true;
		if (m.joueurCourant().estIA()) {
			Thread.sleep(m.joueurCourant().delay() + 250);
			return new Position(-1, -1);
		} else {
			while (error) {
				error = false;
				String s = sc.nextLine();
				String[] splited = s.split("\\s+|,");
				try {
					a = Integer.parseInt(splited[0]);
					b = Integer.parseInt(splited[1]);
					return new Position(a, b);
				} catch (Exception e) {
					if (splited[0].equals("undo")) {
						return new Position(-1, 0);
					} else if (splited[0].equals("redo")) {
						return new Position(-1, 1);
					} else if (splited[0].equals("quit")) {
						System.exit(0);
					} else {
						System.out.println("[Entree incorrect] Entrez \"A B\" ou \"A,B\":");
						error = true;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Appelle le moteur sur les entree traduites avec getInput suivant l'etat
	 * courrant du moteur
	 * 
	 * @param m
	 * @param input
	 * @throws Exception
	 */
	private static void processInput(Moteur m, Position input) throws Exception {
		if (input.equals(undo) && m.undo() == null) {
			System.out.println("Undo impossible.");
		} else if (input.equals(redo) && m.redo() == null) {
			System.out.println("Redo impossible.");
		} else if (m.currentState() == State.POSER_PINGOUIN) {
			if (!(input = m.poserPingouin(input)).equals(error)) {
				System.out.println("Pingouin pose en (" + input.i() + "," + input.j() + ")");
			} else {
				System.out.println("Position invalide.");
			}
		} else if (m.currentState() == State.SELECTIONNER_PINGOUIN) {
			if (!(input = m.selectionnerPingouin(input)).equals(error)) {
				System.out.println("Selection du pingouin en (" + input.i() + "," + input.j() + ")");
			} else {
				System.out.println("Mauvaise selection.");
			}
		} else if (m.currentState() == State.SELECTIONNER_DESTINATION) {
			if (!(input = m.selectionnerDestination(input)).equals(error)) {
				System.out.println("Pingouin deplace en (" + input.i() + "," + input.j() + ")");
			} else {
				System.out.println("Deplacement impossible.");
			}
		}
	}

	private static void prettyPlateau() {
		ArrayList<ArrayList<Pingouin>> pingouins = new ArrayList<ArrayList<Pingouin>>();
		LinkedList<Position> highLight = new LinkedList<Position>();

		for (int i = 0; i <= m.njoueurs(); i++)
			pingouins.add(new ArrayList<Pingouin>());

		if (m.pingouinSelection() != null) {
			highLight = m.plateau().accessible(m.pingouinSelection().position());
		}

		for (int i = 0; i < m.plateau().getSize(); i++) {
			System.out.print(WHITE_BG + BLACK);
			for (int j = 0; j < m.plateau().getSize(); j++) {
				Position p = new Position(i, j);
				if (!m.plateau().getCellule(p).isDestroyed() && m.plateau().getCellule(p).aPingouin()) {
					pingouins.get(m.plateau().getCellule(p).pingouin().employeur())
							.add(m.plateau().getCellule(p).pingouin());
				}
				if (m.plateau().getCellule(p).isDestroyed()) {
					if (j == m.plateau().getSize() - 1) {
						if (i % 2 == 0)
							System.out.print("   ");
						else
							System.out.print("  ");
					} else if (j == 0 && i % 2 == 0)
						System.out.print("   ");
					else
						System.out.print("    ");
				} else if (i % 2 == 1) {
					if (j == m.plateau().getSize() - 1) {
						if (m.plateau().getCellule(p).aPingouin()) {
							if ((m.pingouinSelection() != null)
									&& m.plateau().getCellule(p).pingouin().equals(m.pingouinSelection())) {
								System.out.print(BOLD + UNDERLINE
										+ color_joueurs[m.plateau().getCellule(p).pingouin().employeur()] + "P" + RESET
										+ WHITE_BG + BLACK + " ");
							} else {
								System.out.print(BOLD + color_joueurs[m.plateau().getCellule(p).pingouin().employeur()]
										+ "P" + RESET + WHITE_BG + BLACK + " ");
							}
						} else {
							if (m.currentState() == State.SELECTIONNER_DESTINATION && highLight.contains(p)) {
								System.out.print(RESET + GREEN_BG + m.plateau().getCellule(p).getFish() + RESET
										+ WHITE_BG + BLACK + " ");
							} else {
								System.out.print(m.plateau().getCellule(p).getFish() + " ");
							}
						}
					} else {
						if (m.plateau().getCellule(p).aPingouin()) {
							if ((m.pingouinSelection() != null)
									&& m.plateau().getCellule(p).pingouin().equals(m.pingouinSelection())) {
								System.out.print(BOLD + UNDERLINE
										+ color_joueurs[m.plateau().getCellule(p).pingouin().employeur()] + "P" + RESET
										+ WHITE_BG + BLACK + "   ");
							} else {
								System.out.print(BOLD + color_joueurs[m.plateau().getCellule(p).pingouin().employeur()]
										+ "P" + RESET + WHITE_BG + BLACK + "   ");
							}
						} else {
							if (m.currentState() == State.SELECTIONNER_DESTINATION && highLight.contains(p)) {
								System.out.print(RESET + GREEN_BG + m.plateau().getCellule(p).getFish() + RESET
										+ WHITE_BG + BLACK + "   ");
							} else {
								System.out.print(m.plateau().getCellule(p).getFish() + "   ");
							}
						}
					}
				} else {
					if (j == 0) {
						if (m.plateau().getCellule(p).aPingouin()) {
							if ((m.pingouinSelection() != null)
									&& m.plateau().getCellule(p).pingouin().equals(m.pingouinSelection())) {
								System.out.print("  " + BOLD + UNDERLINE
										+ color_joueurs[m.plateau().getCellule(p).pingouin().employeur()] + "P" + RESET
										+ WHITE_BG + BLACK);
							} else {
								System.out.print(
										"  " + BOLD + color_joueurs[m.plateau().getCellule(p).pingouin().employeur()]
												+ "P" + RESET + WHITE_BG + BLACK);
							}
						} else {
							if (m.currentState() == State.SELECTIONNER_DESTINATION && highLight.contains(p)) {
								System.out.print("  " + RESET + GREEN_BG + m.plateau().getCellule(p).getFish() + RESET
										+ WHITE_BG + BLACK);
							} else {
								System.out.print("  " + m.plateau().getCellule(p).getFish());
							}
						}
					} else {
						if (m.plateau().getCellule(p).aPingouin()) {
							if ((m.pingouinSelection() != null)
									&& m.plateau().getCellule(p).pingouin().equals(m.pingouinSelection())) {
								System.out.print("   " + BOLD + UNDERLINE
										+ color_joueurs[m.plateau().getCellule(p).pingouin().employeur()] + "P" + RESET
										+ WHITE_BG + BLACK);
							} else {
								System.out.print(
										"   " + BOLD + color_joueurs[m.plateau().getCellule(p).pingouin().employeur()]
												+ "P" + RESET + WHITE_BG + BLACK);
							}
						} else {
							if (m.currentState() == State.SELECTIONNER_DESTINATION && highLight.contains(p)) {
								System.out.print("   " + RESET + GREEN_BG + m.plateau().getCellule(p).getFish() + RESET
										+ WHITE_BG + BLACK);
							} else {
								System.out.print("   " + m.plateau().getCellule(p).getFish());
							}

						}
					}
				}
			}
			System.out.println(RESET);
			if(i != m.plateau().getSize()-1) {
				System.out.print(WHITE_BG+BLACK+"                              "+RESET);
				System.out.println();	
			}
		}

		for (int i = 0; i <= m.njoueurs(); i++) {
			if (pingouins.get(i).size() > 0) {
				System.out.print(colorbg_joueurs[i] + "Pingouins" + RESET + " ");
				for (Pingouin p : pingouins.get(i)) {
					if ((m.pingouinSelection() != null) && p.equals(m.pingouinSelection())) {
						System.out.print(BOLD + p.position() + " " + RESET);
					} else {
						System.out.print(p.position() + " ");
					}
				}
				if (m.pingouinSelection() != null && m.pingouinSelection().employeur() == m.joueur(i-1).id()) {
					System.out.println();
					System.out.print(colorbg_joueurs[i] + "Coups possible" + RESET + " ");
					for (Position pos : highLight) {
						System.out.print(pos.toString() + " ");
					}
				}
				System.out.println();
			}
		}

	}
	
	private static void prettyTitle() throws InterruptedException {
		System.out.print(WHITE_BG+BLACK);
		System.out.println("        /@@@@@@@\\                                                                 ");
		System.out.println("      (@@@@@ # @@@@@\\                                                             ");
		System.out.println("     (` \\@@@@@@@@~~~~           ____  (_)___  ____ _____  __  __(_)___  _____     ");
		System.out.println("    /`    \\@@@@@|              / __ \\/ / __ \\/ __ `/ __ \\/ / / / / __ \\/ ___/     ");
		System.out.println("   /@@     ''''  \\            / /_/ / / / / / /_/ / /_/ / /_/ / / / / (__  )      ");
		System.out.println("  /@@@@\\          |          / .___/_/_/ /_/\\__, /\\____/\\__,_/_/_/ /_/____/       ");
		System.out.println(" /@@@@@@@\\        |         /_/            /____/                                 ");
		System.out.println("/@@@@@@@@@        |                                                               ");
		System.out.println("|@@@@@@@@         |                                                               ");
		System.out.println("|@@@@@@@          |                                                               ");
		System.out.println("|@@@@@@@          |                                                               ");
		System.out.println("|@@@'@@@@        |                                                                ");
		System.out.println("|@@@ '@@@        ;                                                                ");
		System.out.println("|@@@  @@;       ;                                                                 ");
		System.out.println("|@@@  ''       ;                                                                  ");
		System.out.println("(@@@@         ;                                                                   ");
		System.out.println(" (@@@@        |                                                                   ");
		System.out.println("  (__@@_______)                                                                   ");
		System.out.print(RESET);
		Thread.sleep(5000);
	}

}
