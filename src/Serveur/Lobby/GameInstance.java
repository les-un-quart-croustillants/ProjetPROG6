package Serveur.Lobby;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Modele.Moteur.Moteur;

public class GameInstance implements Runnable {

	private Moteur moteur;
	private Socket socket;
	private String host;

	public GameInstance(Moteur m,String host,int port) {
		this.moteur = m;
		this.host = host;
		(new Thread(this)).start();
	}
	
	@Override
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			this.socket = serverSocket.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
