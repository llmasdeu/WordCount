package dataStructures;

import javax.tools.ForwardingFileObject;

/**
 * Llista ordenada.
 * @author Lluís Masdeu
 *
 */
public class SortedList {
	private ListNode first;
	private ListNode previous;
	private int sortMode;		//1: wordCount; 2: word

	/**
	 * Constructor.
	 * @param sortMode: mode d'ordre.
	 */
	public SortedList(int sortMode) {
		first = new ListNode(null, 0);
		previous = first;
		first.setNext(null);
		this.sortMode = sortMode;
	}

	/**
	 * Operació encarregada d'inserir la paraula ordenada.
	 * @param word: paraula a inserir.
	 * @param wordCount: nombre d'aparicions.
	 */
	public void addSorted(String word, int wordCount) {
		boolean found = false;

		head();

		if (sortMode == 1) {
			while (!end() && !found) {
				if (wordCount >= check().getWordCount()) {
					found = true;
				} else {
					forward();
				}
			}
		} else {
			while (!end() && !found) {
				if (word.compareTo(check().getWord()) <= 0) {
					found = true;
				} else {
					forward();
				}
			}
		}

		add(word, wordCount);
	}

	/**
	 * Operació encarregada d'afegir la paraula.
	 * @param word: paraula a inserir.
	 * @param wordCount: nombre d'aparicions.
	 */
	private void add(String word, int wordCount) {
		ListNode aux = new ListNode(word, wordCount);
		aux.setWord(word);
		aux.setNext(previous.getNext());
		previous.setNext(aux);
		previous = previous.getNext();
	}

	/**
	 * Operació encarregada de buscar la paraula a la llista.
	 * @param word: paraula a buscar.
	 * @return CERT si s'ha trobat. FALS si no s'ha trobat.
	 */
	private boolean search(String word) {
		boolean found = false;

		head();

		while (!end() && !found) {
			if (check().getWord().equalsIgnoreCase(word)) {
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
		ListNode aux;

		aux = previous.getNext();
		previous.setNext(previous.getNext().getNext());
		aux = null;
	}

	/**
	 * Operació encarregada d'obtenir el node del punt d'interés de la llista ordenada.
	 * @return node.
	 */
	public ListNode check() {
		return previous.getNext();
	}

	/**
	 * Operació encarregada de determinar si la llista es troba buida.
	 * @return CERT si es troba buida. FALS si no es troba buida.
	 */
	private boolean isEmpty() {
		return first.getNext().equals(null);
	}

	/**
	 * Operació encarregada d'avançar a la llista.
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
}
