package com.mediscreen.report.utils;

import com.mediscreen.report.bean.NoteBean;
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


    public int count(List<NoteBean> notesBeans) {
        int count = 0;

        for(NoteBean noteBean : notesBeans){

            String noteContent = noteBean.getContent();

            String noteInUpperCase = noteContent.toUpperCase();

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
        }


        return count;
    }
}
