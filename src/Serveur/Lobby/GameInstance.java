package Serveur.Lobby;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import Utils.Couple;

public class GameInstance implements Runnable {

	private String name;
	private String host;
	private InstanceList instances;
	private Socket socket;

	public GameInstance(String name, InstanceList instances, String host) {
		this.name = name;
		this.host = host;
		(new Thread(this)).start();
	}

	@Override
	public void run() {
		String ip = null;
		try {
			ServerSocket serverSocket = new ServerSocket(0);
			try (final DatagramSocket socket = new DatagramSocket()) {
				socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
				ip = socket.getLocalAddress().getHostAddress();
			}
			this.instances.add(new Couple<String, Integer>(this.name, serverSocket.getLocalPort())); // L'instance s'ajoute aux instances
			System.out.println("Game instance on server " + ip + " listening on port " + serverSocket.getLocalPort());
			System.out.println("Waiting fort game-master " + this.host);
			this.socket = serverSocket.accept();
			
			System.out.println("Connected to "+this.socket.getInetAddress());
			
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
