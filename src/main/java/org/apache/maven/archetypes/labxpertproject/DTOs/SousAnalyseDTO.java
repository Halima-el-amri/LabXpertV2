package org.apache.maven.archetypes.labxpertproject.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.maven.archetypes.labxpertproject.entitiy.enums.StatutDeResultat;

import javax.validation.constraints.*;

@Data
public class SousAnalyseDTO {

    private Long sousAnalyseId;

    @NotNull(message = "Valeur must not be null")
    private double valeur;

    @NotNull(message = "StatutDeResultat must not be null")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private StatutDeResultat statutDeResultat;


}