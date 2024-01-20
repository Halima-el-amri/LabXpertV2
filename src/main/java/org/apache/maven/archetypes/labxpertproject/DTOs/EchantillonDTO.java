package org.apache.maven.archetypes.labxpertproject.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EchantillonDTO {

    private Long echantillonId;

    private Long patientId;
    private LocalDate datePrelevement;

}
