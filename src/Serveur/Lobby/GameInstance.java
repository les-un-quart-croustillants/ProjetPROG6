package Serveur.Lobby;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Modele.Joueurs.Joueur;
import Modele.Plateau.Plateau;
import Utils.Couple;

public class GameInstance implements Runnable {

	private ServerSocket serverSocket;
	private String name;
	private InstanceList instances;
	private socketList clients;


	public GameInstance(String name, InstanceList instances) {
		this.name = name;
		this.instances = instances;
		this.clients = new socketList();
		System.out.println("SALUT");
		System.out.flush();
		(new Thread(this)).start();
	}

	@Override
	public void run() {
		String ip = null;
		boolean hostConnected = false;
		Plateau plateau;
		ArrayList<Joueur> joueurs;
		int nbJoueurs = 0;
		
		try {
			serverSocket = new ServerSocket(0);
			//recuperation de l'ip inet
			try (final DatagramSocket socket = new DatagramSocket()) {
				socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
				ip = socket.getLocalAddress().getHostAddress();
			}
			this.instances.add(new Couple<String, Integer>(this.name, serverSocket.getLocalPort())); // L'instance s'ajoute aux instances
			System.out.println("Game instance on server " + ip + " listening on port " + serverSocket.getLocalPort());
			
			//TODO choix nombre de joueurs
			nbJoueurs = 1;
			
			//Connection de tous les joueurs
			/*
			for(int i=0;i<nbJoueurs;i++) {
				this.clients.connectTo(serverSocket.accept());
				System.out.println("New connection established");
			}
			*/
			
			this.clients.connectTo(serverSocket.accept());
			System.out.println("New connection established");
			
			this.clients.sendToAll("LETS GET THE PARTY STARTED");
			
			System.out.println("Data sent");
			
			//Close connection
			serverSocket.close();
			this.instances.remove(name);
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}	
}
