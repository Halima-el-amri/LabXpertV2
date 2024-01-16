package org.apache.maven.archetypes.labxpertproject.config;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DTOsConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
