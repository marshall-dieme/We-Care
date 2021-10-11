package org.spring.we_care.service;

import org.spring.we_care.excetion.EntityNotFoundException;
import org.spring.we_care.model.Speciality;
import org.spring.we_care.repository.SpecialityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialityService {
    @Autowired
    SpecialityRepository specialityRepository;

    public Speciality getByName(String name) throws EntityNotFoundException {
        return specialityRepository.findByName(name);
    }
}