package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import osoblje.Menadzer;

public class Zaposljavanje extends GUI {

	public Zaposljavanje(String posao, Menadzer menadzer) {
		super(450, 420);
		
		labela(10, 10, 100, 25, "Korisničko ime");
		JTextField koris = tekstPolje(10, 35, 300, 30, "Korisničko ime");
		labela(10, 85, 100, 25, "Lozinka");
		JPasswordField loz1  = tekstPolje(10, 110, 300, 30, "Lozinka", 0);
		labela(10, 160, 100, 25, "Ponovite Lozinku");
		JPasswordField loz2   = tekstPolje(10,  185, 300, 30, "Lozinka", 0);
		labela(10, 235, 100, 25, "Ime i prezime");
		JTextField imeprez   = tekstPolje(10,  260, 300, 30, "Ime/Prezime");
		labela(10, 310, 100, 25, "Plata");
		JTextField plata   = tekstPolje(10, 335, 300, 30, "Plata");
		
		JButton gotovo = new JButton("Potvrdi");
		gotovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				System.out.println("Provera validnosti sifre: " + proveraSifre(loz1.getPassword(), loz2.getPassword()));
				double p = ((plata.getText() == null || plata.getText().equals("")) ? 0 : Double.parseDouble(plata.getText()));
				if(proveraSifre(loz1.getPassword(), loz2.getPassword()) && p >= 23000) {
					menadzer.zaposli(koris.getText(), loz1.getPassword(), imeprez.getText(), posao, p);
					getThis().prikazi(false);
				}
			}
		});
		gotovo.setBounds(325, 335, 100, 30);
		layeredPane.add(gotovo);
	}
	
	private boolean proveraSifre(char[] pass1, char[] pass2) {
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		for(char c : pass1) sb1.append(c);
		for(char c : pass2) sb2.append(c);
		if(sb1.toString().equals(sb2.toString())) return true;
		return false;
	}
	
	private Zaposljavanje getThis() { return this; }
}
