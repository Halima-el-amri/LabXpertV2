package org.apache.maven.archetypes.labxpertproject.service.interfaces;

import org.apache.maven.archetypes.labxpertproject.DTOs.ReactifDTO;
import org.springframework.transaction.annotation.Transactional;

public interface IReactifService {
    @Transactional
    ReactifDTO addReactif(ReactifDTO reactifDTO);
}
