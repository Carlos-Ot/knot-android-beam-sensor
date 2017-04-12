package br.org.cesar.knot.beamsensor.model;



import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import br.org.cesar.knot.lib.model.AbstractThingDevice;
/**
 * Created by carlos on 09/03/17.
 */

public class BeamSensor extends AbstractThingDevice {
    private boolean online;
    private String id;
    private String ipAddress;
    private BeamSensorItem leftFence;
    private BeamSensorItem controller;
    private BeamSensorItem rightFence;

    public BeamSensor(){
        leftFence = new BeamSensorItem();
        controller = new BeamSensorItem();
        rightFence = new BeamSensorItem();
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

//    public ArrayList<LatLng> getLatLngFromSensors() {
//        ArrayList<LatLng> latLngList = new ArrayList<>();
//        latLngList.add(leftFence.getLatLng());
//        latLngList.add(controller.getLatLng());
//        latLngList.add(rightFence.getLatLng());
//        return latLngList;
//    }

    public ArrayList<BeamSensorItem> getBeamSensorItens() {

        ArrayList<BeamSensorItem> beamSensorItemArrayList = new ArrayList<>();

        if(controller == null)
            return beamSensorItemArrayList;

        beamSensorItemArrayList.add(leftFence);
        beamSensorItemArrayList.add(controller);
        beamSensorItemArrayList.add(rightFence);

        return beamSensorItemArrayList;
    }

}
