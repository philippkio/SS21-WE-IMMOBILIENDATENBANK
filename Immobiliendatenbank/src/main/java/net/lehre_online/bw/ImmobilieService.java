package net.lehre_online.bw;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class ImmobilieService {

    private List<Immobilie> immobilien;


    public ImmobilieService(List<Immobilie> immobilienList) {
        immobilien = immobilienList;
    }
    
    
    public List<Immobilie> getImmobilien() {
        return this.immobilien;
    }

    public List<Immobilie> getImmobilien(int size) {

        if (size > immobilien.size()) {
            Random rand = new Random();

            List<Immobilie> randomList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                int randomIndex = rand.nextInt(immobilien.size());
                randomList.add(immobilien.get(randomIndex));
            }

            return randomList;
        }

        else {
            return new ArrayList<>(immobilien.subList(0, size));
        }

    }

    public List<Immobilie> getClonedImmobilien(int size) {
        List<Immobilie> results = new ArrayList<>();
        List<Immobilie> originals = getImmobilien(size);
        for (Immobilie original : originals) {
            results.add(original.clone());
        }
        return results;
    }
}