package com.identifisher.sfwreng3a04.identifisher;

/**
 * Created by Christopher on 2016-04-04.
 */
public abstract class Expert {

    /**
     * Every Expert must implement this method
     * @param data - The information given to the Expert
     * @return An array of Fish names that the data corresponds to
     */
    public  abstract String[] getFish(String data);
}
