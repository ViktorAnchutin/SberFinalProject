package com.vanchutin.deliveryManager.service;

public class ServiceLayerException extends Exception {
    private static final long serialVersionUID = 2711045302731003390L;
    public ServiceLayerException(String msg, Throwable cause){
        super(msg, cause);
    }

    public ServiceLayerException(String msg){
        super(msg);
    }

    public ServiceLayerException(){
        super();
    }
}
