package org.apache.maven.archetypes.labxpertproject.service.implimentation;
import org.apache.maven.archetypes.labxpertproject.entitiy.enums.StatutDanalyse;
import org.apache.maven.archetypes.labxpertproject.entitiy.model.*;
import org.apache.maven.archetypes.labxpertproject.repository.*;
import org.apache.maven.archetypes.labxpertproject.service.interfaces.IAnalyseService;


import org.apache.maven.archetypes.labxpertproject.DTOs.AnalyseDTO;
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
    private ReactifRepository reactifRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EchantillonRepository echantillonRepository;

    @Autowired
    private PlanificationRepository planificationRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

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
        long count = analyseRepository.countByEtatAnalyse(StatutDanalyse.EN_COURS_DANALYSE);
        if (count >=5) {
            analyseDTO.setEtatAnalyse(StatutDanalyse.EN_ATTENTE);
        }else {
            analyseDTO.setEtatAnalyse(StatutDanalyse.EN_COURS_DANALYSE);
        }

        for (Long reactifId : analyseDTO.getReactifsIds()) {
            Reactif reactif = reactifRepository.findById(reactifId)
                    .orElseThrow(() -> new EntityNotFoundException("Reactif not found with id: " + reactifId));
            if (reactif.getQuantite() <= 0) {
                throw new RuntimeException("Insufficient quantity for reactif with id: " + reactifId);
            }
        }
        Analyse analyse = modelMapper.map(analyseDTO, Analyse.class);

        Echantillon echantillon = echantillonRepository.findById(analyseDTO.getEchantillonId()).orElse(null);
        if (echantillon == null) {
            throw new EntityNotFoundException("Echantillon not found with id: " + analyseDTO.getEchantillonId());
        }
        analyse.setEchantillon(echantillon);

        Planification planification = planificationRepository.findById(analyseDTO.getPlanificationId()).orElse(null);
        if (planification == null) {
            throw new EntityNotFoundException("Planification not found with id: " + analyseDTO.getPlanificationId());
        }
        analyse.setPlanification(planification);

        Utilisateur utilisateur = utilisateurRepository.findById(analyseDTO.getUtilisateurId()).orElse(null);
        if (utilisateur == null) {
            throw new EntityNotFoundException("Utilisateur not found with id: " + analyseDTO.getUtilisateurId());
        }
        analyse.setUtilisateur(utilisateur);

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
