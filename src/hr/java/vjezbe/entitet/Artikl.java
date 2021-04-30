package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

/**
 * predstavlja entitet artikl
 * nadklasa automobilu, usluzi i stanu
 * 
 * @author Bruno
 *
 */
public abstract class Artikl extends Entitet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String naslov;
	private String opis;
	private BigDecimal cijena;
	private Stanje stanje;
	
	/**
	 * @param naslov podatak o naslovu oglasa artikla
	 * @param opis podatak o opisu oglasa artikla
	 * @param cijena podatak o cijeni oglasa artikla
	 */
	public Artikl(Long id, String naslov, String opis, BigDecimal cijena, Stanje stanje)
	{
		super(id);
		this.naslov = naslov;
		this.opis = opis;
		this.cijena = cijena;
		this.stanje = stanje;
	}
	
	/**
	 * @return vraca naslov oglasa artikla
	 */
	public String getNaslov()
	{
		return naslov;
	}
	
	/**
	 * @return vraca opis oglasa artikla
	 */
	public String getOpis()
	{
		return opis;
	}
	
	/**
	 * @return vraca cijenu oglasa artikla
	 */
	public BigDecimal getCijena()
	{
		return cijena;
	}
	
	/**
	 * @return vraca stanje oglasa artikla
	 */
	public Stanje getStanje()
	{
		return stanje;
	}
	
	/**
	 * upisuje naslov
	 * @param naslov oglasa artikla
	 */
	public void setNaslov(String naslov)
	{
		this.naslov = naslov;
	}
	
	/**
	 * upisuje opis
	 * @param opis oglasa artikla
	 */
	public void setOpis(String opis)
	{
		this.opis = opis;
	}
	
	/**
	 * upisuje cijenu
	 * @param cijena oglasa artikla
	 */
	public void setCijena(BigDecimal cijena)
	{
		this.cijena = cijena;
	}
	
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cijena == null) ? 0 : cijena.hashCode());
		result = prime * result + ((naslov == null) ? 0 : naslov.hashCode());
		result = prime * result + ((opis == null) ? 0 : opis.hashCode());
		result = prime * result + ((stanje == null) ? 0 : stanje.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artikl other = (Artikl) obj;
		if (cijena == null) {
			if (other.cijena != null)
				return false;
		} else if (!cijena.equals(other.cijena))
			return false;
		if (naslov == null) {
			if (other.naslov != null)
				return false;
		} else if (!naslov.equals(other.naslov))
			return false;
		if (opis == null) {
			if (other.opis != null)
				return false;
		} else if (!opis.equals(other.opis))
			return false;
		if (stanje != other.stanje)
			return false;
		return true;
	}

	/**
	 * upisuje stanje
	 * @param stanje oglasa artikla
	 */
	public void setStanje(Stanje stanje)
	{
		this.stanje = stanje;
	}
	
	/**
	 * @return vraca tekst oglasa
	 */
	public abstract String tekstOglasa();
	
}
