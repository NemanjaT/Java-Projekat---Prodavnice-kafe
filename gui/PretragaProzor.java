package gui;

import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import prodavnica.Artikal;

public class PretragaProzor extends GUI {
	private DefaultTableModel model;
	private JTable table;
	
	public PretragaProzor(String pretraga, String vrednost) {
		super(615, 540);
		
		table = new JTable();
		table.setShowVerticalLines(false);
		table.setEnabled(false);
		model = new DefaultTableModel(new Object[][] {}, new String[] { "Bar kod", "Naziv", "Cena", "Popust", "Kategorija", "Količina" });
		table.setModel(model);
		table.setBounds(0, 0, 600, 500);
		layeredPane.add(table);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, 600, 500);
		layeredPane.add(scrollPane);
		ArrayList<Artikal> artikli = (ArrayList<Artikal>) Artikal.ucitajSve(pretraga + vrednost);
		for(int i = 0; i < artikli.size(); i++) {
			String[] artikalString = new String[] {
					String.valueOf(artikli.get(i).getBarKod()),
					artikli.get(i).getNaziv(),
					String.valueOf(artikli.get(i).getCena()),
					String.valueOf(artikli.get(i).getPopust()),
					artikli.get(i).getKategorija(),
					String.valueOf(artikli.get(i).getKolicina())
			};
			model.addRow(artikalString);
		}
		
	}
	
}
