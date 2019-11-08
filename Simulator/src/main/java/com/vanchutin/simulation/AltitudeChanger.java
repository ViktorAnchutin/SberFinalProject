package com.vanchutin.simulation;

import com.vanchutin.jmavlib.LatLonAlt;

public abstract class AltitudeChanger {

    protected LatLonAlt changeAlt(int val, LatLonAlt currentPosition){
        double[] changeVector = new double[]{0,0,val};
        return currentPosition.add(changeVector);
    }
}
