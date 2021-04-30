package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

import hr.java.vjezbe.iznimke.CijenaJePreniskaException;

/**
 * predstavlja sucelje nekretnine
 * @author Bruno
 *
 */
public interface Nekretnina
{
	/**
	 * @param cijenaNekretnine podatak o cijeni nekretnine
	 * @return vraca porez nekretnine (3 % od cijene)
	 */
	default public BigDecimal izracunajPorez(BigDecimal cijenaNekretnine) throws CijenaJePreniskaException
	{
		if (cijenaNekretnine.compareTo(BigDecimal.valueOf(10000)) < 0)
		{
			throw new CijenaJePreniskaException("Cijena ne smije biti manja od 10.000 kuna!");
		}
		
		BigDecimal porez = null;
		double postotak = 0.03; // 3 % poreza
		
		porez = BigDecimal.valueOf(postotak).multiply(cijenaNekretnine);
		
		return porez;
	}
}
