package com.mediscreen.report.service;

import com.mediscreen.report.bean.NoteBean;
import com.mediscreen.report.model.Report;
import com.mediscreen.report.proxy.MicroserviceNoteProxy;
import com.mediscreen.report.utils.NoteParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ReportService {

    private NoteParser noteParser;

    private MicroserviceNoteProxy microserviceNoteProxy;

    public ReportService(NoteParser noteParser, MicroserviceNoteProxy microserviceNoteProxy) {
        this.noteParser = noteParser;
        this.microserviceNoteProxy = microserviceNoteProxy;
    }

    public Report initReport() {
        String riskLevel = "Test Level";
        Report report = new Report("Boyd", "Jacob", 40, riskLevel);

        /*-------------- testing if noteParser.count is working --------------*/


//        String note = "Taille Poids Fumeur TriggerOne TriggerTwo TriggerThree";
//        int result = noteParser.count(note);
//        if (result == 3) {
//            log.info("il y a bien " + result + " declencheurs");
//        }
//        return report;

        /*--------- récupération des notes ---------*/
        List<NoteBean> notesBeans = new ArrayList<>();
        try {
            notesBeans = microserviceNoteProxy.getNotesByPatientId("6414bfae21e6ed573c1030f6");
        } catch (Exception e) {
            log.warn("error during retreiving notes");
        }

        /*--------- analyse des notes ---------*/
        int numberOfTriggers = 0;
        for (NoteBean note : notesBeans) {
            int count = noteParser.count(note.getContent());
            if(count > 0){
                numberOfTriggers = numberOfTriggers + count;
            }

        }
        log.info("nombre de declencheurs : " + numberOfTriggers);
        return report;
    }
}
