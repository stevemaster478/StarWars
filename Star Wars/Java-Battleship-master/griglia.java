public class griglia
{
	private Posizione[][] griglia;
	private int punti;

	// Costanti per numero di righe e colonne.
	public static final int NUM_rigaS = 10;
	public static final int NUM_colonnaS = 10;

	public griglia()
	{
		if (NUM_rigaS > 26)
		{
			throw new IllegalArgumentException("Errore! NUM_rigaS non puo' essere > 26");
		}

		griglia = new Posizione[NUM_rigaS][NUM_colonnaS];

		for (int riga = 0; riga < griglia.length; riga++)
		{
			for (int colonna = 0; colonna < griglia[riga].length; colonna++)
			{
				Posizione tempLoc = new Posizione();
				griglia[riga][colonna] = tempLoc;
			}
		}
	}

	// Contrassegna un risultato in questa posizione chiamando il metodo segnaStrike
	// sull'oggetto Posizione.
	public void segnaStrike(int riga, int colonna)
	{
		griglia[riga][colonna].segnaStrike();
		punti++;
	}

	// Contrassegna un errore in questa posizione.  
	public void segnaMiss(int riga, int colonna)
	{
		griglia[riga][colonna].segnaMiss();
	}

	// Imposta lo stato di questa posizione.
	public void setStato(int riga, int colonna, int Stato)
	{
		griglia[riga][colonna].setStato(Stato);
	}

	// Ottieni lo stato di questa posizione nella griglia
	public int getStato(int riga, int colonna)
	{
		return griglia[riga][colonna].getStato();
	}

	// Restituisci se questa posizione e' già stata indovinata.
	public boolean giaIndovinato(int riga, int colonna)
	{
		return !griglia[riga][colonna].nonIndovinato();
	}

	// Imposta la presenza o meno di una nave in questa posizione per la val
	public void setNave(int riga, int colonna, boolean val)
	{
		griglia[riga][colonna].setNave(val);
	}

	// Ritorna indipendentemente dalla presenza o meno di una nave qui  
	public boolean haNavi(int riga, int colonna)
	{
		return griglia[riga][colonna].haNavi();
	}

	// Ottieni l'oggetto Posizione in questa posizione di riga e colonna
	public Posizione get(int riga, int colonna)
	{
		return griglia[riga][colonna];
	}

	// Restituisci il numero di righe nella griglia
	public int numRighe()
	{
		return NUM_rigaS;
	}

	// Restituisci il numero di colonne nella griglia
	public int numColonne()
	{
		return NUM_colonnaS;
	}

	public void stampaStato()
	{
		metodoStampaGenerale(0);
	}

	public void printNavi()
	{
		metodoStampaGenerale(1);
	}

	public void stampaCombinato()
	{
		metodoStampaGenerale(2);
	}

	public boolean haPerso()
	{
		if (punti >= 17)
			return true;
		else
			return false;
	}

	public void aggiungiNave(Nave s)
	{
		int riga = s.getRiga();
		int colonna = s.getColonna();
		int lunghezza = s.getLunghezza();
		int dir = s.getDirezione();

		if (!(s.laDirezione()) || !(s.laPosizione()))
			throw new IllegalArgumentException("Errore! La direzione o la posizione non e' impostata / e' in default");

		// 0 - orizzontale; 1 - verticale
		if (dir == 0) // Orizzontale
		{
			for (int i = colonna; i < colonna+lunghezza; i++)
			{
				//System.out.println("DEBUG: riga = " + riga + "; colonna = " + i);
				griglia[riga][i].setNave(true);
				griglia[riga][i].setlunghezzaNave(lunghezza);
				griglia[riga][i].setDirezioneNave(dir);
			}
		}
		else if (dir == 1) // Verticale
		{
			for (int i = riga; i < riga+lunghezza; i++)
			{
				//System.out.println("DEBUG: riga = " + riga + "; colonna = " + i);
				griglia[i][colonna].setNave(true);
				griglia[i][colonna].setlunghezzaNave(lunghezza);
				griglia[i][colonna].setDirezioneNave(dir);
			}
		}
	}

	// Digitare: 0 per stato, 1 per navi, 2 per combinato
	private void metodoStampaGenerale(int type)
	{
		System.out.println();
		// Stampa colonne (HEADER)
		System.out.print("  ");
		for (int i = 1; i <= NUM_colonnaS; i++)
		{
			System.out.print(i + " ");
		}
		System.out.println();

		// Stampa riga
		int letteraFinalePerLoop = (NUM_rigaS - 1) + 65;
		for (int i = 65; i <= letteraFinalePerLoop; i++)
		{
			char ilCarattere = (char) i;
			System.out.print(ilCarattere + " ");

			for (int j = 0; j < NUM_colonnaS; j++)
			{
				if (type == 0) // inserisci == 0; stato
				{
					if (griglia[cambiaContatoreInteroArray(i)][j].nonIndovinato())
						System.out.print("- ");
					else if (griglia[cambiaContatoreInteroArray(i)][j].checkMiss())
						System.out.print("O ");
					else if (griglia[cambiaContatoreInteroArray(i)][j].checkStrike())
						System.out.print("X ");
				}
				else if (type == 1) // inserisci == 1; navi
				{
					if (griglia[cambiaContatoreInteroArray(i)][j].haNavi())
					{
						// System.out.print("X ");
						if (griglia[cambiaContatoreInteroArray(i)][j].getLunghezzaNave() == 2)
						{
							System.out.print("D ");
						}
						else if (griglia[cambiaContatoreInteroArray(i)][j].getLunghezzaNave() == 3)
						{
							System.out.print("C ");
						}
						else if (griglia[cambiaContatoreInteroArray(i)][j].getLunghezzaNave() == 4)
						{
							System.out.print("B ");
						}
						else if (griglia[cambiaContatoreInteroArray(i)][j].getLunghezzaNave() == 5)
						{
							System.out.print("A ");
						}
					}

					else if (!(griglia[cambiaContatoreInteroArray(i)][j].haNavi()))
					{
						System.out.print("- ");
					}

				}
				else // inserisci == 2; combined
				{
					if (griglia[cambiaContatoreInteroArray(i)][j].checkStrike())
						System.out.print("X ");
					else if (griglia[cambiaContatoreInteroArray(i)][j].haNavi())
					{
						// System.out.print("X ");
						if (griglia[cambiaContatoreInteroArray(i)][j].getLunghezzaNave() == 2)
						{
							System.out.print("D ");
						}
						else if (griglia[cambiaContatoreInteroArray(i)][j].getLunghezzaNave() == 3)
						{
							System.out.print("C ");
						}
						else if (griglia[cambiaContatoreInteroArray(i)][j].getLunghezzaNave() == 4)
						{
							System.out.print("B ");
						}
						else if (griglia[cambiaContatoreInteroArray(i)][j].getLunghezzaNave() == 5)
						{
							System.out.print("A ");
						}
					}
					else if (!(griglia[cambiaContatoreInteroArray(i)][j].haNavi()))
					{
						System.out.print("- ");
					}  
				}
			}
			System.out.println();
		}
	}

	public int cambiaContatoreInteroArray (int val)
	{
		int toReturn = -1;
		switch (val)
		{
		case 65:    toReturn = 0;
		break;
		case 66:    toReturn = 1;
		break;
		case 67:    toReturn = 2;
		break;
		case 68:    toReturn = 3;
		break;
		case 69:    toReturn = 4;
		break;
		case 70:    toReturn = 5;
		break;
		case 71:    toReturn = 6;
		break;
		case 72:    toReturn = 7;
		break;
		case 73:    toReturn = 8;
		break;
		case 74:    toReturn = 9;
		break;
		case 75:    toReturn = 10;
		break;
		case 76:    toReturn = 11;
		break;
		case 77:    toReturn = 12;
		break;
		case 78:    toReturn = 13;
		break;
		case 79:    toReturn = 14;
		break;
		case 80:    toReturn = 15;
		break;
		case 81:    toReturn = 16;
		break;
		case 82:    toReturn = 17;
		break;
		case 83:    toReturn = 18;
		break;
		case 84:    toReturn = 19;
		break;
		case 85:    toReturn = 20;
		break;
		case 86:    toReturn = 21;
		break;
		case 87:    toReturn = 22;
		break;
		case 88:    toReturn = 23;
		break;
		case 89:    toReturn = 24;
		break;
		case 90:    toReturn = 25;
		break;
		default:    toReturn = -1;
		break;
		}

		if (toReturn == -1)
		{
			throw new IllegalArgumentException("Errore riscontrato nello switch");
		}
		else
		{
			return toReturn;
		}
	}   
}