package Serveur.Lobby;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import Modele.Moteur.Moteur;

public class LobbyThread implements Runnable {

	private Socket socket;
	private ObjectInputStream inObj = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private ArrayList<GameInstance> instances;
	private int connectedPlayerID;

	public LobbyThread(Socket s) {

		try {
			this.socket = s;
            this.out = new PrintWriter(s.getOutputStream(), true);
            this.in  = new BufferedReader(new InputStreamReader(s.getInputStream()));
            this.inObj = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		(new Thread(this)).start();
	}

	/**
	 * Gestion de la communication avec le client
	 */
	@Override
	public void run() {
		String inputLine;

		// Gestion des inputs
		try {
			
			//this.connectedPlayerID = Integer.parseInt(in.readLine());
			while ((inputLine = in.readLine()) != null) {
				switch(inputLine.charAt(0)) {
					case 'I': //Get instances
						this.out.print(this.instances);
						break;
					case 'C': //Connect to instance
						this.out.println("ok");
						break;
					case 'H': //Create instance
						Moteur m = (Moteur) this.inObj.readObject();
						this.instances.add(new GameInstance(m,this.socket.getRemoteSocketAddress().toString(),2121));
						break;
					default:
						this.out.println("Bye");
						break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
