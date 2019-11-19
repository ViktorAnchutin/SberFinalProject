package com.vanchutin.deliveryManager.dao;

public class DaoLayerException extends Exception {

    private static final long serialVersionUID = 3837593691313210175L;

    public DaoLayerException(String msg, Throwable cause){
        super(msg, cause);
    }

    public DaoLayerException(String msg){
        super(msg);
    }

    public DaoLayerException(){
        super();
    }
}
