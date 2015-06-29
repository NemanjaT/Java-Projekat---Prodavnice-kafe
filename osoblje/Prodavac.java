package osoblje;

import gui.ProdavacGUI;
import gui.Prodavnica;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import prodavnica.Artikal;

public final class Prodavac extends Osoblje{
	private double plata;
	private double bonus;
	
	public Prodavac(int id, String ime_prezime, String posao, double plata) {
		super(id, ime_prezime, posao);
		this.plata = ( plata < 23000 ? 23000 : plata );
		bonus = racunBonus();
	}
	
	public void updatePlata() {
		try {
			ResultSet rs = (ResultSet) bp.query("SELECT plata FROM korisnici WHERE id = " + String.valueOf(id));
			if(rs.first())
				plata = rs.getDouble("plata");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public double getPlata() { return plata; }
	public double getBonus() { return bonus; }
	
	public static Artikal preuzmiArtikal(String barKod) {
		long temp = Long.parseLong(barKod);
		Artikal art = Artikal.ucitaj(temp);
		return art;
	}
	
	public void stampajRacun(double suma, double uplata, List<Artikal> artikli) {
		File[] listaFajlova = new File("./racuni").listFiles();
		File pronadjeni = null;
		int i = 0;
		for(int n = 0; n < listaFajlova.length; n++) {
			if(listaFajlova[n].getName().equals(i + ".txt"))
				i++;
		}
		pronadjeni = new File("./racuni/" + i + ".txt");
		try (BufferedWriter bw = new BufferedWriter(new java.io.FileWriter(pronadjeni))) {
			bw.write("=============================\n");
			bw.write("TOZINA PRODAVNICA KAFE D.O.O\n");
			bw.write("Zemlja Nedodjije 14\n");
			bw.write("Beograd\n");
			bw.write("\n");
			bw.write("PIB: 123456789\n");
			bw.write("IBFM: AG123456\n");
			bw.write("-----------------------------\n");
			bw.write(id + " - " + ime_prezime + "\n");
			bw.write("-----------------------------\n");
			for(int n = 0; n < artikli.size(); n++) {
				bw.write(artikli.get(n).getNaziv() + "\n");
				bw.write("\t" + artikli.get(n).getKolicina() + "x " + artikli.get(n).getCena() + "\n");
			}
			bw.write("-----------------------------\n");
			bw.write("ZA UPLATU: " + suma + "\n");
			bw.write("GOTOVINA : " + uplata + "\n");
			bw.write("POVRACAJ : " + (uplata - suma) + "\n");
			bw.write("=============================");
		} catch (IOException e) {
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void snimiRacun(double suma) {
		try {
			double bonus = (suma > 10000 ? 0.2 : (suma > 5000 ? 0.1 : 0.05));
			bp.query("INSERT INTO prodaja ( id_prodavca, suma_prodaje, bonus_prodavca ) "
					+"VALUES (" + String.valueOf(id) + ", "
					+ suma + ", "
					+ bonus + ")");
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	@Override
	public void snimi(String korisnickoIme, char[] lozinka) {
		try{
			StringBuilder sb = new StringBuilder("");
			for(char c : lozinka)
				sb.append(c);
			bp.query("INSERT INTO korisnici ( korisnicko_ime, lozinka, ime_prezime, slika, posao)"
				+ "VALUES ('" + korisnickoIme + "', '" + sb.toString() + "', '" + ime_prezime + "', '" + slika + "', 'prodavac')");
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	@Override
	public String toString() {
		return super.toString() + " <=> plata: " + plata + ", bonus: " + bonus;
	}
	
	@Override
	public void prikaziEkran() {
		Prodavnica prod = new ProdavacGUI(this);
		prod.prikazi(true);
		System.out.println("Prikaz ekrana Prodavnice - Prodavac");
	}
	
	private double racunBonus() {
		try {
			ResultSet rs = (ResultSet) bp.query("SELECT SUM(bonus_prodavca) AS suma FROM prodaja WHERE id_prodavca = " + id + " GROUP BY id_prodavca");
			if(rs.first())
				return rs.getDouble("suma");
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return 0;
	}
}
