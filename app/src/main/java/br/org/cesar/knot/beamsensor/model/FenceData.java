package br.org.cesar.knot.beamsensor.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ragpf on 18/04/17.
 */

public class FenceData {

    @SerializedName("sensor_id")
    private String sensorId;

    @SerializedName("value")
    private boolean value;

    @SerializedName("uuid")
    private String uuid;

    @SerializedName("token")
    private String token;

    private String name;

    private String date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
