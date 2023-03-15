package com.mediscreen.preventdiabetes.service;

import com.mediscreen.preventdiabetes.model.Patient;
import com.mediscreen.preventdiabetes.repository.PatientRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatient(String lastName){
        return patientRepository.findByLastName(lastName);
    }

    public void addPatient(Patient patient) {
        patientRepository.insert(patient);
    }

    public void deleteAllPatient(){
        patientRepository.deleteAll();
    }
}
