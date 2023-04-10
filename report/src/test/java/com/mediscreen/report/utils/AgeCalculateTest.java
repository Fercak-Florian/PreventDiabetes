package com.mediscreen.report.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AgeCalculateTest {

    private AgeCalculate ageCalculate;

    @BeforeEach
    public void init(){
        ageCalculate = new AgeCalculate();
    }

    @Test
    @DisplayName("Test de la methode de calcul de l'age")
    public void testCalculateAge(){
        /*ARRANGE*/
        String dateOfBirth = "1987-07-21";
        /*ACT*/
        int result = ageCalculate.calculate(dateOfBirth);
        /*ASSERT*/
        assertThat(result).isEqualTo(35);
    }
}
