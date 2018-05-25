package Serveur.Lobby;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class socketList {
	private ArrayList<Socket> joueurs;
	private ArrayList<BufferedReader> in;
	private ArrayList<PrintWriter> out;
	private ArrayList<ObjectOutputStream> outObj;
	private ArrayList<ObjectInputStream> inObj;
	
	public socketList() {
		this.joueurs = new ArrayList<Socket>();
		this.in = new ArrayList<BufferedReader>();
		this.out = new ArrayList<PrintWriter>();
		this.outObj = new ArrayList<ObjectOutputStream>();
		this.inObj = new ArrayList<ObjectInputStream>();
	}
	
	public void connectTo(Socket socket) {
		try {
			this.joueurs.add(socket);
			int index = this.joueurs.size()-1;
			this.out.add(new PrintWriter(joueurs.get(index).getOutputStream(), true));
			this.in.add(new BufferedReader(new InputStreamReader(joueurs.get(index).getInputStream())));
			this.outObj.add(new ObjectOutputStream(joueurs.get(index).getOutputStream()));
			outObj.get(index).flush();
			this.inObj.add(new ObjectInputStream(new BufferedInputStream(joueurs.get(index).getInputStream())));
		} catch (IOException e) {
			System.out.println("Erreur lors de l'ajout d'une nouvelle connection");
			e.printStackTrace();
		}
	}
	
	public void changeConnectionAt(Socket socket,int index) {
		try {
			this.joueurs.set(index,socket);
			this.out.set(index,new PrintWriter(joueurs.get(index).getOutputStream(), true));
			this.in.set(index,new BufferedReader(new InputStreamReader(joueurs.get(index).getInputStream())));
			this.outObj.set(index,new ObjectOutputStream(joueurs.get(index).getOutputStream()));
			outObj.get(index).flush();
			this.inObj.set(index,new ObjectInputStream(new BufferedInputStream(joueurs.get(index).getInputStream())));
		} catch (IOException e) {
			System.out.println("Erreur lors du changement de connection en "+index);
			e.printStackTrace();
		}
	}
	
	public void closeConnectionAt(int index) {
		try {
			this.joueurs.get(index).close();
		} catch (IOException e) {
			System.out.println("Erreur lors de la fermeture de la socket client "+index);
			e.printStackTrace();
		}
		this.joueurs.remove(index);
		
	}
	
	public void sendToAll(String message) {
		for(PrintWriter stream: this.out) {
			stream.println(message);
		}
	}
	
	public void sendToAll(Object message) {
		for(ObjectOutputStream stream: this.outObj) {
			try {
				stream.writeObject(message);
			} catch (IOException e) {
				System.out.println("Erreur lors de l'ecriture dans le stream du client "+this.outObj.indexOf(stream));
				e.printStackTrace();
			}
		}
	}
	
	public void sendTo(int index,String message) {
		this.out.get(index).println(message);
	}
	
	public void sendTo(int index,Object message) {
		try {
			this.outObj.get(index).writeObject(message);
		} catch (IOException e) {
			System.out.println("Erreur lors de l'ecriture dans le stream du client "+index);
			e.printStackTrace();
		}
	}
	
	public String recieveFrom(int index) {
		try {
			return this.in.get(index).readLine();
		} catch (IOException e) {
			System.out.println("Erreur lors de la lecture dans le stream du client "+index);
			e.printStackTrace();
			return null;
		}
	}
	
	public Object recieveObjectFrom(int index) {
		try {
			return this.inObj.get(index).readObject();
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("Erreur lors de la lecture dans le stream du client "+index);
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean isHost(int index, String host) {
		return this.joueurs.get(index).getRemoteSocketAddress().toString().equals(host);
	}
	
}