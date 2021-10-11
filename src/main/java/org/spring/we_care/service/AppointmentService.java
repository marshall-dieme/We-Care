package org.spring.we_care.service;

import java.util.List;
import java.util.Random;

import org.spring.we_care.excetion.EntityNotFoundException;
import org.spring.we_care.excetion.InternalErrorException;
import org.spring.we_care.model.Appointment;
import org.spring.we_care.model.User;
import org.spring.we_care.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    public Appointment getByCodeAppointment(String code) throws EntityNotFoundException {
        return appointmentRepository.findByCode(code);
    }

    public List<Appointment> getAppointments(User user) throws EntityNotFoundException {
        return appointmentRepository.findByUserOrCoach(user);
    }

    public Appointment saveAppointment(Appointment a) throws InternalErrorException {
        a.setCode(generateRandomCode());
        return appointmentRepository.saveAndFlush(a);
    }

    public Appointment editAppointment(String code, Appointment a) throws InternalErrorException {
        Appointment appointment = appointmentRepository.findByCode(code);
        if (appointment.getId() != 0) {
            appointment = a;
            return appointmentRepository.saveAndFlush(appointment);
        }
        return null;
    }

    public Appointment cancelAppointment(String code) throws InternalErrorException {
        Appointment appointment = appointmentRepository.findByCode(code);
        if (appointment.getId() != 0) {
            appointment.setConfirmed(false);
            return appointmentRepository.saveAndFlush(appointment);
        }
        return null;
    }

    public void deleteAppointment(String code) throws EntityNotFoundException {
        appointmentRepository.delete(getByCodeAppointment(code));
    }

    private String generateRandomCode() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
            .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }
}