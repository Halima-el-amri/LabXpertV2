package org.apache.maven.archetypes.labxpertproject.service.interfaces;

import org.apache.maven.archetypes.labxpertproject.DTOs.SousAnalyseMesuresDTO;

import java.util.List;

public interface ISousAnalyseMesuresService {


    SousAnalyseMesuresDTO getSousAnalyseMesures(Long id);

    List<SousAnalyseMesuresDTO> getAllSousAnalyseMesures();
}
