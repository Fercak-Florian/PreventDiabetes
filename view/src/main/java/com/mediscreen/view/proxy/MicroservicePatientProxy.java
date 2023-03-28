package com.mediscreen.view.proxy;

import com.mediscreen.view.bean.LightPatientBean;
import com.mediscreen.view.bean.PatientBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "patient", url = "localhost:8081")
public interface MicroservicePatientProxy {

    /*SIGNATURES DES METHODES PRESENTES DANS PatientController*/

    @GetMapping("/api/patient")
    List<PatientBean> getPatients();

    @GetMapping("/api/patient/{id}")
    PatientBean getPatient(@PathVariable("id") String id);

    @PostMapping("/api/patient/lightPatient")
    PatientBean getPatientByFirstNameAndLastName(@RequestBody LightPatientBean lightPatientBean);

    @PostMapping("/api/patient")
    PatientBean addPatient(@RequestBody PatientBean patientBean);

    @PutMapping("/api/patient/{id}")
    PatientBean updatePatient(@RequestBody PatientBean patientBean, @PathVariable String id);

    @DeleteMapping("/api/patient/{id}")
    PatientBean deletePatient(@PathVariable String id);
}
