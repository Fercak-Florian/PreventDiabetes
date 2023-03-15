package com.mediscreen.preventdiabetes.repository;

import com.mediscreen.preventdiabetes.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends MongoRepository<Patient, Integer> {
    Optional<Patient> findByLastName(String lastName);
}
