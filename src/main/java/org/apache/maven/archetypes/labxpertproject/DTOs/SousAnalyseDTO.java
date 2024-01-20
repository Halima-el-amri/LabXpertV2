package org.apache.maven.archetypes.labxpertproject.DTOs;

import lombok.Data;
import org.apache.maven.archetypes.labxpertproject.entitiy.enums.StatutDeResultat;

import javax.validation.constraints.*;

@Data
public class SousAnalyseDTO {

    private Long sousAnalyseId;

    @NotNull(message = "Valeur must not be null")
    private double valeur;

    //    @NotNull(message = "Analyse ID must not be null")
    @Positive(message = "Analyse ID must be a positive number")
    private Long SousAnalyseMesuresId;

    @NotNull(message = "StatutDeResultat must not be null")
    private StatutDeResultat statutDeResultat;

    @NotBlank(message = "Analyse type must not be blank")
    private String analyseName;

    @NotNull(message = "Min value must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Min value must be greater than 0")
    private double min;

    @NotNull(message = "Max value must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Max value must be greater than 0")
    private double max;

    @NotBlank(message = "Unite must not be blank")
    private String unite;

}