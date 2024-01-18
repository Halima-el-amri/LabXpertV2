package org.apache.maven.archetypes.labxpertproject.controller;

import org.apache.maven.archetypes.labxpertproject.DTOs.EchantillonDTO;
import org.apache.maven.archetypes.labxpertproject.service.interfaces.IEchantillonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/echantillons")
public class EchantillonController {

    @Autowired
    private IEchantillonService echantillonService;


//http://localhost:8080/api/echantillons/21
    @GetMapping("{id}")
    public EchantillonDTO getEchantillonById(@PathVariable("id") Long echantillonId) {
        try {
            EchantillonDTO echantillonById = echantillonService.getEchantillonById(echantillonId);
            return echantillonById;
        } catch (EntityNotFoundException e) {
            System.out.println("there is no echantillon whith zhis id "+echantillonId);
        }return null;
    }
    @GetMapping
    public List<EchantillonDTO> getAllEchantillon(){
        List<EchantillonDTO> echantillons = echantillonService.getAllEchantillons();
      return echantillons;
    }

    @PostMapping
    public EchantillonDTO createEchantillon(@RequestBody EchantillonDTO echantillonDTO) {
        EchantillonDTO createdEchantillon = echantillonService.createEchantillon(echantillonDTO);
        return createdEchantillon;
    }

    @PutMapping("/{id}")
    public ResponseEntity<EchantillonDTO> updateEchantillon(
            @PathVariable("id") Long echantillonId,
            @RequestBody EchantillonDTO updatedEchantillonDTO) {
        EchantillonDTO updatedEchantillon = echantillonService.updateEchantillon(echantillonId ,updatedEchantillonDTO);
        return new ResponseEntity<>(updatedEchantillon, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEchantillon(@PathVariable("id") Long echantillonId) {
        echantillonService.deleteEchantillon(echantillonId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
