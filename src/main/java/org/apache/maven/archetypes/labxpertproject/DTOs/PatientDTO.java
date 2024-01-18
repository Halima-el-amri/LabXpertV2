package org.apache.maven.archetypes.labxpertproject.DTOs;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
    private Long patientId;
    private String nom;
    private LocalDate dateDeNaissance;
    private String sexe;
    private String adresse;
    private String telephone;
}