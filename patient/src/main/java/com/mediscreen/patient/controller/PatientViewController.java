package com.mediscreen.patient.controller;

import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import com.mediscreen.patient.utils.FormComment;
import com.mediscreen.patient.utils.LightPatient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class PatientViewController {

    private PatientService patientService;
    private FormComment formComment;

    public PatientViewController(PatientService patientService, FormComment formComment) {
        this.patientService = patientService;
        this.formComment = formComment;
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
        model.addAttribute("formComment", formComment);
        log.info("display patient information");
        return "patient/search";
    }

    @PostMapping("/patient/get")
    public String getPatient(@Valid LightPatient lightPatient, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formComment", formComment);
            log.warn("error in user input");
            return "patient/search";
        } else {
            Optional<Patient> optionalPatient = patientService.getPatient(lightPatient.getLastName(), lightPatient.getFirstName());
            if (optionalPatient.isEmpty()) {
                log.warn("patient does not exist");
                formComment.setError(true);
                formComment.setMessage("Patient does not exist");
                model.addAttribute("formComment", formComment);
                return "patient/search";
            } else {
                Patient patient = optionalPatient.get();
                model.addAttribute("patient", patient);
                log.info("display patient information");
                return "patient/get";
            }
        }
    }

    @GetMapping("/patient/list")
    public String getPatients(Model model) {
        List<Patient> patients = patientService.getPatients();
        model.addAttribute("patients", patients);
        log.info("display patient list");
        return "patient/list";
    }

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
        Patient patient = patientService.getPatientById(id);
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
}
