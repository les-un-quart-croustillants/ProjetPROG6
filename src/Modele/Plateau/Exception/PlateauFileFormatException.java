package Modele.Plateau.Exception;

/**
 * Created by oloar on 29/05/2018.
 */
public class PlateauFileFormatException extends Throwable {
	public PlateauFileFormatException() {
		super("File format incorrect");
	}

	public PlateauFileFormatException(String f) {
		super(String.format("File %s has incorrect format.", f));
	}
}
