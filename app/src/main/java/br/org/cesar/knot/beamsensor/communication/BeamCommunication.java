package br.org.cesar.knot.beamsensor.communication;

import br.org.cesar.knot.beamsensor.data.networking.callback.AuthenticateRequestCallback;
import br.org.cesar.knot.beamsensor.data.networking.callback.BeamSensorDataCallback;
import br.org.cesar.knot.beamsensor.data.networking.callback.DeviceListRequestCallback;
import br.org.cesar.knot.beamsensor.model.BeamSensorFilter;
import br.org.cesar.knot.lib.model.KnotQueryData;


public interface BeamCommunication {

    void getData(KnotQueryData filter, String uuid, String token, BeamSensorDataCallback callback);
    void getDevices(BeamSensorFilter filter, DeviceListRequestCallback callback);
    void authenticate(String url, int port, String uuid, String token, AuthenticateRequestCallback callback);
    boolean close();
}
