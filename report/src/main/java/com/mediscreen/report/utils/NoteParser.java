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

        String noteWhithoutWhitespace = noteInUpperCase.replaceAll("\\s", "");

        for (String trigger : triggers) {

            /*trigger en UPPERCASE*/
            String triggerInUpperCase = trigger.toUpperCase();

            /*suppression des whitespace du trigger*/
            String correctTrigger = triggerInUpperCase.replaceAll("\\s", "");

            while (noteWhithoutWhitespace.contains(correctTrigger)) {
                /*suppression du trigger de la chaine*/
                noteWhithoutWhitespace = noteWhithoutWhitespace.replace(correctTrigger, "");
                count++;
            }
        }
        return count;
    }
}
