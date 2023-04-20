package com.mediscreen.report.utils;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class RiskLevelDefiner {

    public String define(int age, String sex, int numberOfTriggers) {

        String levelRisk = "";
        if (numberOfTriggers >= 8) {
            numberOfTriggers = 8;
        }

        /*Si le nombre de déclencheurs est 0, quelque soit l'âge ou le sex -> None*/
        if (numberOfTriggers == 0) {
            return "None";
        } else
            /*Si le patient a +30 ans*/
            if (age > 30) {
                switch (numberOfTriggers) {
                    case 2:
                        levelRisk = "Borderline";
                        break;
                    case 6:
                        levelRisk = "In danger";
                        break;
                    /*si le patient a 8 declencheurs ou + */
                    case 8:
                        levelRisk = "Early onset";
                        break;
                    default:
                        levelRisk = "undefined";
                        break;
                }
            } else
                /*si le patient a -30 ans*/
                if (age < 30) {
                    /*si le patient est un homme*/
                    if (sex.contains("M")) {
                        switch (numberOfTriggers) {
                            case 3:
                                levelRisk = "In Danger";
                                break;
                            case 5:
                                levelRisk = "Early onset";
                                break;
                            default:
                                levelRisk = "undefined";
                                break;
                        }
                        /*si le patient est une femme*/
                    } else if (sex.contains("F")) {
                        switch (numberOfTriggers) {
                            case 4:
                                levelRisk = "In Danger";
                                break;
                            case 7:
                                levelRisk = "Early onset";
                                break;
                            default:
                                levelRisk = "undefined";
                                break;
                        }
                    }
                }
        return levelRisk;
    }
}
