package br.org.cesar.knot.beamsensor.data.networking.callback;

import java.util.List;

import br.org.cesar.knot.beamsensor.model.BeamSensor;

/**
 * Created by ragpf on 07/04/17.
 */

public interface SensorsRequestCallback {

    void onSensorsSuccess(List<BeamSensor> sensors);
    void onSensorsFailed();
}
