package net.lehre_online.bw;

import static java.lang.System.out;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Named;


@Named 
@jakarta.faces.bean.RequestScoped
public class MbSuchmaske {

    private String immobilienart;
    private String city;
    private String result;

    public void submit() {
        result = "Submitted values: " + immobilienart + ", " + city;
        System.out.println(result);
    }

    public String getText() {
        return immobilienart;
    }

    public void setText(String text) {
        this.immobilienart = text;
    }

    public String getChoice() {
        return city;
    }

    public void setChoice(String choice) {
        this.city = choice;
    }

    public String getResult() {
        return result;
    }
}
