package org.apache.maven.archetypes.labxpertproject.controller;

import org.apache.maven.archetypes.labxpertproject.DTOs.EchantillonDTO;
import org.apache.maven.archetypes.labxpertproject.service.interfaces.IEchantillonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/echantillons")
public class EchantillonController {

    @Autowired
    private IEchantillonService echantillonService;
    @PostMapping
    public EchantillonDTO createEchantillon(@RequestBody EchantillonDTO echantillonDTO) {
        return echantillonService.createEchantillon(echantillonDTO);
    }

}
