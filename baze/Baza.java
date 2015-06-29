package baze;

import java.sql.SQLException;

/**
 * <h1>Interfejs za bazu podataka</h1>
 * Interfejs za bazu podataka, u slučaju daljeg<br/>
 * unapredjivanja baze podataka.
 * <h2>Metode:</h2>
 * <ul>
 * 	<li>Object query(String)</li>
 * 	<li>String[] login(String, char[], String)</li>
 * 	<li>boolean konekcija()</li>
 * </ul>
 * @author Nemanja
 */
public interface Baza {
	/**
	 * <h1>Query interfejs metoda</h1>
	 * <b>Metoda mora biti preklopljena</b>. sluzi<br/>
	 * za preklapanje metode za upit. U planu je da se<br/>
	 * napravi tester koji detektuje koji je upit u pitanju.
	 * @param sql - String sql upita
	 * @return Objekat tipa ResultSet ili Integer
	 * @throws SQLException
	 */
	public Object query(String sql) throws SQLException;
	/**
	 * <h1>Login interfejs metoda</h1>
	 * Metoda koja se preklapa tako da ispita login informacije<br/>
	 * korisnika, tako sto se konketuje na MySQL bazu.
	 * @param user - Korisnicko ime
	 * @param pass - lozinka (char[], a ne String zbog kratkotrajnosti char[]-a (sigurnost))
	 * @param pozicija - pozicija na kojoj se nalazi korisnik (npr. menadzer, prodavac itd.)
	 * @return Vraca povratne String informaicje
	 * @throws SQLException
	 */
	public String[] login(String user, char[] pass, String pozicija) throws SQLException;
	/**
	 * <h1>Konekcija interfejs metoda</h1>
	 * Vraca trenutno stanje konekcije sa MySQL serverom.
	 * @return boolean stanje konekcije.
	 * @throws SQLException
	 */
	public boolean konekcija() throws SQLException;
}
