package br.org.cesar.knot.beamsensor.data.networking.callback;

import java.util.List;

import br.org.cesar.knot.beamsensor.model.BeamSensorData;

public interface BeamSensorDataCallback {

    void onBeamSensorDataSuccess(List<BeamSensorData> data);
    void onBeamSensorDataFailed();
}
