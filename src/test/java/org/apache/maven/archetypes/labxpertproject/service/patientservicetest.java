package org.apache.maven.archetypes.labxpertproject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;


import org.apache.maven.archetypes.labxpertproject.DTOs.PatientDTO;
import org.apache.maven.archetypes.labxpertproject.entitiy.enums.SexeType;
import org.apache.maven.archetypes.labxpertproject.repository.PatientRepository;
import org.apache.maven.archetypes.labxpertproject.service.interfaces.IPatientService;

@SpringBootTest
public class patientservicetest {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    IPatientService patientService;

    @Autowired
    private ModelMapper modelMapper;

    PatientDTO patientDto;

    @BeforeEach
    void setUp() {
        patientDto = new PatientDTO();
        patientDto.setPatientId(1L);
        patientDto.setNom("rachid");
        patientDto.setDateDeNaissance("1988-12-01");
        patientDto.setSexe(SexeType.HOMME);
        patientDto.setAdresse("France");
        patientDto.setTelephone("+212-699-109-586");
    }

    @Test
    public void test_addPatient() {
        PatientDTO resultDto = patientService.addPatient(patientDto);
        assertNotNull(resultDto, "Patient Not found");
        assertEquals(resultDto.getNom(), patientDto.getNom());
    }

    @Test
    public void test_getAllPatients() {

//        PatientDTO patientDto1 = new PatientDTO(51L, "rachid", new Date(1988, Calendar.NOVEMBER, 7), SexeType.HOMME, "France", "+212-699-109-334");
//        PatientDTO patientDto2 = new PatientDTO(52L, "rachid", new Date(2000, Calendar.JANUARY, 7), SexeType.HOMME, "CASABLANCA", "+212-699-109-586");
//        PatientDTO patientDto3 = new PatientDTO(53L, "HALIMA", new Date(1990, Calendar.MAY, 7), SexeType.FEMME, "UK", "+212-699-109-586");

//        patientService.addPatient(patientDto1);
//        patientService.addPatient(patientDto2);
//        patientService.addPatient(patientDto3);
//
//        List<PatientDTO> patientDtoResult = patientService.getAllPatients();
//
//        List<PatientDTO> patientDtoList = Arrays.asList(
//                patientDto1,
//                patientDto2,
//                patientDto3
//        );
//
//
//        assertEquals(patientDtoResult, patientDtoList);


    }

    @Test
    public void test_PatientById() {


    }

    @Test
    public void test_updatePatient() {

    }

    @Test
    public void test_deletePatient() {

    }
}
