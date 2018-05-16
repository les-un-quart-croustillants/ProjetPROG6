package Serveur.Lobby;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameInstance implements Runnable {

	private String name;
	private String host;
	private InstanceList instances;
	private Socket socket;

	public GameInstance(String name, InstanceList instances, String host, int port) {
		this.name = name;
		this.host = host;
		(new Thread(this)).start();
	}

	@Override
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(2121);
			this.socket = serverSocket.accept();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
