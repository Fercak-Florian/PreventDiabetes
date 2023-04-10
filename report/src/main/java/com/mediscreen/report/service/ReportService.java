package com.mediscreen.report.service;

import com.mediscreen.report.model.Report;
import com.mediscreen.report.utils.NoteParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReportService {

    private NoteParser noteParser;

    public ReportService(NoteParser noteParser) {
        this.noteParser = noteParser;
    }

    public Report initReport() {
        String riskLevel = "Test Level";
        Report report = new Report("Boyd", "Jacob", 40, riskLevel);

        /*-------------- testing if noteParser.count is working --------------*/


        String note = "Taille Poids Fumeur TriggerOne TriggerTwo TriggerThree";
        int result = noteParser.count(note);
        if (result == 3) {
            log.info("il y a bien " + result + " declencheurs");
        }
        return report;
    }
}
