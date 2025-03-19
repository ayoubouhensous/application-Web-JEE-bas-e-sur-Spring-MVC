package org.example.hospitals.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.hospitals.entities.Patient;
import org.example.hospitals.repository.PatientRepository;
import org.example.hospitals.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PatientCOntroller {

    @Autowired
    private PatientService patientService;
    @GetMapping("/")
    public String home() {
        return "redirect:/index"; // Redirection vers /index pour afficher les patients
    }

    @GetMapping("index")
    public String index(Model model, @RequestParam(name = "page" ,defaultValue = "0") int page,
                        @RequestParam(name = "size",defaultValue = "4") int size,
                        @RequestParam(name = "keyword" ,defaultValue = "") String kw){
        Page<Patient> patientsList = patientService.getByName(kw,page, size);
        model.addAttribute("patientsList", patientsList.getContent());
        model.addAttribute("pages", new int[patientsList.getTotalPages()]);
        model.addAttribute("pagecount",page);
        model.addAttribute("keyword",kw);
        return "patients";
    }

    @GetMapping("delet")
    public String delete(@RequestParam(name = "id") Long id,String keyword,int page){
        patientService.delete(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;

    }
    @GetMapping("/addPatient")
    public String showAddPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "addPatient";
    }
    @PostMapping("/savePatient")
    public String savePatient(@Valid  Patient patient,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addPatient";
        }
        patientService.save(patient);
        return "redirect:/index";
    }
    @GetMapping("/editPatient/{id}")
    public String showEditPatientForm(@PathVariable Long id, Model model) {
        Patient patient = patientService.getById(id);
        if (patient == null) {
            return "redirect:/index";
        }
        model.addAttribute("patient", patient);
        return "editPatient";
    }

    @PostMapping("/updatePatient")
    public String updatePatient( @Valid Patient patient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editPatient";
        }
        patientService.save(patient);
        return "redirect:/index";
    }
}
