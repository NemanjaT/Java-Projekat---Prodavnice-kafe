package osoblje;

import gui.Prodavnica;
import gui.SnabdevacGUI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import prodavnica.Artikal;

public final class Snabdevac extends Osoblje{
	Collection<Artikal> artikli = new ArrayList<Artikal>();
	
	public Snabdevac(int id, String ime_prezime, String posao) {
		super(id, ime_prezime, posao);
		dodajArtikle();
	}
	
	public void dodajArtikal(long barKod, String naziv, double cena, double popust, String kategorija, int kolicina, boolean samoUpdate) {
		Artikal.snimi(barKod, naziv, cena, popust, kategorija, kolicina, samoUpdate);
		for(int i = 0; i < artikli.size(); i++) {
			String[] stemp = new String[] { String.valueOf(((ArrayList<Artikal>) artikli).get(i).getBarKod()), String.valueOf(barKod) };
			//System.out.println(String.valueOf(((ArrayList<Artikal>) artikli).get(i).getBarKod()) + " =?= " + String.valueOf(barKod) + " : " + stemp[0].equals(stemp[1]));
			if(stemp[0].equals(stemp[1])) ((ArrayList<Artikal>) artikli).remove(i);
		}
		artikli.add(Artikal.ucitaj(barKod));
	}
	public void dodajArtikal(Artikal artikal) {
		artikal.snimi();
		for(int i = 0; i < artikli.size(); i++) {
			String[] stemp = new String[] { String.valueOf(((ArrayList<Artikal>) artikli).get(i).getBarKod()), String.valueOf(artikal.getBarKod()) };
			if(stemp[0].equals(stemp[1])) artikli.remove(i);
		}
		artikli.add(artikal);
	}
	public void dodajArtikal(long barKod) {
		artikli.add(Artikal.ucitaj(barKod));
	}
	public void dodajArtikle() {
		artikli.clear();
		artikli = Artikal.ucitajSve();
	}
	
	public ArrayList<Artikal> getArtikli() {
		return (ArrayList<Artikal>) artikli;
	}
	
	@Override
	public void snimi(String korisnickoIme, char[] lozinka) {
		try{
			StringBuilder sb = new StringBuilder("");
			for(char c : lozinka)
				sb.append(c);
			bp.query("INSERT INTO korisnici ( korisnicko_ime, lozinka, ime_prezime, slika, posao)"
				+ "VALUES ('" + korisnickoIme + "', '" + sb.toString() + "', '" + ime_prezime + "', '" + slika + "', 'snabdevač')");
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
		}
	}
	
	@Override
	public String toString() {
		return super.toString() + " <=> Broj artiklala: " + artikli.size();
	}
	
	@Override
	public void prikaziEkran() {
		Prodavnica prod = new SnabdevacGUI(this);
		prod.prikazi(true);
		System.out.println("Prikaz ekrana Prodavnice - Snabdevac");
	}
}
