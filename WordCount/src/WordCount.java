import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Scanner;

import dataStructures.*;

/**
 * Classe principal des d'on executem tot el programa.
 * @author Lluís Masdeu
 *
 */
public class WordCount {
	private MaxHeapTree maxHeap;										//1: Max Heap
	private List unsortedList;											//2: Llista Desordenada
	private HashTableClosedAddressing hashTableClosedAddressing;		//3: Taula de Hash (Taula Encadenada Indirecta)
	private HashTableOpenAddressing hashTableOpenAddressing;			//4: Taula de Hash (Taula d'Adreçament Obert)
	private String inputFile;
	private boolean wordsCountedFlag;

	/**
	 * Constructor.
	 */
	public WordCount() {
		this.maxHeap = null;					//1
		this.unsortedList = null;				//2
		this.hashTableClosedAddressing = null;	//3
		this.hashTableOpenAddressing = null;	//4
		this.inputFile = "";
		this.wordsCountedFlag = false;
	}

	/**
	 * Operació des d'on gestionarem les diverses opcions del menú principal.
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void mainMenu() throws FileNotFoundException, UnsupportedEncodingException {
		Scanner scr = new Scanner(System.in);
		int option = -1;

		while (option != 4) {
			System.out.println("\t\t----- WORD COUNT -----\n");

			System.out.println("\t1. Assignar Fitxer d'Entrada");
			System.out.println("\t2. Comptar Paraules");
			System.out.println("\t3. Visualització de Resultats");
			System.out.println("\t4. Sortir\n");

			System.out.print("\tIndica la opció: ");

			option = scr.nextInt();

			switch (option) {
				case 1:
					manageInputFile();
					break;

				case 2:
					if (inputFile.length() == 0) {
						System.out.println("\tError! Introdueix un fitxer d'entrada vàlid!\n");
					} else {
						countWords();
					}
					break;

				case 3:
					if (inputFile.length() == 0) {
						System.out.println("\tError! Introdueix un fitxer d'entrada vàlid!\n");
					} else {
						if (!wordsCountedFlag) {
							System.out.println("\tError! No s'ha comptabilitzat les paraules del fitxer!\n");
						} else {
							exportResults();
						}
					}
					break;

				case 4:
					exit();
					break;

				default:
					System.out.println("\tError! La opció seleccionada no es troba disponible!\n");
					break;
			}
		}
	}

	/**
	 * Operació des d'on gestionem la inserció del fitxer d'entrada.
	 */
	public void manageInputFile() {
		Scanner scr = new Scanner(System.in);

		System.out.println("\n\t\t----- Assignar Fitxer d'Entrada -----\n");

		System.out.print("\tIntrodueix el nom del fitxer: ");

		inputFile = scr.nextLine();

		System.out.println();
	}

	/**
	 * Operació des d'on gestionem el comptatge de paraules.
	 */
	public void countWords() {
		Scanner scr = new Scanner(System.in);
		int option = -1;

		while (option < 1 || option > 4) {
			System.out.println("\n\t\t----- Comptar Paraules -----\n");

			System.out.println("\t1. Max Heap");
			System.out.println("\t2. Llista Desordenada");
			System.out.println("\t3. Taula de Hash (Taula Encadenada Indirecta)");
			System.out.println("\t4. Taula de Hash (Taula d'Adreçament Obert)");

			System.out.print("\n\tIntrodueix la estructura de dades desitjada: ");

			option = scr.nextInt();

			if (option < 1 || option > 4) {
				System.out.println("\tError! La estructura seleccionada no es troba disponible!");
			}
		}

		//Inicialitzem la estructura de dades escollida.
		switch (option) {
			case 1:
				this.maxHeap = new MaxHeapTree();
				break;

			case 2:
				this.unsortedList = new List();
				break;

			case 3:
				this.hashTableClosedAddressing = new HashTableClosedAddressing();
				break;

			case 4:
				this.hashTableOpenAddressing = new HashTableOpenAddressing();
				break;
		}

		long start = 0;
		long time = 0;
		

		Scanner scn1 = null;

		try {
			start = System.currentTimeMillis();

			scn1 = new Scanner(new File(inputFile));

			while (scn1.hasNextLine()) {
				Scanner scn2 = new Scanner(scn1.nextLine());

				while (scn2.hasNext()) {
					String word = scn2.next();

					countWord(word, option);
				}
			}

			time = System.currentTimeMillis() - start;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//Comptem el temps que ha trigat en comptar les paraules.
		long millisecond = (time / 1000);
		long second = (time / 1000) % 60;
		long minute = (time / (1000 * 60)) % 60;
		long hour = (time / (1000 * 60 * 60)) % 24;
		String countExecutionTime = hour + ":" + minute + ":" + second + ":" + millisecond;

		this.wordsCountedFlag = true;

		switch (option) {
			case 1:
				maxHeap.setCountExecutionTime(countExecutionTime);
				break;

			case 2:
				unsortedList.setCountExecutionTime(countExecutionTime);
				break;

			case 3:
				hashTableClosedAddressing.setCountExecutionTime(countExecutionTime);
				break;

			case 4:
				hashTableOpenAddressing.setCountExecutionTime(countExecutionTime);
				break;
		}

		System.out.println();
	}

	/**
	 * Operació encarregada de guardar la paraula comptada en la estructura escollida.
	 * @param wordRead: paraula llegida del fitxer.
	 * @param option: identificador de l'estructura de dades.
	 */
	public void countWord(String wordRead, int option) {
		String word = manageWord(wordRead);

		if (word != "") {
			switch (option) {
				case 1:
					maxHeap.addWord(word, 0);
					break;

				case 2:
					unsortedList.addWord(word, 0);
					break;

				case 3:
					hashTableClosedAddressing.addWord(word, 0);
					break;

				case 4:
					hashTableOpenAddressing.addWord(word, 0);
					break;
			}
		}
	}

	/**
	 * Operació encarregada de comprovar els caràcters suportats en la paraula.
	 * @param wordRead: paraula llegida del fitxer.
	 * @return paraula tractada.
	 */
	public String manageWord(String wordRead) {
		String word = "";
		int aux = -1;

		for (int i = 0; i < wordRead.length(); i++) {
			boolean found = false;

			if ((wordRead.charAt(i) >= 'A' && wordRead.charAt(i) <= 'Z') 
					|| (wordRead.charAt(i) >= 'a' && wordRead.charAt(i) <= 'z') 
					|| (wordRead.charAt(i) >= '0' && wordRead.charAt(i) <= '9') 
					|| wordRead.charAt(i) == '.' || wordRead.charAt(i) == '-' 
					|| wordRead.charAt(i) == '\'' || wordRead.charAt(i) == '/') {
				//No fem res. Caràcters acceptats.
			} else {
				found = true;
			}

			if (found) {
				if (aux == -1) {
					word += wordRead.substring(0, i);
				} else {
					word += wordRead.substring(aux + 1, i);
				}

				aux = i;
			}
		}

		return word;
	}

	/**
	 * Operació encarregada de gestionar la exportació dels resultats.
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void exportResults() throws FileNotFoundException, UnsupportedEncodingException {
		Scanner scr = new Scanner(System.in);
		int structureMode = -1;

		while (structureMode < 1 || structureMode > 4) {
			System.out.println("\n\t\t----- Visualització de Resultats -----\n");

			System.out.println("\t1. Max Heap");
			System.out.println("\t2. Llista Desordenada");
			System.out.println("\t3. Taula de Hash (Taula Encadenada Indirecta)");
			System.out.println("\t4. Taula de Hash (Taula d'Adreçament Obert)");

			System.out.print("\n\tIntrodueix la estructura de dades amb la qual vols comprovar els resultats: ");

			structureMode = scr.nextInt();

			if (structureMode < 1 || structureMode > 4) {
				System.out.println("\tError! La estructura de dades no es troba disponible!\n");
			}
		}

		scr = null;
		scr = new Scanner(System.in);
		int resultsMode = -1;

		while (resultsMode != 1 && resultsMode != 2) {
			System.out.println("\n\t1. Ordenació per Número d'Aparicions");
			System.out.println("\t2. Ordenació Alfabètica");

			System.out.print("\n\tIntrodueix el mode en el qual vols visualitzar els resultats: ");

			resultsMode = scr.nextInt();

			if (resultsMode != 1 && resultsMode != 2) {
				System.out.println("\tError! La opció seleccionada no es troba disponible!");
			}
		}

		switch (structureMode) {
			case 1:
				if (maxHeap == null) {
					System.out.println("\tError! Encara no has comptat les aparicions amb aquesta estructura!\n");
				} else {
					maxHeap.exportResults(resultsMode);
				}
				break;

			case 2:
				if (unsortedList == null) {
					System.out.println("\tError! Encara no has comptat les aparicions amb aquesta estructura!\n");
				} else {
					unsortedList.exportResults(resultsMode);
				}
				break;

			case 3:
				if (hashTableClosedAddressing == null) {
					System.out.println("\tError! Encara no has comptat les aparicions amb aquesta estructura!\n");
				} else {
					hashTableClosedAddressing.exportResults(resultsMode);
				}
				break;

			case 4:
				if (hashTableOpenAddressing == null) {
					System.out.println("\tError! Encara no has comptat les aparicions amb aquesta estructura!\n");
				} else {
					hashTableOpenAddressing.exportResults(resultsMode);
				}
				break;
		}

		System.out.println();
	}

	/**
	 * Operació encarregada de mostrar el missatge de sortida.
	 */
	public void exit() {
		System.out.println("\tFins la propera!");
	}
}
