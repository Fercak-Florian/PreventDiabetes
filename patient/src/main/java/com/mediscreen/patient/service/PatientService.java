package com.mediscreen.patient.service;

import com.mediscreen.patient.exception.PatientNotFoundException;
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
        return patientRepository.findAll();
    }

    public Patient getPatient(String lastName, String firstName) {
        Optional<Patient> optionalPatient = patientRepository.findByLastNameAndFirstName(lastName, firstName);
        if(optionalPatient.isEmpty()){
            log.warn("patient : " + firstName + " " + lastName + " is not found");
            throw new PatientNotFoundException("patient : " + firstName + " " + lastName + " is not found");
        } else {
            return optionalPatient.get();
        }
    }

    public Patient getPatientById(String id) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if(optionalPatient.isEmpty()){
            log.warn("patient with id : " + id + " is not found");
            throw new PatientNotFoundException("patient with id : " + id + " is not found");
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
            throw new PatientNotFoundException("Patient with id " + id + " is not found");
        } else {
            Patient patientToUpdate = optionalPatient.get();
            patientToUpdate.setFirstName(patient.getFirstName());
            patientToUpdate.setLastName(patient.getLastName());
            patientToUpdate.setFamily(patient.getFamily());
            patientToUpdate.setGiven(patient.getGiven());
            patientToUpdate.setDob(patient.getDob());
            patientToUpdate.setSex(patient.getSex());
            patientToUpdate.setAddress(patient.getAddress());
            patientToUpdate.setPhone(patient.getPhone());
            patientRepository.save(patientToUpdate);
            return patientToUpdate;
        }
    }

    public void deletePatient(String id) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if(optionalPatient.isEmpty()){
            log.warn("patient not found for this id : " + id);
            throw new PatientNotFoundException("Patient with id " + id + " is not found");
        } else {
            patientRepository.deleteById(id);
            log.info("patient with the id " + id + " has been deleted");
        }
    }
}
