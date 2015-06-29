package baze;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

/**
 * <h1>Klasa za konekciju sa bazom podataka i njenu upotrebu</h1>
 * Baza za uspostavljanje kontakta i manipulaciju baze podataka.
 * <h2>Javne metode:</h2>
 * <ul>
 * 	<li>getBaza()</li>
 * 	<li>query(String)</li>
 * 	<li>login(String, String)</li>
 * 	<li>konekcija()</li>
 * </ul>
 * @author Nemanja
 */
public class BazaPodataka implements Baza{
	private static BazaPodataka inst;
	private Connection konekcija;
	
	/**
	 * <h1>Privatni konstruktor za bazu podataka</h1>
	 * Konektuje se na bazu podataka. Klasa se moze<br/>
	 * podesavati plitko - menjanjem prva 3 stringa,<br/>
	 * ili dubinski, menjanjem sadrzaja DriverManager<br/>
	 * metode getConnection.
	 * <h2>Argumenti:</h2>
	 * Nema
	 */
	private BazaPodataka() {
		try{
			String imeBaze = "nrt_112_13_baza";
			String user    = "root";
			String pass    = "";
			
			konekcija = DriverManager.getConnection("jdbc:mysql://localhost/" + imeBaze, user, pass); //konekcija.close();
			System.out.println("Konekcija je uspela!");
		} catch (Exception e) {
			System.err.println("Greska pri konekciji: " + this.getClass().getName() + " : " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Greška pri konekciji sa MySQL bazom.");
		}
	}

	/**
	 * <h1>Pristup privatnom konstruktoru i instanci baze</h1>
	 * Kako bi postojala samo jedna instanca konekcije sa bazom<br/>
	 * podataka, ova metoda proverava stanje staticnog objekta<br/>
	 * ove klase i vraca istu.
	 * <h2>Argumenti:</h2>
	 * Nema
	 * @return
	 * BazaPodataka
	 */
	public static BazaPodataka getBaza() {
		if(inst == null)
			inst = new BazaPodataka();
		return inst;
	}
	
	/**
	 * <h1>Upitna metoda</h1>
	 * Metoda na osnovu upita detektuje tip upita i vraca<br/>
	 * vrednost koja odgovara upitu.
	 * @param sql - SQL upit koji se izvrsava
	 * @return
	 * Objekat tipa ResultSet ili int (mora biti eksplicitno trazen)
	 * @throws SQLException
	 */
	@Override
	public Object query(String sql) throws SQLException{
		Statement state = konekcija.createStatement();
		String[] temp = sql.split(" ", 2);
		if(temp[0].equals("SELECT")) {
			ResultSet rs = state.executeQuery(sql);
			ResultSetMetaData meta = rs.getMetaData();
			if(meta.getColumnCount() <= 0)
				throw new SQLException("Upit je prazan.");
			return rs;
		} else {
			int br = state.executeUpdate(sql);
			return br;
		}
	}

	/**
	 * <h1>Metoda za proveru postojanja naloga</h1>
	 * Metoda proverava postojanje naloga i vraca<br/>
	 * true ili false vrednost u zavisnosti od toga<br/>
	 * da li nalog postoji ili ne.
	 * @param user - String username koji se proverava
	 * @param pass - char[] password koji se proverava
	 * @param pozicija - String pozicija na kojoj se nalazi korisnik
	 * @return
	 * true vrednost ako je konekcija uspesna, false ako nije
	 * @throws SQLException
	 */
	@Override
	public final String[] login(String user, char[] pass, String pozicija) throws SQLException{
		StringBuilder lozinka = new StringBuilder("");
		String[] rez = new String[3];
		for(char c : pass)
			lozinka.append(c);
		Statement state = konekcija.createStatement();
		ResultSet rs = state.executeQuery("SELECT * FROM korisnici WHERE korisnicko_ime = '" + user + "' AND "
																	  + "lozinka        = '" + lozinka.toString() + "' AND "
																	  + "posao          = '" + pozicija + "';");
		if(rs.next()){ 
			System.out.println("Uspesno logovanje na nalog: " + 
								rs.getString("korisnicko_ime") + " (" + 
								rs.getString("ime_prezime") + ") [" + 
								rs.getString("posao") + "]");
			rez[0] = rs.getString("id").toString();
			rez[1] = rs.getString("ime_prezime");
			rez[2] = rs.getString("posao");
			return rez;
		}
		throw new SQLException("Korisnicko ime ili lozinka nisu dobri");
	}

	/**
	 * <h1>Provera trenutne konekcije</h1>
	 * Proverava se trenutna konekcija sa MySQL serverom<br/>
	 * i vraca se rezultat u boolean formi.
	 * @return
	 * true ako postoji, false ako ne
	 * @throws SQLException
	 */
	@Override
	public boolean konekcija() throws SQLException {
		if(konekcija.isValid(0))
			return true;
		return false;
	}

}
