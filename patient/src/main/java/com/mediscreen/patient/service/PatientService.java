package com.mediscreen.patient.service;

import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatient(String lastName, String firstName){
        return patientRepository.findByLastNameAndFirstName(lastName, firstName);
    }

    public Optional<Patient> getPatientById(String id){
        return patientRepository.findById(id);
    }

    public void addPatient(Patient patient) {
        patientRepository.insert(patient);
    }

    public Patient updatePatient(String id, Patient patient){
        Optional<Patient> optPatient = patientRepository.findById(id);
        Patient patientToUpdate = optPatient.get();
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

    public void deletePatient(String id){
       patientRepository.deleteById(id);
    }
}
