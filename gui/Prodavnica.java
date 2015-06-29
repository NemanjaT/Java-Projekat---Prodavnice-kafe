package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public abstract class Prodavnica extends GUI {
	
	public Prodavnica() {
		super(800, 600);
		
		//Dodavanje LOGO slike
		BufferedImage slika;
		try{
			slika = ImageIO.read(new File("images/logo.png"));
			JLabel label = new JLabel(new ImageIcon(slika));
			label.setBounds(10, 10, 650, 90);
			layeredPane.add(label);
		} catch(IOException e) {}
		
		//Dodavanje NALOG slike
		try {
			BufferedImage slika2;
			slika2 = ImageIO.read(new File("images/user.png"));
			JLabel label = new JLabel(new ImageIcon(slika2));
			label.setBounds(670, 10, 100, 90);
			layeredPane.add(label);
		} catch(IOException e) {}
		
		//Dugme za izlaz iz programa
		dugmeZaIzlaz(670, 525, 100, 25);
		//Dugme za odjavu
		JButton odjava = new JButton("Odjavi se");
		odjava.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Login.main();
				prikazi(false);
			}
		});
		odjava.setBounds(670, 489, 100, 25);
		layeredPane.add(odjava);
		
		//Labele naloga:
		labela(535, 110, 50, 15, "ID:");
		labela(535, 135, 50, 15, "Ime:");
		labela(535, 160, 50, 15, "Pozicija:");
	}
}
