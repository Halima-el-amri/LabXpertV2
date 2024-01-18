package org.apache.maven.archetypes.labxpertproject.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import org.apache.maven.archetypes.labxpertproject.entitiy.enums.RoleDutilisateur;

import javax.validation.constraints.*;

@Data
public class UtilisateurDTO {
    //TODO : RJ3 LHNA @ POSTIVE
    private Long utilisateurId;


    private String nomUtilisateur;

    private String email;


    private String password;

    private RoleDutilisateur roleDutilisateur;

    private String InformationsPersonalises;
}

