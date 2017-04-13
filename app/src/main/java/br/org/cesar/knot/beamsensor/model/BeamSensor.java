package br.org.cesar.knot.beamsensor.model;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.ArrayList;

import br.org.cesar.knot.beamsensor.util.Security;
import br.org.cesar.knot.lib.model.AbstractThingDevice;
/**
 * Created by carlos on 09/03/17.
 */

public class BeamSensor extends AbstractThingDevice{
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

    public BeamSensor(){
        //leftFence = new BeamSensorItem();
        //controller = new BeamSensorItem();
        //rightFence = new BeamSensorItem();
    }

    public boolean isBeamSensorOwner(){
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

        if(controller == null)
            return beamSensorItemArrayList;

        beamSensorItemArrayList.add(leftFence);
        beamSensorItemArrayList.add(controller);
        beamSensorItemArrayList.add(rightFence);

        return beamSensorItemArrayList;
    }

//    @Override
//    public BeamSensor deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//        final JsonObject jDevice = json.getAsJsonObject();
//        final JsonObject jDeviceOwner = jDevice.get("user").getAsJsonObject();
//        final JsonObject jController = jDevice.get("controller").getAsJsonObject();
//        final JsonObject jLeftFence = jDevice.get("leftFence").getAsJsonObject();
//        final JsonObject jRightFence = jDevice.get("rightFence").getAsJsonObject();
//        final JsonElement jOwnerElement = jDevice.get("beamSensorOwner");
//
//        BeamSensor beamSensor = new BeamSensor();
//
//        if(jDeviceOwner != null){
//            BeamSensorOwner beamSensorOwner = new BeamSensorOwner();
//            try {
//                String uuid  = Security.decrypt(jDeviceOwner.get("uuid").getAsString(),"d6F3Efeq");
//                beamSensorOwner.setUuid(uuid);
//                String token = Security.decrypt(jDeviceOwner.get("token").getAsString(),"d6F3Efeq");
//                beamSensorOwner.setUuid(uuid);
//                beamSensorOwner.setToken(token);
//                beamSensorOwner.setEmail(jDeviceOwner.get("email").getAsString());
//                beamSensorOwner.setPassword(jDeviceOwner.get("password").getAsString());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//        else{
//            beamSensor.setOwner(jOwnerElement.getAsString());
//        }
//
//        if(jController != null) {
//            final BeamSensorItem controller = new BeamSensorItem();
//            controller.setLatitude(jController.get("latitude").getAsDouble());
//            controller.setLongitude(jController.get("longitude").getAsDouble());
//            controller.setStatus(jController.get("status").getAsInt());
//            beamSensor.setController(controller);
//            if (jLeftFence != null) {
//                final BeamSensorItem leftFence = new BeamSensorItem();
//                leftFence.setLatitude(jLeftFence.get("latitude").getAsDouble());
//                leftFence.setLongitude(jLeftFence.get("longitude").getAsDouble());
//                leftFence.setStatus(jLeftFence.get("status").getAsInt());
//                beamSensor.setLeftFence(leftFence);
//            }
//            if (jRightFence != null) {
//                final BeamSensorItem rightFence = new BeamSensorItem();
//                rightFence.setLatitude(jRightFence.get("latitude").getAsDouble());
//                rightFence.setLongitude(jRightFence.get("longitude").getAsDouble());
//                rightFence.setStatus(jRightFence.get("status").getAsInt());
//                beamSensor.setRightFence(rightFence);
//            }
//        }
//
//
//        beamSensor.setUuid(jDevice.get("uuid").getAsString());
//        beamSensor.setOnline(jDevice.get("online").getAsBoolean());
//        beamSensor.setId(jDevice.get("_id").getAsString());
//        beamSensor.setIpAddress(jDevice.get("ipAddress").getAsString());
//
//        return beamSensor;
//
//    }
}
