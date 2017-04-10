package br.org.cesar.knot.beamsensor.data.networking.callback;

import java.util.List;

import br.org.cesar.knot.beamsensor.model.BeamSensor;

public interface DeviceListRequestCallback {

    void onDeviceListsSuccess(List<BeamSensor> deviceList);
    void onDeviceListFailed();
}
