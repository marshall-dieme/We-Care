package org.spring.we_care.repository;

import org.spring.we_care.model.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Integer> {
    public Speciality findByName(String name);
}