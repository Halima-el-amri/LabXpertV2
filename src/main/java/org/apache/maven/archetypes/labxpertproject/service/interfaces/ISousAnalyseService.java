package org.apache.maven.archetypes.labxpertproject.service.interfaces;

import org.apache.maven.archetypes.labxpertproject.DTOs.SousAnalyseDTO;

import java.util.List;

public interface ISousAnalyseService {

    SousAnalyseDTO createSousAnalyse(SousAnalyseDTO sousAnalyseDTO);

    List<SousAnalyseDTO> getAllSousAnalyses();

    SousAnalyseDTO getSousAnalyseById(Long id);

    SousAnalyseDTO updateSousAnalyse(Long sousAnalyseId, SousAnalyseDTO updatedSousAnalyseDTO);

    void deleteSousAnalyse(Long id);

}
