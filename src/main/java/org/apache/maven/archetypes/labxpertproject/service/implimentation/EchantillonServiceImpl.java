package org.apache.maven.archetypes.labxpertproject.service.implimentation;

import org.apache.maven.archetypes.labxpertproject.DTOs.EchantillonDTO;
import org.apache.maven.archetypes.labxpertproject.entitiy.model.Echantillon;
import org.apache.maven.archetypes.labxpertproject.repository.EchantillonRepository;
import org.apache.maven.archetypes.labxpertproject.service.interfaces.IEchantillonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class EchantillonServiceImpl implements IEchantillonService {
    @Autowired
    private EchantillonRepository echantillonRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public EchantillonDTO createEchantillon(EchantillonDTO echantillonDTO) {
        Echantillon echantillon =modelMapper.map(echantillonDTO, Echantillon.class);
        Echantillon savedEchantillon = echantillonRepository.save(echantillon);
        return modelMapper.map(savedEchantillon, EchantillonDTO.class);
    }
}
