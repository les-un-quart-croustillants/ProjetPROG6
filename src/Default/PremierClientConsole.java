package Default;

import java.io.IOException;

import Serveur.Client.ClientAnwserProtocol;

public class PremierClientConsole {
	public static void main(String[] args) throws IOException {
		ClientAnwserProtocol cap = new ClientAnwserProtocol();
		
		new Client(hostname,port,cap);
	}
}
