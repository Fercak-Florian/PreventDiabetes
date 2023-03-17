package com.mediscreen.preventdiabetes.controller;

import com.mediscreen.preventdiabetes.model.Patient;
import com.mediscreen.preventdiabetes.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/")
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

    /**
     * This is an endPoint.
     * This method displays the patient list
     *
     * @param Model Object
     * @return a String which is the path to the HTML page
     */
    @GetMapping("/patient/list")
    public String getAllPatients(Model model) {
        List<Patient> patients = patientService.getAllPatients();
        model.addAttribute("patients", patients);
        log.info("display patient list");
        return "patient/list";
    }

    /**
     * This is an endPoint.
     * This method displays the form to add a Patient
     *
     * @param Model Object
     * @return a String which is the path to the HTML page
     */
    @GetMapping("/patient/add")
    public String showAddPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        log.info("display form to add a patient");
        return "patient/add";
    }

    @PostMapping("/patient/validate")
    public String validatePatient(@ModelAttribute Patient patient){
        patientService.addPatient(patient);
        return "redirect:/patient/list";
    }

    @GetMapping("/patient/update/{lastName}")
    public String showUpdatePatientForm(@PathVariable("lastName") String lastName, Model model){
        log.info("le nom de famille est : " + lastName);
        Optional<Patient> optPatient = patientService.getPatient(lastName);
        Patient patient = optPatient.get();
        model.addAttribute("patient", patient);
        return "patient/update";
    }

    @PostMapping("/patient/update/{lastName}")
    public String updatePatient(@PathVariable("lastName") String lastName, @Valid Patient patient){
        patientService.updatePatient(lastName, patient);
        return "redirect:/patient/list";
    }

    @GetMapping("patient/delete/{id}")
    public String deletePatient(@PathVariable("id") String id){
        patientService.deletePatient(id);
        return "redirect:/patient/list";
    }

    @GetMapping("/patient/deleteAll")
    public String deleteAllPatients() {
        patientService.deleteAllPatient();
        return "redirect:/patient/list";
    }
}
