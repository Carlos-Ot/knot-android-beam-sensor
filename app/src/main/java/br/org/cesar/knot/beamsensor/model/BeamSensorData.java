package br.org.cesar.knot.beamsensor.model;

import com.google.gson.annotations.SerializedName;
import br.org.cesar.knot.lib.model.AbstractThingData;

public class BeamSensorData extends AbstractThingData {

    @SerializedName("uuid")
    private String uuid;

    @SerializedName("source")
    private String source;

    @SerializedName("data")
    private FenceData data;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public FenceData getData() {
        return data;
    }

    public void setData(FenceData data) {
        this.data = data;
    }

}
