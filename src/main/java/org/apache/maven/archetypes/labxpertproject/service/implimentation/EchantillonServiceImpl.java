package org.apache.maven.archetypes.labxpertproject.service.implimentation;

import org.apache.maven.archetypes.labxpertproject.DTOs.EchantillonDTO;
import org.apache.maven.archetypes.labxpertproject.DTOs.PatientDTO;
import org.apache.maven.archetypes.labxpertproject.entitiy.model.Echantillon;
import org.apache.maven.archetypes.labxpertproject.entitiy.model.Patient;
import org.apache.maven.archetypes.labxpertproject.repository.EchantillonRepository;
import org.apache.maven.archetypes.labxpertproject.repository.PatientRepository;
import org.apache.maven.archetypes.labxpertproject.service.interfaces.IEchantillonService;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EchantillonServiceImpl implements IEchantillonService {

    @Autowired
    private EchantillonRepository echantillonRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public EchantillonDTO getEchantillonById(Long id) {
        Echantillon echantillon = echantillonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Echantillon not found with id: " + id));

        return modelMapper.map(echantillon, EchantillonDTO.class);
    }

    @Override
    public List<EchantillonDTO> getAllEchantillons() {
        List<Echantillon> echantillons = echantillonRepository.findAll();
        return echantillons.stream()
                .map(echantillon -> modelMapper.map(echantillon, EchantillonDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public EchantillonDTO createEchantillon(EchantillonDTO echantillonDTO) {
        Patient patient = patientRepository.findById(echantillonDTO.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + echantillonDTO.getPatientId()));

        Echantillon echantillon = modelMapper.map(echantillonDTO, Echantillon.class);
        echantillon.setPatient(patient);

        Echantillon savedEchantillon = echantillonRepository.save(echantillon);

        // Map the saved Echantillon to EchantillonDTO
        EchantillonDTO responseDTO = modelMapper.map(savedEchantillon, EchantillonDTO.class);

        // Map the associated Patient to PatientDTO
        responseDTO.setPatient(modelMapper.map(patient, PatientDTO.class));

        return responseDTO;
    }



    @Override
    public EchantillonDTO updateEchantillon(Long echantillonId, EchantillonDTO updatedEchantillonDTO) {
        Echantillon existingEchantillon = echantillonRepository.findById(echantillonId)
                .orElseThrow(() -> new EntityNotFoundException("Echantillon not found with id: " + echantillonId));

        // Configure ModelMapper for handling null values during mapping
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        // Ensure consistent use of ModelMapper
        modelMapper.map(updatedEchantillonDTO, existingEchantillon);

        Echantillon savedEchantillon = echantillonRepository.save(existingEchantillon);
        return modelMapper.map(savedEchantillon, EchantillonDTO.class);
    }

    @Override
    public void deleteEchantillon(Long id) {
        Echantillon existingEchantillon = echantillonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Echantillon not found with id: " + id));
        echantillonRepository.delete(existingEchantillon);
    }
}
