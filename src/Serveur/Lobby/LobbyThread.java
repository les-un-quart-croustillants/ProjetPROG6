package Serveur.Lobby;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

import Utils.Couple;

public class LobbyThread implements Runnable {

	private Socket socket;
	private ObjectInputStream inObj = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private InstanceList instances;

	public LobbyThread(Socket s, InstanceList il) {

		try {
			this.socket = s;
			this.out = new PrintWriter(s.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			this.inObj = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
			this.instances = il;
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

			// this.connectedPlayerID = Integer.parseInt(in.readLine());
			while ((inputLine = in.readLine()) != null) {
				String[] splited = inputLine.split("\\s+");
				switch (splited[0]) {
				case "I": // Get instances
					this.out.print(this.instances);
					break;
				case "C": // Connect to instance
					String name = splited[1];
					Integer port = Integer.parseInt(splited[2]);
					if(this.instances.exists(name, port)) {
						this.out.println("C ok");
					} else {
						this.out.println("C error");
					}
					break;
				case "H": // Create instance
					this.instances.add(new Couple<String,Integer>(splited[1],2121));
					new GameInstance(splited[1],this.instances,this.socket.getRemoteSocketAddress().toString(),2121);
					break;
				default:
					this.out.println("Bye");
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
