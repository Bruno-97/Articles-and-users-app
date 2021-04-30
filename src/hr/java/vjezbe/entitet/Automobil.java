package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.iznimke.NemoguceOdreditiGrupuOsiguranjaException;

/**
 * predstavlja entitet automobila, nasljeduje artikl 
 * i implementira sucelje vozilo
 * 
 * @author Bruno
 *
 */
public class Automobil extends Artikl implements Vozilo 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(Automobil.class);
	
	BigDecimal snagaKs;
	
	/**
	 * @param naslov podatak o naslovu oglasa automobila
	 * @param opis podatak o opisu oglasa automobila
	 * @param cijena podatak o cijeni oglasa automobila
	 * @param snagaKs podatak o snagi automobila
	 */
	public Automobil(Long id, String naslov, String opis, BigDecimal cijena, Stanje stanje, BigDecimal snagaKs) 
	{
		super(id, naslov, opis, cijena, stanje);
		this.snagaKs = snagaKs;
	}
	
	/**
	 * @return vraca snagu automobila
	 */
	public BigDecimal getSnagaKs()
	{
		return snagaKs;
	}
	
	/**
	 * upisuje snagu
	 * @param snagaKs snaga automobila
	 */
	public void setSnagaKsv(BigDecimal snagaKs)
	{
		this.snagaKs = snagaKs;
	}

	/**
	 *vraca grupu osiguranja automobila s obzirom na snagu motora
	 * @throws NemoguceOdreditiGrupuOsiguranjaException 
	 */
	@Override
	public BigDecimal izracunajGrupuOsiguranja() throws NemoguceOdreditiGrupuOsiguranjaException
	{ 
		Integer gO = 0;
		BigDecimal snagaAutomobila = IzracunajKw(snagaKs);
		
		if (snagaAutomobila.compareTo(BigDecimal.valueOf(1)) >= 0 && snagaAutomobila.compareTo(BigDecimal.valueOf(50)) <= 0 )
		{
			gO = 1;
		}
		
		if (snagaAutomobila.compareTo(BigDecimal.valueOf(51)) >= 0 && snagaAutomobila.compareTo(BigDecimal.valueOf(90)) <= 0 )
		{
			gO = 2;
		}
		
		if (snagaAutomobila.compareTo(BigDecimal.valueOf(91)) >= 0 && snagaAutomobila.compareTo(BigDecimal.valueOf(120)) <= 0 )
		{
			gO = 3;
		}
		
		if (snagaAutomobila.compareTo(BigDecimal.valueOf(121)) >= 0 && snagaAutomobila.compareTo(BigDecimal.valueOf(150)) <= 0 )
		{
			gO = 4;
		}
		
		if (snagaAutomobila.compareTo(BigDecimal.valueOf(151)) >= 0 && snagaAutomobila.compareTo(BigDecimal.valueOf(170)) <= 0 )
		{
			gO = 5;
		}
		
		if (snagaAutomobila.compareTo(BigDecimal.valueOf(170)) > 0 )
		{
			throw new NemoguceOdreditiGrupuOsiguranjaException("Previše kW, ne mogu odrediti grupu osiguranja!");
		}
		
		BigDecimal grupaOsiguranja = BigDecimal.valueOf(gO);
		
		return grupaOsiguranja;
	}

	/**
	 *vraca kompletan oglas automobila
	 */
	@Override
	public String tekstOglasa()
	{
		try
		{
			return "Naslov automobila: " + this.getNaslov() + "\n" + "Opis automobila: " + this.getOpis() + "\n"
					+ "Snaga automobila: " + this.getSnagaKs() + "\n" + "Stanje automobila: " + this.getStanje() + "\n" 
					+ "Izracun osiguranja automobila: " + this.izracunajCijenuOsiguranja() + "\n" + "Cijena automobila: " 
					+ this.getCijena() + "\n";
		}
		
		catch(NemoguceOdreditiGrupuOsiguranjaException ex)
		{
			logger.info("Ne može se odrediti odrediti grupa osiguranja!", ex);
		}
		
		return "Naslov automobila: " + this.getNaslov() + "\n" + "Opis automobila: " + this.getOpis() + "\n"
		+ "Snaga automobila: " + this.getSnagaKs() + "\n" + "Stanje automobila: " + this.getStanje() + "\n" 
		+ "Izracun osiguranja automobila: " + "Previše kW, ne mogu odrediti grupu osiguranja!" + "\n" + "Cijena automobila: " 
		+ this.getCijena() + "\n";
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((snagaKs == null) ? 0 : snagaKs.hashCode());
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
		Automobil other = (Automobil) obj;
		if (snagaKs == null) {
			if (other.snagaKs != null)
				return false;
		} else if (!snagaKs.equals(other.snagaKs))
			return false;
		return true;
	}
}
