package Serveur.Client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import Serveur.Client.ClientAnwserProtocol.State;
import Utils.Couple;

public class Client implements Runnable {

	Socket clientSocket;
	String hostname;
	PrintWriter out;
	ObjectInputStream inObj;
	ObjectOutputStream outObj;
	BufferedReader in;
	ClientAnwserProtocol cap;

	// Une instance de jeu identifie un joueur grace a ça machine distante
	// et son ID.
	ArrayList<Integer> joueursIDs;
	// Socket.getRemoteSocketAddress()

	public Client(String hostName, int port, ClientAnwserProtocol cap) {
		this.joueursIDs = new ArrayList<Integer>();
		try {
			this.hostname = hostname;
			this.clientSocket = new Socket(hostName, port);
			this.out = new PrintWriter(clientSocket.getOutputStream(), true);
			this.out.flush();
			this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.outObj = new ObjectOutputStream(clientSocket.getOutputStream());
			outObj.flush();
			this.inObj = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
			this.cap = cap;
			(new Thread(this)).start();
		} catch (UnknownHostException e) {
			System.err.println("impossible d'atteindre le lobby " + hostName);
		} catch (IOException e) {
			System.err.println("Connection a " + hostName + ":" + port + " a echouee");
		}
	}

	/**
	 * Envois des demandes au lobby, gere le deroulement d'une partie une fois
	 * lancee
	 */
	@Override
	public void run() {
		String inputLine = null, outputLine = null;
		String[] splited;
		Couple<String,Integer> connectionInfo = null;
		Object inputObj;
		
		try {
			// out.print(this.joueursIDs); //Envois des identifiants des joueurs geres par
			// le client
			// Lecture du flus de donnees provenant du serveur
			this.out.println();
			while (inputLine != "Bye") {
				if (cap.currentState() == State.DEFAULT) { // Si le client est en attente d'un message
					inputLine = "none";
					outputLine = cap.processInputString(inputLine);
					splited = outputLine.split("\\s+");
					if(splited[0].equals("C")) {
						connectionInfo = new Couple<String,Integer>(splited[1],Integer.parseInt(splited[2]));
					}
				} else if(cap.currentState() == State.WAITFORANWSER) {
					inputLine = in.readLine();
					outputLine = cap.processInputString(inputLine);
					if(inputLine.equals("C ok")) {
						connectToGameInstance(connectionInfo);
					}
				} else { // Si le client est en attente de donnees
					inputObj = inObj.readObject();
					outputLine = cap.processInputObject(inputObj);
				}
				if (outputLine != null) {
					out.println(outputLine);// Envois de la reponse au serveur
				}
			}

		} catch (IOException e1) {
			System.out.println("Exception levee lors de la lecture du flux de donnees provenant du serveur.");
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			this.clientSocket.close();
		} catch (IOException e) {
			System.out.println("Exception levee lors de la fermeture du socket client.");
			e.printStackTrace();
		}
	}

	private void connectToGameInstance(Couple<String, Integer> c) {
		try {
			this.clientSocket.close();
		} catch (IOException e) {
			System.out.println("Exception levee lors de la fermeture du socket client.");
			e.printStackTrace();
		}
		
		try {
			this.clientSocket = new Socket(this.hostname, c.droit());
			this.out = new PrintWriter(clientSocket.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			this.outObj = new ObjectOutputStream(clientSocket.getOutputStream());
			outObj.flush();
			this.inObj = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
		} catch (IOException e) {
			System.err.println("Connection a " + c.gauche() + ":" + c.droit() + " a echouee");
			e.printStackTrace();
		}
	}
}
