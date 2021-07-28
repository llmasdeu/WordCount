package dataStructures;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Max Heap.
 * @author Lluís Masdeu
 *
 */
public class MaxHeapTree {
	private WordNode[] heap;
	private int last;
	private static final String COUNT_SORT_FILE = "./max-heap-count-sort-results.csv";
	private static final String ALPHABETICAL_SORT_FILE = "./max-heap-alphabetical-sort-results.csv";
	private String countExecutionTime;

	/**
	 * Constructor.
	 */
	public MaxHeapTree() {
		this.heap = new WordNode[1];
		this.last = -1;
		this.countExecutionTime = null;
	}

	/**
	 * Operació encarregada d'afegir la paraula.
	 * @param word: paraula a inserir.
	 * @param wordCount: recompte a inserir.
	 */
	public void addWord(String word, int wordCount) {
		if (!searchWord(word)) {
			last++;

			if (last >= heap.length) {
				WordNode[] auxHeap = new WordNode[heap.length];

				for (int i = 0; i < heap.length; i++) {
					auxHeap[i] = new WordNode(heap[i].getWord(), heap[i].getWordCount());
				}

				this.heap = null;
				this.heap = new WordNode[auxHeap.length + 1];

				for (int i = 0; i < auxHeap.length; i++) {
					heap[i] = new WordNode(auxHeap[i].getWord(), auxHeap[i].getWordCount());
				}
			}

			heap[last] = new WordNode(word, wordCount);

			if (wordCount == 0) {
				updateWord(last);
			}

			sortWord();
		}
	}

	/**
	 * Operació encarregada de buscar la paraula.
	 * @param word: paraula a buscar.
	 * @return CERT si s'ha trobat. FALS si no s'ha trobat.
	 */
	public boolean searchWord(String word) {
		boolean ok = false;

		for (int i = 0; i < heap.length; i++) {
			if (heap[i] != null) {
				if (heap[i].getWord().equalsIgnoreCase(word)) {
					updateWord(i);

					ok = true;
				}
			}
		}

		return ok;
	}

	/**
	 * Operació encarregada d'actualitzar el recompte.
	 * @param location: posició a on actualitzar.
	 */
	public void updateWord(int location) {
		heap[location].updateWordCount(1);
	}

	/**
	 * Operació encarregada d'ordenar la paraula afegida.
	 */
	public void sortWord() {
		WordNode auxHeap;
		int i = last;

		while (heap[i].getWord().compareTo(heap[i / 2].getWord()) > 0) {
			auxHeap = heap[i];
			heap[i] = heap[i / 2];
			heap[i / 2] = auxHeap;
			i = i / 2;
		}
	}

	/**
	 * Operació encarregada d'esborrar una paraula.
	 */
	public void remove() {
		if (last >= 0) {
			heap[0] = heap[last];
			heap[last] = null;
			last--;
			sink(0, last);
		}
	}

	/**
	 * Operació encarregada d'enfonsar la paraula.
	 * @param first: índex inicial.
	 * @param last: índex final.
	 */
	public void sink(int first, int last) {
		int i_left, i_right, i_max;

		i_left = first * 2 + 1;
		i_right = first * 2 + 2;

		if (i_left <= last) {
			if (i_right <= last) {
				i_max = max(i_left, i_right);
			} else {
				i_max = i_left;
			}

			if (heap[first].getWord().compareTo(heap[i_max].getWord()) < 0) {
				WordNode auxHeap = heap[first];
				heap[first] = heap[i_max];
				heap[i_max] = auxHeap;

				sink(i_max, last);
			}
		}
	}

	/**
	 * Operació encarregada d'obtenir l'índex més gran.
	 * @param i_left: índex del fill esquerra.
	 * @param i_right: índex del fill dret.
	 * @return índex més gran.
	 */
	public int max(int i_left, int i_right) {
		if (heap[i_left].getWord().compareTo(heap[i_right].getWord()) < 0) {
			return i_left;
		} else {
			return i_right;
		}
	}

	/**
	 * Operació encarregada d'exportar els resultats.
	 * @param sortMode: mode d'ordre.
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void exportResults(int sortMode) throws FileNotFoundException, UnsupportedEncodingException {
		SortedList sortedWords = new SortedList(sortMode);
		int numWords = 0;
		int numAppearances = 0;

		for (int i = 0; i < heap.length; i++) {
			if (heap[i] != null) {
				String word = heap[i].getWord();
				int wordCount = heap[i].getWordCount();
				numWords++;
				numAppearances += wordCount;

				sortedWords.addSorted(word, wordCount);
			}
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
	 * Operació encarregada d'editar el temps d'execució durant el comptatge.
	 * @param countExecutionTime: temps d'execució.
	 */
	public void setCountExecutionTime(String countExecutionTime) {
		this.countExecutionTime = countExecutionTime;
	}
}
