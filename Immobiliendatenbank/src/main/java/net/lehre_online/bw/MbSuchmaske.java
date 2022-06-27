package net.lehre_online.bw;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.PreparedStatement;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Named;

/**
 * Backing bean der JSF-Seite browse.xhtml
 *
 * @author Wolfgang Lang
 * @version 2021-07-30
 * @see "Foliensatz zur Vorlesung"
 */
@Named
@SessionScoped
public class MbSuchmaske implements Serializable {
	
	


	private static final long serialVersionUID = 1L;

	final String SQL_SELECT = "select ID, city, adress, housenumber, living_area, lot_size, price, description, picture, zip, property_type "
			+ "immobilien from db";

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

	private String immobilienart = null;
	private String stadt;
	private int minGrundflaeche;
	private int maxGrundflaeche;
	private int minWohnflaeche;
	private int maxWohnflaeche;

	/*--------------------------------------------------------------------------*/
	public MbSuchmaske() {
		System.out.println("MbSuchmaske.<init>...");
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

	/*--------------------------------------------------------------------------*/

	private void showData() throws SQLException {
		setImmobilienart(rs.getString("property_type"));
		setStadt(rs.getString("city"));
		//setVorname(rs.getString("vorname"));
		//setGeburtstag(rs.getDate("geburtstag"));
		//setMasterstudent(rs.getBoolean("masterstudent"));
	}

	/*--------------------------------------------------------------------------*/

	/**
	 * Verbindung zur Datenbank herstellen und disconnect button und browse buttons
	 * freigeben
	 * 
	 * @param ae ActionEvent
	 */
	public void connect(ActionEvent ae) {

		// out.println( "connect()..." );

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
	}

	/*--------------------------------------------------------------------------*/

	/**
	 * Verbindung zur Datenbank beenden
	 * 
	 * @param ae ActionEvent
	 */
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

		try {
			// if( ps == null ){
			String sQl = "INSERT INTO immobilien( " + " city ) "
					+ "VALUES ( ? )";
			PreparedStatement ps = con.prepareStatement(sQl);
			// }

			ps.setString(1, "Test");
			//ps.setString(2, name);
			//ps.setString(3, vorname);
			//ps.setDate(4, geburtstag);
			//ps.setBoolean(5, masterstudent);

			int n = ps.executeUpdate();
			if (n == 1) {
				System.out.println("O.K.,  Datensatz eingefügt.");
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "O. K.", "Ein Datensatz erfolgreich eingefügt."));
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
}

/*
 * import static java.lang.System.out;
 * 
 * import java.io.Serializable;
 * 
 * 
 * import jakarta.faces.event.ActionEvent;
 * 
 * 
 * import jakarta.enterprise.context.RequestScoped; import jakarta.inject.Named;
 * 
 * 
 * @Named
 * 
 * @RequestScoped public class MbSuchmaske {
 * 
 * private String immobilienart = null; private String immobilienart1 = null;
 * private String immobilienart2 = null; private String immobilienart3 = null;
 * private String immobilienart4 = null; private String immobilienart5 = null;
 * private String stadt; private String result; private int minGrundflaeche;
 * private int maxGrundflaeche; private int minWohnflaeche; private int
 * maxWohnflaeche; public String getStadt() { return stadt; }
 * 
 * public void setStadt(String stadt) { this.stadt = stadt; }
 * 
 * public int getMinGrundflaeche() { return minGrundflaeche; }
 * 
 * public void setMinGrundflaeche(int minGrundflaeche) { this.minGrundflaeche =
 * minGrundflaeche; }
 * 
 * public int getMaxGrundflaeche() { return maxGrundflaeche; }
 * 
 * public void setMaxGrundflaeche(int maxGrundflaeche) { this.maxGrundflaeche =
 * maxGrundflaeche; }
 * 
 * public int getMinWohnflaeche() { return minWohnflaeche; }
 * 
 * public void setMinWohnflaeche(int minWohnflaeche) { this.minWohnflaeche =
 * minWohnflaeche; }
 * 
 * public int getMaxWohnflaeche() { return maxWohnflaeche; }
 * 
 * public void setMaxWohnflaeche(int maxWohnflaeche) { this.maxWohnflaeche =
 * maxWohnflaeche; }
 * 
 * 
 * 
 * private int immobilienpreis;
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * public int getImmobilienpreis() { return immobilienpreis; }
 * 
 * public void setImmobilienpreis(int immobilienpreis) { this.immobilienpreis =
 * immobilienpreis; }
 * 
 * public void submit() { if (immobilienart1 != null) { result =
 * "Angegebene Immobilienart: " + immobilienart1; System.out.println(result);}
 * else { System.out.println("fail");} }
 * 
 * 
 * 
 * 
 * 
 * public String getImmobilienart1() { return immobilienart1; }
 * 
 * public void setImmobilienart1(String immobilienart1) { this.immobilienart1 =
 * immobilienart1; }
 * 
 * public String getImmobilienart2() { return immobilienart2; }
 * 
 * public void setImmobilienart2(String immobilienart2) { this.immobilienart2 =
 * immobilienart2; }
 * 
 * public String getImmobilienart3() { return immobilienart3; }
 * 
 * public void setImmobilienart3(String immobilienart3) { this.immobilienart3 =
 * immobilienart3; }
 * 
 * public String getImmobilienart4() { return immobilienart4; }
 * 
 * public void setImmobilienart4(String immobilienart4) { this.immobilienart4 =
 * immobilienart4; }
 * 
 * public String getImmobilienart5() { return immobilienart5; }
 * 
 * public void setImmobilienart5(String immobilienart5) { this.immobilienart5 =
 * immobilienart5; }
 * 
 * public String getSubmit() { System.out.println(immobilienart1); return
 * result; }
 * 
 * public void ausgabeImmo() {
 * 
 * System.out.println(immobilienart1); }
 * 
 * public String getImmobilienart() { return immobilienart; }
 * 
 * public void setImmobilienart(String immobilienart) { this.immobilienart =
 * immobilienart; }
 * 
 * 
 * }
 */