package org.spring.we_care.controller;

import java.util.List;

import org.spring.we_care.excetion.EntityNotFoundException;
import org.spring.we_care.excetion.InternalErrorException;
import org.spring.we_care.model.Appointment;
import org.spring.we_care.model.User;
import org.spring.we_care.service.AppointmentService;
import org.spring.we_care.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping(value = "/appointments")
public class AppointmentController {

    @Autowired
    AppointmentService aService;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<Appointment>> getAppointments() throws EntityNotFoundException {
        User user = userService.getByUsername(loggedUser());
        try {
            return new ResponseEntity<>(aService.getAppointments(user), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Object '" + user + "' not found");
        }
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<Appointment>> getSchedules() throws EntityNotFoundException {
        User user = userService.getByUsername(loggedUser());
        try {
            return new ResponseEntity<>(aService.getSchedules(user), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Object '" + user + "' not found");
        }
    }

    @GetMapping(value="/{code}")
    public ResponseEntity<Appointment> appointmentDetails(@PathVariable String code) throws EntityNotFoundException {
        try {
            return new ResponseEntity<>(aService.getByCodeAppointment(code), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Object '" + code + "' not found");
        }
    }
    
    @PostMapping
    public ResponseEntity<Appointment> addAppointment(@RequestBody Appointment appointment) throws InternalErrorException {
        try {
            return new ResponseEntity<>(aService.saveAppointment(appointment), HttpStatus.OK);
        } catch (InternalErrorException e) {
            throw new InternalErrorException("An error occurred while processing the data !!! Try later");
        }
    }
    
    @DeleteMapping(value = "/{code}")
    public ResponseEntity<String> deleteAppointment(@PathVariable String code) throws EntityNotFoundException {
        try {
            aService.deleteAppointment(code);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Object '" + code + "' not found");
        }
    }
    
    @PutMapping(value="edit/{code}")
    public ResponseEntity<Appointment> putMethodName(@PathVariable String code, @RequestBody Appointment appointment) throws InternalErrorException {
        try {
            return new ResponseEntity<>(aService.editAppointment(code, appointment), HttpStatus.OK);
        } catch (InternalErrorException e) {
            throw new InternalErrorException("An error occurred while processing the data !!! Try later");
        }
    }

    @PutMapping(value="/{code}")
    public ResponseEntity<Appointment> cancelAppointment(@PathVariable String code) throws InternalErrorException {
        try {
            return new ResponseEntity<>(aService.cancelAppointment(code), HttpStatus.OK);
        } catch (InternalErrorException e) {
            throw new InternalErrorException("An error occurred while processing the data !!! Try later");
        }
    }

    private String loggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails)principal). getUsername();
        } else {
            return "";
        }
    }
}