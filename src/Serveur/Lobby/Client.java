package Serveur.Lobby;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable{
	
	Socket clientSocket;
	PrintWriter out;
	BufferedReader in;
	
	public Client(int port) {
		String hostName = "none";
		try {
            clientSocket = new Socket(hostName, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
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
