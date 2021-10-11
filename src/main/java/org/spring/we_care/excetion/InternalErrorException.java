package org.spring.we_care.excetion;

public class InternalErrorException extends Exception {
    
    public InternalErrorException(){
        super();
    }
    
    public InternalErrorException(String error){
        super(error);
    }
}