package net.lehre_online.bw;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.file.UploadedFile;

import java.sql.Date;
import java.sql.PreparedStatement;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Named;

/**
 * Backing bean aller JSF-Seiten 
 */
@Named("MbSuchmaske")
@RequestScoped
public class MbSuchmaske implements Serializable {
	
	


	private static final long serialVersionUID = 1L;

	final String SQL_SELECT = "select ID, city, adress, housenumber, living_area, lot_size, price, description, picture, zip, property_type "
			+ "from immobilien";
	
	
	
	

	private boolean connected = false;
	private boolean prevButtonDisabled = true;
	private boolean nextButtonDisabled = true;

	/*
	 * DatabaseHelper ist eine Hilfsklasse, die u. a. den Verbindungsaufbau zur
	 * Datenbank vereinfacht:
	 */
	private DatabaseHelper DatabaseHelper = new DatabaseHelper();

	private Connection con = null;
	private Statement stm = null;
	private ResultSet rs = null;
	// private PreparedStatement ps = null;
	
	private String kennung = "";
	private String pw = "";
	private boolean loggedIn = false;

	private String immobilienart =null;
	private String stadt=null;
	private int minGrundflaeche;
	private int maxGrundflaeche;
	private int minWohnflaeche;
	private int maxWohnflaeche;
	private int preis;
	private String insert_immobilienart =null;
	private String insert_stadt=null;
	private int insert_Grundflaeche;
	private int insert_Wohnflaeche;
	private int insert_Preis;
	private String insert_Hausnummer= null;
	private String insert_Strasse=null;
	private int insert_Plz;
	private String insert_Beschreibung=null;
	private String ausgabe_stadt=null;
	private String ausgabe_immobilienart=null;
	private String ausgabe_strasse=null;
	private String ausgabe_hausnummer=null;
	private int ausgabe_preis;
	private int ausgabe_plz;
	private int ausgabe_grundflaeche;
	private int ausgabe_wohnflaeche;
	private String ausgabe_beschreibung=null;
	
	private UploadedFile file;
	
	private List<Immobilie> immobilienList = new ArrayList<Immobilie>();

	/*--------------------------------------------------------------------------*/
	public MbSuchmaske() {
		System.out.println("MbSuchmaske.<init>...");
		System.out.println("immobilienart anfang: "+immobilienart+ "und Stadt"+stadt+" Grundfläche: "+ minGrundflaeche);
	}

	@PostConstruct
	public void init() {
		System.out.println("@PostConstruct.MbSuchmaske");
	}

	public void preRenderAction() {
		System.out.println("MbSuchmaske.preRenderAction");
	}

	public void postRenderAction() {
		System.out.println("MbSuchmaske.postRenderAction");
	}

	public String getImmobilienart() {
		return immobilienart;
	}

	public void setImmobilienart(String immobilienart) {
		this.immobilienart = immobilienart;
	}

	public int getPreis() {
		return preis;
	}

	public void setPreis(int preis) {
		this.preis = preis;
	}

	public String getStadt() {
		return stadt;
	}

	public void setStadt(String stadt) {
		this.stadt = stadt;
	}

	public int getMinGrundflaeche() {
		return minGrundflaeche;
	}

	public void setMinGrundflaeche(int minGrundflaeche) {
		this.minGrundflaeche = minGrundflaeche;
	}

	public int getMaxGrundflaeche() {
		return maxGrundflaeche;
	}

	public void setMaxGrundflaeche(int maxGrundflaeche) {
		this.maxGrundflaeche = maxGrundflaeche;
	}

	public int getMinWohnflaeche() {
		return minWohnflaeche;
	}

	public void setMinWohnflaeche(int minWohnflaeche) {
		this.minWohnflaeche = minWohnflaeche;
	}

	public int getMaxWohnflaeche() {
		return maxWohnflaeche;
	}

	public void setMaxWohnflaeche(int maxWohnflaeche) {
		this.maxWohnflaeche = maxWohnflaeche;
	}

	public boolean getPrevButtonDisabled() {
		return prevButtonDisabled;
	}

	public boolean getNextButtonDisabled() {
		return nextButtonDisabled;
	}

	public boolean getConnected() {
		return connected;
	}

	public void setConnected(boolean b) {
		connected = b;
	}

	public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
	/*----------------------------------Ausgabewerte fuer resultBoard----------------------------------------*/

	
	
	public String getAusgabe_stadt() {
		return ausgabe_stadt;
	}

	public String getAusgabe_immobilienart() {
		return ausgabe_immobilienart;
	}

	public void setAusgabe_immobilienart(String ausgabe_immobilienart) {
		this.ausgabe_immobilienart = ausgabe_immobilienart;
	}

	public String getAusgabe_strasse() {
		return ausgabe_strasse;
	}

	public void setAusgabe_strasse(String ausgabe_strasse) {
		this.ausgabe_strasse = ausgabe_strasse;
	}

	public String getAusgabe_hausnummer() {
		return ausgabe_hausnummer;
	}

	public void setAusgabe_hausnummer(String ausgabe_hausnummer) {
		this.ausgabe_hausnummer = ausgabe_hausnummer;
	}

	public int getAusgabe_preis() {
		return ausgabe_preis;
	}

	public void setAusgabe_preis(int ausgabe_preis) {
		this.ausgabe_preis = ausgabe_preis;
	}

	public int getAusgabe_plz() {
		return ausgabe_plz;
	}

	public void setAusgabe_plz(int ausgabe_plz) {
		this.ausgabe_plz = ausgabe_plz;
	}

	public int getAusgabe_grundflaeche() {
		return ausgabe_grundflaeche;
	}

	public void setAusgabe_grundflaeche(int ausgabe_grundflaeche) {
		this.ausgabe_grundflaeche = ausgabe_grundflaeche;
	}

	public int getAusgabe_wohnflaeche() {
		return ausgabe_wohnflaeche;
	}

	public void setAusgabe_wohnflaeche(int ausgabe_wohnflaeche) {
		this.ausgabe_wohnflaeche = ausgabe_wohnflaeche;
	}

	public String getAusgabe_beschreibung() {
		return ausgabe_beschreibung;
	}

	public void setAusgabe_beschreibung(String ausgabe_beschreibung) {
		this.ausgabe_beschreibung = ausgabe_beschreibung;
	}

	public void setAusgabe_stadt(String ausgabe_stadt) {
		this.ausgabe_stadt = ausgabe_stadt;
	}
	
	/*-------------------------------Eingabewerte von insertBoard-------------------------------------------*/

	public String getInsert_immobilienart() {
		return insert_immobilienart;
	}

	public void setInsert_immobilienart(String insert_immobilienart) {
		this.insert_immobilienart = insert_immobilienart;
	}

	public String getInsert_stadt() {
		return insert_stadt;
	}

	public void setInsert_stadt(String insert_stadt) {
		this.insert_stadt = insert_stadt;
	}

	public int getInsert_Grundflaeche() {
		return insert_Grundflaeche;
	}

	public void setInsert_Grundflaeche(int insert_Grundflaeche) {
		this.insert_Grundflaeche = insert_Grundflaeche;
	}

	public int getInsert_Wohnflaeche() {
		return insert_Wohnflaeche;
	}

	public void setInsert_Wohnflaeche(int insert_Wohnflaeche) {
		this.insert_Wohnflaeche = insert_Wohnflaeche;
	}

	public int getInsert_Preis() {
		return insert_Preis;
	}

	public void setInsert_Preis(int insert_Preis) {
		this.insert_Preis = insert_Preis;
	}

	public String getInsert_Hausnummer() {
		return insert_Hausnummer;
	}

	public void setInsert_Hausnummer(String insert_Hausnummer) {
		this.insert_Hausnummer = insert_Hausnummer;
	}

	public String getInsert_Strasse() {
		return insert_Strasse;
	}

	public void setInsert_Strasse(String insert_Strasse) {
		this.insert_Strasse = insert_Strasse;
	}


	public int getInsert_Plz() {
		return insert_Plz;
	}

	public void setInsert_Plz(int insert_Plz) {
		this.insert_Plz = insert_Plz;
	}

	public String getInsert_Beschreibung() {
		return insert_Beschreibung;
	}

	public void setInsert_Beschreibung(String insert_Beschreibung) {
		this.insert_Beschreibung = insert_Beschreibung;
	}

	/*--------------------------------------------------------------------------*/
	public void setKennung( String s ){ kennung = s; } 
	public String getKennung(){ return kennung; }
	
	public void setPw( String s ){ pw = s; } 
	public String getPw(){ return pw; }
	/*--------------------------------------------------------------------------*/
	
	private void showData() throws SQLException {
		//setImmobilienart(rs.getString("property_type"));
		//setStadt(rs.getString("city"));
		
		setAusgabe_strasse        ( rs.getString    ( "adress" ) );
		setAusgabe_hausnummer         ( rs.getString ( "housenumber") );
		setAusgabe_plz      ( rs.getInt ( "zip") );
		setAusgabe_stadt   ( rs.getString   ( "city") );
		setAusgabe_wohnflaeche( rs.getInt( "living_area") );
		setAusgabe_grundflaeche( rs.getInt( "lot_size") );
		setPreis( rs.getInt( "price") );
		setAusgabe_immobilienart( rs.getString( "property_type") );
		setAusgabe_beschreibung( rs.getString( "description") );
		
		
		  
		
	}

	/*--------------------------------------------------------------------------*/
	public void suchErgebnis() {
		connect();
		if (DatabaseHelper != null)
			con = DatabaseHelper.getCon();
		if (con != null) {
			try {
				String sQlEingaben =  
						"SELECT ID,zip,city,adress,housenumber,living_area,lot_size,price,description,property_type FROM immobilien "
						+ "WHERE ID>=0 AND zip>=0 AND city='"+stadt+"' AND living_area>="+minWohnflaeche+" AND living_area<="+maxWohnflaeche+" AND lot_size>="+minGrundflaeche+" AND lot_size<="+maxGrundflaeche+" AND price<="+preis+" AND property_type='"+immobilienart+"';";
				
				stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				rs = stm.executeQuery(sQlEingaben);
			
				System.out.println("Zu Ihrer Suche passende Immobilien sind: ");
				while (rs.next()) {
					System.out.print("ID: "+rs.getInt("ID")+" , ");
					System.out.print("Straße: "+rs.getString("adress")+" , ");
			        System.out.print("Hausnummer: "+rs.getString("housenumber")+" , ");
					System.out.print("PLZ: "+rs.getInt("zip")+" , ");
					ausgabe_plz=rs.getInt("zip");
			        System.out.print("Stadt: "+rs.getString("city")+" , ");
			        ausgabe_stadt=rs.getString("city");
			        System.out.print("Wohnfläche: "+rs.getInt("living_area")+" , ");
			        ausgabe_wohnflaeche=rs.getInt("living_area");
			        System.out.print("Grundfläche: "+rs.getInt("lot_size")+" , ");
			        ausgabe_grundflaeche=rs.getInt("lot_size");
			        System.out.print("Preis: "+rs.getInt("price")+" , ");
			        ausgabe_preis=rs.getInt("price");
			        System.out.print("Immobilienart: "+rs.getString("property_type")+"\n");
			        ausgabe_immobilienart=rs.getString("property_type");
			        System.out.print("Beschreibung: "+rs.getString("description")+" , ");
			        Immobilie immobilie = new Immobilie(rs.getInt("ID"), rs.getString("adress"), rs.getString("city"), rs.getString("property_type"),
			        		rs.getString("housenumber"), rs.getInt("price"), rs.getInt("zip"), rs.getInt("lot_size"), rs.getInt("living_area"), 
			        		rs.getString("description"));
					System.out.println(immobilie);
					immobilienList.add(immobilie);
			        
			    }
						
				ImmobilieService service= new ImmobilieService(immobilienList);
				CarouselView view= new CarouselView();
				
				
				stm.close();
				} catch (SQLException ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "SQLException", ex.getLocalizedMessage()));
			System.out.println("Error: " + ex);
			ex.printStackTrace();
			System.out.println("Insert catch...");
		}
			}
		
		
	}
	
	
	
	/**
	 * Verbindung zur Datenbank herstellen und disconnect button und browse buttons
	 * freigeben
	 * 
	 * @param ae ActionEvent
	 */
	public void connect() {

		 System.out.println( "connect()..." );

		if (DatabaseHelper != null)
			con = DatabaseHelper.getCon();
		if (con != null) {
			try {
				stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				rs = stm.executeQuery(SQL_SELECT);
				if (rs.first())
					showData();
				connected = true;
				prevButtonDisabled = false;
				nextButtonDisabled = false;
			} catch (Exception ex) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "SQLException", ex.getLocalizedMessage()));
				System.out.println("Error: " + ex);
				ex.printStackTrace();
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Exception", "Keine Verbindung zur Datenbank (Treiber nicht gefunden?)"));
			System.out.println("Keine Verbingung zur Datenbank");
		}
		//insert();
	}

	/*--------------------------------------------------------------------------*/

	/**
	 * Verbindung zur Datenbank beenden
	 * 
	 * @param ae ActionEvent
	 
	public void disconnect(ActionEvent ae) {

		if (con != null) {
			try {
				if (rs != null)
					rs.close();
				if (stm != null)
					stm.close();

				DatabaseHelper.closeConnection(con);

				connected = false;
				prevButtonDisabled = true;
				nextButtonDisabled = true;

				//setMatNr(0);
				//setName("");
				//setGeburtstag(null);
				//setMasterstudent(false);

			} catch (Exception ex) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Exception", ex.getLocalizedMessage()));
				System.out.println("Error: " + ex);
				ex.printStackTrace();
			}
		}
	}

	/*--------------------------------------------------------------------------*/

	/**
	 * Zum vorherigen Datensatz scrollen
	 * 
	 * @param ae ActionEvent
	 */
	public void prev(ActionEvent ae) {
		try {
			if ((rs != null) && rs.previous()) {
				showData();

		       
		        
				nextButtonDisabled = false;
			} else
				prevButtonDisabled = true;

		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Exception", ex.getLocalizedMessage()));
			System.out.println("Error: " + ex);
			ex.printStackTrace();
		}
	}

	/*--------------------------------------------------------------------------*/

	/**
	 * Weiterscrollen
	 * 
	 * @param ae ActionEvent
	 */
	public void next(ActionEvent ae) {
		try {
			if ((rs != null) && rs.next()) {
				showData();
				prevButtonDisabled = false;
			} else
				nextButtonDisabled = true;

		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Exception", ex.getLocalizedMessage()));
			System.out.println("Error: " + ex);
			ex.printStackTrace();
		}
	}

	/*--------------------------------------------------------------------------*/

	/**
	 * Datensatz einfügen
	 * 
	 * @param ae ActionEvent
	 */
	public void insert() {
		
		System.out.println("Insert entered...");
		
		connect();

		try {
			System.out.println("Insert try entered...");
			// if( ps == null ){
			String sQl = "INSERT INTO immobilien (city, adress, housenumber, living_area, lot_size, price, description, picture, zip, property_type)"
					+ " "
					+ "VALUES (?, ?, ?, ?, ? , ?, ? , ?, ?, ? )";
			System.out.println("Insert sql statement...");
			PreparedStatement ps = con.prepareStatement(sQl);
			// }
			System.out.println("Die stadt ist vorm SQL: "+insert_stadt);
			
			ps.setString(1, insert_stadt);
			ps.setString(2, insert_Strasse);
			ps.setString(3, insert_Hausnummer);
			ps.setInt(4, insert_Wohnflaeche);
			ps.setInt(5, insert_Grundflaeche);
			ps.setInt(6, insert_Preis);
			ps.setString(7, insert_Beschreibung);
			ps.setBinaryStream(8, file.getInputStream());
			ps.setInt(9, insert_Plz);
			ps.setString(10, insert_immobilienart);

			System.out.println("Insert 2...");
			int n = ps.executeUpdate();
			if (n == 1) {
				System.out.println("O.K.,  Datensatz eingefügt.");
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "O. K.", "Ein Datensatz erfolgreich eingefügt."));
			}

			System.out.println("Insert 3...");
			ps.close();

			// Result set neu aufbauen:
			rs = stm.executeQuery(SQL_SELECT);
		} catch (SQLException | IOException ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "SQLException", ex.getLocalizedMessage()));
			System.out.println("Error: " + ex);
			ex.printStackTrace();
			System.out.println("Insert catch...");
		}
		System.out.println("Insert exit...");
		immobilienart = null;
	}

	/*--------------------------------------------------------------------------*/

	/**
	 * Datensatz löschen
	 * 
	 * @param ae ActionEvent
	 */
	public void delete(ActionEvent ae) {

		if (DatabaseHelper != null)
			DatabaseHelper.log("delete()...");

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, "Datensatz nicht gelöscht!", "Löschen nicht erlaubt."));
	}

	/*--------------------------------------------------------------------------*/

	/**
	 * Datensatz aktualisieren
	 * 
	 * @param ae ActionEvent
	 */
	public void update(ActionEvent ae) {

		// out.println( "update()..." );
		if (DatabaseHelper != null)
			DatabaseHelper.log("update()...");

		try {
			PreparedStatement ps = con.prepareStatement("UPDATE student SET "
					+ "name = ?, vorname = ?, geburtstag = ?, masterstudent = ? " + "WHERE mat_nr = ?");
/*
			ps.setString(1, name);
			ps.setString(2, vorname);
			ps.setDate(3, geburtstag);
			ps.setBoolean(4, masterstudent);
			ps.setInt(5, matNr);
*/
			int n = ps.executeUpdate();
			if (n == 1) {
				System.out.println("O.K.,  Datensatz geändert.");
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "O. K.", "Datensatz wurde erfolgreich geändert."));
			} else if (n == 0) {
				System.out.println("Keine Änderung!!");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Datensatz nicht geändert!", "PK-Änderung nicht erlaubt."));
			}

			ps.close();

			// Result set neu aufbauen:
			rs = stm.executeQuery(SQL_SELECT);
		} catch (SQLException ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "SQLException", ex.getLocalizedMessage()));
			System.out.println("Error: " + ex);
			ex.printStackTrace();
		}
	}
	
	
	public String actLogin() {
			
			String sOutcome = null; 
			System.out.println( "actLogin()..." );
			
			kennung = kennung.trim(); 
			pw = pw.trim();
			
			if( kennung.equalsIgnoreCase( "user" ) && 
					pw.equals( "user" ) )
			{
				sOutcome = "user";
				loggedIn = true;
			}
			else if( kennung.equalsIgnoreCase( "admin" ) &&
						pw.equals( "admin" ) )
			{
				sOutcome = "admin"; 
				loggedIn = true;
			}
			else FacesContext.getCurrentInstance().addMessage( null,
					new FacesMessage( FacesMessage.SEVERITY_WARN,
							"Fehler", "Kennung oder PW falsch (user/user oder admin/admin)" ));
			return sOutcome;
		}
	public void aclLogout( ActionEvent ae ) { loggedIn = false; }
}

