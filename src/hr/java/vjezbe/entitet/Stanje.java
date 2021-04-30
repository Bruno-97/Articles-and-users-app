package hr.java.vjezbe.entitet;


/**
 * predstavlja enumeraciju stanja
 * @author Bruno
 *
 */
public enum Stanje 
{
	novo(1), izvrsno(2), rabljeno(3), neispravno(4);
	
	private final int vrijednost;
	
	Stanje(int st) 
	{
		this.vrijednost = st;	
	}
	
	public int getValue() 
	{
		return vrijednost;	
	}
	
	public static Stanje valueOf(Integer stanje) 
	{
		if(stanje==1) 
		{
			return Stanje.novo;
		}
		
		if(stanje==2) 
		{
			return Stanje.izvrsno;	
		}
		
		if(stanje==3) 
		{
			return Stanje.rabljeno;	
		}
		
		if(stanje==4) 
		{
			return Stanje.neispravno;
		}

		else return null;
	}

}
