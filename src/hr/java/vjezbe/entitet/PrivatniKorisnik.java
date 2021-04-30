package hr.java.vjezbe.entitet;

/**
 * predstavlja entitet privatnog korisnika (osoba)
 * nasljeduje entitet korisnika
 * @author Bruno
 *
 */
public class PrivatniKorisnik extends Korisnik 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ime;
	private String prezime;
	
	/**
	 * @param ime podatak o imenu privatnog korisnika
	 * @param prezime podatak o prezimenu privatnog korisnika
	 * @param telefon podatak o telefonu privatnog korisnika
	 * @param email podatak o emailu privatnog korisnika
	 */
	public PrivatniKorisnik(Long id, String ime, String prezime, String email, String telefon) 
	{
		super(id, email, telefon);
		this.ime = ime;
		this.prezime = prezime;
	}
	
	/**
	 * @return vraca ime privatnog korisnika
	 */
	public String getIme()
	{
		return ime;
	}
	
	/**
	 * @return vraca prezime privatnog korisnika
	 */
	public String getPrezime()
	{
		return prezime;
	}
	
	/**
	 * upisuje ime
	 * @param ime privatnog korisnika
	 */
	public void setIme(String ime)
	{
		this.ime = ime;
	}
	
	/**
	 * upisuje prezime
	 * @param prezime privatnog korisnika
	 */
	public void setPrezime(String prezime)
	{
		this.prezime = prezime;
	}

	/**
	 * vraca podatke o privatnom korisniku 
	 */
	@Override
	public String dohvatiKontakt() 
	{
		return "Osobni podaci prodavatelja: " + getIme() + " " + getPrezime() + ", mail: " + getEmail() + ", tel: " + getTelefon();
	}
}
