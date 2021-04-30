package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.iznimke.CijenaJePreniskaException;

/**
 * predstavlja entitet stana
 * nasljeduje klasu artikl i implementira sucelje nekretnine
 * @author Bruno
 *
 */
public class Stan extends Artikl implements Nekretnina 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(Automobil.class);
	
	Integer kvadratura;
	
	/**
	 * @param naslov podatak o naslovu oglasa stana
	 * @param opis podatak o opisu oglasa stana
	 * @param cijena podatak o cijeni oglasa stana
	 * @param kvadratura podatak o kvadraturi oglasa stana
	 */
	public Stan(Long id, String naslov, String opis, BigDecimal cijena, Stanje stanje, Integer kvadratura) 
	{
		super(id, naslov, opis, cijena, stanje);
		this.kvadratura = kvadratura;
	}
	
	/**
	 * @return vraca kvadraturu stana
	 */
	public Integer getKvadratura()
	{
		return kvadratura;
	}
	
	/**
	 * upisuje kvadaraturu
	 * @param kvadratura stana
	 */
	public void setKvadratura(Integer kvadratura)
	{
		this.kvadratura = kvadratura;
	}

	/**
	 * vraca kompletan oglas stana
	 */
	@Override
	public String tekstOglasa() 
	{
		try
		{
			return "Naslov nekretnine: " + this.getNaslov() + "\n" + "Opis nekretnine: " + this.getOpis() + "\n"
					+ "Kvadratura nekretnine: " + this.getKvadratura() + "\n" + "Stanje nekretnine: " + this.getStanje() + "\n" + "Porez na nekretnine: " + izracunajPorez(getCijena())
					+ "\n" + "Cijena nekretnine: " + this.getCijena() + "\n";
		}
		
		catch(CijenaJePreniskaException ex)
		{
			logger.info("Ne može se odrediti odrediti porez nekretnine!", ex);
		}
		
		return "Naslov nekretnine: " + this.getNaslov() + "\n" + "Opis nekretnine: " + this.getOpis() + "\n"
		+ "Kvadratura nekretnine: " + this.getKvadratura() + "\n" + "Stanje nekretnine: " + this.getStanje() + "\n" + "Porez na nekretnine: " + "Cijena ne smije biti manja od 10.000 kuna!"
		+ "\n" + "Cijena nekretnine: " + this.getCijena() + "\n";
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + kvadratura;
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stan other = (Stan) obj;
		if (kvadratura != other.kvadratura)
			return false;
		return true;
	}

}
