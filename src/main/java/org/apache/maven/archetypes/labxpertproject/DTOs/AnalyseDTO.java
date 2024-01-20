package org.apache.maven.archetypes.labxpertproject.DTOs;

import lombok.Data;
import org.apache.maven.archetypes.labxpertproject.entitiy.enums.StatutDanalyse;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class AnalyseDTO {

    private Long analyseId;

//    @NotNull(message = "Utilisateur ID must not be null")
    private Long utilisateurId;

    private LocalDate dateDebutAnalyse;

    private LocalDate dateFinAnalyse;

    private List<Long> sousAnalysesIds;

//    @NotNull(message = "EtatAnalyse must not be null")
    private StatutDanalyse etatAnalyse;

    private String commentaire;

//    @NotNull(message = "Planification ID must not be null")
    private Long planificationId;

//    @NotNull(message = "Echantillon ID must not be null")
    private Long echantillonId;

    private List<Long> reactifsIds;
}
