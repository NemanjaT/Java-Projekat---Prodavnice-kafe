package osoblje;

import baze.BazaPodataka;

public abstract class Osoblje {
	protected static BazaPodataka bp;
	protected int id;
	protected String ime_prezime;
	protected String slika;
	protected String posao;
	
	public Osoblje(int id, String ime_prezime, String posao) {
		this.id = id;
		this.ime_prezime = ime_prezime;
		this.posao = posao;
		bp = BazaPodataka.getBaza();
	}
	
	public abstract void snimi(String korisnickoIme, char[] lozinka);
	
	public abstract void prikaziEkran();
	
	public int getId() {
		return id;
	}

	public String getIme_prezime() {
		return ime_prezime;
	}

	public String getPosao() {
		return posao;
	}

	@Override
	public String toString() {
		return ime_prezime + " (" + posao + ")";
	}
}
