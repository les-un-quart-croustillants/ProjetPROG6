package Serveur.Client;

import Serveur.Lobby.InstanceList;

public class ClientAnwserProtocol {
	private String ihmMessage;
	private String serverMessage;
	private InstanceList instances;
	private State currentState;

	public enum State {
		DEFAULT, // Wait a string by default
		WAITFORINSTANCES; // Wait for the instances object

		public String toString(State s) {
			switch (s) {
			case DEFAULT:
				return "DEFAULT";
			case WAITFORINSTANCES:
				return "WAITFORINSTANCES";
			default:
				return "UNDEFINED";
			}
		}
	}

	public ClientAnwserProtocol() {
		this.ihmMessage = null;
		this.serverMessage = null;
		this.instances = null;
		this.currentState = State.DEFAULT;
	}

	/**
	 * Gestion des input du cient, retourne la string de reponse ici ce lancera une
	 * instance de jeu
	 * 
	 * @param input
	 * @return
	 */
	synchronized public String processInputString(String input) {
		while (this.ihmMessage == null) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		String res = this.ihmMessage;
		this.serverMessage = input;
		this.ihmMessage = null;
		notifyAll();
		return res;
	}

	synchronized public String processInputObject(Object input) {
		switch (this.currentState) {
		case WAITFORINSTANCES:
			this.instances = (InstanceList) input;
			this.currentState = State.DEFAULT;
			this.ihmMessage = "K";
			return this.ihmMessage;
		default:
			this.ihmMessage = null;
			return this.ihmMessage;
		}
	}

	synchronized public String reponseServeurString() {
		while (this.serverMessage == null) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		String res = this.serverMessage;
		this.serverMessage = null;
		return res;
	}

	synchronized public InstanceList getInstances() {
		while (this.instances == null) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		InstanceList tmp = instances;
		instances = null;
		return tmp;
	}

	synchronized public void instances() {
		this.ihmMessage = "I";
		this.currentState = State.WAITFORINSTANCES;
		notifyAll();
	}

	synchronized public void connecte(String hostName, int port) {
		this.ihmMessage = "C " + hostName + " " + port;
		notifyAll();
	}

	synchronized public void heberger(String name) {
		this.ihmMessage = "H "+name;
		notifyAll();
	}

	synchronized public State currentState() {
		return this.currentState;
	}
}
