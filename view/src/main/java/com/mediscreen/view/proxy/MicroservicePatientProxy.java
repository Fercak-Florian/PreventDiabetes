package com.mediscreen.view.proxy;

import com.mediscreen.view.bean.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "patient", url = "localhost:8081")
public interface MicroservicePatientProxy {

    /*SIGNATURES DES METHODES PRESENTES DANS PatientController*/

    @GetMapping("/api/patient")
    List<PatientBean> getPatients();

    @GetMapping("/api/patient/{id}")
    PatientBean getPatient(@PathVariable("id") String id);

}
