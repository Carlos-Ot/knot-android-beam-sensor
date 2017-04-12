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
    private BeamSensorItem leftEmitter;
    private BeamSensorItem centerReceiver;
    private BeamSensorItem rightEmitter;

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

    public BeamSensorItem getCenterReceiver() {
        return centerReceiver;
    }

    public void setCenterReceiver(BeamSensorItem centerReceiver) {
        this.centerReceiver = centerReceiver;
    }

    public BeamSensorItem getRightEmitter() {
        return rightEmitter;
    }

    public void setRightEmitter(BeamSensorItem rightEmitter) {
        this.rightEmitter = rightEmitter;
    }

    public BeamSensorItem getLeftEmitter() {
        return leftEmitter;
    }

    public void setLeftEmitter(BeamSensorItem leftEmitter) {
        this.leftEmitter = leftEmitter;
    }

    public ArrayList<LatLng> getLatLngFromSensors() {
        ArrayList<LatLng> latLngList = new ArrayList<>();
        latLngList.add(leftEmitter.getLatLng());
        latLngList.add(centerReceiver.getLatLng());
        latLngList.add(rightEmitter.getLatLng());
        return latLngList;
    }

    public ArrayList<BeamSensorItem> getBeamSensorItens() {

        ArrayList<BeamSensorItem> beamSensorItemArrayList = new ArrayList<>();
        beamSensorItemArrayList.add(leftEmitter);
        beamSensorItemArrayList.add(centerReceiver);
        beamSensorItemArrayList.add(rightEmitter);
        return beamSensorItemArrayList;
    }

}
