package org.spring.we_care.excetion;

public class EntityNotFoundException extends Exception {
    

    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String error) {
        super(error);
    }
    
}