package org.apache.maven.archetypes.labxpertproject.controller;

import org.apache.maven.archetypes.labxpertproject.DTOs.EchantillonDTO;
import org.apache.maven.archetypes.labxpertproject.service.interfaces.IEchantillonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/echantillons")
public class EchantillonController {

    @Autowired
    private IEchantillonService echantillonService;






    @PostMapping
    public EchantillonDTO createEchantillon(@RequestBody EchantillonDTO echantillonDTO) {
        EchantillonDTO createdEchantillon = echantillonService.createEchantillon(echantillonDTO);
        return createdEchantillon;
    }
}
