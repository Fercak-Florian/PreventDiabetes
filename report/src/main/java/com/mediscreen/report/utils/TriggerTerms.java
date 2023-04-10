package com.mediscreen.report.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@AllArgsConstructor
public class TriggerTerms {

    private List<String> triggers = new ArrayList<>();

    public TriggerTerms(){
        initTriggerTerms();
    }


    public List<String> initTriggerTerms() {
        triggers.add("Hémoglobine A1C");
        triggers.add("Microalbumine");
        triggers.add("Taille");
        triggers.add("Poids");
        triggers.add("Fumeur");
        triggers.add("Anormal");
        triggers.add("Cholestérol");
        triggers.add("Vertige");
        triggers.add("Rechute");
        triggers.add("Réaction");
        triggers.add("Anticorps");
        return triggers;
    }
}
