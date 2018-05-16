package Serveur.Lobby;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

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
			System.out.println("Connected to " + this.socket.getRemoteSocketAddress() + ", starting lobby thread.");
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

		System.out.println("lobby thread connected to " + this.socket.getRemoteSocketAddress() + " running");

		// Gestion des inputs
		try {

			// this.connectedPlayerID = Integer.parseInt(in.readLine());
			while ((inputLine = in.readLine()) != null) {
				String[] splited = inputLine.split("\\s+");
				switch (splited[0]) {
				case "I": // Get instances
					System.out.println("Client asking for instances. Sending instances...");
					this.out.print(this.instances);
					System.out.println("done");
					break;
				case "C": // Connect to instance
					String name = splited[1];
					Integer port = Integer.parseInt(splited[2]);
					System.out.println("Client asking for connection to the game instance at" + name + ":" + port);
					if (this.instances.exists(name, port)) {
						System.out.println(name + ":" + port + " in data base : access granted");
						this.out.println("C ok");
					} else {
						System.out.println(name + ":" + port + " not in data base : access denied");
						this.out.println("C error");
					}
					break;
				case "H": // Create instance
					String instanceName = splited[1];
					new GameInstance(instanceName, this.instances, this.socket.getRemoteSocketAddress().toString());
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
