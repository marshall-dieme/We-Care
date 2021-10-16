package org.spring.we_care.controller;


import java.util.List;

import org.spring.we_care.excetion.EntityNotFoundException;
import org.spring.we_care.excetion.InternalErrorException;
import org.spring.we_care.model.User;
import org.spring.we_care.service.SpecialityService;
import org.spring.we_care.service.UserService;
import org.spring.we_care.utils.JwtRequest;
import org.spring.we_care.utils.JwtResponse;
import org.spring.we_care.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
public class UserController {
    
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserService userService;

    @Autowired
    SpecialityService specialityService;

    @PostMapping(value="/authenticate")
    public ResponseEntity<JwtResponse> authentication(@RequestBody JwtRequest jwtRequest) {
        authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
        final UserDetails userDetails = userService
				.loadUserByUsername(jwtRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
    }

    @PostMapping(value="/register")
    public ResponseEntity<JwtResponse> register(@RequestBody User user) throws InternalErrorException {
        User u = (User) userService.saveUser(user);
        if (u.getId() != 0) {
            authenticate(u.getUsername(), u.getPassword());
            final UserDetails userDetails = userService
				.loadUserByUsername(u.getUsername());

            final String token = jwtTokenUtil.generateToken(userDetails);
            return new ResponseEntity<>(new JwtResponse(token), HttpStatus.CREATED);
        }
        throw new InternalErrorException("An error occurred while processing the data !!! Try later");
    }
    
    @GetMapping(value="/users/{code}")
    public User getUserByCode(@PathVariable String code) {
        return userService.getByCode(code);
    }

    @GetMapping(value="/users/{speciality}")
    public ResponseEntity<List<User>> getBySpeciality(@PathVariable String speciality) throws EntityNotFoundException {
        List<User> users = userService.getBySpeciality(specialityService.getByName(speciality));
        if (users != null) {
            return new ResponseEntity<>(users, new HttpHeaders(), HttpStatus.OK);
        }
        throw new EntityNotFoundException("Object '" + speciality + "' not found");
    }
    
    @GetMapping(value="/users")
    public ResponseEntity<List<User>> getCoaches() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    
    
    
    
    
    /***
     * user credentials verification
     * @param username
     * @param password
     */
    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new DisabledException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
	}
}