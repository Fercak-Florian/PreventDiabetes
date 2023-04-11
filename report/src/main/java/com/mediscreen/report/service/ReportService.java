package com.mediscreen.report.service;

import com.mediscreen.report.bean.NoteBean;
import com.mediscreen.report.bean.PatientBean;
import com.mediscreen.report.model.Report;
import com.mediscreen.report.proxy.MicroserviceNoteProxy;
import com.mediscreen.report.proxy.MicroservicePatientProxy;
import com.mediscreen.report.utils.AgeCalculate;
import com.mediscreen.report.utils.NoteParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ReportService {

    private NoteParser noteParser;

    private AgeCalculate ageCalculate;

    private MicroserviceNoteProxy microserviceNoteProxy;

    private MicroservicePatientProxy microservicePatientProxy;

    public ReportService(NoteParser noteParser, AgeCalculate ageCalculate, MicroserviceNoteProxy microserviceNoteProxy, MicroservicePatientProxy microservicePatientProxy) {
        this.noteParser = noteParser;
        this.ageCalculate = ageCalculate;
        this.microserviceNoteProxy = microserviceNoteProxy;
        this.microservicePatientProxy = microservicePatientProxy;
    }

    public Report initReport() {
        String riskLevel = "Test Level";
//        Report fakeReport = new Report("Boyd", "Jacob", 40, riskLevel);


        /*--------- récupération du patient ---------*/
        PatientBean patientBean;
        try {
            patientBean = microservicePatientProxy.getPatientById("64355898701db7761b367706");
        } catch (Exception e){
            patientBean = null;
            log.warn("error during retreiving patient");
        }


        /*--------- récupération des notes ---------*/
        List<NoteBean> notesBeans = new ArrayList<>();
        try {
            notesBeans = microserviceNoteProxy.getNotesByPatientId("64355898701db7761b367706");
        } catch (Exception e) {
            notesBeans = null;
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
        return new Report(patientBean.getLastName(), patientBean.getFirstName(), ageCalculate.calculate(patientBean.getDob()), riskLevel);
    }
}
