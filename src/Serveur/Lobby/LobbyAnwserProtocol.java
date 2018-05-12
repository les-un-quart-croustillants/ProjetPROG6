package Serveur.Lobby;

public class LobbyAnwserProtocol {
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

	public LobbyAnwserProtocol() {
		this.currentState = State.WAITING;
	}

	public String processInput(String input) {
		return "Bye";
	}
}
