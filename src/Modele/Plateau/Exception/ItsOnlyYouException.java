package Modele.Plateau.Exception;

import Utils.Position;

public class ItsOnlyYouException extends PlateauException {
	public ItsOnlyYouException() {
		super("Please use your penguins.");
	}

	public ItsOnlyYouException(Position p) {
		super("No penguin on " + p);
	}

	public ItsOnlyYouException(String message) {
		super(message);
	}
}
