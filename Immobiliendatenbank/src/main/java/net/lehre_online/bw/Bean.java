package net.lehre_online.bw;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.PreparedStatement;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Named;


@Named // Use @javax.faces.bean.ManagedBean on outdated environments.
@RequestScoped // Use @javax.faces.bean.RequestScoped on outdated environments.
public class Bean {
	
    private String text;
    private String choice;
    private String result;

    public void deineMutter() {
    	System.out.println("blabla");
    }
    public void submit() {
        result = "Submitted values: " + text + ", " + choice;
        System.out.println(result);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getResult() {
        return result;
    }
}
