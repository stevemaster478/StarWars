import java.util.Scanner;

public class Battleship
{
	public static Scanner reader = new Scanner(System.in);

	public static void main(String[] args)
	{
		System.out.println("SPACESHIP - ** by F2T **");  

		System.out.println("\nImpostazione del giocatore:");
		Giocatore utenteGiocatore = new Giocatore();
		setup(utenteGiocatore);

		System.out.println("Computer impostato. Premere ENTER per continuare: ");
		reader.nextLine();
		reader.nextLine();
		Giocatore computer = new Giocatore();
		impostaComputer(computer);
		System.out.println("\nGriglia del computer (PER IL DEBUG)...");
		computer.grigliaGiocatore.printNavi();

		String result = "";
		while(true)
		{
			System.out.println(result);
			System.out.println("\nL'utente sta tentando di indovinare: ");
			result = askForGuess(utenteGiocatore, computer);

			if (utenteGiocatore.grigliaGiocatore.haPerso())
			{
				System.out.println("Computer ha colpito l'astronave! ... L'utente ha perso!");
				break;
			}
			else if (computer.grigliaGiocatore.haPerso())
			{
				System.out.println("Colpito! ... Il computer ha perso!");
				break;
			}

			System.out.println("\nIl computer sta tentando di indovinare...");


			compMakeGuess(computer, utenteGiocatore);
		}
	}

	private static void compMakeGuess(Giocatore comp, Giocatore user)
	{
		Randomizer rand = new Randomizer();
		int riga = rand.nextInt(0, 9);
		int colonna = rand.nextInt(0, 9);

		// While computer already guessed this posiiton, make a new random guess
		while (comp.oppgriglia.giaIndovinato(riga, colonna))
		{
			riga = rand.nextInt(0, 9);
			colonna = rand.nextInt(0, 9);
		}

		if (user.grigliaGiocatore.haNavi(riga, colonna))
		{
			comp.oppgriglia.segnaStrike(riga, colonna);
			user.grigliaGiocatore.segnaStrike(riga, colonna);
			System.out.println("Computer ha colpito: " + convertIntToLetter(riga) + convertCompcolonnaToRegular(colonna));
		}
		else
		{
			comp.oppgriglia.segnaMiss(riga, colonna);
			user.grigliaGiocatore.segnaMiss(riga, colonna);
			System.out.println("Computer ha mancato: " + convertIntToLetter(riga) + convertCompcolonnaToRegular(colonna));
		}


		System.out.println("\nLa tua griglia ... Premere ENTER per continuare: ");
		reader.nextLine();
		user.grigliaGiocatore.stampaCombinato();
		System.out.println("Premere ENTER per continuare: ");
		reader.nextLine();
	}

	private static String askForGuess(Giocatore p, Giocatore opp)
	{
		System.out.println("Vedendo i miei tentativi:");
		p.oppgriglia.stampaStato();

		int riga = -1;
		int colonna = -1;

		String oldriga = "Z";
		int oldcolonna = -1;

		while(true)
		{
			System.out.print("Inserisci riga (A-J): ");
			String userInputriga = reader.next();
			userInputriga = userInputriga.toUpperCase();
			oldriga = userInputriga;
			riga = convertLetterToInt(userInputriga);

			System.out.print("Inserisci colonna (1-10): ");
			colonna = reader.nextInt();
			oldcolonna = colonna;
			colonna = convertUsercolonnaToProcolonna(colonna);

			//System.out.println("DEBUG: " + riga + colonna);

			if (colonna >= 0 && colonna <= 9 && riga != -1)
				break;

			System.out.println("Posizione invalida!");
		}

		if (opp.grigliaGiocatore.haNavi(riga, colonna))
		{
			p.oppgriglia.segnaStrike(riga, colonna);
			opp.grigliaGiocatore.segnaStrike(riga, colonna);
			return "**L'utente ha colpito: " + oldriga + oldcolonna + " **";
		}
		else
		{
			p.oppgriglia.segnaMiss(riga, colonna);
			opp.grigliaGiocatore.segnaMiss(riga, colonna);
			return "**L'utente ha mancato: " + oldriga + oldcolonna + " **";
		}
	}

	private static void setup(Giocatore p)
	{
		p.grigliaGiocatore.printNavi();
		System.out.println();
		int counter = 1;
		int normCounter = 0;
		while (p.numNaviRimaste() > 0)
		{
			for (Nave s: p.navi)
			{
				System.out.println("\nNave #" + counter + ": lunghezza-" + s.getLunghezza());
				int riga = -1;
				int colonna = -1;
				int dir = -1;
				while(true)
				{
					System.out.print("Inserisci riga (A-J): ");
					String userInputriga = reader.next();
					userInputriga = userInputriga.toUpperCase();
					riga = convertLetterToInt(userInputriga);

					System.out.print("Inserisci colonna (1-10): ");
					colonna = reader.nextInt();
					colonna = convertUsercolonnaToProcolonna(colonna);

					System.out.print("Inserisci direzione (0-H, 1-V): ");
					dir = reader.nextInt();

					//System.out.println("DEBUG: " + riga + colonna + dir);

					if (colonna >= 0 && colonna <= 9 && riga != -1 && dir != -1) // Check valid input
					{
						if (!hasErrors(riga, colonna, dir, p, normCounter)) // Check if errors will produce (out of bounds)
						{
							break;
						}
					}

					System.out.println("Posizione invalida!");
				}

				//System.out.println("FURTHER DEBUG: riga = " + riga + "; colonna = " + colonna);
				p.navi[normCounter].setPosizione(riga, colonna);
				p.navi[normCounter].setDirezione(dir);
				p.grigliaGiocatore.aggiungiNave(p.navi[normCounter]);
				p.grigliaGiocatore.printNavi();
				System.out.println();
				System.out.println("Hai " + p.numNaviRimaste() + " navi rimanenti da piazzare.");

				normCounter++;
				counter++;
			}
		}
	}

	private static void impostaComputer(Giocatore p)
	{
		System.out.println();
		int counter = 1;
		int normCounter = 0;

		Randomizer rand = new Randomizer();

		while (p.numNaviRimaste() > 0)
		{
			for (Nave s: p.navi)
			{
				int riga = rand.nextInt(0, 9);
				int colonna = rand.nextInt(0, 9);
				int dir = rand.nextInt(0, 1);

				//System.out.println("DEBUG: riga-" + riga + "; colonna-" + colonna + "; dir-" + dir);

				while (hasErrorsComp(riga, colonna, dir, p, normCounter)) // mentre i numeri casuali provocano errori, ricomincia
				{
					riga = rand.nextInt(0, 9);
					colonna = rand.nextInt(0, 9);
					dir = rand.nextInt(0, 1);
					//System.out.println("RIPETI-DEBUG: riga-" + riga + "; colonna-" + colonna + "; dir-" + dir);
				}

				//System.out.println("ULTERIORE DEBUG: riga = " + riga + "; colonna = " + colonna);
				p.navi[normCounter].setPosizione(riga, colonna);
				p.navi[normCounter].setDirezione(dir);
				p.grigliaGiocatore.aggiungiNave(p.navi[normCounter]);

				normCounter++;
				counter++;
			}
		}
	}

	private static boolean hasErrors(int riga, int colonna, int dir, Giocatore p, int count)
	{
		//System.out.println("DEBUG: count arg e' " + count);

		int lunghezza = p.navi[count].getLunghezza();

		// Controlla se e' fuori griglia - Orizzontale
		if (dir == 0)
		{
			int checker = lunghezza + colonna;
			//System.out.println("DEBUG: checker is " + checker);
			if (checker > 10)
			{
				System.out.println("L'astronave non ci sta");
				return true;
			}
		}

		// Controlla se e' fuori griglia - Verticale
		if (dir == 1) // Verticale
		{
			int checker = lunghezza + riga;
			//System.out.println("DEBUG: checker is " + checker);
			if (checker > 10)
			{
				System.out.println("L'astronave non ci sta");
				return true;
			}
		}

		// Controlla se si sovrappone a un'altra astronave
		if (dir == 0) // Orizzontale
		{
			// Per ogni posizione occupata da una nave, controlla se l'astronave e' già li'
			for (int i = colonna; i < colonna+lunghezza; i++)
			{
				//System.out.println("DEBUG: riga = " + riga + "; colonna = " + i);
				if(p.grigliaGiocatore.haNavi(riga, i))
				{
					System.out.println("E' GIA' PRESENTE UN'ALTRA ASTRONAVE");
					return true;
				}
			}
		}
		else if (dir == 1) // Verticale
		{
			// Per ogni posizione occupata da una nave, controlla se l'astronave e' già li'
			for (int i = riga; i < riga+lunghezza; i++)
			{
				//System.out.println("DEBUG: riga = " + riga + "; colonna = " + i);
				if(p.grigliaGiocatore.haNavi(i, colonna))
				{
					System.out.println("E' GIA' PRESENTE UN'ALTRA ASTRONAVE");
					return true;
				}
			}
		}

		return false;
	}

	private static boolean hasErrorsComp(int riga, int colonna, int dir, Giocatore p, int count)
	{
		//System.out.println("DEBUG: count arg e' " + count);

		int lunghezza = p.navi[count].getLunghezza();

		// Controlla se e' fuori griglia - Orizzontale
		if (dir == 0)
		{
			int checker = lunghezza + colonna;
			//System.out.println("DEBUG: checker is " + checker);
			if (checker > 10)
			{
				return true;
			}
		}

		// Controlla se e' fuori griglia - Verticale
		if (dir == 1) // VERTICAL
		{
			int checker = lunghezza + riga;
			//System.out.println("DEBUG: checker is " + checker);
			if (checker > 10)
			{
				return true;
			}
		}

		// Controlla se si sovrappone a un'altra astronave
		if (dir == 0) // Orizzontale
		{
			// Per ogni posizione occupata da una nave, controlla se l'astronave e' già li'
			for (int i = colonna; i < colonna+lunghezza; i++)
			{
				//System.out.println("DEBUG: riga = " + riga + "; colonna = " + i);
				if(p.grigliaGiocatore.haNavi(riga, i))
				{
					return true;
				}
			}
		}
		else if (dir == 1) // Verticale
		{
			// Per ogni posizione occupata da una nave, controlla se l'astronave e' già li'
			for (int i = riga; i < riga+lunghezza; i++)
			{
				//System.out.println("DEBUG: riga = " + riga + "; colonna = " + i);
				if(p.grigliaGiocatore.haNavi(i, colonna))
				{
					return true;
				}
			}
		}

		return false;
	}


	/*METODI HELPER*/
	private static int convertLetterToInt(String val)
	{
		int toReturn = -1;
		switch (val)
		{
		case "A":   toReturn = 0;
		break;
		case "B":   toReturn = 1;
		break;
		case "C":   toReturn = 2;
		break;
		case "D":   toReturn = 3;
		break;
		case "E":   toReturn = 4;
		break;
		case "F":   toReturn = 5;
		break;
		case "G":   toReturn = 6;
		break;
		case "H":   toReturn = 7;
		break;
		case "I":   toReturn = 8;
		break;
		case "J":   toReturn = 9;
		break;
		default:    toReturn = -1;
		break;
		}

		return toReturn;
	}

	private static String convertIntToLetter(int val)
	{
		String toReturn = "Z";
		switch (val)
		{
		case 0:   toReturn = "A";
		break;
		case 1:   toReturn = "B";
		break;
		case 2:   toReturn = "C";
		break;
		case 3:   toReturn = "D";
		break;
		case 4:   toReturn = "E";
		break;
		case 5:   toReturn = "F";
		break;
		case 6:   toReturn = "G";
		break;
		case 7:   toReturn = "H";
		break;
		case 8:   toReturn = "I";
		break;
		case 9:   toReturn = "J";
		break;
		default:    toReturn = "Z";
		break;
		}

		return toReturn;
	}

	private static int convertUsercolonnaToProcolonna(int val)
	{
		int toReturn = -1;
		switch (val)
		{
		case 1:   toReturn = 0;
		break;
		case 2:   toReturn = 1;
		break;
		case 3:   toReturn = 2;
		break;
		case 4:   toReturn = 3;
		break;
		case 5:   toReturn = 4;
		break;
		case 6:   toReturn = 5;
		break;
		case 7:   toReturn = 6;
		break;
		case 8:   toReturn = 7;
		break;
		case 9:   toReturn = 8;
		break;
		case 10:   toReturn = 9;
		break;
		default:    toReturn = -1;
		break;
		}

		return toReturn;
	}

	private static int convertCompcolonnaToRegular(int val)
	{
		int toReturn = -1;
		switch (val)
		{
		case 0:   toReturn = 1;
		break;
		case 1:   toReturn = 2;
		break;
		case 2:   toReturn = 3;
		break;
		case 3:   toReturn = 4;
		break;
		case 4:   toReturn = 5;
		break;
		case 5:   toReturn = 6;
		break;
		case 6:   toReturn = 7;
		break;
		case 7:   toReturn = 8;
		break;
		case 8:   toReturn = 9;
		break;
		case 9:   toReturn = 10;
		break;
		default:    toReturn = -1;
		break;
		}

		return toReturn;
	}
}