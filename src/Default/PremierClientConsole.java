package Default;

import java.io.IOException;

import Serveur.Client.Client;
import Serveur.Client.ClientAnwserProtocol;
import Serveur.Lobby.InstanceList;

public class PremierClientConsole {
	public static void main(String[] args) throws IOException {
		String hostname = "192.168.1.8";
		Integer port = 33059;
		ClientAnwserProtocol cap = new ClientAnwserProtocol();
		
		System.out.println("Starting new Client configurated to connect to lobby "+hostname+":"+port+" ...");
		
		new Client(hostname,port,cap); //Starting client
		
		System.out.println("done");
		System.out.println("Trying to access game instances...");
		cap.getInstances();
		InstanceList il = cap.getInstances();
		System.out.println("done");
		System.out.println(il.getInstances().toString());
	}
}
