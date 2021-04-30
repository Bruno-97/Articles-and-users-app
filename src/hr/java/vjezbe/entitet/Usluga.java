package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

/**
 * predstavlja entitet usluge
 * nasljeduje klasu artikl
 * @author Bruno
 *
 */
public class Usluga extends Artikl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param naslov podatak o naslovu oglasa usluge
	 * @param opis podatak o opis oglasa usluge
	 * @param cijena podatak o cijeni oglasa usluge
	 */
	public Usluga(Long id, String naslov, String opis, BigDecimal cijena, Stanje stanje) 
	{
		super(id, naslov, opis, cijena, stanje);
	}

	/**
	 * vraca kompletan oglas usluge
	 */
	@Override
	public String tekstOglasa() 
	{
		return "Naslov usluge: " + this.getNaslov() + "\n" + "Opis usluge: " + this.getOpis() + "\n"
			   + "Cijena usluge: " + this.getCijena() + "\n" + "Kvaliteta usluge: " + this.getStanje() + "\n";
	}
}