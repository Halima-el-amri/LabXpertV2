package org.apache.maven.archetypes.labxpertproject.service.implimentation;
import org.apache.maven.archetypes.labxpertproject.service.interfaces.IAnalyseService;


import org.apache.maven.archetypes.labxpertproject.DTOs.AnalyseDTO;
import org.apache.maven.archetypes.labxpertproject.entitiy.model.Analyse;
import org.apache.maven.archetypes.labxpertproject.repository.AnalyseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnalyseServiceImpl implements IAnalyseService {

    @Autowired
    private AnalyseRepository analyseRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AnalyseDTO getAnalyseById(Long id) {
        Analyse analyse = analyseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Analyse not found with id: " + id));

        return modelMapper.map(analyse, AnalyseDTO.class);
    }

    public List<AnalyseDTO> getAllAnalyses() {
        List<Analyse> analyses = analyseRepository.findAll();
        return analyses.stream()
                .map(analyse -> modelMapper.map(analyse, AnalyseDTO.class))
                .collect(Collectors.toList());
    }

    public AnalyseDTO addAnalyse(AnalyseDTO analyseDTO) {
        Analyse analyse = modelMapper.map(analyseDTO, Analyse.class);
        Analyse savedAnalyse = analyseRepository.save(analyse);
        return modelMapper.map(savedAnalyse, AnalyseDTO.class);
    }

    public AnalyseDTO updateAnalyse(Long analyseId, AnalyseDTO updatedAnalyseDTO) {
        Analyse existingAnalyse = analyseRepository.findById(analyseId)
                .orElseThrow(() -> new EntityNotFoundException("Analyse not found with id: " + analyseId));

        updatedAnalyseDTO.setAnalyseId(analyseId);
        modelMapper.map(updatedAnalyseDTO, existingAnalyse);
        Analyse savedAnalyse = analyseRepository.save(existingAnalyse);
        return modelMapper.map(savedAnalyse, AnalyseDTO.class);
    }

    public void deleteAnalyse(Long id) {
        Analyse existingAnalyse = analyseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Analyse not found with id: " + id));
        analyseRepository.delete(existingAnalyse);
    }
}
