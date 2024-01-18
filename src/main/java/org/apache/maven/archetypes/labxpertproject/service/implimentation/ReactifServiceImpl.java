package org.apache.maven.archetypes.labxpertproject.service.implimentation;

import org.apache.maven.archetypes.labxpertproject.DTOs.ReactifDTO;
import org.apache.maven.archetypes.labxpertproject.entitiy.model.Reactif;
import org.apache.maven.archetypes.labxpertproject.repository.AnalyseRepository;
import org.apache.maven.archetypes.labxpertproject.repository.ReactifRepository;
import org.apache.maven.archetypes.labxpertproject.service.interfaces.IReactifService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReactifServiceImpl implements IReactifService {
    @Autowired
    private ReactifRepository reactifRepository;

    @Autowired
    private AnalyseRepository analyseRepository;

    @Autowired
    private ModelMapper modelMapper;


    public ReactifDTO convertToDTO(Reactif reactif) {
        return modelMapper.map(reactif, ReactifDTO.class);
    }


    public Reactif convertToEntity(ReactifDTO reactifDTO) {
        return modelMapper.map(reactifDTO, Reactif.class);
    }


    @Override
    @Transactional
    public ReactifDTO addReactif(ReactifDTO reactifDTO) {
        try {
            Reactif reactif= convertToEntity(reactifDTO);
            reactif = reactifRepository.save(reactif);
            System.out.println("Reactif added successfully service");
            return convertToDTO(reactif);
        } catch (Exception e) {
            throw new RuntimeException("Error adding reactif", e);
        }
    }


}
