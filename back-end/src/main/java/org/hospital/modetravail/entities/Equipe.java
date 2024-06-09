package org.hospital.modetravail.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity  @Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String responsable;
    @OneToMany(mappedBy = "equipe")
    private Collection<Employe> employes;
    @OneToOne
    private ShiftPlan shiftPlan;
}