package org.apache.maven.archetypes.labxpertproject.DTOs;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.maven.archetypes.labxpertproject.entitiy.enums.SexeType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {

    private Long patientId;

    @NotBlank(message = "Veuillez fournir un nom")
    @NotNull(message = "Le nom ne peut pas être null")
    @Size(min = 5, max = 20, message = "Le nom d'patient doit comporter entre 5 et 50 caractères")
    private String nom;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$",
            message = "Format de la date de naissance et invalide ,veuillez respecter le format suivant dd-mm-yyyy")
    @NotBlank(message = "Veuillez fournir la date de naissance")
    @NotNull(message = "La date de naissance ne peut pas être null")
    private String dateDeNaissance;

    @NotNull(message = "Le nom ne peut pas être null")
    private SexeType sexe;

    @NotBlank(message = "Veuillez fournir une Adresse")
    @NotNull(message = "Le nom ne peut pas être null")
    private String adresse;

    @NotNull(message = "Veuillez saisir un numero de telephone")
    @Pattern(regexp = "^\\+212-\\d{3}-\\d{3}-\\d{3}$",
            message = "Format de numéro de téléphone invalide ,veuillez respecter le format suivant ex:+212-000-000-000")
    private String telephone;

}