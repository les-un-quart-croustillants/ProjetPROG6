package Modele.Plateau.Exception;

public class BewareOfOrcasException extends PlateauException {
	public BewareOfOrcasException() {
		super("Vous êtes tombé à l'eau.");
	}
	public BewareOfOrcasException(String message) {
		super(message);
	}
}
