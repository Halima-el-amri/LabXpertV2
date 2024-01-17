package org.apache.maven.archetypes.labxpertproject.service.implimentation;

import org.apache.maven.archetypes.labxpertproject.DTOs.UtilisateurDTO;

import org.apache.maven.archetypes.labxpertproject.entitiy.model.Utilisateur;
import org.apache.maven.archetypes.labxpertproject.repository.UtilisateurRepository;
import org.apache.maven.archetypes.labxpertproject.service.interfaces.IUtilisateurSerivce;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class UtilisateurServiceImpl implements IUtilisateurSerivce {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public UtilisateurDTO convertToDTO(Utilisateur utilisateur) {
        return modelMapper.map(utilisateur, UtilisateurDTO.class);
    }

    public Utilisateur convertToEntity(UtilisateurDTO utilisateurDTO) {
        return modelMapper.map(utilisateurDTO, Utilisateur.class);
    }

    @Transactional
    public UtilisateurDTO addUtilisateur(UtilisateurDTO userDTO) {
        try {
            Utilisateur utilisateur = convertToEntity(userDTO);
            utilisateur = utilisateurRepository.save(utilisateur);  // Save and get the entity with generated ID
            System.out.println("Utilisateur added successfully service");
            return convertToDTO(utilisateur);  // Return the DTO with the generated ID
        } catch (Exception e) {
            // Handle exception, log, or rethrow
            throw new RuntimeException("Error adding utilisateur", e);
        }
    }

    @Transactional(readOnly = true)
    public List<UtilisateurDTO> getAllUtilisateur() {
        try {
            List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
            List<UtilisateurDTO> utilisateurDTOS = new ArrayList<>();
            for (Utilisateur utilisateur : utilisateurs) {
                utilisateurDTOS.add(convertToDTO(utilisateur));
            }
            return utilisateurDTOS;
        } catch (Exception e) {
            // Handle exception, log, or rethrow
            throw new RuntimeException("Error getting all utilisateurs", e);
        }
    }

    @Transactional(readOnly = true)
    public UtilisateurDTO getUtilisateurById(Long userId) {
        try {
            Optional<Utilisateur> utilisateur = utilisateurRepository.findById(userId);
            return utilisateur.map(this::convertToDTO).orElse(null);
        } catch (Exception e) {
            // Handle exception, log, or rethrow
            throw new RuntimeException("Error getting utilisateur by ID", e);
        }
    }

    @Transactional
    public UtilisateurDTO updateUtilisateur(UtilisateurDTO userDTO) {
        try {
            Optional<Utilisateur> utilisateur = utilisateurRepository.findById(userDTO.getUtilisateurId());
            if (utilisateur.isPresent()) {
                utilisateur.get().setNomUtilisateur(userDTO.getNomUtilisateur());
                utilisateur.get().setPassword(userDTO.getPassword());
                utilisateur.get().setRoleDutilisateur(userDTO.getRoleDutilisateur());
                utilisateurRepository.save(utilisateur.get());
                return convertToDTO(utilisateur.get());
            }
            return null;
        } catch (Exception e) {
            // Handle exception, log, or rethrow
            throw new RuntimeException("Error updating utilisateur", e);
        }
    }

    @Transactional
    public void deleteUtilisateur(Long userId) {
        try {
            utilisateurRepository.deleteById(userId);
        } catch (Exception e) {
            // Handle exception, log, or rethrow
            throw new RuntimeException("Error deleting utilisateur", e);
        }
    }


}