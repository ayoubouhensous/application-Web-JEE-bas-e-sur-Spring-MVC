package org.example.hospitals;

import org.example.hospitals.entities.Patient;
import org.example.hospitals.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Date;

@SpringBootApplication
public class HospitalsApplication implements CommandLineRunner {

    @Autowired
    PatientRepository patientRepository;

    public static void main(String[] args) {
        SpringApplication.run(HospitalsApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        patientRepository.save(new Patient(null, "John", new Date(),true,25));
        patientRepository.save(new Patient(null, "John", java.sql.Date.valueOf(LocalDate.of(2024, 12, 11)), true, 25));


    }

}
