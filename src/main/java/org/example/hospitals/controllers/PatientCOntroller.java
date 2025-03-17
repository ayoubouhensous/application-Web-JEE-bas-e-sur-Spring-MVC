package org.example.hospitals.controllers;

import lombok.AllArgsConstructor;
import org.example.hospitals.entities.Patient;
import org.example.hospitals.repository.PatientRepository;
import org.example.hospitals.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PatientCOntroller {

    @Autowired
    private PatientService patientService;

    @GetMapping("index")
    public String index(Model model, @RequestParam(name = "page" ,defaultValue = "0") int page,@RequestParam(name = "size",defaultValue = "4") int size){
        Page<Patient> patientsList = patientService.getAll(page, size);
        model.addAttribute("patientsList", patientsList.getContent());
        model.addAttribute("pages", new int[patientsList.getTotalPages()]);
        return "patients";
    }
}
