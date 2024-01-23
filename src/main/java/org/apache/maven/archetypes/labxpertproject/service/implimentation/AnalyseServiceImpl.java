package org.apache.maven.archetypes.labxpertproject.service.implimentation;
import org.apache.maven.archetypes.labxpertproject.DTOs.SousAnalyseDTO;
import org.apache.maven.archetypes.labxpertproject.DTOs.SousAnalyseMesuresDTO;
import org.apache.maven.archetypes.labxpertproject.entitiy.enums.AnalyseType;
import org.apache.maven.archetypes.labxpertproject.entitiy.enums.StatutDanalyse;
import org.apache.maven.archetypes.labxpertproject.entitiy.enums.StatutDeResultat;
import org.apache.maven.archetypes.labxpertproject.entitiy.model.*;
import org.apache.maven.archetypes.labxpertproject.repository.*;
import org.apache.maven.archetypes.labxpertproject.service.interfaces.IAnalyseService;


import org.apache.maven.archetypes.labxpertproject.DTOs.AnalyseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @Autowired
    private SousAnalyseRepository sousAnalyseRepository;

    @Autowired
    private SousAnalyseMesuresRepository sousAnalyseMesuresRepository;

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
        // Check lab capacity
        long count = analyseRepository.countByEtatAnalyse(StatutDanalyse.EN_COURS_DANALYSE);
        if (count >= 5) {
            throw new RuntimeException("The lab is full");
        } else {
            analyseDTO.setEtatAnalyse(StatutDanalyse.EN_COURS_DANALYSE);
            // Check reactif quantities
            for (Long reactifId : analyseDTO.getReactifsIds()) {
                Reactif reactif = reactifRepository.findById(reactifId)
                        .orElseThrow(() -> new EntityNotFoundException("Reactif not found with id: " + reactifId));
                if (reactif.getQuantite() <= 0) {
                    throw new RuntimeException("Insufficient quantity for reactif with id: " + reactifId);
                }
            }
            // Fetch related entities
            Echantillon echantillon = echantillonRepository.findById(analyseDTO.getEchantillonId())
                    .orElseThrow(() -> new EntityNotFoundException("Echantillon not found with id: " + analyseDTO.getEchantillonId()));
            Planification planification = planificationRepository.findById(analyseDTO.getPlanificationId())
                    .orElseThrow(() -> new EntityNotFoundException("Planification not found with id: " + analyseDTO.getPlanificationId()));
            Utilisateur utilisateur = utilisateurRepository.findById(analyseDTO.getUtilisateurId())
                    .orElseThrow(() -> new EntityNotFoundException("Utilisateur not found with id: " + analyseDTO.getUtilisateurId()));

            // Map AnalyseDTO to Analyse
            Analyse analyse = modelMapper.map(analyseDTO, Analyse.class);
            // Set relationships
            analyse.setEchantillon(echantillon);
            analyse.setPlanification(planification);
            analyse.setUtilisateur(utilisateur);

            // Fetch and set SousAnalyses
            List<SousAnalyse> sousAnalyses = new ArrayList<>();
            for (SousAnalyseDTO sousAnalyseDTO : analyseDTO.getSousAnalyses()) {
                SousAnalyse sousAnalyse = sousAnalyseRepository.findById(sousAnalyseDTO.getSousAnalyseId())
                        .orElseThrow(() -> new EntityNotFoundException("SousAnalyse not found with id: " + sousAnalyseDTO.getSousAnalyseId()));

                // Fetch SousAnalyseMesures using ID
                SousAnalyseMesures sousAnalyseMesures = sousAnalyseMesuresRepository.findById(sousAnalyseDTO.getSousAnalyseMesuresId())
                        .orElseThrow(() -> new EntityNotFoundException("SousAnalyseMesures not found with id: " + sousAnalyseDTO.getSousAnalyseMesuresId()));

                // Compare valeur with min and max and set StatutDeResultat
                StatutDeResultat statutDeResultat = compareValeurWithMinMax(sousAnalyse.getValeur(), sousAnalyseMesures.getMin(), sousAnalyseMesures.getMax());
                sousAnalyse.setStatutDeResultat(statutDeResultat);

                // Set SousAnalyseMesuresId
                sousAnalyse.setSousAnalyseMesures(sousAnalyseMesures);

                // Add to the list
                sousAnalyses.add(sousAnalyse);
            }

            analyse.setSousAnalyses(sousAnalyses);
            analyse.setDateDebutAnalyse(LocalDate.now()); // Set your desired value
            analyse.setAnalyseType(AnalyseType.CHIMIE); // Set your desired value
            analyse.setCommentaire("Any comment you provided"); // Set your desired value

            // Save the Analyse entity to the database
            Analyse savedAnalyse = analyseRepository.save(analyse);

            // Map to AnalyseDTO with sousAnalyseMesures details
            AnalyseDTO resultDTO = modelMapper.map(savedAnalyse, AnalyseDTO.class);
            resultDTO.getSousAnalyses().forEach(sousAnalyseDTO -> {
                sousAnalyseDTO.setSousAnalyseMesures(modelMapper.map(sousAnalyseMesuresRepository.findById(sousAnalyseDTO.getSousAnalyseMesuresId()).get(), SousAnalyseMesuresDTO.class));
            });

            return resultDTO;
        }
    }

//
//    public AnalyseDTO addAnalyse(AnalyseDTO analyseDTO) {
//        // Check lab capacity
//        long count = analyseRepository.countByEtatAnalyse(StatutDanalyse.EN_COURS_DANALYSE);
//        if (count >= 5) {
//            throw new RuntimeException("The lab is full");
//        } else {
//            analyseDTO.setEtatAnalyse(StatutDanalyse.EN_COURS_DANALYSE);
////
////            // Check reactif quantities
////            for (Long reactifId : analyseDTO.getReactifsIds()) {
////                Reactif reactif = reactifRepository.findById(reactifId)
////                        .orElseThrow(() -> new EntityNotFoundException("Reactif not found with id: " + reactifId));
////                if (reactif.getQuantite() <= 0) {
////                    throw new RuntimeException("Insufficient quantity for reactif with id: " + reactifId);
////                }
////            }
//
//            // Fetch related entities
//            Echantillon echantillon = echantillonRepository.findById(analyseDTO.getEchantillonId())
//                    .orElseThrow(() -> new EntityNotFoundException("Echantillon not found with id: " + analyseDTO.getEchantillonId()));
//            Planification planification = planificationRepository.findById(analyseDTO.getPlanificationId())
//                    .orElseThrow(() -> new EntityNotFoundException("Planification not found with id: " + analyseDTO.getPlanificationId()));
//            Utilisateur utilisateur = utilisateurRepository.findById(analyseDTO.getUtilisateurId())
//                    .orElseThrow(() -> new EntityNotFoundException("Utilisateur not found with id: " + analyseDTO.getUtilisateurId()));
//
//            // Map AnalyseDTO to Analyse
//            Analyse analyse = modelMapper.map(analyseDTO, Analyse.class);
//            // Set relationships
//            analyse.setEchantillon(echantillon);
//            analyse.setPlanification(planification);
//            analyse.setUtilisateur(utilisateur);
//
//            // Fetch and set SousAnalyses
//            List<SousAnalyse> sousAnalyses = analyseDTO.getSousAnalyses().stream()
//                    .map(sousAnalyseDTO -> {
//                        SousAnalyse sousAnalyse = sousAnalyseRepository.findById(sousAnalyseDTO.getSousAnalyseId())
//                                .orElseThrow(() -> new EntityNotFoundException("SousAnalyse not found with id: " + sousAnalyseDTO.getSousAnalyseId()));
//                        sousAnalyse.setValeur(sousAnalyseDTO.getValeur());
//                        return sousAnalyse;
//                    })
//                    .collect(Collectors.toList());
//
//            analyse.setSousAnalyses(sousAnalyses);
//
//            // Fetch and set SousAnalyseMesures (assuming each SousAnalyse has a corresponding SousAnalyseMesures)
//            for (SousAnalyse sousAnalyse : sousAnalyses) {
//                SousAnalyseMesures sousAnalyseMesures = sousAnalyseMesuresRepository.findById(sousAnalyse.getSousAnalyseMesures().getSousAnalyseMesuresId())
//                        .orElseThrow(() -> new EntityNotFoundException("SousAnalyseMesures not found with id: " + sousAnalyse.getSousAnalyseMesures().getSousAnalyseMesuresId()));
//                // Compare valeur with min and max and set StatutDeResultat
//                StatutDeResultat statutDeResultat = compareValeurWithMinMax(sousAnalyse.getValeur(), sousAnalyseMesures.getMin(), sousAnalyseMesures.getMax());
//                sousAnalyse.setStatutDeResultat(statutDeResultat);
//            }
//
//            Analyse savedAnalyse = analyseRepository.save(analyse);
//            return modelMapper.map(savedAnalyse, AnalyseDTO.class);
//        }
//    }


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

    private StatutDeResultat compareValeurWithMinMax(double valeur, double min, double max) {
        if (valeur >= min && valeur <= max) {
            return StatutDeResultat.NORMAL;
        } else {
            return StatutDeResultat.ANORMAL;
        }
    }
}
