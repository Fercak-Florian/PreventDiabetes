package com.mediscreen.report.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class NoteParserTest {

    private NoteParser noteParser;

    @Mock
    private TriggerTerms triggerTerms;

    private List<String> triggers = new ArrayList<>();

    @BeforeEach
    public void init() {
        noteParser = new NoteParser(triggerTerms);


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
        when(triggerTerms.getTriggers()).thenReturn(triggers);
    }

    @Disabled
    @Test
    @DisplayName("Méthode trouve 2 déclencheurs")
    public void testCountFindTwoTriggersTerms() {
        /*ARRANGE*/
        String note = "Taille Poids TriggerOne TriggerTwo TriggerThree";
        /*ACT*/
        int result = noteParser.count(note);
        /*ASSERT*/
        assertThat(result).isEqualTo(2);
    }
}
