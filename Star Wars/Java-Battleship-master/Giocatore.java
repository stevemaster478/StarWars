public class Giocatore
{
	// Queste sono le lunghezze di tutte le navi.
	private static final int[] NAVE_lunghezzaS = {2, 3, 3, 4, 5};
	private static final int NUM_DI_NAVI = 5;

	public Nave[] navi;
	public griglia grigliaGiocatore;
	public griglia oppgriglia;

	public Giocatore()
	{
		if (NUM_DI_NAVI != 5) // Il numero di navi dev'essere di 5
		{
			throw new IllegalArgumentException("Errore! Il numero delle navi dev'essere di 5");
		}

		navi = new Nave[NUM_DI_NAVI];
		for (int i = 0; i < NUM_DI_NAVI; i++)
		{
			Nave tempNave = new Nave(NAVE_lunghezzaS[i]);
			navi[i] = tempNave;
		}

		grigliaGiocatore = new griglia();
		oppgriglia = new griglia();
	}

	public void aggiungiNave()
	{
		for (Nave s: navi)
		{
			grigliaGiocatore.aggiungiNave(s);
		}
	}

	public int numNaviRimaste()
	{
		int contatore = 5;
		for (Nave s: navi)
		{
			if (s.laPosizione() && s.laDirezione())
				contatore--;
		}

		return contatore;

	}

	public void scegliPosizioneNave(Nave s, int riga, int colonna, int direzione)
	{
		s.setPosizione(riga, colonna);
		s.setDirezione(direzione);
		grigliaGiocatore.aggiungiNave(s);
	}
}