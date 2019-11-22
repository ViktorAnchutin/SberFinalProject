package com.vanchutin.deliveryManager.service;

public class NoFreeDronesException extends Exception {
    private static final long serialVersionUID = -4081123770075287336L;
    public NoFreeDronesException(String msg, Throwable cause){
        super(msg, cause);
    }

    public NoFreeDronesException(String msg){
        super(msg);
    }

    public NoFreeDronesException(){
        super();
    }
}
