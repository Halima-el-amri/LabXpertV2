package org.apache.maven.archetypes.labxpertproject.service.implimentation;

import org.apache.maven.archetypes.labxpertproject.DTOs.UtilisateurDTO;

import org.apache.maven.archetypes.labxpertproject.entitiy.model.Utilisateur;
import org.apache.maven.archetypes.labxpertproject.repository.UtilisateurRepository;
import org.apache.maven.archetypes.labxpertproject.service.interfaces.IUtilisateurSerivce;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public UtilisateurDTO addUtilisateur(UtilisateurDTO userDTO) {
        Utilisateur utilisateur = convertToEntity(userDTO);
        utilisateurRepository.save(utilisateur);
        return userDTO;
    }

    public List<UtilisateurDTO> getAllUtilisateur() {
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        List<UtilisateurDTO> utilisateurDTOS = new ArrayList<>();
        for (Utilisateur utilisateur : utilisateurs) {
            utilisateurDTOS.add(convertToDTO(utilisateur));
        }
        return utilisateurDTOS;
    }

    public UtilisateurDTO getUtilisateurById(Long userId) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(userId);
        if (utilisateur.isPresent()) {
            return convertToDTO(utilisateur.get());
        }
        return null;
    }

    public UtilisateurDTO updateUtilisateur(UtilisateurDTO userDTO) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(userDTO.getUtilisateurId());
        if (utilisateur.isPresent()) {
            utilisateur.get().setNomUtilisateur(userDTO.getNomUtilisateur());
            utilisateur.get().setPassword(userDTO.getPassword());
            utilisateur.get().setRoleDutilisateur(userDTO.getRoleDutilisateur());
            utilisateurRepository.save(utilisateur.get());
            return convertToDTO(utilisateur.get());
        }
        return null;
    }

    public void deleteUtilisateur(Long userId) {
        utilisateurRepository.deleteById(userId);
    }

}