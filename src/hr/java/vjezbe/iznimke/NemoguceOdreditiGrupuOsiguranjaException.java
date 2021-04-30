package hr.java.vjezbe.iznimke;

/**
 * predstavlja iznimku kada automobil ima previše kilowata 
 * @author Bruno
 *
 */
public class NemoguceOdreditiGrupuOsiguranjaException extends Exception
{
	private static final long serialVersionUID = 2970828597421605572L;

	/**
	 * konstruktor klase
	 */
	public NemoguceOdreditiGrupuOsiguranjaException() 
	{ 
		super("Previše kW, ne mogu odrediti grupu osiguranja!"); 
	}
	
	/**
	 * konstruktor klase
	 * @param poruka koju prima o iznimci
	 */
	public NemoguceOdreditiGrupuOsiguranjaException(String poruka) 
	{ 
		super(poruka); 
	}
	
	/**
	 * konstruktor klase
	 * @param poruka koju prima o iznimci
	 * @param uzrok iznimke
	 */
	public NemoguceOdreditiGrupuOsiguranjaException(String poruka, Throwable uzrok) 
	{ 
		super(poruka, uzrok); 
	}
	
	/**
	 * konstruktor klase
	 * @param uzrok iznimke
	 */
	public NemoguceOdreditiGrupuOsiguranjaException(Throwable uzrok) 
	{ 
		super(uzrok); 
	}
}
