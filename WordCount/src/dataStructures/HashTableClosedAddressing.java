package dataStructures;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Taula d'adreçament tancat (Taula encadenada indirecta)
 * @author Lluís Masdeu
 *
 */
public class HashTableClosedAddressing {
	private List[] hashTable;
	private static final int MAX = 26;
	private static final String COUNT_SORT_FILE = "./hash-table-1-count-sort-results.csv";
	private static final String ALPHABETICAL_SORT_FILE = "./hash-table-1-alphabetically-sort-results.csv";
	private String countExecutionTime;

	/**
	 * Constructor.
	 */
	public HashTableClosedAddressing() {
		this.hashTable = new List[MAX];

		//Creem totes les llistes.
		for (int i = 0; i < hashTable.length; i++) {
			hashTable[i] = new List();
		}

		this.countExecutionTime = null;
	}

	/**
	 * Operació encarregada de sumar els caràcters de la paraula.
	 * @param word: paraula a inserir.
	 * @return valor de la suma dels caràcters.
	 */
	public int getWordValue(String word) {
		int wordValue = 0;

		//Obtenim el valor numèric de la paraula.
		for (int j = 0; j < word.length(); j++) {
			int charValue = word.charAt(j);
			wordValue += charValue;
		}

		return wordValue;
	}

	/**
	 * Operació amb la qual obtenim la posició en la taula de Hash.
	 * @param word: paraula a inserir.
	 * @return posició on inserir la paraula.
	 */
	public int hash(String word) {
		int i = -1;
		int wordValue = getWordValue(word);

		i = wordValue % MAX;

		return i;
	}

	/**
	 * Operació encarregada de gestionar l'adhesió de la paraula.
	 * @param word: paraula a inserir.
	 * @param wordCount: nombre de aparicions.
	 */
	public void addWord(String word, int wordCount) {
		int i = hash(word);

		hashTable[i].addWord(word, wordCount);
	}

	/**
	 * Operació encarregada d'exportar els resultats obtinguts.
	 * @param sortMode: opció d'ordre.
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void exportResults(int sortMode) throws FileNotFoundException, UnsupportedEncodingException {
		SortedList sortedWords = new SortedList(sortMode);
		int[] totalWords = new int[MAX];
		int numWords = 0;
		int numAppearances = 0;

		//Calculem el nombre total de paraules per cada posició.
		for (int i = 0; i < hashTable.length; i++) {
			hashTable[i].head();

			while (!hashTable[i].end()) {
				totalWords[i]++;
				numWords++;
				String word = hashTable[i].check().getWord();
				int wordCount = hashTable[i].check().getWordCount();
				numAppearances += wordCount;

				sortedWords.addSorted(word, wordCount);

				hashTable[i].forward();
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

		int j = totalWords.length - 1;

		PrintWriter writer = new PrintWriter(exportFile, "UTF-8");

		writer.println("sep=,");

		//Escrivim en el fitxer el nombre de total de paraules.
		for (int i = 0; i < totalWords.length - 1; i++) {
			writer.print("#_words[" + i + "],");
		}

		writer.println("#_words[" + j + "]");

		for (int i = 0; i < totalWords.length - 1; i++) {
			writer.print(totalWords[i] + ",");
		}

		writer.println(totalWords[j] + "\n");

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
	 * Operació encarregada d'afegir a la classe el temps d'execució durant el comptatge.
	 * @param countExecutionTime
	 */
	public void setCountExecutionTime(String countExecutionTime) {
		this.countExecutionTime = countExecutionTime;
	}
}
