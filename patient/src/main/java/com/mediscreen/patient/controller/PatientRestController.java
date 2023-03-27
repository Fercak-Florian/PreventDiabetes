package com.mediscreen.patient.controller;

import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@Slf4j
@RestController
public class PatientRestController {

    private PatientService patientService;

    public PatientRestController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/api/patient")
    public ResponseEntity<List<Patient>> getPatients() {
        List<Patient> patients = patientService.getPatients();
        if(patients.isEmpty()){
            log.warn("patient list is empty");
            return ResponseEntity.internalServerError().build();
        } else {
            log.info("display patient list");
            return ResponseEntity.ok(patients);
        }
    }

    @GetMapping("/api/patient/{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable("id") String id) {
        try {
            Patient patient = patientService.getPatientById(id);
            log.info("providing patient information");
            return ResponseEntity.ok(patient);
        } catch (Exception e) {
            log.warn("patient not found for this id : " + id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/patient")
    public ResponseEntity<Patient> addPatient(@Valid @RequestBody Patient patient, BindingResult result) {
        if (result.hasErrors()) {
            log.warn("error in input fields");
            return ResponseEntity.badRequest().build();
        } else {
            Patient addedPatient = patientService.addPatient(patient);
            log.info("patient created");
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(addedPatient.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        }
    }

    @PutMapping("/api/patient/{id}")
    public ResponseEntity<Patient> updatePatient(@Valid @RequestBody Patient patient, @PathVariable String id, BindingResult result) {
        if (result.hasErrors()) {
            log.warn("error in input field");
            return ResponseEntity.badRequest().build();
        } else {
            try {
                Patient updatedPatient = patientService.updatePatient(id, patient);
                log.info("patient updated");
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(updatedPatient.getId())
                        .toUri();
                return ResponseEntity.created(location).build();
            } catch (Exception e) {
                log.warn("patient not found for this id : " + id);
                return ResponseEntity.notFound().build();
            }
        }
    }

    @DeleteMapping("/api/patient/{id}")
    public ResponseEntity<Patient> deletePatient(@PathVariable String id) {
        try {
            patientService.deletePatient(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.warn("error during deleting patient with id : " + id);
            return ResponseEntity.notFound().build();
        }
    }
}