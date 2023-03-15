package com.mediscreen.preventdiabetes.controller;

import com.mediscreen.preventdiabetes.model.Patient;
import com.mediscreen.preventdiabetes.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/patient/get")
    public String getPatient(@RequestParam String lastName, Model model) {
        Optional<Patient> optPatient = patientService.getPatient(lastName);
        Patient patient = optPatient.get();
        model.addAttribute("patient", patient);
        log.info("display patient information");
        return "patient/get";
    }

    @GetMapping("/patient/getAll")
    public String getAllPatients(Model model) {
        List<Patient> patients = patientService.getAllPatients();
        model.addAttribute("patients", patients);
        log.info("display patient list");
        return "patient/list";
    }

    @PostMapping("/patient/add")
    public void addPatient(@RequestBody Patient patient) {
        patientService.addPatient(patient);
    }

    @DeleteMapping("/patient/deleteAll")
    public void deleteAllPatients() {
        patientService.deleteAllPatient();
    }
}
