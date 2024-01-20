package org.apache.maven.archetypes.labxpertproject.controller;

import org.apache.maven.archetypes.labxpertproject.DTOs.SousAnalyseDTO;
import org.apache.maven.archetypes.labxpertproject.service.interfaces.ISousAnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/sousanalyses")
public class SousAnalyseController {

    @Autowired
    private ISousAnalyseService sousAnalyseService;

    @PostMapping
    public ResponseEntity<SousAnalyseDTO> createSousAnalyse(@RequestBody @Valid SousAnalyseDTO sousAnalyseDTO) {
        SousAnalyseDTO createdSousAnalyse = sousAnalyseService.createSousAnalyse(sousAnalyseDTO);
        return new ResponseEntity<>(createdSousAnalyse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SousAnalyseDTO>> getAllSousAnalyses() {
        List<SousAnalyseDTO> sousAnalyses = sousAnalyseService.getAllSousAnalyses();
        return new ResponseEntity<>(sousAnalyses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SousAnalyseDTO> getSousAnalyseById(@PathVariable Long id) {
        SousAnalyseDTO sousAnalyse = sousAnalyseService.getSousAnalyseById(id);
        return new ResponseEntity<>(sousAnalyse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SousAnalyseDTO> updateSousAnalyse(@PathVariable Long id, @RequestBody @Valid SousAnalyseDTO sousAnalyseDTO) {
        SousAnalyseDTO updatedSousAnalyse = sousAnalyseService.updateSousAnalyse(id, sousAnalyseDTO);
        return new ResponseEntity<>(updatedSousAnalyse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSousAnalyse(@PathVariable Long id) {
        sousAnalyseService.deleteSousAnalyse(id);
        return new ResponseEntity<>("SousAnalyse deleted successfully", HttpStatus.OK);
    }
}
