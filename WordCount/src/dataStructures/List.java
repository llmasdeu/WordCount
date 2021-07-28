package dataStructures;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Llista desordenada.
 * @author Lluís Masdeu
 *
 */
public class List {
	private ListNode first;
	private ListNode previous;
	private static final String COUNT_SORT_FILE = "./list-count-sort-results.csv";
	private static final String ALPHABETICAL_SORT_FILE = "./list-alphabetical-sort-results.csv";
	private String countExecutionTime;

	/**
	 * Constructor.
	 */
	public List() {
		this.first = new ListNode(null, 0);
		this.previous = first;
		this.countExecutionTime = null;
	}

	/**
	 * Operació encarregada d'afegir la paraula.
	 * @param word: paraula a inserir.
	 * @param wordCount: nombre d'aparicions.
	 */
	public void addWord(String word, int wordCount) {
		if (!searchWord(word)) {
			ListNode aux = new ListNode(word, wordCount);

			aux.setWord(word);
			aux.setNext(previous.getNext());
			previous.setNext(aux);

			if (wordCount == 0) {
				countAppearance();
			}

			forward();
		}
	}

	/**
	 * Operació encarregada de buscar la paraula.
	 * @param word: paraula a buscar.
	 * @return CERT si s'ha trobat. FALS si no s'ha trobat.
	 */
	public boolean searchWord(String word) {
		boolean found = false;

		head();

		while (!end() && !found) {
			if (check().getWord().equalsIgnoreCase(word)) {
				countAppearance();
				found = true;
			} else {
				forward();
			}
		}

		return found;
	}

	/**
	 * Operació encarregada d'esborrar una paraula.
	 */
	public void remove() {
		ListNode aux = previous.getNext();
		previous.setNext(previous.getNext().getNext());
		aux = null;
	}

	/**
	 * Operació encarregada d'obtenir el node 'previous' de la llista.
	 * @return
	 */
	public ListNode check() {
		return previous.getNext();
	}

	/**
	 * Operació encarregada de determinar si la llista es troba buida.
	 * @return
	 */
	public boolean isEmpty() {
		return first.getNext() == null;
	}

	/**
	 * Operació encarregada d'avançar en la llista.
	 */
	public void forward() {
		previous = previous.getNext();
	}

	/**
	 * Operació encarregada de determinar si el punt d'interés es troba al final de la llista.
	 * @return CERT si es troba al final. FALS si no es troba al final.
	 */
	public boolean end() {
		return previous.getNext() == null;
	}

	/**
	 * Operació encarregada d'anar a l'inici de la llista.
	 */
	public void head() {
		previous = first;
	}

	/**
	 * Operació encarregada de destruir la llista.
	 */
	public void destroy() {
		head();

		while (!end()) {
			remove();
		}

		first = null;
		previous = null;
	}

	/**
	 * Operació encarregada d'actualitzar el comptatge de la paraula.
	 */
	public void countAppearance() {
		check().updateWordCount(1);
	}

	/**
	 * Operació encarregada de gestionar la exportació dels resultats.
	 * @param sortMode: mode d'ordre.
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void exportResults(int sortMode) throws FileNotFoundException, UnsupportedEncodingException {
		SortedList sortedWords = new SortedList(sortMode);
		int numWords = 0;
		int numAppearances = 0;

		head();

		while (!end()) {
			String word = check().getWord();
			int wordCount = check().getWordCount();
			numWords++;
			numAppearances += wordCount;

			sortedWords.addSorted(word, wordCount);

			forward();
		}

		String exportFile = "";

		switch (sortMode) {
			case 1:
				exportFile = COUNT_SORT_FILE;
				break;

			case 2:
				exportFile = ALPHABETICAL_SORT_FILE;
				break;
		}

		PrintWriter writer = new PrintWriter(exportFile, "UTF-8");

		writer.println("sep=,");

		writer.println("#_words,#_appearances,,execution_time");
		writer.println(numWords + "," + numAppearances + ",," + countExecutionTime + "\n");

		writer.println("word,word_count");

		sortedWords.head();

		while (!sortedWords.end()) {
			writer.println(sortedWords.check().getWord() + "," + sortedWords.check().getWordCount());
			sortedWords.forward();
		}

		writer.close();

		//Destruïm la llista.
		sortedWords.destroy();
		sortedWords = null;
	}

	/**
	 * Operació encarregada d'afegir el temps d'execució durant el comptatge del fitxer.
	 * @param countExecutionTime
	 */
	public void setCountExecutionTime(String countExecutionTime) {
		this.countExecutionTime = countExecutionTime;
	}
}
