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
    @DisplayName("+30 ans, homme, 0 déclencheurs : None")
    public void testMoreThan30_M_0_Triggers_Should_Return_None(){
        /*ARRANGE*/
        int age = 40;
        String sex = "M";
        int numberOfTriggers = 0;

        /*ACT*/
        String result = riskLevelDefiner.define(age, sex, numberOfTriggers);

        /*ASSERT*/
        assertThat(result).isEqualTo("None");
    }

    @Test
    @DisplayName("+30 ans, homme, 2 déclencheurs : Borderline")
    public void testMoreThan30_M_2_Triggers_Should_Return_Borderline(){
        /*ARRANGE*/
        int age = 40;
        String sex = "M";
        int numberOfTriggers = 2;

        /*ACT*/
        String result = riskLevelDefiner.define(age, sex, numberOfTriggers);

        /*ASSERT*/
        assertThat(result).isEqualTo("Borderline");
    }

    @Test
    @DisplayName("+30 ans, femme, 2 déclencheurs : Borderline")
    public void testMoreThan30_F_2_Triggers_Should_Return_Borderline(){
        /*ARRANGE*/
        int age = 40;
        String sex = "F";
        int numberOfTriggers = 2;

        /*ACT*/
        String result = riskLevelDefiner.define(age, sex, numberOfTriggers);

        /*ASSERT*/
        assertThat(result).isEqualTo("Borderline");
    }

    @Test
    @DisplayName("+30 ans, homme, 6 déclencheurs : In danger")
    public void testMoreThan30_M_6_Triggers_Should_Return_In_Danger(){
        /*ARRANGE*/
        int age = 40;
        String sex = "M";
        int numberOfTriggers = 6;

        /*ACT*/
        String result = riskLevelDefiner.define(age, sex, numberOfTriggers);

        /*ASSERT*/
        assertThat(result).isEqualTo("In danger");
    }

    @Test
    @DisplayName("+30 ans, femme, 6 déclencheurs : In danger")
    public void testMoreThan30_F_6_Triggers_Should_Return_In_Danger(){
        /*ARRANGE*/
        int age = 40;
        String sex = "F";
        int numberOfTriggers = 6;

        /*ACT*/
        String result = riskLevelDefiner.define(age, sex, numberOfTriggers);

        /*ASSERT*/
        assertThat(result).isEqualTo("In danger");
    }

    @Test
    @DisplayName("+30 ans, homme, 9 déclencheurs : Early onset")
    public void testMoreThan30_M_9_Triggers_Should_Return_Early_Onset(){
        /*ARRANGE*/
        int age = 40;
        String sex = "M";
        int numberOfTriggers = 9;

        /*ACT*/
        String result = riskLevelDefiner.define(age, sex, numberOfTriggers);

        /*ASSERT*/
        assertThat(result).isEqualTo("Early onset");
    }

    @Test
    @DisplayName("+30 ans, homme, 9 déclencheurs : Early onset")
    public void testMoreThan30_F_9_Triggers_Should_Return_Early_Onset(){
        /*ARRANGE*/
        int age = 40;
        String sex = "F";
        int numberOfTriggers = 9;

        /*ACT*/
        String result = riskLevelDefiner.define(age, sex, numberOfTriggers);

        /*ASSERT*/
        assertThat(result).isEqualTo("Early onset");
    }

    @Test
    @DisplayName("-30 ans, femme, 4 déclencheurs : In danger")
    public void testLessThan30_F_4_Triggers_Should_Return_In_Danger(){
        /*ARRANGE*/
        int age = 20;
        String sex = "F";
        int numberOfTriggers = 4;

        /*ACT*/
        String result = riskLevelDefiner.define(age, sex, numberOfTriggers);

        /*ASSERT*/
        assertThat(result).isEqualTo("In danger");
    }

    @Test
    @DisplayName("-30 ans, femme, 7 déclencheurs : Early onset")
    public void testLessThan30_F_7_Triggers_Should_Return_Early_onset(){
        /*ARRANGE*/
        int age = 20;
        String sex = "F";
        int numberOfTriggers = 7;

        /*ACT*/
        String result = riskLevelDefiner.define(age, sex, numberOfTriggers);

        /*ASSERT*/
        assertThat(result).isEqualTo("Early onset");
    }

    @Test
    @DisplayName("-30 ans, homme, 3 déclencheurs : In danger")
    public void testLessThan30_M_3_Triggers_Should_Return_In_Danger(){
        /*ARRANGE*/
        int age = 20;
        String sex = "M";
        int numberOfTriggers = 3;

        /*ACT*/
        String result = riskLevelDefiner.define(age, sex, numberOfTriggers);

        /*ASSERT*/
        assertThat(result).isEqualTo("In danger");
    }

    @Test
    @DisplayName("-30 ans, homme, 5 déclencheurs : Early onset")
    public void testLessThan30_M_5_Triggers_Should_Return_Early_Onset(){
        /*ARRANGE*/
        int age = 20;
        String sex = "M";
        int numberOfTriggers = 5;

        /*ACT*/
        String result = riskLevelDefiner.define(age, sex, numberOfTriggers);

        /*ASSERT*/
        assertThat(result).isEqualTo("Early onset");
    }

    @Test
    @DisplayName("+30 ans, homme, 3 déclencheurs : Undefined")
    public void testMoreThan30_M_3_Triggers_Should_Return_Undefined(){
        /*ARRANGE*/
        int age = 40;
        String sex = "M";
        int numberOfTriggers = 3;

        /*ACT*/
        String result = riskLevelDefiner.define(age, sex, numberOfTriggers);

        /*ASSERT*/
        assertThat(result).isEqualTo("undefined");
    }

    @Test
    @DisplayName("-30 ans, femme, 5 déclencheurs : Undefined")
    public void testLessThan30_F_5_Triggers_Should_Return_Undefined(){
        /*ARRANGE*/
        int age = 20;
        String sex = "F";
        int numberOfTriggers = 5;

        /*ACT*/
        String result = riskLevelDefiner.define(age, sex, numberOfTriggers);

        /*ASSERT*/
        assertThat(result).isEqualTo("undefined");
    }

    @Test
    @DisplayName("-30 ans, homme, 4 déclencheurs : Undefined")
    public void testLessThan30_M_4_Triggers_Should_Return_Undefined(){
        /*ARRANGE*/
        int age = 20;
        String sex = "M";
        int numberOfTriggers = 4;

        /*ACT*/
        String result = riskLevelDefiner.define(age, sex, numberOfTriggers);

        /*ASSERT*/
        assertThat(result).isEqualTo("undefined");
    }
}
