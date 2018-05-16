package Serveur.Lobby;

public class ClientAnwserProtocol {
	private String ihmMessage;
	private String serverMessage;

	public ClientAnwserProtocol() {
		this.ihmMessage = null;
		this.serverMessage = null;
	}

	/**
	 * Gestion des input du cient, retourne la string de reponse
	 * ici ce lancera une instance de jeu
	 * @param input
	 * @return
	 */
	synchronized public String processInput(String input) {
		while(this.ihmMessage == null) {
			try {wait();} catch (InterruptedException e) {}
		}
		String res = this.ihmMessage;
		this.serverMessage = input;
		this.ihmMessage = null;
		notifyAll();
		return res;
	}
	
	synchronized public String reponseServeur() {
		while(this.serverMessage == null) {
			try {wait();} catch (InterruptedException e) {}
		}
		String res = this.serverMessage;
		this.serverMessage = null;
		return res;
	}
	
	synchronized public void instances() {
		this.ihmMessage = "I";
		notifyAll();
	}
	
	synchronized public void connecte(String hostName, int port) {
		this.ihmMessage = "C "+hostName+":"+port;
		notifyAll();
	}
	
	synchronized public void heberger() {
		this.ihmMessage = "H";
		notifyAll();
	}
}
