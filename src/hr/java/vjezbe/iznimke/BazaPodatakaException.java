package hr.java.vjezbe.iznimke;

public class BazaPodatakaException extends RuntimeException
{
	private static final long serialVersionUID = -5656202067681104007L;

	public BazaPodatakaException(String poruka, Exception e) 
	{
		super(poruka, e);
	}
}
