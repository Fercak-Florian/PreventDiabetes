package com.mediscreen.patient.controller;

import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import com.mediscreen.patient.utils.LightPatient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@Slf4j
@RestController
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patient")
    public ResponseEntity<List<Patient>> getPatients() {
        List<Patient> patients = patientService.getPatients();
        if (patients.isEmpty()) {
            log.warn("patient list is empty");
            return ResponseEntity.internalServerError().build();
        } else {
            log.info("display patient list");
            return ResponseEntity.ok(patients);
        }
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable("id") String id) {
        try {
            Patient patient = patientService.getPatientById(id);
            log.info("providing patient information");
            return ResponseEntity.ok(patient);
        } catch (Exception e) {
            log.warn("patient not found for this id : " + id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/patient/lightPatient")
    public ResponseEntity<Patient> getPatientByFirstNameAndLastName(@Valid @RequestBody LightPatient lightPatient) {
        try {
            Patient patient = patientService.getPatientByFirstNameAndLastName(lightPatient.getLastName(), lightPatient.getFirstName());
            log.info("providing patient information");
            return ResponseEntity.ok(patient);
        } catch (Exception e) {
            log.warn("patient : " + lightPatient.getFirstName() + " " + lightPatient.getLastName() + " is not found");
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/patient")
    public ResponseEntity<Patient> addPatient(@Valid @RequestBody Patient patient) {
        Patient addedPatient = patientService.addPatient(patient);
        log.info("patient created");
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(addedPatient.getId())
                .toUri();
        return ResponseEntity.created(location).body(addedPatient);
    }

    @PutMapping("/patient/{id}")
    public ResponseEntity<Patient> updatePatient(@Valid @RequestBody Patient patient, @PathVariable String id) {
        try {
            Patient updatedPatient = patientService.updatePatient(id, patient);
            log.info("patient updated");
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(updatedPatient.getId())
                    .toUri();
            return ResponseEntity.created(location).body(updatedPatient);
        } catch (Exception e) {
            log.warn("patient not found for this id : " + id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/patient/{id}")
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
