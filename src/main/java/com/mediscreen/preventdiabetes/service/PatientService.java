package com.mediscreen.preventdiabetes.service;

import com.mediscreen.preventdiabetes.model.Patient;
import com.mediscreen.preventdiabetes.repository.PatientRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
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

    public Patient updatePatient(String lastName, Patient patient){
        Optional<Patient> optPatient = patientRepository.findByLastName(lastName);
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

    public void deleteAllPatient(){
        patientRepository.deleteAll();
    }
}
