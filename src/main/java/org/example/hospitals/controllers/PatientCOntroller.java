package org.example.hospitals.controllers;

import lombok.AllArgsConstructor;
import org.example.hospitals.entities.Patient;
import org.example.hospitals.repository.PatientRepository;
import org.example.hospitals.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PatientCOntroller {

    @Autowired
    private PatientService patientService;

    @GetMapping("index")
    public String index(Model model){
        List<Patient> patientsList = patientService.getAll();
        model.addAttribute("patientsList", patientsList);
        return "patients";
    }
}
