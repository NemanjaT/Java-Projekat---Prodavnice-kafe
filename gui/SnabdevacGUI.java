package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import osoblje.Snabdevac;
import prodavnica.Artikal;

public class SnabdevacGUI extends Prodavnica {
	private Snabdevac snabdevac;
	private JTable table;
	private DefaultTableModel model;

	public SnabdevacGUI(Snabdevac snabdevac) {
		super();
		this.snabdevac = snabdevac;
		//Informacije korisnika
		labela(590, 110, 110, 14, String.valueOf(snabdevac.getId()));
		labela(590, 135, 110, 14, snabdevac.getIme_prezime());
		labela(590, 160, 110, 14, snabdevac.getPosao());
		
		// tabela
		table();
		
		//dodaj artikal dugme
		JButton dodaj = new JButton("Dodaj artikal");
		dodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				DodajArtikal da = new DodajArtikal(snabdevac, getThis());
				da.prikazi(true);
			}
		});
		dodaj.setBounds(10, 525, 200, 25);
		layeredPane.add(dodaj);
	}
	
	private void table() {
		table = new JTable();
		table.setShowVerticalLines(false);
		table.setEnabled(false);
		model = new DefaultTableModel(new Object[][] {}, new String[] { "Bar kod", "Naziv", "Cena", "Kategorija", "Količina" });
		table.setModel(model);
		table.setBounds(10, 110, 500, 350);
		Iterator<Artikal> artikli = snabdevac.getArtikli().iterator(); //Popunjavanje tabele
		while(artikli.hasNext()) {
			Artikal a = artikli.next();
			String[] s = new String[] {String.valueOf(a.getBarKod()), 
									   a.getNaziv(), 
									   String.valueOf((a.getCena() - a.getPopust())),
									   a.getKategorija(),
									   String.valueOf(a.getKolicina()) };
			model.addRow(s);
		}
		layeredPane.add(table);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 110, 500, 350);
		layeredPane.add(scrollPane);
	}
	
	public void updateTable() {
		model = new DefaultTableModel(new Object[][] {}, new String[] { "Bar kod", "Naziv", "Cena", "Kategorija", "Količina" });
		table.setModel(model);
		while(model.getRowCount() > 0) model.removeRow(0);
		Iterator<Artikal> artikli = snabdevac.getArtikli().iterator(); //Popunjavanje tabele
		while(artikli.hasNext()) {
			Artikal a = artikli.next();
			String[] s = new String[] {String.valueOf(a.getBarKod()), 
									   a.getNaziv(), 
									   String.valueOf((a.getCena() - a.getPopust())),
									   a.getKategorija(),
									   String.valueOf(a.getKolicina()) };
			model.addRow(s);
		}
	}
	private SnabdevacGUI getThis() { return this; }
}
