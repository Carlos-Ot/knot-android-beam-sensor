package br.org.cesar.knot.beamsensor.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.org.cesar.knot.lib.model.AbstractThingData;

public class BeamSensorData extends AbstractThingData {

    @SerializedName("data")
    private List<JsonObject> items;

    public BeamSensorData(List<JsonObject> items) {
        this.items = items;
    }

    public List<JsonObject> getItems() {
        return items;
    }
}
