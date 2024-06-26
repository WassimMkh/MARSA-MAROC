package org.hospital.modetravail.web;

import jakarta.persistence.EntityNotFoundException;
import org.hospital.modetravail.entities.*;
import org.hospital.modetravail.repository.ModeRepository;
import org.hospital.modetravail.repository.ShiftPlanRepository;
import org.hospital.modetravail.requests.*;
import org.hospital.modetravail.service.GestionRessourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin("*")
public class GestionRessourcesController {
    @Autowired
    private GestionRessourcesService gestionRessourcesService;

    @PostMapping("/increment-modetravail")
    public void incrementModeTravail(@RequestBody ModeTravailRequest modeTravailRequest) {
        gestionRessourcesService.incrementModeTravail(modeTravailRequest.getSemaine(),modeTravailRequest.getJour());
    }

    @PostMapping("/periode-Ramadan")
    public  void incrementPeriodeShift(@RequestParam String normalShift1, @RequestParam  String normalShift2,  @RequestParam  String normalShift3, @RequestParam  String ramadanShift1,  @RequestParam  String ramadanShift2, @RequestParam  String ramadanShift3, @RequestParam LocalDate ramadanStartDate,@RequestParam LocalDate ramadanEndDate) {
        gestionRessourcesService.incrementPeriodeShift(normalShift1,normalShift2,normalShift3,ramadanShift1,ramadanShift2,ramadanShift3,ramadanStartDate,ramadanEndDate);
    }
    @PostMapping("/cree-equipe")
    public  void incrementEquipe(@RequestBody EquipeRequest equipeRequest) {
        gestionRessourcesService.incrementEquipe(equipeRequest.getNom(), equipeRequest.getResponsable(), equipeRequest.getEmployeIds());
    }
    @GetMapping("/employes/by-fonction/{fonction}")
    public List<Employe> getEmployesByFonction(@PathVariable("fonction") String fonction) {
        return gestionRessourcesService.getEmployesByFonction(fonction);
    }
    @GetMapping("/fonctions")
    public List<String> getFonctions() {
        return gestionRessourcesService.getFonctions();
    }
    @PostMapping("/cree-shiftPlan")
    public ResponseEntity<String> createShiftPlan(@RequestBody ShiftPlanRequest shiftPlanRequest) {
        System.out.println(shiftPlanRequest);
        try {
            gestionRessourcesService.incrementShiftPlan(
                    shiftPlanRequest.getPeriode(),
                    shiftPlanRequest.getDateDebut(),
                    shiftPlanRequest.getDateFin(),
                    shiftPlanRequest.getModeTravailId(),
                    shiftPlanRequest.getShift(),
                    shiftPlanRequest.getEquipeId()
            );
            return ResponseEntity.ok("Shift plan created successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    @PostMapping("/cree-maintheorique")
    public void incrementMainTheorique(@RequestBody MainTheoriqueRequest mainTheoriqueRequest){
        gestionRessourcesService.incrementMainTheorique(mainTheoriqueRequest.getNom(),mainTheoriqueRequest.getTypeTraficIds(),mainTheoriqueRequest.getTraficIds(),mainTheoriqueRequest.getEquipementFamilleIds(),mainTheoriqueRequest.getEquipementIds(),mainTheoriqueRequest.getAccessoireIds());

    }

    @PostMapping("/cree-normeproductivite")
    public ResponseEntity<NormeProductiviteRequest> incrementNormeProductivite(@RequestBody NormeProductiviteRequest normeProductiviteRequest) {
        Map<String, String> response = new HashMap<>();
        try {
            gestionRessourcesService.incrementNormeProductivite(normeProductiviteRequest.getTraficId(),
                    normeProductiviteRequest.getMainTheoriqueId(), normeProductiviteRequest.getModeId(),
                    normeProductiviteRequest.getNorme(),
                    normeProductiviteRequest.getSens(),
                    normeProductiviteRequest.getSuiviProduit());
            return ResponseEntity.ok(normeProductiviteRequest);
        } catch (EntityNotFoundException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", "An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }





    @GetMapping("/maintheorique/{mainId}")
    public List<Trafic> getTraficByMainId(@PathVariable("mainId") Long id) {
        return gestionRessourcesService.getTraficByMainId(id);
    }
    @GetMapping("/equipes")
    public List<Equipe> getEquipe(){
    return gestionRessourcesService.getEquipe();
    }
    @GetMapping("/maintheorique")
    public List<MainTheorique> getMainTheorique() {
        return gestionRessourcesService.getMainTheorique();
    }
    @GetMapping("/modes")
    public List<Mode> getMode() {
        return  gestionRessourcesService.getMode();
    }
    @GetMapping("/norme_productivite")
    public List<NormeProductivite> getNormeProductivite() {
        return gestionRessourcesService.getNormeProductivite();
    }
    @DeleteMapping("/norme-productivite/{normeProducId}")
    public void deleteNormeProductivite(@PathVariable("normeProducId") Long normeProductiviteId) {
        gestionRessourcesService.deleteNormeProductvite(normeProductiviteId);
    }
    @PutMapping("/norme-productivite/{id}")
    public void updateNormeProductivite(@PathVariable("id") Long id, @RequestBody NormeProductiviteRequest normeProductiviteRequest) {
        normeProductiviteRequest.setId(id);
        gestionRessourcesService.updateNormeProductvite(
                id,
                normeProductiviteRequest.getTraficId(),
                normeProductiviteRequest.getMainTheoriqueId(),
                normeProductiviteRequest.getModeId(),
                normeProductiviteRequest.getNorme(),
                normeProductiviteRequest.getSens(),
                normeProductiviteRequest.getSuiviProduit()
        );
    }
    @GetMapping("/shifts")
    public PeriodeShift getShifts() {
        return gestionRessourcesService.getPeriodeShifts();
    }
    @PatchMapping("/shifts/{id}")
    public void updateRamadanDates(@PathVariable("id") Long id,
                                   @RequestBody PeriodeShift periodeShift)  {
        gestionRessourcesService.updatePeriodeShift(id, periodeShift.getRamadanStartDate(), periodeShift.getRamadanEndDate());
    }
    @GetMapping("/modedetravail")
    public List<ModeTravail> getModeDeTravail() {
        return gestionRessourcesService.getModeTravail();
    }

    @GetMapping("/typetrafic")
    public List<TypeTrafic> getTypeTrafic() {
        return gestionRessourcesService.getTypeTrafic();
    }
    @GetMapping("/typetrafic/trafic/{id}")
    public List<Trafic> getTraficByTypeTrafic(@PathVariable("id") Long id) {
        return gestionRessourcesService.getTraficBYTypetrafic(id);
    }

    @GetMapping("/equipementfamille/equipement/{id}")
    public List<Equipement> getEquipementByEquipementFamille(@PathVariable("id") Long id) {
        return gestionRessourcesService.getEquipementByEquipementFamille(id);
    }

    @GetMapping("/equipementfamille/accessoir/{id}")
    public List<Accessoir> getAccessoirByEquipementFamille(@PathVariable("id") Long id ) {
        return gestionRessourcesService.getAccessoirByEquipementFamille(id);
    }

    @GetMapping("/equipementfamille")
    public List<EquipementFamille> getEquipementFamille()  {
        return gestionRessourcesService.getEquipementFamille();
    }

    @GetMapping("equipes/{nom}")
    public Boolean checkEquipeNom(@PathVariable("nom") String nom) {
        return gestionRessourcesService.checkEquipeNom(nom);
    }

    @PutMapping("/shiftplan/{id}")
    public void updateShiftPlan(@PathVariable("id") Long id,@RequestBody ShiftPlanRequest shiftPlanRequest) {
        System.out.println("id = " + id);
        System.out.println(shiftPlanRequest);
        gestionRessourcesService.updateShiftPlan(id,
                shiftPlanRequest.getPeriode(),
                shiftPlanRequest.getDateDebut(),
                shiftPlanRequest.getDateFin(),
                shiftPlanRequest.getShift(),
                shiftPlanRequest.getEquipeId(),
                shiftPlanRequest.getModeTravailId()
        );
    }
    @GetMapping("/exist/{equipeId}")
    public ResponseEntity<ShiftPlan> checkShiftPlanExists(@PathVariable("equipeId") Long equipeId) {
        ShiftPlan shiftPlan = gestionRessourcesService.checkShiftPlanExists(equipeId);
        if (shiftPlan != null) {
            return ResponseEntity.ok(shiftPlan);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/employes")
    public List<Employe> getEmployes() {
        return  gestionRessourcesService.getEmployes();
    }

    @PostMapping("/cree_employe")
    public  void addEmploye(@RequestBody EmployeRequesrt employeRequesrt) {
        gestionRessourcesService.addEmploye(employeRequesrt.getFonction(),employeRequesrt.getNom());
    }
    @PutMapping("/employes/{id}")
    public void updateEmploye(@PathVariable Long id, @RequestBody EmployeRequesrt employeRequesrt) {
        gestionRessourcesService.updateEmploye(id,employeRequesrt.getFonction(),employeRequesrt.getNom());
    }
    @DeleteMapping("employes/delete/{id}")
    public void deleteEmploye(@PathVariable Long id) {
        gestionRessourcesService.deleteEmploye(id);
    }
}
