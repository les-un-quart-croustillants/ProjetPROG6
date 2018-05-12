package Serveur.Lobby;

public class ClientAnwserProtocol {
	private State currentState;

	private enum State {
		WAITING;

		public String toString(State s) {
			switch (s) {
			case WAITING:
				return "WAITING";
			default:
				return "UNKNOWN";
			}
		}
	}

	public ClientAnwserProtocol() {
		this.currentState = State.WAITING;
	}

	/**
	 * Gestion des input du cient, retourne la string de reponse
	 * ici ce lancera une instance de jeu
	 * @param input
	 * @return
	 */
	public String processInput(String input) {
		return "Bye";
	}
}
