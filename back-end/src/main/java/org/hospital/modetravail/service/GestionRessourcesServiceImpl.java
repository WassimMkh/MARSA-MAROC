package org.hospital.modetravail.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import org.hospital.modetravail.entities.*;
import org.hospital.modetravail.repository.*;
import org.hospital.modetravail.requests.ModeTravailRequest;
import org.hospital.modetravail.requests.NormeProductiviteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service @Transactional
public class GestionRessourcesServiceImpl implements GestionRessourcesService {
    @Autowired
    TypeTraficRepository typeTraficRepository;
    @Autowired
    TraficRepository traficRepository;
    @Autowired
    EmployeRepository employeRepository;
    @Autowired
    EquipementFamilleRepository equipementFamilleRepository;
    @Autowired
    EquipementRepository equipementRepository;
    @Autowired
    EquipeRepository equipeRepository;
    @Autowired
    AccessoirRepository accessoirRepository;
    @Autowired
    ModeRepository modeRepository;
    @Autowired
    ModeTravailRepository modeTravailRepository;
    @Autowired
    PeriodeShiftRepository periodeShiftRepository;
    @Autowired
    ShiftPlanRepository shiftPlanRepository;
    @Autowired
    MainTheoriqueRepository mainTheoriqueRepository;
    @Autowired
    NormeProductiviteRepository normeProductiviteRepository;

    public void initTypeTrafics() {
        Stream.of("Sacherie et divers", "Vrac", "Conteneur")
                .forEach(typeT -> {
                    TypeTrafic typeTrafic = new TypeTrafic();
                    typeTrafic.setType(typeT);
                    typeTraficRepository.save(typeTrafic);
                });

    }

    public void initTrafics() {

        TypeTrafic sacherie = typeTraficRepository.findByType("Sacherie et divers");
        TypeTrafic vrac = typeTraficRepository.findByType("Vrac");
        TypeTrafic conteneur = typeTraficRepository.findByType("Conteneur");

        // Create and associate Trafic instances
        List<Trafic> trafics = Arrays.asList(
                new Trafic(null, "Pallette", sacherie, null, null),
                new Trafic(null, "Big Bag", sacherie, null, null),
                new Trafic(null, "Cartons", sacherie, null, null),
                new Trafic(null, "Sacs", sacherie, null, null),
                new Trafic(null, "Céréales", vrac, null, null),
                new Trafic(null, "Minerais", vrac, null, null),
                new Trafic(null, "Produits Chimiques", vrac, null, null),
                new Trafic(null, "Agrégats", vrac, null, null),
                new Trafic(null, "20 pieds", conteneur, null, null),
                new Trafic(null, "40 pieds", conteneur, null, null),
                new Trafic(null, "Reefer", conteneur, null, null),
                new Trafic(null, "Open Top", conteneur, null, null)
        );
        traficRepository.saveAll(trafics);

    }

    public void initEmployes() {
        List<String> names = Arrays.asList("Hassan_123", "Youssef_456", "Amina_789", "Fatimazahra_111", "Nawal_222",
                "Karim_333", "Said_444", "Khadija_555", "Omar_666", "Rachid_777", "Imane_888", "Samira_999", "Amine_123", "Yassine_456", "Jamal_789", "Habiba_111", "Salim_222",
                "Ikram_333", "Amal_444", "Ahlam_555", "Hamza_666", "Rachida_997", "Ilhame_888", "Saad_999");

        List<String> functions = Arrays.asList("CE", "CER", "CEQ", "Grutier");

        IntStream.range(0, names.size()).forEach(i -> {
            Employe employe = new Employe();
            employe.setNom(names.get(i));
            employe.setFonction(functions.get(i % functions.size()));
            employeRepository.save(employe);

        });
    }

    @Override
    public void initEquipementFamilles() {
        Stream.of("Grue", "Portique", "Chariot", "Tracteur")
                .forEach(f -> {
                    EquipementFamille equipementFamille = new EquipementFamille();
                    equipementFamille.setNom(f);
                    equipementFamilleRepository.save(equipementFamille);
                });
    }


    @Override
    public void initAccessoirs() {
        EquipementFamille grue = equipementFamilleRepository.findByNom("Grue");
        EquipementFamille portique = equipementFamilleRepository.findByNom("Portique");
        EquipementFamille chariot = equipementFamilleRepository.findByNom("Chariot");
        EquipementFamille tracteur = equipementFamilleRepository.findByNom("Tracteur");
        List<Accessoir> accessoirs = Arrays.asList(
                new Accessoir(null, "Palonniers", 50, grue, null),
                new Accessoir(null, "Crochet", 30, grue, null),
                new Accessoir(null, "Élingues", 20, grue, null),
                new Accessoir(null, "Treuils", 70, grue, null),
                new Accessoir(null, "Contrepoids", 5, grue, null),
                new Accessoir(null, "Dispositifs de sécurité", 13, grue, null),
                new Accessoir(null, "Palonniers de levage", 83, portique, null),
                new Accessoir(null, "Systèmes de pesage", 12, portique, null),
                new Accessoir(null, "Crochets de levage", 46, portique, null),
                new Accessoir(null, "Systèmes de guidage", 20, portique, null),
                new Accessoir(null, "Fourches supplémentaires", 35, chariot, null),
                new Accessoir(null, "Pinces", 50, chariot, null),
                new Accessoir(null, "Godets", 60, chariot, null),
                new Accessoir(null, "Bascules rotatives", 15, chariot, null),
                new Accessoir(null, "Crochets de levage", 11, chariot, null),
                new Accessoir(null, "Barres de Remorquage", 10, tracteur, null),
                new Accessoir(null, "Dispositifs d'Alignement de Remorque ", 22, tracteur, null),
                new Accessoir(null, "Bennes Basculantes ", 12, tracteur, null),
                new Accessoir(null, "Plaque de Sellette ", 11, tracteur, null)
        );
        accessoirRepository.saveAll(accessoirs);
    }

    @Override
    public void initEquipemnts() {
        EquipementFamille grue = equipementFamilleRepository.findByNom("Grue");
        EquipementFamille portique = equipementFamilleRepository.findByNom("Portique");
        EquipementFamille chariot = equipementFamilleRepository.findByNom("Chariot");
        EquipementFamille tracteur = equipementFamilleRepository.findByNom("Tracteur");
        List<Equipement> equipements = Arrays.asList(
                new Equipement(null, "à Pneux(100 à 120 t)", 50, grue, null),
                new Equipement(null, "à Pneux(6 à 100 t)", 30, grue, null),
                new Equipement(null, "à Rails(6 à 40 t)", 10, grue, null),
                new Equipement(null, "Autogrues(6 à 35 t)", 33, grue, null),
                new Equipement(null, "à Conteneurs(40 à 65 t)", 26, portique, null),
                new Equipement(null, "à minerais(14 à 28 t)", 14, portique, null),
                new Equipement(null, "de Parc(RTG)", 48, portique, null),
                new Equipement(null, "Cavalier 40t", 60, chariot, null),
                new Equipement(null, "Elevateur (2.5 à 45 t)", 70, chariot, null),
                new Equipement(null, "à Sellettes 60t", 30, tracteur, null),
                new Equipement(null, "à Sellettes (25 à 160 t )", 20, tracteur, null)
        );
        equipementRepository.saveAll(equipements);

    }

    @Override
    public void initMode() {
        Mode mode1 = new Mode();
        mode1.setNom("T/M");
        Mode mode2 = new Mode();
        mode2.setNom("T/M/Shift");
        modeRepository.save(mode1);
        modeRepository.save(mode2);
    }

    public void incrementModeTravail(String semaine, String jour) {
        ModeTravail modeTravail = new ModeTravail();
        modeTravail.setSemaine(semaine);
        modeTravail.setJour(jour);
        if (modeTravailRepository.count() > 0) {
            modeTravailRepository.deleteAll();
            modeTravailRepository.alterModeTravail();
        }
        modeTravailRepository.save(modeTravail);
    }

    public void incrementPeriodeShift(String normalShift1, String normalShift2, String normalShift3, String ramadanShift1, String ramadanShift2, String ramadanShift3, LocalDate ramadanStartDate, LocalDate ramadanEndDate) {
        PeriodeShift periodeShift = new PeriodeShift(null, normalShift1, normalShift2, normalShift3, ramadanShift1, ramadanShift2, ramadanShift3, ramadanStartDate, ramadanEndDate);
        periodeShiftRepository.save(periodeShift);
    }

    public void incrementEquipe(String nom, String responsable, List<Long> employeIds) {
        Equipe equipe = new Equipe();
        equipe.setNom(nom);
        equipe.setResponsable(responsable);
        List<Employe> employes = employeRepository.findAllById(employeIds);
        equipe.setEmployes(employes);
        employes.forEach(employe -> employe.setEquipe(equipe));

        equipeRepository.save(equipe);
        employeRepository.saveAll(employes);
    }

    public List<Employe> getEmployesByFonction(String fonction) {
        return (List<Employe>) employeRepository.findByFonction(fonction);
    }


    @Override
    public void incrementShiftPlan(String periode, LocalDate dateDebut,
                                   LocalDate dateFin, Long modeTravailId, String shift,
                                   Long equipeId) {
        ModeTravail modeTravail = modeTravailRepository.findById(modeTravailId).orElseThrow(()
                -> new EntityNotFoundException("ModeTravail not found with id " + modeTravailId));
        Equipe equipe = equipeRepository.findById(equipeId).orElseThrow(()
                -> new EntityNotFoundException("Equipe not found with id " + equipeId));

        ShiftPlan shiftPlan = equipe.getShiftPlan();
        if (shiftPlan == null) {
            shiftPlan = new ShiftPlan();
            shiftPlan.setEquipe(equipe);
        }

        shiftPlan.setPeriode(periode);
        shiftPlan.setModeTravail(modeTravail);
        shiftPlan.setDateDebut(dateDebut);
        shiftPlan.setDateFin(dateFin);
        shiftPlan.setShift(shift);

        shiftPlanRepository.save(shiftPlan);
    }


    @Override
    public void incrementMainTheorique(String nom,Long typeTraficIds, Long traficIds,List<Long> equipementFamilleIds, List<Long> equipementIds, List<Long> accessoireIds) {
        System.out.println("starting");
        TypeTrafic typeTrafic=typeTraficRepository.findById(typeTraficIds).orElseThrow(()
                -> new EntityNotFoundException("not found with id " ));
        System.out.println("typetrafic");
        Trafic trafics=traficRepository.findByIdd(traficIds);
        List<EquipementFamille> equipementFamilles=equipementFamilleRepository.findAllById(equipementFamilleIds);
        List<Equipement> equipements=equipementRepository.findAllById(equipementIds);
        MainTheorique mainTheorique=new MainTheorique();
        mainTheoriqueRepository.save(mainTheorique);
        equipementFamilles.forEach(equif -> equif.setMainTheorique(mainTheorique));


        for (Equipement equipement : equipements) {
            int nouvelleQuantite = equipement.getQuantite() - 1;
            equipement.setQuantite(nouvelleQuantite);
            equipement.setMainTheorique(mainTheorique);
        }


//        for (Trafic trafic : trafics) {
//            Collection<MainTheorique> mainTheoriqueCollection = trafic.getMainTheorique();
//            if (mainTheoriqueCollection == null) {
//                mainTheoriqueCollection = new ArrayList<>();
//                trafic.setMainTheorique(mainTheoriqueCollection);
//            }
//            mainTheoriqueCollection.add(mainTheorique);
//        }
        Collection<MainTheorique> mainTheoriqueCollection = trafics.getMainTheorique();
        mainTheoriqueCollection.add(mainTheorique);
        List<Accessoir> accessoirs=accessoirRepository.findAllById(accessoireIds);
        for (Accessoir accessoir : accessoirs) {
            int nouvelleQuantite = accessoir.getQuantite() - 1;
            accessoir.setQuantite(nouvelleQuantite);
            accessoir.setMainTheorique(mainTheorique);
        }
        mainTheorique.setNom(nom);
        mainTheorique.setTypeTrafic(typeTrafic);
        mainTheorique.setTrafics(trafics);
        mainTheorique.setEquipementFamilles(equipementFamilles);
        mainTheorique.setEquipements(equipements);
        mainTheorique.setAccessoirs(accessoirs);


        equipementFamilleRepository.saveAll(equipementFamilles);
        equipementRepository.saveAll(equipements);
        accessoirRepository.saveAll(accessoirs);
        traficRepository.save(trafics);
        typeTraficRepository.save(typeTrafic);
    }



    @Override
    public void incrementNormeProductivite(Long traficId, Long mainTheoriqueId,
                                           Long modeId,
                                           int norme, String sens, String suiviProduit) {
        Trafic trafic = traficRepository.findById(traficId).orElseThrow(()
                -> new EntityNotFoundException("not found with id "));
        MainTheorique mainTheorique = mainTheoriqueRepository
                .findById(mainTheoriqueId).orElseThrow(()
                        -> new EntityNotFoundException("not found with id "));
        Mode mode = modeRepository.findById(modeId).orElseThrow(()
                -> new EntityNotFoundException("not found with id "));
        NormeProductivite normeProductivite = new NormeProductivite();
        normeProductivite.setNorme(norme);
        normeProductivite.setSens(sens);
        normeProductivite.setMainTheorique(mainTheorique);
        normeProductivite.setSuiviProduit(suiviProduit);
        normeProductivite.setTrafic(trafic);
        normeProductivite.setMode(mode);
        traficRepository.save(trafic);
        modeRepository.save(mode);
        mainTheoriqueRepository.save(mainTheorique);
        normeProductiviteRepository.save(normeProductivite);
    }

    @Override
    public List<MainTheorique> getMainTheorique() {
        return mainTheoriqueRepository.findAll();
    }

    @Override
    public List<Trafic> getTraficByMainId(Long id) {
        return traficRepository.findAllByMainId(id);
    }


    @Override
    public List<Equipe> getEquipe() {
        return equipeRepository.findAll();
    }

    public List<String> getFonctions() {
        return employeRepository.findDistinctFonction();
    }

    public List<TypeTrafic> getTypeTrafic() {
        return typeTraficRepository.findAll();
    }

    @Override
    public List<Trafic> getTraficBYTypetrafic(Long id) {
        return traficRepository.findByTypeTrafic_Id(id);
    }


    public List<Mode> getMode() {
        return modeRepository.findAll();
    }

    public List<Equipement> getEquipementByEquipementFamille(Long Id) {
        return equipementRepository.findByEquipementFamille_Id(Id);
    }

    public List<Accessoir> getAccessoirByEquipementFamille(Long Id) {
        return accessoirRepository.findByEquipementFamille_Id(Id);
    }

    @Override
    public List<NormeProductivite> getNormeProductivite() {
        return normeProductiviteRepository.findAll();
    }

    @Override
    public void deleteNormeProductvite(Long normeProductiviteId) {
        normeProductiviteRepository.deleteById(normeProductiviteId);
    }

    @Override
    public void updateNormeProductvite(Long id, Long traficId,
                                       Long maintheoriqueId,
                                       Long modeId,
                                       int norme,
                                       String sens,
                                       String suiviProduit) {
        NormeProductivite normeProductivite = normeProductiviteRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("not found with id "));
        Trafic trafic = traficRepository.findById(traficId).orElseThrow(()
                -> new EntityNotFoundException("not found with id "));
        MainTheorique mainTheorique = mainTheoriqueRepository
                .findById(maintheoriqueId).orElseThrow(()
                        -> new EntityNotFoundException("not found with id "));
        Mode mode = modeRepository.findById(modeId).orElseThrow(()
                -> new EntityNotFoundException("not found with id "));
        normeProductivite.setId(id);
        normeProductivite.setNorme(norme);
        normeProductivite.setMainTheorique(mainTheorique);
        normeProductivite.setTrafic(trafic);
        normeProductivite.setSens(sens);
        normeProductivite.setSuiviProduit(suiviProduit);
        normeProductivite.setMode(mode);
        normeProductiviteRepository.updateNormeProductivite(
                normeProductivite.getId(),
                normeProductivite.getNorme(),
                normeProductivite.getSens(),
                normeProductivite.getSuiviProduit(),
                normeProductivite.getMainTheorique(),
                normeProductivite.getTrafic(),
                normeProductivite.getMode()
        );
    }

    @Override
    public void initPeriodShfit() {
        PeriodeShift periodeShift = new PeriodeShift();
        periodeShift.setNormalShift1("06:45 à 14:45");
        periodeShift.setNormalShift2("14:45 à 22:45");
        periodeShift.setNormalShift3("22:45 à 06:45");
        periodeShift.setRamadanShift1("07:45 à 15:45 ");
        periodeShift.setRamadanShift2("15:45 à 23:45");
        periodeShift.setRamadanShift3("23:45 à 07:45");
        if (periodeShiftRepository.count() > 0) {
            periodeShiftRepository.deleteAll();
            periodeShiftRepository.alterPeriodShift();
        }
        periodeShiftRepository.save(periodeShift);
    }

    @Override
    public PeriodeShift getPeriodeShifts() {
        return periodeShiftRepository.getPeriodeShift();
    }

    @Override
    public void updatePeriodeShift(Long id, LocalDate ramadanStartDate, LocalDate ramadanEndDate) {
        periodeShiftRepository.updateRamadanDates(id, ramadanStartDate, ramadanEndDate);
    }

    @Override
    public List<ModeTravail> getModeTravail() {
        return modeTravailRepository.findAll();
    }

    public List<EquipementFamille> getEquipementFamille() {
        return equipementFamilleRepository.findAll();
    }

    @Override
    public Boolean checkEquipeNom(String equipeNom) {
        return equipeRepository.existsByNom(equipeNom);
    }

    @Override
    public void updateShiftPlan(Long id, String periode, LocalDate dateDebut, LocalDate dateFin, String shift, Long equipeId, Long modeTravailId) {
        ShiftPlan shiftPlan = shiftPlanRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("not found with id "));
        ModeTravail modeTravail = modeTravailRepository.findById(modeTravailId).orElseThrow(() -> new EntityNotFoundException("not found with id "));
        Equipe equipe = equipeRepository.findById(equipeId).orElseThrow(() -> new EntityNotFoundException("not found with id "));



        shiftPlan.setPeriode(periode);
        shiftPlan.setModeTravail(modeTravail);
        shiftPlan.setEquipe(equipe);
        shiftPlan.setDateDebut(dateDebut);
        shiftPlan.setDateFin(dateFin);
        shiftPlan.setShift(shift);

        shiftPlanRepository.updateShiftPlan(id,shiftPlan.getPeriode(),shiftPlan.getDateDebut(),shiftPlan.getDateFin(),shiftPlan.getShift(),shiftPlan.getEquipe(),shiftPlan.getModeTravail());
    }

    @Override
    public ShiftPlan checkShiftPlanExists(Long equipeId) {
        return shiftPlanRepository.findByEquipeId(equipeId);
    }

    @Override
    public List<Employe> getEmployes() {
        return employeRepository.findAll();
    }

    @Override
    @Transactional
    public void addEmploye(String fonction,String nom) {
        Employe employe = new Employe();
        employe.setFonction(fonction);
        employe.setNom(nom);

        employeRepository.save(employe);
    }
    @Transactional
    @Override
    public void updateEmploye(Long id, String fonction , String nom) {
        Employe employe = employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe not found with id " + id));

        employe.setNom(nom);
        employe.setFonction(fonction);

        employeRepository.save(employe);
    }
    @Transactional
    @Override
    public void deleteEmploye(Long id) {
        Employe employe = employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe not found with id " + id));
        employeRepository.delete(employe);
    }
}