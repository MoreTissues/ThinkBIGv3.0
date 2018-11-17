package com.thinkbig.thinkbig.GoogleMap;

public class FTSMObject {
    private double lati;
    private double longi;
    private String locname;

    public FTSMObject(double lati, double longi, String locname) {
        this.lati = lati;
        this.longi = longi;
        this.locname = locname;
    }

    public double getLati() {
        return lati;
    }

    public double getLongi() {
        return longi;
    }

    public String getLocname() {
        return locname;
    }
}
