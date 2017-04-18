package br.org.cesar.knot.beamsensor.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import br.org.cesar.knot.lib.model.AbstractThingDevice;

public class BeamSensor extends AbstractThingDevice {

    private boolean online;
    @SerializedName("_id")
    private String id;
    private String ipAddress;
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("longitude")
    private double longitude;
//    @SerializedName("schema")
    private ArrayList<BeamSensorItem> schema;
    @SerializedName("user")
    private BeamSensorOwner beamSensorOwner;

    public BeamSensor() {
    }

    public boolean isBeamSensorOwner() {
        return beamSensorOwner != null;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public boolean isOnline() {
        return online;
    }

    public void setBeamSensorOwner(BeamSensorOwner beamSensorOwner) {
        this.beamSensorOwner = beamSensorOwner;
    }

    public BeamSensorOwner getBeamSensorOwner() {
        return this.beamSensorOwner;
    }


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

    public ArrayList<BeamSensorItem> getSchema() {
        return schema;
    }

    public void setSchema(ArrayList<BeamSensorItem> schema) {
        this.schema = schema;
    }
}
