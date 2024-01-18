package org.apache.maven.archetypes.labxpertproject.DTOs;

import org.apache.maven.archetypes.labxpertproject.entitiy.model.Analyse;
import org.apache.maven.archetypes.labxpertproject.entitiy.model.Utilisateur;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PlanificationDTO {

    private Long planificationId;

    private List<Analyse> analyses = new ArrayList<>();

    private Utilisateur utilisateur;

    private LocalDate dateDebutPlanification;

    private LocalDate dateFinPlanification;

}
