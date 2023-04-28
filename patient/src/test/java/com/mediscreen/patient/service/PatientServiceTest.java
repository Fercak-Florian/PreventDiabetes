package com.mediscreen.patient.service;

import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@Profile("dev")
@ActiveProfiles("dev")
@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    private PatientService patientService;

    @Mock
    PatientRepository patientRepository;

    @BeforeEach
    public void init() {
        patientService = new PatientService(patientRepository);
    }

    @Test
    @DisplayName("Test de récupération de tous les patients")
    public void testGetPatients() {
        /*ARRANGE*/
        Patient patient = new Patient(1, "Carman", "Tessa", "1966-12-31", "F", "1 Brookside St", "100-222-333");
        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient);
        when(patientService.getPatients()).thenReturn(patientList);

        /*ACT*/

        List<Patient> result = patientService.getPatients();
        /*ASSERT*/
        assertThat(result.get(0).getFirstName()).isEqualTo("Tessa");
        verify(patientRepository).findAll();
    }

    @Test
    @DisplayName("Test de récupération d'un patient par son nom et prénom'")
    public void testGetPatientByFirstNameAndLastName() {
        /*ARRANGE*/
        Patient patient = new Patient(1, "Carman", "Tessa", "1966-12-31", "F", "1 Brookside St", "100-222-333");
        Optional<Patient> optionalPatient = Optional.of(patient);
        when(patientRepository.findByLastNameAndFirstName("Carman", "Tessa")).thenReturn(optionalPatient);

        /*ACT*/
        Patient result = patientService.getPatientByFirstNameAndLastName("Carman", "Tessa");

        /*ASSERT*/
        assertThat(result.getDob()).isEqualTo("1966-12-31");
        verify(patientRepository).findByLastNameAndFirstName("Carman", "Tessa");
    }

    @Test
    @DisplayName("Echec lors lors de la récupération d'un patient par son nom et prénom'")
    public void testGetPatientByFirstNameAndLastNameReturnNull() {
        /*ARRANGE*/
        Optional<Patient> optionalPatient = Optional.empty();
        when(patientRepository.findByLastNameAndFirstName("", "")).thenReturn(optionalPatient);

        /*ACT*/
        Patient result = patientService.getPatientByFirstNameAndLastName("", "");

        /*ASSERT*/
        assertThat(result).isNull();
        verify(patientRepository).findByLastNameAndFirstName("", "");
    }

    @Test
    @DisplayName("Test de récupération d'un patient par son id")
    public void testGetPatientById() {
        /*ARRANGE*/
        Patient patient = new Patient(1, "Carman", "Tessa", "1966-12-31", "F", "1 Brookside St", "100-222-333");
        Optional<Patient> optionalPatient = Optional.of(patient);
        when(patientRepository.findById(1)).thenReturn(optionalPatient);

        /*ACT*/
        Patient result = patientService.getPatientById(1);

        /*ASSERT*/
        assertThat(result.getFirstName()).isEqualTo("Tessa");
        verify(patientRepository).findById(1);
    }

    @Test
    @DisplayName("Echec de la récupération d'un patient par son id")
    public void testGetPatientByIdReturnNull() {
        /*ARRANGE*/
        Optional<Patient> optionalPatient = Optional.empty();
        when(patientRepository.findById(1)).thenReturn(optionalPatient);

        /*ACT*/
        Patient result = patientService.getPatientById(1);

        /*ASSERT*/
        assertThat(result).isNull();
        verify(patientRepository).findById(1);
    }

    @Test
    @DisplayName("Test de sauvegarde d'un patient")
    public void testAddPatient() {
        /*ARRANGE*/
        Patient patient = new Patient(1, "Carman", "Tessa", "1966-12-31", "F", "1 Brookside St", "100-222-333");
        when(patientRepository.save(patient)).thenReturn(patient);

        /*ACT*/
        Patient result = patientService.addPatient(patient);

        /*ASSERT*/
        assertThat(result.getFirstName()).isEqualTo("Tessa");
        verify(patientRepository).save(patient);
    }

    @Test
    @DisplayName("Test de mis à jour d'un patient")
    public void testUpdatePatient() {
        /*ARRANGE*/
        Patient patient = new Patient(1, "Carman", "Tessa", "1966-12-31", "F", "1 Brookside St", "100-222-333");
        Optional<Patient> optionalPatient = Optional.of(patient);

        Patient modifiedPatient = new Patient(1, "Boyd", "Tessa", "1966-12-31", "F", "1 Brookside St", "100-222-333");

        Patient updatedPatient = new Patient(1, "Boyd", "Tessa", "1966-12-31", "F", "1 Brookside St", "100-222-333");
        when(patientRepository.findById(1)).thenReturn(optionalPatient);
        when(patientRepository.save(modifiedPatient)).thenReturn(updatedPatient);

        /*ACT*/
        Patient result = patientService.updatePatient(1, modifiedPatient);

        /*ASSERT*/
        assertThat(result.getLastName()).isEqualTo("Boyd");
        verify(patientRepository).findById(1);
        verify(patientRepository).save(modifiedPatient);
    }

    @Test
    @DisplayName("Echec de mis à jour d'un patient")
    public void testUpdatePatientReturnNull() {
        /*ARRANGE*/
        Optional<Patient> optionalPatient = Optional.empty();
        when(patientRepository.findById(1)).thenReturn(optionalPatient);

        /*ACT*/
        Patient result = patientService.updatePatient(1, new Patient());

        /*ASSERT*/
        assertThat(result).isNull();
        verify(patientRepository).findById(1);
    }

    @Test
    @DisplayName("Test de suppression d'un patient")
    public void testDeletedPatient() {
        /*ARRANGE*/
        Patient patient = new Patient(1, "Carman", "Tessa", "1966-12-31", "F", "1 Brookside St", "100-222-333");
        Optional<Patient> optionalPatient = Optional.of(patient);
        when(patientRepository.findById(1)).thenReturn(optionalPatient);

        /*ACT*/
        Patient result = patientService.deletePatient(1);

        /*ASSERT*/
        assertThat(result.getFirstName()).isEqualTo("Tessa");
        verify(patientRepository).deleteById(1);
    }

    @Test
    @DisplayName("Echec lors de la suppression d'un patient")
    public void testDeletePatientReturnNull() {
        /*ARRANGE*/
        Optional<Patient> optionalPatient = Optional.empty();
        when(patientRepository.findById(1)).thenReturn(optionalPatient);

        /*ACT*/
        Patient result = patientService.deletePatient(1);

        /*ASSERT*/
        assertThat(result).isNull();
        verify(patientRepository).findById(1);
    }
}
