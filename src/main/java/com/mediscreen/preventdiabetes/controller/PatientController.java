package com.mediscreen.preventdiabetes.controller;

import com.mediscreen.preventdiabetes.model.Patient;
import com.mediscreen.preventdiabetes.service.PatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patient/get")
    public Optional<Patient> getPatient(@RequestParam String lastName){
        return patientService.getPatient(lastName);
    }

    @GetMapping("/patient/getAll")
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @PostMapping("/patient/add")
    public void addPatient(@RequestBody Patient patient) {
        patientService.addPatient(patient);
    }

    @DeleteMapping("/patient/deleteAll")
    public void deleteAllPatients(){
        patientService.deleteAllPatient();
    }
}
