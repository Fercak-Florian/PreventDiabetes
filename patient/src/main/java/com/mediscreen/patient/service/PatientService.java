package com.mediscreen.patient.service;

import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PatientService {

    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getPatients() {
        log.info("providing patient list");
        return patientRepository.findAll();
    }

    public Patient getPatientByFirstNameAndLastName(String lastName, String firstName) {
        Optional<Patient> optionalPatient = patientRepository.findByLastNameAndFirstName(lastName, firstName);
        if (optionalPatient.isEmpty()) {
            log.warn("patient : " + firstName + " " + lastName + " is not found");
            return null;
        } else {
            log.warn("providing patient object");
            return optionalPatient.get();
        }
    }

    public Patient getPatientById(String id) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isEmpty()) {
            log.warn("patient with id : " + id + " is not found");
            return null;
        } else {
            log.info("patient is found");
            return optionalPatient.get();
        }
    }

    public Patient addPatient(Patient patient) {
        return patientRepository.insert(patient);
    }

    public Patient updatePatient(String id, Patient patient) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isEmpty()) {
            log.warn("patient not found for this id : " + id);
            return null;
        } else {
            Patient patientToUpdate = optionalPatient.get();
            patientToUpdate.setLastName(patient.getLastName());
            patientToUpdate.setFirstName(patient.getFirstName());
            patientToUpdate.setDob(patient.getDob());
            patientToUpdate.setSex(patient.getSex());
            patientToUpdate.setAddress(patient.getAddress());
            patientToUpdate.setPhone(patient.getPhone());
            return patientRepository.save(patientToUpdate);
        }
    }

    public Patient deletePatient(String id) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isEmpty()) {
            log.warn("patient not found for this id : " + id);
            return null;
        } else {
            patientRepository.deleteById(id);
            log.info("patient with the id " + id + " is deleted");
            return optionalPatient.get();
        }
    }
}
