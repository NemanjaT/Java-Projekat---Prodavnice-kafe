package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import osoblje.Prodavac;
import prodavnica.Artikal;

public class ProdavacGUI extends Prodavnica {
	private Prodavac prodavac;
	private JTable table;
	private DefaultTableModel model;
	private double ukupniRacun;
	private JLabel ukupniLabela;
	private List<Artikal> artikli = new ArrayList<Artikal>();
	
	public ProdavacGUI(Prodavac prodavac) {
		super();
		ukupniRacun = 0;
		this.prodavac = prodavac;
		
		//Informacije korisnika
		labela(590, 110, 110, 14, String.valueOf(prodavac.getId()));
		labela(590, 135, 110, 14, prodavac.getIme_prezime());
		labela(590, 160, 110, 14, prodavac.getPosao());
		labela(10, 460, 110, 14, "Ukupni račun:");
		ukupniLabela = new JLabel(String.valueOf(ukupniRacun));
		ukupniLabela.setBounds(120, 460, 110, 14);
		layeredPane.add(ukupniLabela);
		
		table = new JTable();
		table.setShowVerticalLines(false);
		table.setEnabled(false);
		model = new DefaultTableModel(new Object[][] {}, new String[] { "Bar kod", "Naziv", "Cena", "Kategorija", "Količina" });
		table.setModel(model);
		table.setBounds(10, 110, 500, 350);
		layeredPane.add(table);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 110, 500, 350);
		layeredPane.add(scrollPane);
		
		//dodaj produkt u listu.
		dodajProdukt();
		
		//Otvori prozor za pretragu proizvoda po kategoriji
		JButton btnPretraga = new JButton("Pretraga proizvoda");
		btnPretraga.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				String[] opcije = new String[] {"kapucino", "nes", "crna", "espreso"};
				int opcija = JOptionPane.showOptionDialog(null, "Izaberite kategoriju:", 
						 "Kategorija", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
						 null, opcije, opcije[0]);
				if(opcija != -1) {
					PretragaProzor pp = new PretragaProzor("kategorija", " = '" + opcije[opcija] + "'");
					pp.prikazi(true);
				}
			}
		});
		btnPretraga.setBounds(535, 200, 210, 23);
		layeredPane.add(btnPretraga);
		
		JButton btnPlata = new JButton("Trenutna plata");
		btnPlata.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				JOptionPane.showMessageDialog(null, "Trenutna plata: " + (prodavac.getBonus() + prodavac.getPlata()));
			}
		});
		btnPlata.setBounds(535, 290, 210, 23);
		layeredPane.add(btnPlata);
		
		//Otvori prozor za pretragu proizvoda po popustu
		JButton btnNaPopustu = new JButton("Proizvodi na popustu");
		btnNaPopustu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				PretragaProzor pp = new PretragaProzor("popust", " > 0");
				pp.prikazi(true);
			}
		});
		btnNaPopustu.setBounds(535, 234, 209, 23);
		layeredPane.add(btnNaPopustu);
		
		//Isplati racun
		JButton btnIsplati = new JButton("Isplati");
		btnIsplati.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				isplati();
			}
		});
		btnIsplati.setBounds(10, 526, 115, 23);
		layeredPane.add(btnIsplati);
	}
	
	private void updateTable(Artikal artikal, String kolicina) {
		String[] s = new String[] {
				String.valueOf(artikal.getBarKod()), 
				artikal.getNaziv(), 
				String.valueOf((artikal.getCena() - artikal.getPopust())),
				artikal.getKategorija(),
				String.valueOf(artikal.getKolicina())
		};
		ukupniRacun += (double)Integer.parseInt(kolicina) * ( artikal.getCena() - artikal.getPopust() );
		ukupniLabela.setText(String.valueOf(ukupniRacun));
		model.addRow(s);
	}
	private void deleteTable() {
		while(model.getRowCount() > 0) 
			model.removeRow(0);
	}

	private void dodajProdukt() {
		JButton btnProdaj = new JButton("Dodaj na kasu");
		btnProdaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				String barKod = "", kolicina = "";
				Artikal artikal;
				while(true) {
					barKod = JOptionPane.showInputDialog("Unesite bar kod produkta:");
					barKod = ((barKod == null || barKod.equals("")) ? "0" : barKod.toString());
					if(barKod.equals("0")) break;
					if(String.valueOf(Long.parseLong(barKod)).length() == 8) {
						kolicina = JOptionPane.showInputDialog("Unesite količinu:");
						kolicina = ((kolicina == null || kolicina.equals("")) ? "0" : kolicina.toString());
						if(Integer.parseInt(kolicina) > 0 && Integer.parseInt(kolicina) <= brojProizvoda(barKod)) break;
					}
					JOptionPane.showMessageDialog(null, "Nije ispravno unet bar kod ili kolicina!");
				}
				if(barKod != null && !barKod.equals("0")) {
					artikal = Prodavac.preuzmiArtikal(barKod);
					artikli.add(artikal);
					artikli.get(artikli.size() - 1).setKolicina(Integer.parseInt(kolicina));
					updateTable(artikal, kolicina);
				}
			}
		});
		btnProdaj.setBounds(10, 490, 115, 23);
		layeredPane.add(btnProdaj);
	}
	
	private void isplati() {
		if(artikli.size() > 0) {
			String uplata = null;
			while(true) {
				uplata = JOptionPane.showInputDialog("Uplaceni novac:");
				uplata = ((uplata == null || uplata.equals("")) ? "0" : uplata);
				if(Double.parseDouble(uplata) < ukupniRacun) { JOptionPane.showMessageDialog(null, "Premala uplata!"); continue; }
				JOptionPane.showMessageDialog(null, "Kusur: " + (Double.parseDouble(uplata) - ukupniRacun));
				break;
			}
			prodavac.snimiRacun(ukupniRacun);
			prodavac.stampajRacun(ukupniRacun, Double.parseDouble(uplata), artikli);
			for(int i = 0; i < artikli.size(); i++) {
				Artikal.oduzmi(artikli.get(i).getBarKod(), artikli.get(i).getKolicina());
			}
			System.out.println("Završena sesija kupovine");
			artikli.clear();
			deleteTable();
			ukupniRacun = 0;
			ukupniLabela.setText(String.valueOf(ukupniRacun));
		} else {
			JOptionPane.showMessageDialog(null, "Nemate proizvoda u korpi!");
		}
	}
	
	private int brojProizvoda(String barKod) {
		Artikal temp = Prodavac.preuzmiArtikal(barKod);
		int br = (temp != null ? temp.getKolicina() : -1);
		return br;
	}

}
