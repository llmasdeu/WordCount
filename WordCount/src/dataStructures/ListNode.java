package dataStructures;

/**
 * Node de la llista.
 * @author Lluís Masdeu
 *
 */
public class ListNode {
	private WordNode wordNode;
	private ListNode next;

	/**
	 * Constructor.
	 * @param word: paraula a inserir.
	 * @param wordCount: número d'aparicions.
	 */
	public ListNode(String word, int wordCount) {
		this.wordNode = new WordNode(word, wordCount);
		this.next = null;
	}

	/**
	 * Operació encarregada d'obtenir el node on emmagatzemem la paraula.
	 * @return node.
	 */
	public WordNode getWordNode() {
		return wordNode;
	}

	/**
	 * Operació encarregada d'obtenir la paraula.
	 * @return paraula.
	 */
	public String getWord() {
		return wordNode.getWord();
	}

	/**
	 * Operació encarregada d'editar la paraula.
	 * @param word: paraula.
	 */
	public void setWord(String word) {
		wordNode.setWord(word);
	}

	/**
	 * Operació encarregada d'obtenir el recompte.
	 * @return recompte d'aparicions.
	 */
	public int getWordCount() {
		return wordNode.getWordCount();
	}

	/**
	 * Operació encarregada d'editar el node de la paraula.
	 * @param wordCount: node de la paraula.
	 */
	public void setWordCount(int wordCount) {
		wordNode.setWordCount(wordCount);
	}

	/**
	 * Operació encarregada d'actualitzar el recompte de paraules.
	 * @param wordCount: nombre a actualitzar.
	 */
	public void updateWordCount(int wordCount) {
		wordNode.updateWordCount(wordCount);
	}

	/**
	 * Operació encarregada d'obtenir el node 'next' de la llista.
	 * @return node 'next'.
	 */
	public ListNode getNext() {
		return next;
	}

	/**
	 * Operació encarregada d'editar el node 'next' de la llista.
	 * @param next: node 'next' de la llista.
	 */
	public void setNext(ListNode next) {
		this.next = next;
	}
}
