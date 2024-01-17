package org.apache.maven.archetypes.labxpertproject.DTOs;

import lombok.Data;
import org.apache.maven.archetypes.labxpertproject.entitiy.enums.RoleDutilisateur;

import javax.persistence.*;

@Data
public class UtilisateurDTO {

    private Long utilisateurId;
    private String nomUtilisateur;
    private String email;
    private String password;
    private RoleDutilisateur roleDutilisateur;
    private String InformationsPersonalises;


}
