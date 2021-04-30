package hr.java.vjezbe.entitet;

/**
 * Predstavlja entitet korisnika
 * 
 * @author Bruno
 * 
 */

public abstract class Korisnik extends Entitet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String telefon;
	private String email;
	
	/**
	 * @param telefon podatak o telefonu
	 * @param email podatak o emailu
	 */
	public Korisnik(Long id, String email, String telefon)
	{
		super(id);
		this.telefon = telefon;
		this.email = email;
	}
	
	/**
	 * @return vraca broj telefona
	 */
	public String getTelefon()
	{
		return telefon;
	}
	
	/**
	 * @return vraca email
	 */
	public String getEmail()
	{
		return email;
	}
	
	/**
	 * upisuje telefon
	 * @param telefon korisnika
	 */
	public void setTelefon(String telefon)
	{
		this.telefon = telefon;
	}
	
	/**
	 * upisuje emaiil
	 * @param email korisnika
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	/**
	 * @return vraca podatke o korisniku
	 */
	public abstract String dohvatiKontakt();	
}
