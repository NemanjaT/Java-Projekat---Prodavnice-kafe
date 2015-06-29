package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import osoblje.Snabdevac;

public class DodajArtikal extends GUI {
	
	public DodajArtikal(Snabdevac snabdevac, SnabdevacGUI sgui) {
		super(450, 500);
		
		labela(10, 10, 100, 25, "Bar kod (8 cifara)");
		JTextField barkod = tekstPolje(10, 35, 300, 30, "Bar kod");
		labela(10, 85, 100, 25, "Naziv produkta");
		JTextField naziv  = tekstPolje(10, 110, 300, 30, "Naziv");
		labela(10, 160, 100, 25, "Cena");
		JTextField cena   = tekstPolje(10,  185, 300, 30, "Cena");
		labela(10, 235, 100, 25, "Popust");
		JTextField popust   = tekstPolje(10,  260, 300, 30, "Cena");
		labela(10, 310, 100, 25, "Kategorija");
		JTextField kategorija   = tekstPolje(10,  335, 300, 30, "Cena");
		labela(10, 375, 100, 25, "Količina");
		JTextField kolicina   = tekstPolje(10, 400 , 300, 30, "Cena");
		
		//dugme za potvrdu samo-dodavanja
		JRadioButton samoDodaj = new JRadioButton("Samo dodavanje");
		samoDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(naziv.isEnabled()) {
					naziv.setEnabled(false); naziv.setText("/");
					cena.setEnabled(false); cena.setText("1");
					popust.setEnabled(false); popust.setText("0");
					kategorija.setEnabled(false); kategorija.setText("/");
				} else {
					naziv.setEnabled(true); naziv.setText("");
					cena.setEnabled(true); cena.setText("");
					popust.setEnabled(true); popust.setText("");
					kategorija.setEnabled(true); kategorija.setText("");
				}
			}
		});
		samoDodaj.setBounds(300, 10, 125, 25);
		layeredPane.add(samoDodaj);
		
		//dugme za potvrdu
		JButton gotovo = new JButton("Potvrdi");
		gotovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(barkod.getText().equals("")
				|| naziv.getText().equals("")
				|| cena.getText().equals("")
				|| popust.getText().equals("")
				|| kategorija.getText().equals("")
				|| kolicina.getText().equals("")) { 
					System.err.println("Sva polja moraju biti uneta"); 
					JOptionPane.showMessageDialog(null, "Sva polja moraju biti uneta!"); 
					return;
				} else if(String.valueOf(Long.parseLong(barkod.getText())).length() < 8
					   || Double.parseDouble(cena.getText()) <= 0 
					   || Double.parseDouble(popust.getText()) >= Double.parseDouble(cena.getText())
					   || Integer.parseInt(kolicina.getText()) <= 0
					   || (!kategorija.getText().equals("crna") && !kategorija.getText().equals("espreso") 
					   && !kategorija.getText().equals("kapucino") && !kategorija.getText().equals("nes")
					   && (!kategorija.getText().equals("/") && !samoDodaj.isSelected()))) { 
					System.err.println("Neko polje je neispravno uneto!");
					JOptionPane.showMessageDialog(null, "Neko polje je neispravno uneto!");
					return;
				}
				snabdevac.dodajArtikal(Long.parseLong(barkod.getText()), 
									   naziv.getText(), 
									   Double.parseDouble(cena.getText()), 
									   Double.parseDouble(popust.getText()), 
									   kategorija.getText(), 
									   Integer.parseInt(kolicina.getText()),
									   samoDodaj.isSelected());
				sgui.updateTable();
				nazad();
			}
		});
		gotovo.setBounds(325, 400, 100, 30);
		layeredPane.add(gotovo);
	}
	
	private void nazad() {
		this.prikazi(false);
	}
	
}
