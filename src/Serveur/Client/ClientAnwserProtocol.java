package Serveur.Client;

import Serveur.Lobby.InstanceList;
import Utils.Couple;

public class ClientAnwserProtocol {
	private String ihmMessage;
	private String serverMessage;
	private InstanceList instances;
	private State currentState;

	public enum State {
		DEFAULT, // Wait a string by default
		WAITFORINSTANCES, // Wait for the instances object
		WAITFORANWSER; // Message from server is expected
		
		public static String toString(State s) {
			switch (s) {
			case DEFAULT:
				return "DEFAULT";
			case WAITFORINSTANCES:
				return "WAITFORINSTANCES";
			case WAITFORANWSER:
				return "WAITFORANWSER";
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
		if(this.currentState == State.DEFAULT) {
			while (this.ihmMessage == null) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
		}
		String res = this.ihmMessage;
		if(res == "I") {
			this.currentState = State.WAITFORINSTANCES;	
		} else if (this.currentState == State.DEFAULT && !this.ihmMessage.equals("K")) {
			this.currentState = State.WAITFORANWSER;
		} else {
			this.currentState = State.DEFAULT;
		}
		this.serverMessage = input;
		System.out.println(this.ihmMessage+"|"+this.serverMessage);
		this.ihmMessage = null;
		notifyAll();
		return res;
	}

	synchronized public String processInputObject(Object input) {
		switch (this.currentState) {
		case WAITFORINSTANCES:
			this.instances = (InstanceList) input;
			this.currentState = State.DEFAULT;
			this.ihmMessage = null;
			this.serverMessage = null;
			notifyAll();
			return this.ihmMessage;
		default:
			this.serverMessage = null;
			this.ihmMessage = null;
			notifyAll();
			return this.ihmMessage;
		}
	}

	synchronized public String reponseServeurString() {
		while (this.serverMessage == null || this.serverMessage=="none") {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		String res = this.serverMessage;
		instances = null;
		this.serverMessage = null;
		notifyAll();
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
		this.serverMessage = null;
		notifyAll();
		return tmp;
	}

	synchronized public void instances() {
		this.ihmMessage = "I";
		notifyAll();
	}

	synchronized public void connecte(String hostName, int port) {
		this.ihmMessage = "C " + hostName + " " + port;
		notifyAll();
	}
	
	synchronized public void connect(Couple<String,Integer> c) {
		this.ihmMessage = "C " + c.gauche() + " " + c.droit();
		
		notifyAll();
	}

	synchronized public void heberger(String name) {
		this.ihmMessage = "H " + name;
		notifyAll();
	}

	synchronized public State currentState() {
		return this.currentState;
	}
}
