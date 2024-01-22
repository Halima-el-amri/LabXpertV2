package org.apache.maven.archetypes.labxpertproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;
import java.util.Arrays;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    @Test
    public void test_addPatient() throws Exception {

        PatientDTO patientDto = new PatientDTO(50L, "rachid", new Date(1988, Calendar.NOVEMBER, 7), SexeType.HOMME, "France", "+212-699-109-586");

        when(patientService.addPatient(patientDto)).thenReturn(patientDto);
        mockMvc.perform(post("/api/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(patientDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void test_getPatientById() throws Exception {

        PatientDTO patientDto = new PatientDTO(50L, "rachid",  new Date(1988, Calendar.NOVEMBER, 7), SexeType.HOMME, "France", "+212-699-109-586");

        when(patientService.addPatient(patientDto))
                .thenReturn(patientDto);

        mockMvc.perform(get("/api/patient/{id}", 50L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("rachid"))
                .andExpect(jsonPath("$.address").value("France"))
                .andExpect(jsonPath("$.telephone").value("+212-699-109-586"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_getAllPatients() throws Exception {

        List<PatientDTO> patientDTOList = Arrays.asList(
                new PatientDTO(50L, "rachid",  new Date(1988, Calendar.NOVEMBER, 7), SexeType.HOMME, "France", "+212-699-109-485"),
                new PatientDTO(51L, "hassan",  new Date(1989, Calendar.MARCH, 5), SexeType.HOMME, "espagne", "+212-699-109-254"),
                new PatientDTO(52L, "mohamed",  new Date(1986, Calendar.JANUARY, 2), SexeType.HOMME, "maroc", "+212-699-109-782")
        );

        when(patientService.getAllPatients()).thenReturn(patientDTOList);

        mockMvc.perform(get("/api/patient").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void test_updatePatient() throws Exception {

        PatientDTO existingPatientDto = new PatientDTO(50L, "hassan",  new Date(1982, Calendar.APRIL, 2), SexeType.HOMME, "France", "+212-699-109-586");
        PatientDTO updatedPatientDto = new PatientDTO(50L, "rachid",  new Date(1988, Calendar.NOVEMBER, 7), SexeType.HOMME, "Kenitra", "+212-699-109-587");

        when(patientService.getPatientById(50L)).thenReturn(existingPatientDto);
        when(patientService.updatePatient(existingPatientDto)).thenReturn(updatedPatientDto);

        mockMvc.perform(put("api/patient/50")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedPatientDto)))
                .andExpect(jsonPath("$.patientId").value(50L))
                .andExpect(jsonPath("$.name").value("rachid"))
                .andExpect(jsonPath("$.dateDeNaissance").value("1990-01-1"))
                .andExpect(jsonPath("$.sexe").value("HOMME"))
                .andExpect(jsonPath("$.address").value("Kenitra"))
                .andExpect(jsonPath("$.telephone").value("+212-699-109-587"))
                .andExpect(status().isOk());
        verify(patientService, times(1)).getPatientById(50L);
        verify(patientService, times(1)).updatePatient(existingPatientDto);

    }

    @Test
    public void test_deletePatient() throws Exception {

        PatientDTO patientDto = new PatientDTO(50L, "rachid",  new Date(2000, Calendar.DECEMBER, 4), SexeType.HOMME, "France", "+212-689-109-584");
        PatientDTO patientDto1 = new PatientDTO(51L, "HASSAN",  new Date(1988, Calendar.MAY, 7), SexeType.HOMME, "France", "+212-659-159-587");
        PatientDTO patientDto2 = new PatientDTO(52L, "BRAHIM",  new Date(2002, Calendar.AUGUST, 2), SexeType.HOMME, "France", "+212-299-109-584");

        long patientId = 50L;

        doNothing().when(patientService).deletePatient(patientId);

        mockMvc.perform(delete("/api/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(patientId)))
                .andExpect(status().isOk());
    }
}
