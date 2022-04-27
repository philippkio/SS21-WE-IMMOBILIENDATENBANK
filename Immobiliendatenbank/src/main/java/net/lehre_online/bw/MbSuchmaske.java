package net.lehre_online.bw;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Named;

@Named
@SessionScoped
public class MbSuchmaske implements Serializable  {

	private static final long serialVersionUID = 1L;

	private String s = "Stadt";

	
	
	
	
	public String getS() { return s; }
	  public void setS( String s ) { this.s = s; }
	
	
	

	
	
	
}
