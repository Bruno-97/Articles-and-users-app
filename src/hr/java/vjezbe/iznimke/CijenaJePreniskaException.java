package hr.java.vjezbe.iznimke;

/**
 * predstavlja iznimku kada je cijena artikla preniska
 * @author Bruno
 *
 */
public class CijenaJePreniskaException extends RuntimeException
{
	private static final long serialVersionUID = 2555341954833609776L;

	/**
	 * konstruktor klase
	 */
	public CijenaJePreniskaException() 
	{ 
		super("Cijena ne smije biti manja od 10.000 kuna!"); 
	}
	
	/**
	 * konstruktor klase
	 * @param poruka koju prima o iznimci
	 */
	public CijenaJePreniskaException(String poruka) 
	{ 
		super(poruka); 
	}
	
	/**
	 * konstruktor klase
	 * @param poruka koju prima o iznimci
	 * @param uzrok iznimke
	 */
	public CijenaJePreniskaException(String poruka, Throwable uzrok) 
	{ 
		super(poruka, uzrok); 
	}
	
	/**
	 * konstruktor klase
	 * @param uzrok iznimke
	 */
	public CijenaJePreniskaException(Throwable uzrok) 
	{ 
		super(uzrok); 
	}
}
