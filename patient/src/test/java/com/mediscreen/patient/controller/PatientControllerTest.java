package com.mediscreen.patient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.patient.exception.PatientNotFoundException;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import com.mediscreen.patient.utils.LightPatient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@Profile("dev")
@ActiveProfiles("dev")
@WebMvcTest(PatientController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private PatientController patientController;

    @MockBean
    private PatientService patientService;

    @BeforeEach
    public void init() {
        patientController = new PatientController(patientService);
    }

    @Test
    @DisplayName("Test de récupération de tous les patients")
    public void testGetPatients() throws Exception {
        /*ARRANGE*/
        Patient patient = new Patient(1, "Carman", "Tessa", "1966-12-31", "F", "1 Brookside St", "100-222-333");
        List<Patient> patientList = new ArrayList<>();
        patientList.add(patient);
        when(patientService.getPatients()).thenReturn(patientList);

        /*ACT AND ASSERT*/
        mockMvc.perform(get("/patient"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].firstName", is("Tessa")));
        verify(patientService).getPatients();
    }

    @Test
    @DisplayName("Echec de la récupération de tous les patients")
    public void testGetPatientsThrowsPatientNotFoundException() throws Exception {
        /*ARRANGE*/
        List<Patient> patientList = new ArrayList<>();
        when(patientService.getPatients()).thenReturn(patientList);

        /*ACT AND ASSERT*/
        mockMvc.perform(get("/patient")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException() instanceof PatientNotFoundException))
                .andExpect(result -> assertThat(result.getResolvedException().getMessage()).isEqualTo("patient list is empty"));
        verify(patientService).getPatients();
    }

    @Test
    @DisplayName("Test de récupération d'un patient par son id")
    public void testGetPatientById() throws Exception {
        /*ARRANGE*/
        Patient patient = new Patient(1, "Tessa", "Carman", "1966-12-31", "F", "1 Brookside St", "100-222-333");
        int id = 1;
        when(patientService.getPatientById(id)).thenReturn(patient);

        /*ACT AND ASSERT*/
        mockMvc.perform(get("/patient/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Tessa")));
        verify(patientService).getPatientById(id);
    }

    @Test
    @DisplayName("Echec de récupération d'un patient par son id")
    public void testGetPatientByIdThrowsPatientNotFoundException() throws Exception {
        /*ARRANGE*/
        int id = 1;
        when(patientService.getPatientById(id)).thenReturn(null);

        /*ACT AND ASSERT*/
        mockMvc.perform(get("/patient/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException() instanceof PatientNotFoundException))
                .andExpect(result -> assertThat(result.getResolvedException().getMessage()).isEqualTo("patient not found for this id : " + id));
        verify(patientService).getPatientById(id);
    }

    @Test
    @DisplayName("Test de la récupération d'un patient par son nom et prénom")
    public void testGetPatientByFirstNameAndLastName() throws Exception {
        /*ARRANGE*/
        Patient patient = new Patient(1, "Carman", "Tessa", "1966-12-31", "F", "1 Brookside St", "100-222-333");
        ObjectMapper objectMapper = new ObjectMapper();
        when(patientService.getPatientByFirstNameAndLastName("Carman", "Tessa")).thenReturn(patient);

        /*ACT AND ASSERT*/
        mockMvc.perform(post("/patient/lightPatient")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Tessa")));
        verify(patientService).getPatientByFirstNameAndLastName("Carman", "Tessa");
    }

    @Test
    @DisplayName("Echec de la récupération d'un patient par son nom et prénom")
    public void testGetPatientByFirstNameAndLastNameThrowsPatientNotFoundException() throws Exception {
        /*ARRANGE*/
        LightPatient lightPatient = new LightPatient("T", "T");
        ObjectMapper objectMapper = new ObjectMapper();
        when(patientService.getPatientByFirstNameAndLastName("T", "T")).thenReturn(null);
        /*ACT AND ASSERT*/
        mockMvc.perform(post("/patient/lightPatient").contentType("application/json")
                        .content(objectMapper.writeValueAsString(lightPatient)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException() instanceof PatientNotFoundException))
                .andExpect(result -> assertThat(result.getResolvedException().getMessage()).isEqualTo("patient : " + lightPatient.getFirstName() + " " + lightPatient.getLastName() + " is not found"));
        verify(patientService).getPatientByFirstNameAndLastName("T", "T");
    }

    @Test
    @DisplayName("Test de la sauvegarde d'un patient")
    public void testAddPatient() throws Exception {
        /*ARRANGE*/
        Patient patient = new Patient(1, "Tessa", "Carman", "1966-12-31", "F", "1 Brookside St", "100-222-333");
        ObjectMapper objectMapper = new ObjectMapper();
        when(patientService.addPatient(patient)).thenReturn(patient);

        /*ACT AND ASSERT*/
        mockMvc.perform(post("/patient")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("Tessa")));
        verify(patientService).addPatient(patient);
    }

    @Test
    @DisplayName("Test de la mise à jour d'un patient")
    public void testUpdatePatient() throws Exception {
        /*ARRANGE*/
        int id = 1;
        Patient patientToUpdate = new Patient(1, "Jacob", "Boyd", "1966-12-31", "F", "1 Brookside St", "100-222-333");
        Patient patient = new Patient(1, "Tessa", "Carman", "1966-12-31", "F", "1 Brookside St", "100-222-333");
        ObjectMapper objectMapper = new ObjectMapper();
        when(patientService.updatePatient(id, patientToUpdate)).thenReturn(patient);

        /*ACT AND ASSERT*/
        mockMvc.perform(put("/patient/{id}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(patientToUpdate)))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("Tessa")));
        verify(patientService).updatePatient(id, patientToUpdate);
    }

    @Test
    @DisplayName("Echec de la mise à jour d'un patient")
    public void testUpdatePatientThrowsPatientNotFoundException() throws Exception {
        int id = 1;
        Patient patientToUpdate = new Patient(1, "T", "T", "1966-12-31", "F", "1 Brookside St", "100-222-333");
        when(patientService.updatePatient(id, patientToUpdate)).thenReturn(null);
        ObjectMapper objectMapper = new ObjectMapper();

        /*ACT AND ASSERT*/
        mockMvc.perform(put("/patient/{id}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(patientToUpdate)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException() instanceof PatientNotFoundException))
                .andExpect(result -> assertThat(result.getResolvedException().getMessage()).isEqualTo("Patient with id " + id + " is not found"));
        verify(patientService).updatePatient(id, patientToUpdate);
    }

    @Test
    @DisplayName("Test de la suppression d'un patient")
    public void testDeletePatient() throws Exception {
        /*ARRANGE*/
        int id = 1;
        Patient patient = new Patient(1, "Tessa", "Carman", "1966-12-31", "F", "1 Brookside St", "100-222-333");
        when(patientService.deletePatient(id)).thenReturn(patient);

        /*ACT AND ASSERT*/
        mockMvc.perform(delete("/patient/{id}", id))
                .andExpect(content().string(containsString("Tessa")))
                .andExpect(status().isOk());
        verify(patientService).deletePatient(id);
    }

    @Test
    @DisplayName("Echec de la suppression d'un patient")
    public void testDeletePatientThrowsPatientNotFoundException() throws Exception {
        /*ARRANGE*/
        int id = 1;
        when(patientService.deletePatient(id)).thenReturn(null);

        /*ACT AND ASSERT*/
        mockMvc.perform(delete("/patient/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException() instanceof PatientNotFoundException))
                .andExpect(result -> assertThat(result.getResolvedException().getMessage()).isEqualTo("Patient with id " + id + " is not found"));
        verify(patientService).deletePatient(id);
    }
}
