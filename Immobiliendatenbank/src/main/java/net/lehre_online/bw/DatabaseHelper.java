package net.lehre_online.bw;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.Connection;

import jakarta.faces.application.Application;
import jakarta.faces.context.FacesContext;

public class DatabaseHelper {
	  
	  /**
	   * Wichtig ist, dass hier der Bean-Name steht, der in der 
	   * faces-config mit &lt;managed-bean-name&gt;xxx&lt;/managed-bean-name&gt; 
	   * definiert wurde
	   */
	  final static String DATABASE_BEAN_NAME = "mb_db";
	  
	  final String CLASSNAME = getClass().getName();
	  
	  private DatabaseConection mbDb = null;

	  //@Inject
	  //private FacesContext fc; Klappt nicht
	  
	  /*--------------------------------------------------------------------------*/
	  
	  public DatabaseHelper() {}

	  /*--------------------------------------------------------------------------*/
	  
	  /**
	   * Datenbankverbindung schließen
	   * @param con : Aktuelle DB-Verbindung
	   */  
	  public void closeConnection( Connection con ){
	    if( mbDb == null ) mbDb = (DatabaseConection) getBean( DATABASE_BEAN_NAME );
	    if( mbDb != null ) mbDb.closeConnection( con );
	    else System.err.println( 
	              "Util.closeConnection(): Fehler beim Schließen der Connection!" );
	  }
	  
	  /*--------------------------------------------------------------------------*/
	  
	  /**
	  * Gibt ein Connection-Objekt aus dem Pool zurück
	  * @return : Connection-Objekt
	  */
	  public Connection getCon(){
	          
			final String MNAME = ".getCon()";  
			final String TAG = CLASSNAME + MNAME;
			
			Connection con = null;
			
			log( TAG + ": entering..." );
			FacesContext fc = FacesContext.getCurrentInstance();
			if( fc == null ) log( TAG + ": FacesContext is null." );
			else {
				Application app = fc.getApplication();
				if( app == null ) log( TAG + ": Application is null." );
				else {
					con = app.evaluateExpressionGet( fc, "#{" + DATABASE_BEAN_NAME + ".con}", 
							                             Connection.class );
					if( con == null ) log( TAG + ": Connection is null." );
				}
			}
					
	    /*return FacesContext.getCurrentInstance().getApplication().
	           evaluateExpressionGet( FacesContext.getCurrentInstance(),
	           "#{" + DATABASE_BEAN_NAME + ".con}", Connection.class );*/
			
			log( TAG + ": ...exiting" );
			return con;
	  }
	  
	  /*--------------------------------------------------------------------------*/
	  
	  public void log( String s ) {
	  	if( mbDb == null ) mbDb = (DatabaseConection) getBean( DATABASE_BEAN_NAME );
	    if( mbDb != null ) mbDb.log( s );
	  }
	  
	  /*--------------------------------------------------------------------------*/
	  
	  // Nur der Systematik wegen (2012-09-16):
	  public Connection getConnection(){ return getCon(); }
	  
	  /*--------------------------------------------------------------------------*/
	  
	  /**
	   * Gibt eine Referenz auf einen Managed Bean zurück
	   *
	   * @param sBean Name der Bean
	   * @return : Referenz auf Managed Bean
	   */
	  public Object getBean( String sBean ){
	      
	  	Object o = null;
	  	
	    if( sBean != null ) {
	    	FacesContext fc = FacesContext.getCurrentInstance();
	    	if( fc != null ) {
	    		Application app = fc.getApplication();
	    		if( app != null ) {
	    			o = app.evaluateExpressionGet( fc, "#{" + sBean +"}", Object.class );
	    		}    				                                        
	    	} else System.err.println( "FacesContext in getBean ist null!");
	    }
	    	/*return FacesContext.getCurrentInstance().getApplication().
	               evaluateExpressionGet( FacesContext.getCurrentInstance(),
	               "#{" + sBean +"}", Object.class );*/ 
	      
	      /* FacesContext fc = FacesContext.getCurrentInstance();
	      Application app = fc.getApplication();
	      Object o = app.evaluateExpressionGet( fc, "#{" + sBean +"}", Object.class );
	      return o; */      
	    
	    return o;
	  }
	  
	  /*--------------------------------------------------------------------------*/
	  
	  /**
	   * Verschlüsselung eines Passworts
	   * Aus Kennung und Passwort wird mittels SHA-Hash das verschlüsselete Passwort
	   * generiert. 
	   * @param user Kennung
	   * @param pw   Passwort im Klartext
	   * @return out Verschlüsseltes Passwort oder unverschlüsselte 
	   *             Input-Parameter bei Fehler
	   */
	  public String cryptpw( String user, String pw ) {
	    
	    String in = user + pw;
	    String out = in;
	    
	    try{
	      MessageDigest md = MessageDigest.getInstance( "SHA" );
	      byte[] bHash = md.digest( in.getBytes() ); // oder getBytes( "UTF-8" ) ?
	      StringBuffer sb = new StringBuffer();
	      
	      for( int i = 0; i < bHash.length; i++ ){
	        sb.append( Integer.toHexString( 0xF0 & bHash[i] ).charAt(0) );
	        sb.append( Integer.toHexString( 0x0F & bHash[i] ) );
	      }
	      out = sb.toString();
	    }
	    catch( NoSuchAlgorithmException ex ){
	      ex.printStackTrace();   
	    }
	    
	    return out;
	  }
	}

