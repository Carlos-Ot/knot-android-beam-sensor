package br.org.cesar.knot.beamsensor.model;

/**
 * Created by carlos on 29/03/17.
 */

public class BeamSensorItem {
    private double latitude;
    private double longitude;
    private int status;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}