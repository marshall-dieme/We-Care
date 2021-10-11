package org.spring.we_care.utils;

import java.io.Serializable;

public class JwtResponse implements Serializable {
    private final String jwttoken;


    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getJwttoken() {
        return this.jwttoken;
    }

}