package br.org.cesar.knot.beamsensor.model;

import com.google.gson.annotations.SerializedName;
import br.org.cesar.knot.lib.model.AbstractThingData;

public class BeamSensorData extends AbstractThingData {

    @SerializedName("sensor_id")
    private String id;

    private String value;

    private int status;

    private String uuid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
