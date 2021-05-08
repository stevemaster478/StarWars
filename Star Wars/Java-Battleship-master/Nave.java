public class Nave
{
	/* Variabili di istanza */
	private int riga;
	private int colonna;
	private int lunghezza;
	private int direzione;

	// Costanti di direzione
	public static final int NONSETTATO = -1;
	public static final int ORIZZONTALE = 0;
	public static final int VERTICALE = 1;

	// Construttore
	public Nave(int lunghezza)
	{
		this.lunghezza = lunghezza;
		this.riga = -1;
		this.colonna = -1;
		this.direzione = NONSETTATO;
	}

	// La posizione è stata iniziata
	public boolean laPosizione()
	{
		if (riga == -1 || colonna == -1)
			return false;
		else
			return true;
	}

	// La direzione è stata iniziata
	public boolean laDirezione()
	{
		if (direzione == NONSETTATO)
			return false;
		else
			return true;
	}

	// Imposta la posizione della nave
	public void setPosizione(int riga, int colonna)
	{
		this.riga = riga;
		this.colonna = colonna;
	}

	// Imposta la direzione della nave
	public void setDirezione(int direzione)
	{
		if (direzione != NONSETTATO && direzione != ORIZZONTALE && direzione != VERTICALE)
			throw new IllegalArgumentException("Direzione invalida. Dev'essere -1, 0, o 1");
		this.direzione = direzione;
	}

	// Getter per il valore di riga
	public int getRiga()
	{
		return riga;
	}

	// Getter per il valore della colonna
	public int getColonna()
	{
		return colonna;
	}

	// Getter per la lunghezza della nave
	public int getLunghezza()
	{
		return lunghezza;
	}

	// Getter per la direzione
	public int getDirezione()
	{
		return direzione;
	}

	//Metodo Helper per avere la stringa dalla direzione
	private String direzioneToString()
	{
		if (direzione == NONSETTATO)
			return "NON SETTATO";
		else if (direzione == ORIZZONTALE)
			return "ORIZZONTALE";
		else
			return "VERTICALE";
	}

	// toString for quest'astronave
	public String toString()
	{
		return "L'astronave: " + getRiga() + ", " + getColonna() + " con lunghezza " + getLunghezza() + " e direzione " + direzioneToString();
	}
}