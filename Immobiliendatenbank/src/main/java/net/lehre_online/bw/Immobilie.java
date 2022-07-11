package net.lehre_online.bw;

import java.io.Serializable;

/**
 * 
 * @author Philipp Kionke und Magdalena Czerwinska
 * In der Immobilie Klasse wird in zusammenarbeit mit der CarouselView und ImmobilieService die Rückgabe 
 * der passenden Immobilien an die resultBoard.xhtml Seite geregelt.
 *
 */

public class Immobilie implements Serializable {

	/**
	 * alle benötigten variablen werden angelegt.
	 */
    private int id_new;
    private String adress_new;
    private String stadt_new;
	private String immobilienart_new;
	private String hausnummer_new;
	private int preis_new;
	private int plz_new;
	private int grundflaeche_new;
	private int wohnflaeche_new;
	private String beschreibung_new;
    

    public Immobilie() {
    }

    public Immobilie(int id_new, String adress_new, String stadt_new, String immobilienart_new, String hausnummer_new, int preis_new,
    		int plz_new, int grundflaeche_new, int wohnflaeche_new, String beschreibung_new) {
        this.id_new = id_new;
        this.adress_new = adress_new;
        this.stadt_new = stadt_new;
    	this.immobilienart_new = immobilienart_new;
    	this.hausnummer_new = hausnummer_new;
    	this.preis_new = preis_new;
    	this.plz_new = plz_new;
    	this.grundflaeche_new = grundflaeche_new;
    	this.wohnflaeche_new = wohnflaeche_new;
    	this.beschreibung_new = beschreibung_new;
        
    }

    /**
     * Im Folgenden werden alle Getter und Setter für die jeweiligen Felder/ wie z. B. die Adresse instanziirt.
     */
    @Override
    public Immobilie clone() {
        return new Immobilie(getId_new(), getadress_new(), getStadt_new(), getImmobilienart_new(), getHausnummer_new(), getPreis_new(),
        		getPlz_new(), getGrundflaeche_new(), getWohnflaeche_new(), getBeschreibung_new());
    }

    public int getId_new() {
        return id_new;
    }

    public void setId_new(int id_new) {
        this.id_new = id_new;
    }

    public String getadress_new() {
        return adress_new;
    }

    public void setadress_new(String adress_new) {
        this.adress_new = adress_new;
    }
    
    public String getStadt_new() {
		return stadt_new;
	}

	public void setStadt_new(String stadt_new) {
		this.stadt_new = stadt_new;
	}

	public String getImmobilienart_new() {
		return immobilienart_new;
	}

	public void setImmobilienart_new(String immobilienart_new) {
		this.immobilienart_new = immobilienart_new;
	}

	public String getHausnummer_new() {
		return hausnummer_new;
	}

	public void setHausnummer_new(String hausnummer_new) {
		this.hausnummer_new = hausnummer_new;
	}

	public int getPreis_new() {
		return preis_new;
	}

	public void setPreis_new(int preis_new) {
		this.preis_new = preis_new;
	}

	public int getPlz_new() {
		return plz_new;
	}

	public void setPlz_new(int plz_new) {
		this.plz_new = plz_new;
	}

	public int getGrundflaeche_new() {
		return grundflaeche_new;
	}

	public void setGrundflaeche_new(int grundflaeche_new) {
		this.grundflaeche_new = grundflaeche_new;
	}

	public int getWohnflaeche_new() {
		return wohnflaeche_new;
	}

	public void setWohnflaeche_new(int wohnflaeche_new) {
		this.wohnflaeche_new = wohnflaeche_new;
	}

	public String getBeschreibung_new() {
		return beschreibung_new;
	}

	public void setBeschreibung_new(String beschreibung_new) {
		this.beschreibung_new = beschreibung_new;
	}

	
}