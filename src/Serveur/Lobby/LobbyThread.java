package Serveur.Lobby;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class LobbyThread implements Runnable {

	private Socket socket;
	private ObjectOutputStream outObj = null;
	private ObjectInputStream inObj = null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private InstanceList instances;

	public LobbyThread(Socket s, InstanceList il) {

		try {
			this.socket = s;
			this.out = new PrintWriter(s.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			this.outObj = new ObjectOutputStream(socket.getOutputStream());
			outObj.flush();
			this.inObj = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
			this.instances = il;
			(new Thread(this)).start();
		} catch (Exception e) {
			System.out.println("Connection au client echouee");
		}
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
					this.outObj.writeObject(this.instances);
					System.out.println("done");
					break;
				case "C": // Connect to instance
					String name = splited[1];
					Integer port = Integer.parseInt(splited[2]);
					System.out.println("Client asking for connection to the game instance at " + name + ":" + port);
					if (this.instances.exists(name, port)) {
						System.out.println(name + ":" + port + " in data base : access granted");
						this.out.println("C ok");
						System.out.println("oui");
					} else {
						System.out.println(name + ":" + port + " not in data base : access denied");
						this.out.println("C error");
					}
					break;
				case "H": // Create instance
					String instanceName = splited[1];
					System.out.println("Client asking to create new game instance named "+instanceName); 
					if(instances.get("bob") == null) {
						new GameInstance(instanceName, this.instances, this.socket.getRemoteSocketAddress().toString());
						this.out.println("H ok");
						System.out.println("Instance created");
						System.out.flush();
					} else {
						this.out.println("H error");
						System.out.println("Instance already exists");
					}
					break;
				default: 
					//this.out.println("Bye");
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
