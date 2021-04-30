package hr.java.vjezbe.entitet;

import java.io.Serializable;

public abstract class Entitet implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	
	public Entitet(Long id)
	{
		this.id = id;
	}
	
	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}
}
