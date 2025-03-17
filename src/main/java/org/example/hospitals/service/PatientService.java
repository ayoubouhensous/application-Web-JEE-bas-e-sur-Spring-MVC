package org.example.hospitals.service;

import org.example.hospitals.entities.Patient;
import org.example.hospitals.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


    public Page<Patient> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return patientRepository.findAll(pageable);
    }

    public Page<Patient> getByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return patientRepository.findByNomContains(name, pageable);
    }

}
