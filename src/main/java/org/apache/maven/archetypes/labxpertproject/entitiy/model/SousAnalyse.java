package org.apache.maven.archetypes.labxpertproject.entitiy.model;

import lombok.Data;
import org.apache.maven.archetypes.labxpertproject.entitiy.enums.StatutDeResultat;

import javax.persistence.*;

@Entity
@Data
@Table(name = "SousAnalyse")
public class SousAnalyse {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SousAnalyseSequance")
    @SequenceGenerator(name = "SousAnalyseSequance", sequenceName = "SousAnalyseSequance", allocationSize = 1)
    private Long resultatId;

    @Column(name = "valeurAtester")
    private double valeurAtester;


    @Enumerated(EnumType.STRING)
    @Column(name = "statutDeResultat")
    private StatutDeResultat statutDeResultat;

    // In SousAnalyse class
    @OneToOne(mappedBy = "sousAnalyse", cascade = CascadeType.ALL)
    private SousAnalyseMesures sousAnalyseMesures;


    @ManyToOne (cascade = CascadeType.REMOVE)
    @JoinColumn(name = "analyse_id")
    private Analyse analyse;
}
