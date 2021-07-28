import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

/**
 * Classe principal del programa.
 * @author Lluís Masdeu
 *
 */
public class Main {
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		WordCount wCount = new WordCount();

		//Iniciem l'execució del programa.
		wCount.mainMenu();
	}
}
