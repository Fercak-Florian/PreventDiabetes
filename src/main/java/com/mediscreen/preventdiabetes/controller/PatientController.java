package com.mediscreen.preventdiabetes.controller;

import com.mediscreen.preventdiabetes.model.Patient;
import com.mediscreen.preventdiabetes.service.PatientService;
import com.mediscreen.preventdiabetes.utils.LightPatient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
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
    public String home() {
        return "home";
    }


    @GetMapping("/patient/search")
    public String showSearchPatientForm(Model model) {
        LightPatient lightPatient = new LightPatient();
        Patient patient = new Patient();
        model.addAttribute("lightPatient", lightPatient);
        log.info("display patient information");
        return "patient/search";
    }

    @PostMapping("/patient/get")
    public String getPatient(@Valid LightPatient lightPatient, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.warn("error in user input");
            return "patient/search";
        } else {
            Optional<Patient> optPatient = patientService.getPatient(lightPatient.getLastName(), lightPatient.getFirstName());
            Patient patient = optPatient.get();
            model.addAttribute("patient", patient);
            log.info("display patient information");
            return "patient/get";
        }
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
    public String validatePatient(@Valid Patient patient, BindingResult result) {
        if (result.hasErrors()) {
            log.warn("patient not saved");
            return "patient/add";
        } else {
            patientService.addPatient(patient);
            log.info("patient saved");
            return "redirect:/patient/list";
        }
    }

    @GetMapping("/patient/update/{id}")
    public String showUpdatePatientForm(@PathVariable("id") String id, Model model) {
        Optional<Patient> optPatient = patientService.getPatientById(id);
        Patient patient = optPatient.get();
        model.addAttribute("patient", patient);
        return "patient/update";
    }

    @PostMapping("/patient/update/{id}")
    public String updatePatient(@PathVariable("id") String id, @Valid Patient patient, BindingResult result) {
        if (result.hasErrors()) {
            log.warn("patient not updated");
            return "patient/update";
        } else {
            patientService.updatePatient(id, patient);
            return "redirect:/patient/list";
        }
    }

    @GetMapping("patient/delete/{id}")
    public String deletePatient(@PathVariable("id") String id) {
        patientService.deletePatient(id);
        return "redirect:/patient/list";
    }

    @GetMapping("/patient/deleteAll")
    public String deleteAllPatients() {
        patientService.deleteAllPatient();
        return "redirect:/patient/list";
    }
}
