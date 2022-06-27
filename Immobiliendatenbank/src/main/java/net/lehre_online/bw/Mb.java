package net.lehre_online.bw;
/*
* Mb.java
* JSF 3 Übung
*/


import static java.lang.System.*;

import java.io.Serializable;
import java.util.Date;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Named;

/**
* Backing bean der JSF-Seite login.xhtml
*
* @author Wolfgang Lang
* @version 2021-08-02
*/
@Named
@SessionScoped
public class Mb implements Serializable {

private static final long serialVersionUID = 1L;

private String kennung = "";
private String pw = "";
private boolean loggedIn = false;

/*--------------------------------------------------------------------------*/

public Mb() {
out.println( "Mb.<init>...\n" + (new Date()).toString() );
}

@PostConstruct
public void init() { out.println( "@PostConstruct.Mb" ); }

public void preRenderAction() { out.println( "Mb.preRenderAction" );}
public void postRenderAction() { out.println( "Mb.postRenderAction" );}

/*--------------------------------------------------------------------------*/

public void setKennung( String s ){ kennung = s; }
public String getKennung(){ return kennung; }

public void setPw( String s ){ pw = s; }
public String getPw(){ return pw; }

public Date getDate() { return new Date(); }

public boolean isLoggedIn() { return loggedIn; }
/*--------------------------------------------------------------------------*/

public String actLogin() {

String sOutcome = null;
System.out.println( "actLogin()..." );

kennung = kennung.trim();
pw = pw.trim();

if( kennung.equalsIgnoreCase( "user" ) && pw.equals( "user" ) )
{
sOutcome = "user";
loggedIn = true;
}
else if( kennung.equalsIgnoreCase( "admin" ) && pw.equals( "admin" ) )
{ 
sOutcome = "admin";
loggedIn = true;
}

else FacesContext.getCurrentInstance().addMessage( null,new FacesMessage( FacesMessage.SEVERITY_WARN, "Fehler", "Kennung oder PW falsch (user/user oder admin/admin)" ));

return sOutcome;
}

/*--------------------------------------------------------------------------*/

public void aclLogout( ActionEvent ae ) { loggedIn = false; }
}
