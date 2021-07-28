package dataStructures;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Taula d'adreçament obert.
 * @author Lluís Masdeu
 *
 */
public class HashTableOpenAddressing {
	private HashTableOpenNode[] hashTable;
	private static final int MAX = 6350;
	private static final String COUNT_SORT_FILE = "./hash-table-2-count-sort-results.csv";
	private static final String ALPHABETICAL_SORT_FILE = "./hash-table-2-alphabetically-sort-results.csv";
	private String countExecutionTime;

	/**
	 * Constructor.
	 */
	public HashTableOpenAddressing() {
		this.hashTable = new HashTableOpenNode[MAX];

		for (int i = 0; i < hashTable.length; i++) {
			hashTable[i] = new HashTableOpenNode();
		}

		this.countExecutionTime = null;
	}

	/**
	 * Operació amb la qual obtenim la posició a inserir.
	 * @param word: paraula a inserir.
	 * @param i: índex amb el qual ens ajudarem a l'hora de fer els càlculs.
	 * @return posició a on inserir.
	 */
	public int hash(String word, int i) {
		int wordValue = getWordValue(word);

		return (int) Math.floor((wordValue + Math.pow(i, 2)) % MAX);
	}

	/**
	 * Operació encarregada d'obtenir el valor de la suma dels caràcters de la paraula.
	 * @param word: paraula a inserir.
	 * @return valor de la suma dels caràcters.
	 */
	public int getWordValue(String word) {
		int wordValue = 0;

		for (int i = 0; i < word.length(); i++) {
			wordValue += word.charAt(i);
		}

		return wordValue;
	}

	/**
	 * Operació encarregada d'afegir la paraula.
	 * @param word: paraula a inserir.
	 * @param wordCount: nombre d'aparicions.
	 */
	public void addWord(String word, int wordCount) {
		boolean ok = false;
		int i = 0;
		int j;

		if (wordCount == 0) {
			wordCount = 1;
		}

		while (!ok) {
			j = hash(word, i);

			if (!hashTable[j].empty()) {
				if (searchWord(word, j)) {
					hashTable[j].updateWordCount(wordCount);
					ok = true;
				}
			} else {
				if (hashTable[j].free()) {
					hashTable[j].setWord(word, wordCount);
					ok = true;
				}
			}

			if (!ok) {
				i++;
			}
		}
	}

	/**
	 * Operació encarregda de buscar la paraula.
	 * @param word: paraula a buscar.
	 * @param location: posició a on buscar.
	 * @return CERT si s'ha trobat. FALS si no s'ha trobat.
	 */
	public boolean searchWord(String word, int location) {
		return hashTable[location].getWord().equalsIgnoreCase(word);
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

		for (int i = 0; i < hashTable.length; i++) {
			if (!hashTable[i].empty()) {
				String word = hashTable[i].getWord();
				int wordCount = hashTable[i].getWordCount();
				numWords++;
				numAppearances += wordCount;

				sortedWords.addSorted(word, wordCount);
			}
		}

		String exportFile = "";

		//Obtenim el nom del fitxer a exportar.
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
	 * Operació encarregada d'afegir a la classe el temps en el qual s'ha comptat el fitxer.
	 * @param countExecutionTime: temps d'execució.
	 */
	public void setCountExecutionTime(String countExecutionTime) {
		this.countExecutionTime = countExecutionTime;
	}
}
