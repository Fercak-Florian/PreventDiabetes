package com.mediscreen.report.utils;

import com.mediscreen.report.bean.NoteBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class NoteParserTest {

    private NoteParser noteParser;

    private TriggerTerms triggerTerms;


    @BeforeEach
    public void init() {

        List<String> triggers = new ArrayList<>();

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

        triggerTerms = new TriggerTerms(triggers);
        noteParser = new NoteParser(triggerTerms);

    }

    @Test
    @DisplayName("Méthode trouve 2 déclencheurs")
    public void testCountFindTwoTriggersTerms() {
        /*ARRANGE*/
        List<NoteBean> notesBeans = new ArrayList<>();
        NoteBean noteBean = new NoteBean("aaa", 1, "2023-04-23", "Taille Poids TriggerOne TriggerTwo TriggerThree");
        notesBeans.add(noteBean);

        /*ACT*/
        int result = noteParser.count(notesBeans);

        /*ASSERT*/
        assertThat(result).isEqualTo(2);
    }
}
