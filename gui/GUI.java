package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import baze.BazaPodataka;

public abstract class GUI {
	protected JFrame frame;
	protected JLayeredPane layeredPane;
	protected BazaPodataka bp;
	
	public GUI(int sirina, int visina) {
		//Pokretanje instance baze podataka
		bp = BazaPodataka.getBaza();
				
		//definisanje glavnog ekrana
		frame = new JFrame();
		{
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			frame.setBounds(d.width/2 - sirina/2, d.height/2 - visina/2, sirina, visina);
		}
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		//Postavljanje lejera za slobodno postavljanje elemenata forme
		layeredPane = new JLayeredPane();
		frame.getContentPane().add(layeredPane, BorderLayout.CENTER);
	}
	
	public void prikazi(boolean stanje) {
		frame.setVisible(stanje);
		if(!stanje)
			frame.dispose();
	}
	
	protected void labela(int x, int y, int sirina, int visina, String tekst) {
		JLabel labela = new JLabel(tekst);
		labela.setBounds(x, y, sirina, visina);
		layeredPane.add(labela);
	}
	
	protected void razdelnaLinija(int x, int y, int sirina, int visina) {
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 213, 414, 2);
		layeredPane.add(separator);
	}
	
	protected JTextField tekstPolje(int x, int y, int sirina, int visina, String toolTip) {
		JTextField txt = new JTextField();
		txt.setToolTipText(toolTip);
		txt.setBounds(x, y, sirina, visina);
		layeredPane.add(txt);
		return txt;
	}
	
	protected JPasswordField tekstPolje(int x, int y, int sirina, int visina, String toolTip, int k) {
		JPasswordField txt = new JPasswordField();
		txt.setToolTipText(toolTip);
		txt.setBounds(x, y, sirina, visina);
		layeredPane.add(txt);
		return txt;
	}
	
	protected void konekcionaLabela(int x, int y, int sirina, int visina, int fontTip, int fontVelicina) {
		JLabel konekcijaLBL = new JLabel("MySQL Konekcija: ...");
		try {
			if(bp.konekcija())
				konekcijaLBL.setText("MySQL Konekcija: USPEŠNA!");
			else {
				konekcijaLBL.setText
				("MySQL Konekcija: NEUSPEŠNA!");
			}
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			frame.dispose();
		}
		konekcijaLBL.setHorizontalAlignment(SwingConstants.CENTER);
		konekcijaLBL.setFont(new Font("Tahoma", fontTip, fontVelicina));
		konekcijaLBL.setBounds(x, y, sirina, visina);
		layeredPane.add(konekcijaLBL);
	}
	
	protected void dugmeZaIzlaz(int x, int y, int sirina, int visina) {
		JButton btn = new JButton("Izlaz");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				frame.dispose();
			}
		});
		btn.setBounds(x, y, sirina, visina);
		layeredPane.add(btn);	
	}

}
