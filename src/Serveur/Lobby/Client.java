package Serveur.Lobby;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client implements Runnable{
	
	Socket clientSocket;
	PrintWriter out;
	ObjectInputStream inObj;
	BufferedReader in;
	
	// Une instance de jeu identifie un joueur grace a ça machine distante
	// et son ID.
	ArrayList<Integer> joueursIDs;
	// Socket.getRemoteSocketAddress()
	
	public Client(int port, Integer jID) {
		String hostName = "none";
		this.joueursIDs = new ArrayList<Integer>();
		this.joueursIDs.add(jID);
		try {
            this.clientSocket = new Socket(hostName, port);
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.in  = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.inObj = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
		} catch (UnknownHostException e) {
            System.err.println("impossible d'atteindre le lobby "+hostName);
        } catch (IOException e) {
            System.err.println("Connection a "+hostName+" a echouee");
        }
		
		(new Thread(this)).start();
	}
	
	public Client(int port, ArrayList<Integer> jsIDs) {
		String hostName = "none";
		this.joueursIDs = jsIDs;
		try {
            this.clientSocket = new Socket(hostName, port);
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.inObj = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
		} catch (UnknownHostException e) {
            System.err.println("impossible d'atteindre le lobby "+hostName);
        } catch (IOException e) {
            System.err.println("Connection a "+hostName+" a echouee");
        }
		
		(new Thread(this)).start();
	}
	
	/**
	 * Envois des demandes au lobby, gere le deroulement d'une partie une fois lancee
	 */
	@Override
	public void run() {
		String inputLine, outputLine;
		ClientAnwserProtocol cap = new ClientAnwserProtocol();

        try {
        	//out.print(this.joueursIDs); //Envois des identifiants des joueurs geres par le client
        	//Lecture du flus de donnees provenant du serveur
			while ((inputLine = in.readLine()) != null) {
			    outputLine = cap.processInput(inputLine);
			    if(outputLine != null) {
			    	out.println(outputLine);//Envois de la reponce au serveur
			    }
			}
		} catch (IOException e1) {
			System.out.println("Exception levee lors de la lecture du flux de donnees provenant du serveur.");
			e1.printStackTrace();
		}
		
		try {
			this.clientSocket.close();
		} catch (IOException e) {
			System.out.println("Exception levee lors de la fermeture du socket client.");
			e.printStackTrace();
		}
	}
}
