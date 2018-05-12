package Serveur.Lobby;

import Modele.Moteur.Moteur;

public class GameInstance implements Runnable {

	private Moteur moteur;

	public GameInstance(Moteur m) {
		this.moteur = m;
		(new Thread(this)).start();
	}
		
	@Override
	public void run() {
	}

}
