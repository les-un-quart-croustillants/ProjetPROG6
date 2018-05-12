package Serveur.Lobby;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LobbyThread implements Runnable {

	private BufferedReader in = null;
	private PrintWriter out = null;

	public LobbyThread(Socket s) {

		try {
            out = new PrintWriter(s.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		(new Thread(this)).start();
	}

	/**
	 * Gestion de la communication avec le client
	 */
	@Override
	public void run() {
		String inputLine, outputLine;
		LobbyAnwserProtocol lep = new LobbyAnwserProtocol();
		outputLine = lep.processInput(null);
		out.println(outputLine);

		// Gestion des inputs
		try {
			while ((inputLine = in.readLine()) != null) {
				outputLine = lep.processInput(inputLine);
				out.println(outputLine);
				if (outputLine.equals("Bye.")) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
