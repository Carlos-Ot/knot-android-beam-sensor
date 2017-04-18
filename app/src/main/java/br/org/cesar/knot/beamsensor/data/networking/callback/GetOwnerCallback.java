package br.org.cesar.knot.beamsensor.data.networking.callback;

import java.util.List;

import br.org.cesar.knot.beamsensor.model.BeamSensor;

public interface GetOwnerCallback {

    void onGetOwnerSuccess(List<BeamSensor> ownerList);
    void onGetOwnerFailed();
}
