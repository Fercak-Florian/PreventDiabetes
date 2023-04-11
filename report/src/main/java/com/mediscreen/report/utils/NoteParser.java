package com.mediscreen.report.utils;

import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class NoteParser {

    private List<String> triggers;

    private TriggerTerms triggerTerms;

    public NoteParser(TriggerTerms triggerTerms) {
        this.triggerTerms = triggerTerms;
        triggers = triggerTerms.getTriggers();
    }


    public int count(String note) {
        int count = 0;
        String noteInUpperCase = note.toUpperCase();
        System.out.println("Contenu de la note en UPPERCASE : " + noteInUpperCase);

        String noteWhithoutWhitespace = noteInUpperCase.replaceAll("\\s", "");
        System.out.println("Contenu de la note en UPPERCASE et SANS WHITESPACE : " + noteWhithoutWhitespace);

        for (String trigger : triggers) {

            /*trigger en UPPERCASE*/
            String triggerInUpperCase = trigger.toUpperCase();
            System.out.println("Mise en forme du trigger en UPPERCASE : " + triggerInUpperCase);

            /*suppression des whitespace du trigger*/
            String correctTrigger = triggerInUpperCase.replaceAll("\\s", "");
            System.out.println("Mise en forme du trigger en sans les whitespace : " + correctTrigger);

            while (noteWhithoutWhitespace.contains(correctTrigger)) {
                System.out.println("declencheur rencontré : " + correctTrigger);
                /*suppression du trigger de la chaine*/
                noteWhithoutWhitespace = noteWhithoutWhitespace.replace(correctTrigger, "");
                System.out.println("note sans le trigger : " + noteWhithoutWhitespace);
                count++;
            }
        }
        return count;
    }
}
