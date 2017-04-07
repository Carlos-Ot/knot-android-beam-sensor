package br.org.cesar.knot.beamsensor.communication;


import org.json.JSONException;

import br.org.cesar.knot.beamsensor.data.networking.callback.AuthenticateRequestCallback;
import br.org.cesar.knot.beamsensor.data.networking.callback.SensorsRequestCallback;
import br.org.cesar.knot.beamsensor.model.BeamSensorFilter;
import br.org.cesar.knot.lib.exception.InvalidParametersException;
import br.org.cesar.knot.lib.exception.KnotException;
import br.org.cesar.knot.lib.exception.SocketNotConnected;

/**
 * Created by carlos on 09/03/17.
 */

public interface BeamCommunication {

    void getSensors(BeamSensorFilter filter, SensorsRequestCallback callback);
    void authenticate(String url, int port, String uuid, String token, AuthenticateRequestCallback callback);
//    void addListener(Subscriber subscriber);
//    void removeListener(Subscriber subscriber);
    boolean close();
}
