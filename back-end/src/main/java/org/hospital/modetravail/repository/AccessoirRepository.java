package org.hospital.modetravail.repository;

import org.hospital.modetravail.entities.Accessoir;
import org.hospital.modetravail.entities.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessoirRepository extends JpaRepository<Accessoir,Long> {
   public List<Accessoir> findByEquipementFamille_Id(Long equifId);

}
