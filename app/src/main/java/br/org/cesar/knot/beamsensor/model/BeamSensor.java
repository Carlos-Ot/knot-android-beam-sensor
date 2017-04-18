package br.org.cesar.knot.beamsensor.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import br.org.cesar.knot.lib.model.AbstractThingDevice;

public class BeamSensor extends AbstractThingDevice {
    private boolean online;
    @SerializedName("_id")
    private String id;
    private String ipAddress;
    @SerializedName("leftFence")
    private BeamSensorItem leftFence;
    @SerializedName("controller")
    private BeamSensorItem controller;
    @SerializedName("rightFence")
    private BeamSensorItem rightFence;
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

    public BeamSensorItem getController() {
        return controller;
    }

    public void setController(BeamSensorItem controller) {
        this.controller = controller;
    }

    public BeamSensorItem getRightFence() {
        return rightFence;
    }

    public void setRightFence(BeamSensorItem rightFence) {
        this.rightFence = rightFence;
    }

    public BeamSensorItem getLeftFence() {
        return leftFence;
    }

    public void setLeftFence(BeamSensorItem leftFence) {
        this.leftFence = leftFence;
    }

    public ArrayList<BeamSensorItem> getBeamSensorItens() {

        ArrayList<BeamSensorItem> beamSensorItemArrayList = new ArrayList<>();

        if (controller == null) return beamSensorItemArrayList;

        if (leftFence != null) {
            beamSensorItemArrayList.add(leftFence);
        }

        if (rightFence != null) {
            beamSensorItemArrayList.add(rightFence);
        }

        return beamSensorItemArrayList;
    }

    public void setBeamSensorOwner(BeamSensorOwner beamSensorOwner) {
        this.beamSensorOwner = beamSensorOwner;
    }

    public BeamSensorOwner getBeamSensorOwner() {
        return this.beamSensorOwner;
    }

}
