package com.mediscreen.report.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RiskLevelDefinerTest {

    private RiskLevelDefiner riskLevelDefiner;

    @BeforeEach
    public void init(){
        riskLevelDefiner = new RiskLevelDefiner();
    }

    @Test
    @DisplayName("Attente d'un niveau de risque : Borderline")
    public void testDefineReturnBorderline(){
        /*ARRANGE*/
        int age = 40;
        String sex = "M";
        int numberOfTriggers = 2;

        /*ACT*/
        String result = riskLevelDefiner.define(age, sex, numberOfTriggers);

        /*ASSERT*/
        assertThat(result).isEqualTo("Borderline");
    }
}
