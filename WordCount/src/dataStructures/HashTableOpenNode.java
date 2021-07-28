package dataStructures;

/**
 * Node des d'on gestionar la taula d'adreçament obert.
 * @author Lluís Masdeu
 *
 */
public class HashTableOpenNode {
	private WordNode wordNode;
	private int flag;				//-1: Lliure. -2: Esborrat. 0: Ocupat.

	/**
	 * Constructor.
	 */
	public HashTableOpenNode() {
		this.wordNode = null;
		this.flag = -1;
	}

	/**
	 * Operació encarregada de determinar si el node es troba ocupat.
	 * @return CERT si es troba ocupat. FALS si no es troba ocupat.
	 */
	public boolean used() {
		return flag == 0;
	}

	/**
	 * Operació encarregada de determinar si el node es troba lliure.
	 * @return CERT si es troba lliure. FALS si no es troba liure.
	 */
	public boolean free() {
		return flag == -1;
	}

	/**
	 * Operació encarregada de determinar si el node es troba esborrat.
	 * @return CERT si es troba esborrat. FALS si no es troba esborrat.
	 */
	public boolean erased() {
		return flag == -2;
	}

	/**
	 * Operació encarregada de determinar si el node no es troba inicialitzat.
	 * @return CERT si no es troba inicialitzat. FALS si es troba inicialitzat.
	 */
	public boolean empty() {
		return wordNode == null;
	}

	/**
	 * Operació encarregada d'obtenir la paraula emmagatzemada en el node.
	 * @return paraula.
	 */
	public String getWord() {
		return wordNode.getWord();
	}

	/**
	 * Operació encarregada d'afegir la paraula en el node.
	 * @param word: paraula a inserir.
	 * @param wordCount: número d'aparicions.
	 */
	public void setWord(String word, int wordCount) {
		wordNode = new WordNode(word, wordCount);
		this.flag = 0;
	}

	/**
	 * Operació encarregada d'obtenir el nombre d'aparicions.
	 * @return nombre d'aparicions.
	 */
	public int getWordCount() {
		return wordNode.getWordCount();
	}

	/**
	 * Operació encarregada d'actualitzar el nombre d'aparicions.
	 * @param wordCount: nombre a sumar.
	 */
	public void updateWordCount(int wordCount) {
		wordNode.updateWordCount(wordCount);
	}
}
