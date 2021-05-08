public class Posizione
{
	// Variabili globali
	public static final int NONINDOVINATO = 0;
	public static final int COLPITO = 1;
	public static final int MANCATO = 2;

	// Variabili di istanza
	private boolean haNavi;
	private int Stato;
	private int lunghezzaNave;
	private int direzioneOfShip;

	// Costruttore di Posizione
	public Posizione()
	{
		// Imposta i valori iniziali
		Stato = 0;
		haNavi = false;
		lunghezzaNave = -1;
		direzioneOfShip = -1;
	}

	// E' stata colpita questa posizione?
	public boolean checkStrike()
	{
		if (Stato == COLPITO)
			return true;
		else
			return false;
	}

	// E' stata mancata questa posizione?
	public boolean checkMiss()
	{
		if (Stato == MANCATO)
			return true;
		else
			return false;
	}

	// Non e' ancora stata indovinata questa posizione?
	public boolean nonIndovinato()
	{
		if (Stato == NONINDOVINATO)
			return true;
		else
			return false;
	}

	// Segna questa posizione come colpita.
	public void segnaStrike()
	{
		setStato(COLPITO);
	}

	// Segna questa posizione come mancata.
	public void segnaMiss()
	{
		setStato(MANCATO);
	}

	// Ritorna indipendentemente dal fatto che questa posizione abbia o meno una nave.
	public boolean haNavi()
	{
		return haNavi;
	}

	// Imposta il valore se questa posizione ha una nave.
	public void setNave(boolean val)
	{
		this.haNavi = val;
	}

	// Imposta lo stato di questa posizione.
	public void setStato(int Stato)
	{
		this.Stato = Stato;
	}

	// Ottieno lo stato di questa posizione.
	public int getStato()
	{
		return Stato;
	}

	public int getLunghezzaNave()
	{
		return lunghezzaNave; 
	}

	public void setlunghezzaNave(int val)
	{
		lunghezzaNave = val;
	}

	public int getDirezioneNave()
	{
		return direzioneOfShip; 
	}

	public void setDirezioneNave(int val)
	{
		direzioneOfShip = val;
	}
}