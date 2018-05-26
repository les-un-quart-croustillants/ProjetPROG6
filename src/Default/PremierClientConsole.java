package Default;

import java.io.IOException;
import java.util.Scanner;

import Serveur.Client.Client;
import Serveur.Client.ClientAnwserProtocol;
import Serveur.Lobby.InstanceList;
import Utils.Couple;

public class PremierClientConsole {
	public static void main(String[] args) throws IOException {
		String hostname = "192.168.1.8";
		Integer port = 40247;
		ClientAnwserProtocol cap = new ClientAnwserProtocol();
		
		System.out.println("Starting new Client configurated to connect to lobby "+hostname+":"+port+" ...");
		
		new Client(hostname,port,cap); //Starting client
		
		System.out.println("connected");
		
		boolean run = true;
		Scanner sc = new Scanner(System.in);
		InstanceList il = null;
		String cmd;
		String[] splited;
		
		
		while(run) {
			System.out.print("> ");
			cmd = sc.nextLine();
			splited = cmd.split("\\s+");
			
			switch(splited[0]) {
				case "host":
					cap.heberger(splited[1]);
					System.out.println(cap.reponseServeurString());
					break;
				case "seek":
					cap.instances();
					il = cap.getInstances();
					System.out.println(il.getInstances().toString());	
					break;
				case "connect":
					if(il == null) {
						System.out.println("Please get instances first");
					} else {
						Couple<String,Integer> c = il.get(splited[1]);
						if(c != null) {
							cap.connecte(c.gauche(), c.droit());
						}
						System.out.println(cap.reponseServeurString());
						System.out.println(cap.reponseServeurString());	
					}
					break;
				case "exit":
					run = false;
				default:
					System.out.println("Commande inconnue");
					break;
			}
		}
		
		sc.close();
	}
}