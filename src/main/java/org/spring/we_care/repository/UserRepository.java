package org.spring.we_care.repository;

import java.util.List;

import org.spring.we_care.model.Speciality;
import org.spring.we_care.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByUsername(String username);

    public User findByCode(String code);

    public List<User> findBySpeciality(Speciality speciality);
}