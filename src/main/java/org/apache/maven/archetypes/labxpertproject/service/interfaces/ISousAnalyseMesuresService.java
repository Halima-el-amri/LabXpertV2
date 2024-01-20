package org.apache.maven.archetypes.labxpertproject.service.interfaces;

import org.apache.maven.archetypes.labxpertproject.DTOs.SousAnalyseMesuresDTO;

import java.util.List;

public interface ISousAnalyseMesuresService {

    SousAnalyseMesuresDTO getSousAnalyseMesuresById(Long id);

    List<SousAnalyseMesuresDTO> getAllSousAnalyseMesures();

    SousAnalyseMesuresDTO createSousAnalyseMesures(SousAnalyseMesuresDTO sousAnalyseMesuresDTO);

    SousAnalyseMesuresDTO updateSousAnalyseMesures(Long sousAnalyseMesuresId, SousAnalyseMesuresDTO updatedSousAnalyseMesuresDTO);

    void deleteSousAnalyseMesures(Long id);
}
