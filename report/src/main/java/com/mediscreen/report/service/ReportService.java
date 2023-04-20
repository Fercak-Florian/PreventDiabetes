package com.mediscreen.report.service;

import com.mediscreen.report.bean.NoteBean;
import com.mediscreen.report.bean.PatientBean;
import com.mediscreen.report.exception.NoteNotFoundException;
import com.mediscreen.report.exception.PatientNotFoundException;
import com.mediscreen.report.model.Report;
import com.mediscreen.report.proxy.MicroserviceNoteProxy;
import com.mediscreen.report.proxy.MicroservicePatientProxy;
import com.mediscreen.report.utils.AgeCalculate;
import com.mediscreen.report.utils.NoteParser;
import com.mediscreen.report.utils.RiskLevelDefiner;
import com.mediscreen.report.utils.SubReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ReportService {

    private NoteParser noteParser;

    private AgeCalculate ageCalculate;

    private RiskLevelDefiner riskLevelDefiner;

    private MicroserviceNoteProxy microserviceNoteProxy;

    private MicroservicePatientProxy microservicePatientProxy;

    public ReportService(NoteParser noteParser, AgeCalculate ageCalculate, RiskLevelDefiner riskLevelDefiner, MicroserviceNoteProxy microserviceNoteProxy, MicroservicePatientProxy microservicePatientProxy) {
        this.noteParser = noteParser;
        this.ageCalculate = ageCalculate;
        this.riskLevelDefiner = riskLevelDefiner;
        this.microserviceNoteProxy = microserviceNoteProxy;
        this.microservicePatientProxy = microservicePatientProxy;
    }

    public Report getReport(int id) {

        /*--------- récupération du patient ---------*/
        PatientBean patientBean;
        try {
            patientBean = microservicePatientProxy.getPatientById(id);
        } catch (PatientNotFoundException e) {
            patientBean = null;
            log.warn("error during retreiving patient");
        }


        /*--------- récupération des notes ---------*/
        List<NoteBean> notesBeans = new ArrayList<>();
        try {
            notesBeans = microserviceNoteProxy.getNotesByPatientId(id);
        } catch (NoteNotFoundException e) {
            notesBeans = null;
            log.warn("error during retreiving notes");
        }

        if (patientBean == null || notesBeans == null) {
            return null;
        }

        /*--------- analyse des notes ---------*/
        int numberOfTriggers = 0;
        for (NoteBean note : notesBeans) {
            int count = noteParser.count(note.getContent());
            if (count > 0) {
                numberOfTriggers = numberOfTriggers + count;
            }
        }

        /*--------- définition du niveau de risque ---------*/
        int age = ageCalculate.calculate(patientBean.getDob());
        String sex = patientBean.getSex();
        String riskLevel = riskLevelDefiner.define(age, sex, numberOfTriggers);

        /*--------- paramètres de génération du rapport ---------*/
        SubReport subReport = new SubReport(sex, numberOfTriggers, age);

        log.info("nombre de declencheurs : " + numberOfTriggers);

        /*--------- construction et retour du rapport ---------*/
        return new Report(patientBean.getLastName(), patientBean.getFirstName(), age, riskLevel, subReport);
    }
}
