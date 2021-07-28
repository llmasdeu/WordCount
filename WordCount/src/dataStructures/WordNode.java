package dataStructures;

/**
 * Node on emmagatzemem la paraula.
 * @author Lluís Masdeu
 *
 */
public class WordNode {
	private String word;	//KEY
	private int wordCount;	//VALUE

	/**
	 * Constructor.
	 * @param word: paraula a inserir.
	 * @param wordCount: nombre d'aparicions.
	 */
	public WordNode(String word, int wordCount) {
		this.word = word;
		this.wordCount = wordCount;
	}

	/**
	 * Operació encarregada d'obtenir la paraula.
	 * @return paraula.
	 */
	public String getWord() {
		return word;
	}

	/**
	 * Operació encarregada d'editar la paraula emmagatzemada.
	 * @param word: paraula a inserir.
	 */
	public void setWord(String word) {
		this.word = word;
	}

	/**
	 * Operació encarregada d'obtenir el nombre d'aparicions.
	 * @return nombre d'aparicions.
	 */
	public int getWordCount() {
		return wordCount;
	}

	/**
	 * Operació encarregada d'editar el nombre d'aparicions de la paraula.
	 * @param wordCount: nombre d'aparicions.
	 */
	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}

	/**
	 * Operació encarregada d'actualitzar el nombre d'aparicions.
	 * @param wordCount: nombre a actualitzar.
	 */
	public void updateWordCount(int wordCount) {
		this.wordCount += wordCount;
	}
}
