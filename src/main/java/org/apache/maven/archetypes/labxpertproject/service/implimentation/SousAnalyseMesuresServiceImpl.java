package org.apache.maven.archetypes.labxpertproject.service.implimentation;

import org.apache.maven.archetypes.labxpertproject.DTOs.SousAnalyseMesuresDTO;
import org.apache.maven.archetypes.labxpertproject.entitiy.model.SousAnalyseMesures;
import org.apache.maven.archetypes.labxpertproject.repository.SousAnalyseMesuresRepository;
import org.apache.maven.archetypes.labxpertproject.service.interfaces.ISousAnalyseMesuresService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SousAnalyseMesuresServiceImpl implements ISousAnalyseMesuresService {

    @Autowired
    private SousAnalyseMesuresRepository sousAnalyseMesuresRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SousAnalyseMesuresDTO getSousAnalyseMesuresById(Long id) {
        SousAnalyseMesures sousAnalyseMesures = sousAnalyseMesuresRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SousAnalyseMesures not found with id: " + id));

        return modelMapper.map(sousAnalyseMesures, SousAnalyseMesuresDTO.class);
    }

    @Override
    public List<SousAnalyseMesuresDTO> getAllSousAnalyseMesures() {
        List<SousAnalyseMesures> sousAnalyseMesuresList = sousAnalyseMesuresRepository.findAll();
        return sousAnalyseMesuresList.stream()
                .map(sousAnalyseMesures -> modelMapper.map(sousAnalyseMesures, SousAnalyseMesuresDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public SousAnalyseMesuresDTO createSousAnalyseMesures(SousAnalyseMesuresDTO sousAnalyseMesuresDTO) {
        SousAnalyseMesures sousAnalyseMesures = modelMapper.map(sousAnalyseMesuresDTO, SousAnalyseMesures.class);
        SousAnalyseMesures savedSousAnalyseMesures = sousAnalyseMesuresRepository.save(sousAnalyseMesures);
        return modelMapper.map(savedSousAnalyseMesures, SousAnalyseMesuresDTO.class);
    }

    @Override
    public SousAnalyseMesuresDTO updateSousAnalyseMesures(Long sousAnalyseMesuresId, SousAnalyseMesuresDTO updatedSousAnalyseMesuresDTO) {
        SousAnalyseMesures existingSousAnalyseMesures = sousAnalyseMesuresRepository.findById(sousAnalyseMesuresId)
                .orElseThrow(() -> new EntityNotFoundException("SousAnalyseMesures not found with id: " + sousAnalyseMesuresId));

        updatedSousAnalyseMesuresDTO.setAnalyseId(sousAnalyseMesuresId);
        modelMapper.map(updatedSousAnalyseMesuresDTO, existingSousAnalyseMesures);
        SousAnalyseMesures savedSousAnalyseMesures = sousAnalyseMesuresRepository.save(existingSousAnalyseMesures);
        return modelMapper.map(savedSousAnalyseMesures, SousAnalyseMesuresDTO.class);
    }

    @Override
    public void deleteSousAnalyseMesures(Long id) {
        SousAnalyseMesures existingSousAnalyseMesures = sousAnalyseMesuresRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SousAnalyseMesures not found with id: " + id));
        sousAnalyseMesuresRepository.delete(existingSousAnalyseMesures);
    }
}
