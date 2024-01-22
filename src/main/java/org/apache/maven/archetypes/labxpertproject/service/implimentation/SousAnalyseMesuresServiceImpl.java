package org.apache.maven.archetypes.labxpertproject.service.implimentation;

import org.apache.maven.archetypes.labxpertproject.DTOs.SousAnalyseMesuresDTO;
import org.apache.maven.archetypes.labxpertproject.entitiy.model.SousAnalyseMesures;
import org.apache.maven.archetypes.labxpertproject.repository.SousAnalyseMesuresRepository;
import org.apache.maven.archetypes.labxpertproject.service.interfaces.ISousAnalyseMesuresService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SousAnalyseMesuresServiceImpl implements ISousAnalyseMesuresService {

    @Autowired
    private SousAnalyseMesuresRepository sousAnalyseMesuresRepository;

    @Autowired
    private ModelMapper modelMapper;

    public SousAnalyseMesuresDTO getSousAnalyseMesures(Long id) {
        SousAnalyseMesures sousAnalyseMesures = sousAnalyseMesuresRepository.findById(id).orElse(null);
        return modelMapper.map(sousAnalyseMesures, SousAnalyseMesuresDTO.class);
    }

    public List<SousAnalyseMesuresDTO> getAllSousAnalyseMesures() {
        List<SousAnalyseMesures> allSousAnalyseMesures = sousAnalyseMesuresRepository.findAll();
        return allSousAnalyseMesures.stream()
                .map(sousAnalyseMesures -> modelMapper.map(sousAnalyseMesures, SousAnalyseMesuresDTO.class))
                .collect(Collectors.toList());
    }
}
