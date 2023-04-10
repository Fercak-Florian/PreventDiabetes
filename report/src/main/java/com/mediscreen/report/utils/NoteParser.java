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
        for (String trigger : triggers) {
            if (note.contains(trigger)) {
                System.out.println("declencheur rencontré : " + trigger);
                count++;
            }
        }
        return count;
    }
}
