package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import osoblje.Menadzer;
import osoblje.Osoblje;
import osoblje.Prodavac;

public class MenadzerGUI extends Prodavnica {
	private JTable table;
	private DefaultTableModel model;
	private Menadzer menadzer;
	
	public MenadzerGUI(Menadzer menadzer) {
		super();
		this.menadzer = menadzer;
		
		//Informacije korisnika
		labela(590, 110, 110, 14, String.valueOf(menadzer.getId()));
		labela(590, 135, 110, 14, menadzer.getIme_prezime());
		labela(590, 160, 110, 14, menadzer.getPosao());
		
		//tabela zaposlenih
		table = new JTable();
		table.setShowVerticalLines(false);
		table.setEnabled(false);
		model = new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Ime i Prezime", "Pozicija", "Plata", "Bonus" });
		table.setModel(model);
		table.setBounds(10, 110, 500, 350);
		layeredPane.add(table);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 110, 500, 350);
		layeredPane.add(scrollPane);
		
		JButton btnZaposli = new JButton("Zaposli");
		btnZaposli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] opcije = new String[] {"prodavac", "snabdevac"};
				int opcija = JOptionPane.showOptionDialog(null, "Izaberite vrstu radnika:", 
							"Vrsta radnika", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
							null, opcije, opcije[0]);
				if(opcija != -1) {
					Zaposljavanje zap = new Zaposljavanje(opcije[opcija], menadzer);
					zap.prikazi(true);
				}
			}
		});
		btnZaposli.setBounds(535, 185, 110, 23);
		layeredPane.add(btnZaposli);
		
		JButton btnOtpusti = new JButton("Otpusti");
		btnOtpusti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String id = JOptionPane.showInputDialog("ID korisnika:");
				int idnum = ((id == null || id.equals("")) ? -1 : Integer.parseInt(id));
				if(idnum != -1) {
					menadzer.otpusti(idnum);
				}
			}
		});
		btnOtpusti.setBounds(660, 185, 110, 23);
		layeredPane.add(btnOtpusti);
		
		JButton btnNajveiBonus = new JButton("Pretraga po Bonusu");
		btnNajveiBonus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pretraga(1);
			}
		});
		btnNajveiBonus.setBounds(535, 219, 235, 23);
		layeredPane.add(btnNajveiBonus);
		
		JButton btnListajZaposlene = new JButton("Listaj zaposlene");
		btnListajZaposlene.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pretraga(0);
			}
		});
		btnListajZaposlene.setBounds(535, 253, 235, 23);
		layeredPane.add(btnListajZaposlene);
		
		JButton btnProfit = new JButton("Profit");
		btnProfit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double profit = menadzer.izracunajProfit();
				JOptionPane.showMessageDialog(null, "Ukupni profit: " + profit);
			}
		});
		btnProfit.setBounds(535, 287, 235, 23);
		layeredPane.add(btnProfit);
	}
	
	private void pretraga(int opcija) {
		while(model.getRowCount() != 0) model.removeRow(0);
		ArrayList<Osoblje> osoblje = menadzer.getZaposleni();
		for(Osoblje o : osoblje) {
			switch(opcija){
			case 0:
				if(o.getPosao().equals("snabdevac")) {
					String[] temp = new String[] { String.valueOf(o.getId()), o.getIme_prezime(), o.getPosao() };
					model.addRow(temp);
				}
			default:
				if(o.getPosao().equals("prodavac")) {
					String[] temp = new String[] { String.valueOf(o.getId()), o.getIme_prezime(), o.getPosao(), String.valueOf(((Prodavac) o).getPlata()), 
							String.format("%.2f", ((Prodavac) o).getBonus())};
					model.addRow(temp);
				}
			}
		}
	}
}
