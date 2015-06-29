package osoblje;

import gui.MenadzerGUI;
import gui.Prodavnica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Menadzer extends Osoblje {
	List<Osoblje> zaposleni = new ArrayList<Osoblje>();
	
	public Menadzer(int id, String ime_prezime, String posao) {
		super(id, ime_prezime, posao);
		try {
			ResultSet rs = (ResultSet) bp.query("SELECT * FROM korisnici WHERE posao = 'snabdevac'");
			while(rs.next())
				zaposleni.add(new Snabdevac(rs.getInt("id"), rs.getString("ime_prezime"), "snabdevac"));
			rs = (ResultSet) bp.query("SELECT * FROM korisnici WHERE posao = 'prodavac'");
			while(rs.next())
				zaposleni.add(new Prodavac(rs.getInt("id"), rs.getString("ime_prezime"), "prodavac", rs.getDouble("plata")));
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public ArrayList<Osoblje> getZaposleni() { return (ArrayList<Osoblje>) zaposleni; }
	
	@Override
	public void snimi(String korisnickoIme, char[] lozinka) {
		try{
			StringBuilder sb = new StringBuilder("");
			for(char c : lozinka)
				sb.append(c);
			bp.query("INSERT INTO korisnici ( korisnicko_ime, lozinka, ime_prezime, slika, posao)"
				+ "VALUES ('" + korisnickoIme + "', '" + sb.toString() + "', '" + ime_prezime + "', '" + slika + "', 'menadžer')");
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	@Override
	public String toString() {
		return super.toString() + " <=> Broj zaposlenih = " + zaposleni.size();
	}
	
	@Override
	public void prikaziEkran() {
		Prodavnica prod = new MenadzerGUI(this);
		prod.prikazi(true);
		System.out.println("Prikaz ekrana Prodavnice - Menadzer");
	}

	public void zaposli(String koris, char[] pass, String imeprez, String posao, double plata) {
		try {
			StringBuilder sbPass = new StringBuilder();
			for(char c : pass) sbPass.append(c);
			bp.query("INSERT INTO korisnici (korisnicko_ime, lozinka, ime_prezime, plata, posao) VALUES ("
					+ "'" + koris + "',"
					+ "'" + sbPass.toString() + "',"
					+ "'" + imeprez + "',"
					+ String.valueOf(plata) + ","
					+ "'" + posao + "');");
			if(posao.equals("snabdevac")) {
				zaposleni.add(new Snabdevac(-1, imeprez, "snabdevac"));
			} else {
				zaposleni.add(new Prodavac(-1, imeprez, "prodavac", plata));
			}
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage());			
		}
	}

	public void otpusti(int idnum) {
		try {
			ResultSet rs = (ResultSet) bp.query("SELECT * FROM korisnici WHERE id = " + String.valueOf(idnum));
			if(rs.first()) {
				if(!rs.getString("posao").equals("menadžer")) {
					bp.query("DELETE FROM korisnici WHERE id = " + idnum);
					if(rs.getString("posao").equals("prodavac")) bp.query("DELETE FROM prodaja WHERE id_prodavca = " + idnum);
				} else throw new SQLException("Samo administrator može obrisati korisnika tipa menadžer!");
			} else {
				throw new SQLException("Ne postoji korisnik sa zadatim ID-jem!");
			}
			for(int i = 0; i < zaposleni.size(); i++) {
				if(zaposleni.get(i).getId() == idnum) zaposleni.remove(i);
			}
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public double izracunajProfit() {
		File[] listaFajlova = new File("./racuni").listFiles();
		double ukupniProfit = 0;
		for(File f : listaFajlova) {
			try (BufferedReader br = new BufferedReader(new FileReader(f))){
				String linija = "";
				while((linija = br.readLine()) != null) {
					String[] niz = linija.split(" ");
					if(niz.length == 3 && niz[0].equals("ZA") && niz[1].equals("UPLATU:")) {
						ukupniProfit += Double.parseDouble(niz[2]);
					}
				}
			} catch (IOException e) {
				System.err.println(e.getClass().getName() + " : " + e.getMessage());
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
		return ukupniProfit;
	}
	
}
