package org.example.hospitals.service;

import org.example.hospitals.entities.Patient;
import org.example.hospitals.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public  void save(Patient patient){
        patientRepository.save(patient);
    }
    public Patient getById(Long id){
        return patientRepository.findById(id).orElse(null);
    }

    public List<Patient> getAll(){
        return patientRepository.findAll();
    }
}
