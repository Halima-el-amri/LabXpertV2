package org.apache.maven.archetypes.labxpertproject.controller;

import org.apache.maven.archetypes.labxpertproject.DTOs.UtilisateurDTO;
import org.apache.maven.archetypes.labxpertproject.service.interfaces.IUtilisateurSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UtilisateurController {

    @Autowired
    private IUtilisateurSerivce utilisateurService;

    @PostMapping
    public ResponseEntity<UtilisateurDTO> addUser(@RequestBody @Valid UtilisateurDTO utilisateurDTO) {
        UtilisateurDTO addedUser = utilisateurService.addUtilisateur(utilisateurDTO);
        return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
    }


}
