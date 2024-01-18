package org.apache.maven.archetypes.labxpertproject.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

@Data
public class EchantillonDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Positive(message = "echantillonId must be a positive number")
    private Long echantillonId;

    @Positive(message = "patientId must be a positive number")
    private Long patientId;

    @NotNull(message = "datePrelevement must not be null")
    @PastOrPresent(message = "The datePrelevement must be in the past or present")
    private LocalDate datePrelevement;
    @NotNull(message = "analyses must not be null")
    private List<AnalyseDTO> analyses;
}
