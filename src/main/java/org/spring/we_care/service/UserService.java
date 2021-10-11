package org.spring.we_care.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityNotFoundException;

import org.spring.we_care.excetion.InternalErrorException;
import org.spring.we_care.model.Speciality;
import org.spring.we_care.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        org.spring.we_care.model.User user = userRepository.findByUsername(username);
        if (user != null) {
            return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
        }
        throw new UsernameNotFoundException("User '" + username + "' not found");
    }

    public UserDetails saveUser(org.spring.we_care.model.User user) throws InternalErrorException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCode(generateRandomCode());

        return userRepository.saveAndFlush(user);
    }    

    public List<org.spring.we_care.model.User> getBySpeciality(Speciality speciality) throws EntityNotFoundException {
        return userRepository.findBySpeciality(speciality);
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

    public org.spring.we_care.model.User getByCode(String code) throws EntityNotFoundException {
        return userRepository.findByCode(code);
    }

    public List<org.spring.we_care.model.User> getUsers() {
        return userRepository.findAll();
    }
}