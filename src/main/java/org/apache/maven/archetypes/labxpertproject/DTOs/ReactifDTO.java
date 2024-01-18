package org.apache.maven.archetypes.labxpertproject.DTOs;

import lombok.Data;

@Data
public class ReactifDTO {

    private Long reactifId;
    private String nom;
    private String description;
    private int quantite;
    private String dateDeExpiration;
    private String fournisseur;
    private Long analyseId;


}
