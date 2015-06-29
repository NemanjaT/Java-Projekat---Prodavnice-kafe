package prodavnica;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JOptionPane;

import baze.BazaPodataka;

public class Artikal {
	private long barKod;
	private String naziv;
	private double cena;
	private double popust;
	private String kategorija;
	private int kolicina;
	private static BazaPodataka bp;
	
	public long getBarKod()       { return barKod;     }
	public String getNaziv()      { return naziv;      }
	public double getCena()       { return cena;       }
	public double getPopust()     { return popust;     }
	public String getKategorija() { return kategorija; }
	public int getKolicina()      { return kolicina;   }
	
	public void setKolicina(int kolicina) { this.kolicina = kolicina; } 
	
	private Artikal(ResultSet rs) throws SQLException {
		this(rs.getLong("barkod"), rs.getString("naziv"), rs.getDouble("cena"), rs.getDouble("popust"), rs.getString("kategorija"), rs.getInt("kolicina"));
	}
	
	private Artikal(long barKod, String naziv, double cena, double popust, String kategodija, int kolicina) {
		this.barKod     = barKod;
		this.naziv      = naziv;
		this.cena       = cena;
		this.popust     = popust;
		this.kategorija = kategodija;
		this.kolicina   = kolicina;
	}
	
	public void snimi() {
		bp = BazaPodataka.getBaza();
		try {
			int snimljeno = update(barKod, kolicina);
			if(snimljeno <= 0)
				bp.query("INSERT INTO artikli ( barkod, naziv, cena, popust, kategorija, kolicina )"
						+ "VALUES (" + barKod + ", '" + naziv + "', " + cena + ", " + popust + ", '" + kategorija + "', " + kolicina + ")");
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	public static Artikal snimi(long barKod, String naziv, double cena, double popust, String kategorija, int kolicina, boolean samoUpdate) {
		bp = BazaPodataka.getBaza();
		try {
			int snimljeno = update(barKod, kolicina);
			if(snimljeno <= 0 && samoUpdate)
				throw new SQLException("Nije promenjeno ni jedno polje.");
			snimljeno = (samoUpdate == true ? 1 : snimljeno);
			if(snimljeno <= 0)
				bp.query("INSERT INTO artikli ( barkod, naziv, cena, popust, kategorija, kolicina )"
						+ "VALUES (" + barKod + ", '" + naziv + "', " + cena + ", " + popust + ", '" + kategorija + "', " + kolicina + ")");
			return new Artikal(barKod, naziv, cena, popust, kategorija, kolicina);
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage());
			System.exit(0);
		}
		return null;
	}
	
	public static Artikal ucitaj(long barKod) {
		bp = BazaPodataka.getBaza();
		try {
			ResultSet rs = (ResultSet) bp.query("SELECT * FROM artikli WHERE barkod = " + barKod);
			rs.first();
			return new Artikal(rs.getLong("barkod"), rs.getString("naziv"), rs.getDouble("cena"), rs.getDouble("popust"), rs.getString("kategorija"), rs.getInt("kolicina"));
		} catch(SQLException e) {
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return null;
	}
	
	public static Collection<Artikal> ucitajSve(String ... whereUpit) {
		bp = BazaPodataka.getBaza();
		try {
			String s = (whereUpit.length == 0 ? "" : " WHERE " + whereUpit[0]);
			Collection<Artikal> artikli = new ArrayList<Artikal>();
			ResultSet rs = (ResultSet) bp.query("SELECT * FROM artikli" + s);
			while(rs.next())
				artikli.add(new Artikal(rs));
			return artikli;
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return new ArrayList<Artikal>();
	}
	
	public static void oduzmi(long barKod, int kolicina) {
		try {
			bp.query("UPDATE artikli SET kolicina = kolicina - " + kolicina + " WHERE barkod = " + String.valueOf(barKod));
			ResultSet rs = (ResultSet) bp.query("SELECT kolicina FROM artikli WHERE barkod = " + String.valueOf(barKod));
			if(rs.next()) {
				int kol = rs.getInt("kolicina");
				if(kol <= 0)
					bp.query("DELETE FROM artikli WHERE barkod = " + barKod);
			}
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	@Override
	public String toString() {
		return "Artikal [barKod=" + barKod + ", naziv=" + naziv + ", cena="
				+ cena + ", popust=" + popust + ", kategorija=" + kategorija
				+ ", kolicina=" + kolicina + "]";
	}
	
	private static int update(long barKod, int kolicina) {
		try{
			int p = (int) bp.query("UPDATE artikli SET kolicina = kolicina + " + kolicina + " WHERE  barkod = " + String.valueOf(barKod));
			return p;
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return 0;
	}
	
}
