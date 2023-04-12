package com.mediscreen.patient.controller;

import com.mediscreen.patient.exception.PatientNotFoundException;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import com.mediscreen.patient.utils.LightPatient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@Slf4j
@RestController
@Api("API executant les operation CRUD sur les patients")
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @ApiOperation("Endpoint permettant la récupération de tous les patients")
    @GetMapping("/patient")
    public ResponseEntity<List<Patient>> getPatients() {
        List<Patient> patients = patientService.getPatients();
        if (patients.isEmpty()) {
            log.warn("patient list is empty");
            throw new PatientNotFoundException("patient list is empty");
        } else {
            log.info("display patient list");
            return ResponseEntity.ok(patients);
        }
    }

    @ApiOperation("Endpoint permettant la récupération d'un patient par son id")
    @GetMapping("/patient/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable("id") String id) {
        Patient patient = patientService.getPatientById(id);
        if (patient == null) {
            log.warn("patient not found for this id : " + id);
            throw new PatientNotFoundException("patient not found for this id : " + id);
        } else {
            log.info("providing patient information");
            return ResponseEntity.ok(patient);
        }
    }

    @ApiOperation("Endpoint permettant la récupération d'un patient par le couple nom, prénom")
    @PostMapping("/patient/lightPatient")
    public ResponseEntity<Patient> getPatientByFirstNameAndLastName(@Valid @RequestBody LightPatient lightPatient) {
        Patient patient = patientService.getPatientByFirstNameAndLastName(lightPatient.getLastName(), lightPatient.getFirstName());
        if (patient == null) {
            log.warn("patient : " + lightPatient.getFirstName() + " " + lightPatient.getLastName() + " is not found");
            throw new PatientNotFoundException("patient : " + lightPatient.getFirstName() + " " + lightPatient.getLastName() + " is not found");
        } else {
            log.info("providing patient information");
            return ResponseEntity.ok(patient);
        }
    }

    @ApiOperation("Endpoint permettant l'ajout d'un patient")
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

    @ApiOperation("Endpoint permettant la mise à jour d'un patient")
    @PutMapping("/patient/{id}")
    public ResponseEntity<Patient> updatePatient(@Valid @RequestBody Patient patient, @PathVariable String id) {
        Patient updatedPatient = patientService.updatePatient(id, patient);
        if (updatedPatient == null) {
            log.warn("patient not found for this id : " + id);
            throw new PatientNotFoundException("Patient with id " + id + " is not found");
        } else {
            log.info("patient updated");
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(updatedPatient.getId())
                    .toUri();
            return ResponseEntity.created(location).body(updatedPatient);
        }
    }

    @ApiOperation("Endpoint permettant la suppression d'un patient")
    @DeleteMapping("/patient/{id}")
    public ResponseEntity<Patient> deletePatient(@PathVariable String id) {
        Patient patient = patientService.deletePatient(id);
        if (patient == null) {
            log.warn("error during deleting patient with id : " + id);
            throw new PatientNotFoundException("Patient with id " + id + " is not found");
        }
        log.info("patient with the id " + id + " is deleted");
        return ResponseEntity.ok(patient);
    }
}

