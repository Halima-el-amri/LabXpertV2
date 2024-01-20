package org.apache.maven.archetypes.labxpertproject.service.implimentation;

import org.apache.maven.archetypes.labxpertproject.DTOs.SousAnalyseDTO;
import org.apache.maven.archetypes.labxpertproject.entitiy.model.SousAnalyse;
import org.apache.maven.archetypes.labxpertproject.entitiy.model.SousAnalyseMesures;
import org.apache.maven.archetypes.labxpertproject.repository.SousAnalyseMesuresRepository;
import org.apache.maven.archetypes.labxpertproject.repository.SousAnalyseRepository;
import org.apache.maven.archetypes.labxpertproject.service.interfaces.ISousAnalyseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SousAnalyseServiceImpl implements ISousAnalyseService {

    @Autowired
    private SousAnalyseRepository sousAnalyseRepository;

    @Autowired
    private SousAnalyseMesuresRepository sousAnalyseMesuresRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public SousAnalyseDTO createSousAnalyse(SousAnalyseDTO sousAnalyseDTO) {
        // Map DTO to entity
        SousAnalyseMesures sousAnalyseMesures = modelMapper.map(sousAnalyseDTO, SousAnalyseMesures.class);
        // Save SousAnalyseMesures
        sousAnalyseMesures = sousAnalyseMesuresRepository.save(sousAnalyseMesures);

        // Map DTO to entity
        SousAnalyse sousAnalyse = modelMapper.map(sousAnalyseDTO, SousAnalyse.class);
        // Set the relationship with SousAnalyseMesures
        sousAnalyse.setSousAnalyseMesures(sousAnalyseMesures);
        // Save SousAnalyse
        sousAnalyse = sousAnalyseRepository.save(sousAnalyse);

        // Update the ID in DTO for the client
        sousAnalyseDTO.setSousAnalyseId(sousAnalyse.getSousAnalyseId());
        sousAnalyseDTO.setSousAnalyseMesuresId(sousAnalyseMesures.getSousAnalyseMesuresId());

        return sousAnalyseDTO;
    }

    public List<SousAnalyseDTO> getAllSousAnalyses() {
        List<SousAnalyse> sousAnalyses = sousAnalyseRepository.findAll();
        return sousAnalyses.stream()
                .map(sousAnalyse -> modelMapper.map(sousAnalyse, SousAnalyseDTO.class))
                .collect(Collectors.toList());
    }

    public SousAnalyseDTO getSousAnalyseById(Long id) {
        SousAnalyse sousAnalyse = sousAnalyseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SousAnalyse not found with id: " + id));

        return modelMapper.map(sousAnalyse, SousAnalyseDTO.class);
    }

    @Transactional
    public SousAnalyseDTO updateSousAnalyse(Long sousAnalyseId, SousAnalyseDTO updatedSousAnalyseDTO) {
        // Retrieve existing SousAnalyse
        SousAnalyse existingSousAnalyse = sousAnalyseRepository.findById(sousAnalyseId)
                .orElseThrow(() -> new EntityNotFoundException("SousAnalyse not found with id: " + sousAnalyseId));

        // Map updated DTO to existing entity
        modelMapper.map(updatedSousAnalyseDTO, existingSousAnalyse);

        // Update SousAnalyseMesures
        SousAnalyseMesures existingSousAnalyseMesures = existingSousAnalyse.getSousAnalyseMesures();

        if (existingSousAnalyseMesures == null) {
            // If the existing SousAnalyseMesures is null, create a new instance
            existingSousAnalyseMesures = new SousAnalyseMesures();
        }

        // Map the attributes from DTO to existing SousAnalyseMesures
        modelMapper.map(updatedSousAnalyseDTO, existingSousAnalyseMesures);

        // Set the bidirectional relationship
        existingSousAnalyse.setSousAnalyseMesures(existingSousAnalyseMesures);
        existingSousAnalyseMesures.setSousAnalyse(existingSousAnalyse);

        // Save the SousAnalyseMesures first
        sousAnalyseMesuresRepository.save(existingSousAnalyseMesures);

        // Save the updated SousAnalyse
        existingSousAnalyse = sousAnalyseRepository.save(existingSousAnalyse);

        return modelMapper.map(existingSousAnalyse, SousAnalyseDTO.class);
    }
}