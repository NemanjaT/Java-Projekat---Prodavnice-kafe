package gui;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JPasswordField;

import java.awt.Font;

import javax.swing.JRadioButton;

import osoblje.Menadzer;
import osoblje.Osoblje;
import osoblje.Prodavac;
import osoblje.Snabdevac;

public class Login extends GUI{
	
	private JTextField userTXT;
	private JPasswordField passTXT;
	private JRadioButton[] radioDugmad;
	
	public static void main(String ... args) {
		try {
			Login pocetni = new Login();
			pocetni.prikazi(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
		}
	}
	
	public Login() {
		super(450, 310);                                                 //Pozivanje superklase GUIFunkcije
		userTXT = tekstPolje(67, 37, 300, 25, "Korisničko ime");         //username i password polja
		passTXT = tekstPolje(67, 97, 300, 25, "Lozinka", 0);          
		labela(77, 11, 200, 25, "Korisničko ime");                       //username i password labela
		labela(77, 73, 200, 25, "Lozinka");                           
		radioDugmad = new JRadioButton[3];                               //radio dugmad
		{
			int prviX = 35, y = 123, sirina = 114, visina = 25;
			radioDugmad[0] = new JRadioButton("snabdevac");
			radioDugmad[0].setBounds(prviX + 135*2, y, sirina, visina);
			radioDugmad[1] = new JRadioButton("prodavac");
			radioDugmad[1].setBounds(prviX + 135, y, sirina, visina);
			radioDugmad[2] = new JRadioButton("menadžer");
			radioDugmad[2].setBounds(prviX, y, sirina, visina);
			radioDugmad[2].setSelected(true);
		}
		ButtonGroup radioDugmadi = new ButtonGroup();
		for(int i = 0; i < radioDugmad.length; i++) {
			radioDugmadi.add(radioDugmad[i]);
			layeredPane.add(radioDugmad[i]);
		}
		dugmeZaPrijavu(77, 153, 279, 23);
		dugmeZaIzlaz(152, 179, 129, 23);
		razdelnaLinija(10, 213, 414, 2);
		konekcionaLabela(10, 213, 414, 47, Font.BOLD, 16);
	}
	
	private void dugmeZaPrijavu(int x, int y, int sirina, int visina) {
		JButton btn = new JButton("Prijava");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				try{
					String pozicija = "";
					for(int i = 0; i < radioDugmad.length; i++)
						if(radioDugmad[i].isSelected())
							pozicija = radioDugmad[i].getText();
					String[] login = bp.login(userTXT.getText(), passTXT.getPassword(), pozicija);
					if(login.length > 0) {
						int id = Integer.parseInt(login[0]);
						Osoblje osoba;
						switch(pozicija) {
						case "snabdevac":
							System.out.println("Pravim objekat Snabdevac");
							osoba = new Snabdevac(id , login[1], login[2]);
							break;
						case "prodavac":
							System.out.println("Pravim objekat Prodavac");
							osoba = new Prodavac(id, login[1], login[2], 16000);
							((Prodavac)osoba).updatePlata();
							break;
						default:
							System.out.println("Pravim objekat Menadzer");
							osoba = new Menadzer(id, login[1], login[2]);
						}
						osoba.prikaziEkran();
						frame.dispose();
						System.out.println(osoba);
						System.out.println("-------------------------------------------");
					}
				} catch(SQLException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					passTXT.setText("");
				}
			}
		});
		btn.setBounds(x, y, sirina, visina);
		layeredPane.add(btn);
	}
	
}
