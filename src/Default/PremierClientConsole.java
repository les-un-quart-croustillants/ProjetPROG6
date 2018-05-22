package Default;

import java.io.IOException;

import Serveur.Client.Client;
import Serveur.Client.ClientAnwserProtocol;
import Serveur.Lobby.InstanceList;
import Utils.Couple;

public class PremierClientConsole {
	public static void main(String[] args) throws IOException {
		String hostname = "130.190.76.244";
		Integer port = 43979;
		ClientAnwserProtocol cap = new ClientAnwserProtocol();
		
		System.out.println("Starting new Client configurated to connect to lobby "+hostname+":"+port+" ...");
		
		new Client(hostname,port,cap); //Starting client
		
		System.out.println("done");
		
		System.out.println("Trying to create the bob instance...");
		cap.heberger("bob");
		System.out.println("Server anwser: "+cap.reponseServeurString());
		/*
		System.out.println("Trying to create the alice instance...");
		cap.heberger("alice");
		System.out.println("Server anwser: "+cap.reponseServeurString());
		*/
		System.out.println("Trying to access game instances...");
		cap.instances();
		InstanceList il = cap.getInstances();
		System.out.println("done");
		System.out.println("game instances recived :"+il.getInstances().toString());
		System.out.println("Trying to connect to bob");
		Couple<String,Integer> c = il.get("bob");
		if(c != null) {
			cap.connecte(c.gauche(), c.droit());
		}
		System.out.println("Server anwser: "+cap.reponseServeurString());
	}
}