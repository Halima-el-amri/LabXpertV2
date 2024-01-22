package org.apache.maven.archetypes.labxpertproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import java.util.Date;
import java.util.List;
import java.util.Arrays;
import java.util.Calendar;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.apache.maven.archetypes.labxpertproject.DTOs.PatientDTO;
import org.apache.maven.archetypes.labxpertproject.entitiy.enums.SexeType;
import org.apache.maven.archetypes.labxpertproject.service.interfaces.IPatientService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @MockBean
    IPatientService patientService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    private PatientDTO existingPatientDto;

    @BeforeEach
    void setUp() {
        existingPatientDto = new PatientDTO();
        existingPatientDto.setPatientId(1L);
        existingPatientDto.setNom("rachid");
        existingPatientDto.setDateDeNaissance("1988-12-01");
        existingPatientDto.setSexe(SexeType.HOMME);
        existingPatientDto.setAdresse("France");
        existingPatientDto.setTelephone("+212-699-109-586");
    }

    @Test
    public void test_addPatient() throws Exception {

        when(patientService.addPatient(this.existingPatientDto)).thenReturn(this.existingPatientDto);

        ResultActions resultAction = mockMvc.perform(post("/api/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(existingPatientDto)));

        resultAction.andExpect(jsonPath("$.nom", CoreMatchers.is(existingPatientDto.getNom())))
                .andExpect(jsonPath("$.dateDeNaissance", CoreMatchers.is(existingPatientDto.getDateDeNaissance())))
                .andExpect(jsonPath("$.sexe", CoreMatchers.is(existingPatientDto.getSexe())))
                .andExpect(jsonPath("$.Adresse", CoreMatchers.is(existingPatientDto.getAdresse())))
                .andExpect(jsonPath("$.telephone", CoreMatchers.is(existingPatientDto.getTelephone())))
                .andExpect(status().isOk());
    }

    @Test
    public void test_getPatientById() throws Exception {
        Long patientId = 1L;

        when(patientService.getPatientById(patientId)).thenReturn(this.existingPatientDto);

        ResultActions resultAction = mockMvc.perform(get("/api/patient/{id}", patientId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(existingPatientDto)));

        resultAction.andExpect(jsonPath("$.nom", CoreMatchers.is(existingPatientDto.getNom())))
                .andExpect(jsonPath("$.dateDeNaissance", CoreMatchers.is(existingPatientDto.getDateDeNaissance())))
                .andExpect(jsonPath("$.sexe", CoreMatchers.is(existingPatientDto.getSexe())))
                .andExpect(jsonPath("$.Adresse", CoreMatchers.is(existingPatientDto.getAdresse())))
                .andExpect(jsonPath("$.telephone", CoreMatchers.is(existingPatientDto.getTelephone())))
                .andExpect(status().isOk());
    }

    @Test
    public void test_getAllPatients() throws Exception {

        List<PatientDTO> patientDTOList = Arrays.asList(
                new PatientDTO(50L, "rachid", "1988-11-07", SexeType.HOMME, "France", "+212-697-159-788"),
                new PatientDTO(51L, "hassan", "2000-08-01", SexeType.HOMME, "espagne", "+212-698-106-454"),
                new PatientDTO(52L, "Halima", "1996-05-03", SexeType.FEMME, "maroc", "+212-689-105-732")
        );

        when(patientService.getAllPatients()).thenReturn(patientDTOList);

        ResultActions resultAction = mockMvc.perform(get("/api/patient")
                .contentType(MediaType.APPLICATION_JSON));

        resultAction.andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void test_updatePatient() throws Exception {

        PatientDTO updatedPatientDto = new PatientDTO(50L, "rachid", "1988-11-07", SexeType.HOMME, "France", "+212-697-159-788");

        when(patientService.getPatientById(50L)).thenReturn(existingPatientDto);

        when(patientService.updatePatient(existingPatientDto)).thenReturn(updatedPatientDto);

        ResultActions resultAction = mockMvc.perform(put("api/patient/50")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedPatientDto)));

        resultAction.andExpect(jsonPath("$.nom", CoreMatchers.is(existingPatientDto.getNom())))
                .andExpect(jsonPath("$.dateDeNaissance", CoreMatchers.is(existingPatientDto.getDateDeNaissance())))
                .andExpect(jsonPath("$.sexe", CoreMatchers.is(existingPatientDto.getSexe())))
                .andExpect(jsonPath("$.Adresse", CoreMatchers.is(existingPatientDto.getAdresse())))
                .andExpect(jsonPath("$.telephone", CoreMatchers.is(existingPatientDto.getTelephone())))
                .andExpect(status().isOk());

        verify(patientService, times(1)).getPatientById(50L);
        verify(patientService, times(1)).updatePatient(existingPatientDto);

    }

    @Test
    public void test_deletePatient() throws Exception {

        new PatientDTO(50L, "rachid", "1988-11-07", SexeType.HOMME, "France", "+212-697-159-788");
        new PatientDTO(51L, "hassan", "2000-08-01", SexeType.HOMME, "espagne", "+212-698-106-454");
        new PatientDTO(52L, "Halima", "1996-05-03", SexeType.FEMME, "maroc", "+212-689-105-732");

        long patientId = 50L;

        doNothing().when(patientService).deletePatient(patientId);

        mockMvc.perform(delete("/api/patient/50")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(patientId)))
                .andExpect(status().isOk());
    }
}
