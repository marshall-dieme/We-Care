package org.spring.we_care.repository;

import java.util.List;

import org.spring.we_care.model.Appointment;
import org.spring.we_care.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    public Appointment findByCode(String code);

    @Query("SELECT a FROM Appointment a WHERE a.user=:user")
    public List<Appointment> findByUser(@Param("user") User user);

    public List<Appointment> findByCoach(@Param("user") User user);
}