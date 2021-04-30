package hr.java.vjezbe.entitet;

import java.util.List;

/**
 * predstavlja entitet kategorije
 * 
 * @author Bruno
 *
 */
public class Kategorija<T extends Artikl> extends Entitet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String naziv;
	private List<T> artikli;
	
	/**
	 * @param naziv podatak o nazivu kategorije
	 * @param artikli polje artikala
	 */ 
	public Kategorija(Long id, String naziv,List<T> artikli)
	{
		super(id);
		this.naziv = naziv;
		this.artikli = artikli;
	}
	
	/**
	 * @return vraca naziv kategorije
	 */
	public String getNaziv()
	{
		return naziv;
	}
	
	/**
	 * upisuje naziv
	 * @param naziv kategorije
	 */
	public void setNaziv(String naziv)
	{
		this.naziv = naziv;
	}
	

	/**
	 * @param artikl
	 */
	public void dodajArtikl(T artikl) 
	{
		artikli.add(artikl);
	}
	
	/**
	 * @return vraca set artikala
	 */
	public List<T> getArtikli() 
	{
		return artikli;
	}
	
	public T dohvatiArtikl(Integer index)
	{
		return artikli.get(index);	
	}
}
