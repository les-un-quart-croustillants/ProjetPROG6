package Serveur.Lobby;

import java.io.IOException;
import java.net.ServerSocket;

public class Lobby {
    public static void main(String[] args) throws IOException {
        
        if (args.length != 1) {
            System.err.println("Usage: java Lobby <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try {
        	// Creation socket
        	boolean run = true;
            ServerSocket serverSocket = new ServerSocket(portNumber);
        	//Attente de client, lancement nouveau thread pour gestion client
            while(run)	new LobbyThread(serverSocket.accept());
            serverSocket.close();
            
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
