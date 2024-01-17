package org.apache.maven.archetypes.labxpertproject.service.interfaces;

import org.apache.maven.archetypes.labxpertproject.DTOs.UtilisateurDTO;
import org.apache.maven.archetypes.labxpertproject.entitiy.model.Utilisateur;


import java.util.*;

public interface IUtilisateurSerivce {


    UtilisateurDTO addUtilisateur(UtilisateurDTO user);

    UtilisateurDTO getUtilisateurById(Long userId);

    List<UtilisateurDTO> getAllUtilisateur();

    UtilisateurDTO updateUtilisateur(UtilisateurDTO user);

    void deleteUtilisateur(Long userId);

}
