package hr.java.vjezbe.entitet;

import java.time.LocalDate;

/**
 * predstavlja entitet prodaje
 * @author Bruno
 *
 */
public class Prodaja extends Entitet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Artikl artikl;
	private Korisnik korisnik;
	private LocalDate datumObjave;
	
	/**
	 * @param artikl podatak o artiklu na oglasu
	 * @param korisnik podatak o korisniku koji je objavio oglas
	 * @param datumObjave podatak o datumu objave oglasa prodaje
	 */
	public Prodaja(Long id, Artikl artikl, Korisnik korisnik, LocalDate datumObjave)
	{
		super(id);
		this.artikl = artikl;
		this.korisnik = korisnik;
		this.datumObjave = datumObjave;
	}
	
	/**
	 * @return vraca artikl
	 */
	public Artikl getArtikl()
	{
		return artikl;
	}
	
	/**
	 * @return vraca korisnika
	 */
	public Korisnik getKorisnik()
	{
		return korisnik;
	}
	
	/**
	 * @return vraca datum objave
	 */
	public LocalDate getDatumObjave()
	{
		return datumObjave;
	}
	
	/**
	 * upisuje artikl
	 * @param artikl prodaje
	 */
	public void setArtikl(Artikl artikl)
	{
		this.artikl = artikl;
	}
	
	/**
	 * upisuje korisnika
	 * @param korisnik prodaje
	 */
	public void setKorisnik(Korisnik korisnik)
	{
		this.korisnik = korisnik;
	}
	
	/**
	 * upisuje datum objave
	 * @param datumObjave prodaje
	 */
	public void setDatumObjave(LocalDate datumObjave)
	{
		this.datumObjave = datumObjave;
	}
}
