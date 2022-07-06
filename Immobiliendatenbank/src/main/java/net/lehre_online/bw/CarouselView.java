package net.lehre_online.bw;

import java.io.Serializable;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class CarouselView implements Serializable {

    private List<Immobilie> immobilien;

    

    @Inject
    private ImmobilieService service;

    @PostConstruct
    public void init() {
        immobilien = service.getImmobilien();
        
    }

    public List<Immobilie> getImmobilien() {
        return immobilien;
    }

    public void setService(ImmobilieService service) {
        this.service = service;
    }

   
}