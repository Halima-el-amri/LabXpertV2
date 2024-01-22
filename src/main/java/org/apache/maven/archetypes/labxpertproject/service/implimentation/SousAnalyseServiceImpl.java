package org.apache.maven.archetypes.labxpertproject.service.implimentation;

import org.apache.maven.archetypes.labxpertproject.DTOs.SousAnalyseDTO;
import org.apache.maven.archetypes.labxpertproject.entitiy.model.SousAnalyse;
import org.apache.maven.archetypes.labxpertproject.repository.SousAnalyseRepository;
import org.apache.maven.archetypes.labxpertproject.service.interfaces.ISousAnalyseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SousAnalyseServiceImpl implements ISousAnalyseService {

    @Autowired
    private SousAnalyseRepository sousAnalyseRepository;

    @Autowired
    private ModelMapper modelMapper;

    public SousAnalyseDTO createSousAnalyse(SousAnalyseDTO sousAnalyseDTO) {
        SousAnalyse sousAnalyse = modelMapper.map(sousAnalyseDTO, SousAnalyse.class);
        sousAnalyse = sousAnalyseRepository.save(sousAnalyse);
        return modelMapper.map(sousAnalyse, SousAnalyseDTO.class);
    }

    public List<SousAnalyseDTO> getAllSousAnalyses() {
        return sousAnalyseRepository.findAll().stream()
                .map(sousAnalyse -> modelMapper.map(sousAnalyse, SousAnalyseDTO.class))
                .collect(Collectors.toList());
    }

    public SousAnalyseDTO getSousAnalyseById(Long id) {
        SousAnalyse sousAnalyse = sousAnalyseRepository.findById(id).orElse(null);
        return modelMapper.map(sousAnalyse, SousAnalyseDTO.class);
    }

    public SousAnalyseDTO updateSousAnalyse(Long sousAnalyseId, SousAnalyseDTO updatedSousAnalyseDTO) {
        SousAnalyse sousAnalyse = sousAnalyseRepository.findById(sousAnalyseId).orElse(null);
        if (sousAnalyse != null) {
            sousAnalyse = modelMapper.map(updatedSousAnalyseDTO, SousAnalyse.class);
            sousAnalyse = sousAnalyseRepository.save(sousAnalyse);
            return modelMapper.map(sousAnalyse, SousAnalyseDTO.class);
        }
        return null;
    }

    public void deleteSousAnalyse(Long id) {
        sousAnalyseRepository.deleteById(id);
    }
}