package Default;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;

import Serveur.Lobby.InstanceList;
import Serveur.Lobby.LobbyThread;

public class Lobby {
	public static void main(String[] args) throws IOException {
		String ip = null;

		try {
			// Creation socket
			boolean run = true;
			ServerSocket serverSocket = new ServerSocket(0);
			InstanceList instances = new InstanceList();
			try (final DatagramSocket socket = new DatagramSocket()) {
				socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
				ip = socket.getLocalAddress().getHostAddress();
			}
			System.out.println("Server " + ip + " listening on port " + serverSocket.getLocalPort());
			// Attente de client, lancement nouveau thread pour gestion client
			while (run)
				new LobbyThread(serverSocket.accept(), instances);
			serverSocket.close();

		} catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on an available port or listening for a connection");
			System.out.println(e.getMessage());
		}
	}
}
