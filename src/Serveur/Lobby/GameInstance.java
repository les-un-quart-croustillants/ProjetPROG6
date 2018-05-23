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
	private String host;
	private InstanceList instances;
	private ArrayList<Socket> joueurs;
	private ArrayList<BufferedReader> in = null;
	private ArrayList<PrintWriter> out = null;
	private ArrayList<ObjectOutputStream> outObj = null;
	private ArrayList<ObjectInputStream> inObj = null;

	public GameInstance(String name, InstanceList instances, String host) {
		this.name = name;
		this.host = host;
		this.instances = instances;
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
			System.out.println("Waiting fort game-master " + this.host);
			
			/* Ouverture d'une connection */
			while(!hostConnected) {
				if(this.host.equals(this.joueurs.get(0).getRemoteSocketAddress().toString())) {
					this.out.get(0).println("H ok");
					hostConnected = true;
				} else {
					this.out.get(0).println("H error");
				}	
			}
			
			//TODO choix nombre de joueurs
			nbJoueurs = 3;
			
			for(int i=0;i<nbJoueurs;i++) {
				
			}
			
			System.out.println("Connected to "+this.joueurs.getInetAddress());
			serverSocket.close();
			this.instances.remove(name);
			return;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void acceptJoueur() {
		try {
			this.joueurs.add(serverSocket.accept());
			int index = this.joueurs.size()-1;
			this.out.add(new PrintWriter(joueurs.get(index).getOutputStream(), true));
			this.in.add(new BufferedReader(new InputStreamReader(joueurs.get(index).getInputStream())));
			this.outObj.add(new ObjectOutputStream(joueurs.get(index).getOutputStream()));
			outObj.get(0).flush();
			this.inObj.add(new ObjectInputStream(new BufferedInputStream(joueurs.get(index).getInputStream())));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
