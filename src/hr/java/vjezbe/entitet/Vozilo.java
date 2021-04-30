package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

import hr.java.vjezbe.iznimke.NemoguceOdreditiGrupuOsiguranjaException;

/**
 * predstavlja sucelje vozilo
 * @author Bruno
 *
 */
public interface Vozilo 
{
	/**
	 * @param konjskeSnage podatak o konjskim snagama automobila
	 * @return vraca kilowate automobila
	 */
	default public BigDecimal IzracunajKw(BigDecimal konjskeSnage)
	{
		double hp_to_kW = 0.73549875;
		BigDecimal kiloWati = new BigDecimal(hp_to_kW).multiply(konjskeSnage);
		
		return 	kiloWati;
	}
	
	/**
	 * implementacija metode je unutar klase automobil
	 * @return vraca grupu osiguranja
	 */
	public BigDecimal izracunajGrupuOsiguranja() throws NemoguceOdreditiGrupuOsiguranjaException;
	
	/**
	 * @return vraca cijenu osiguranja automobila s obzirom na grupu osiguranja
	 */
	default public BigDecimal izracunajCijenuOsiguranja() throws NemoguceOdreditiGrupuOsiguranjaException 
	{
		Integer grupaOsiguranja = izracunajGrupuOsiguranja().intValue();
		//Random x = new Random();
		Integer cOs = 0;
		
		switch(grupaOsiguranja)
		{
			case 1:
				cOs = 2000;
				break;
				
			case 2:
				cOs = 4000;
				break;
				
			case 3:
				cOs = 5000;
				break;
				
			case 4:
				cOs = 10000;
				break;
				
			case 5:
				cOs = 20000;
				break;
		}
		
		BigDecimal cijenaOsiguranja = BigDecimal.valueOf(cOs);
		return cijenaOsiguranja;
	}
}

/*1 horsepower (metric) = 0.73549875 kW
1 kW = 1.3596216173 horsepower (metric)*/

/** Random generiranje brojeva**/
/*Random ran = new Random();
int x = ran.nextInt(6) + 5;*/

