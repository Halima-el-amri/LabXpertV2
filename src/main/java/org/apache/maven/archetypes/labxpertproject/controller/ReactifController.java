package org.apache.maven.archetypes.labxpertproject.controller;


import org.apache.maven.archetypes.labxpertproject.DTOs.PlanificationDTO;
import org.apache.maven.archetypes.labxpertproject.DTOs.ReactifDTO;
import org.apache.maven.archetypes.labxpertproject.service.interfaces.IReactifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/reactifs")
public class ReactifController {

    @Autowired
    private IReactifService reactifService;
    @PostMapping
    public ResponseEntity<ReactifDTO> addPlanification(@RequestBody @Valid ReactifDTO reactifDTO) {
    ReactifDTO addedReactif = reactifService.addReactif(reactifDTO);
        return new ResponseEntity<>(addedReactif , HttpStatus.CREATED);
    }

}
