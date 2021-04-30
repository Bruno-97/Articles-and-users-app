package hr.java.vjezbe.entitet;

/**
 * predstavlja entitet poslovnog korisnika (tvrtka)
 * nasljeduje entitet korisnika
 * @author Bruno
 *
 */
public class PoslovniKorisnik extends Korisnik 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String naziv;
	private String web;
	
	/**
	 * @param naziv podatak o nazivu poslovnog korisnika
	 * @param web podatak o web stranici poslovnog korisnika
	 * @param telefon podatak o telefonu poslovnog korisnika
	 * @param email pdoatak o emailu poslovnog korisnika
	 */
	public PoslovniKorisnik(Long id, String naziv, String web, String email, String telefon)
	{
		super(id, email, telefon);
		this.naziv = naziv;
		this.web = web;
	}
	
	/**
	 * @return vraca naziv poslovnog korisnika
	 */
	public String getNaziv()
	{
		return naziv;
	}
	
	/**
	 * @return vraca web stranicu poslovnog korisnika 
	 */
	public String getWeb()
	{
		return web;
	}
	
	/**
	 * upisuje naziv
	 * @param naziv poslovnog korisnika
	 */
	public void setNaziv(String naziv)
	{
		this.naziv = naziv;
	}
	
	/**
	 * upisuje web
	 * @param web poslovnog korisnika
	 */
	public void setWeb(String web)
	{
		this.web = web;
	}
	
	/**
	 * vraca podatke o poslovnom korisniku
	 */
	@Override
	public String dohvatiKontakt() 
	{
		return "Naziv tvrtke: " + getNaziv() + ", mail: " + getEmail() + ", tel: " + getTelefon() + ", web: " + getWeb();
	}

}
