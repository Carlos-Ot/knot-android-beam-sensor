package br.org.cesar.knot.beamsensor.communication;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import br.org.cesar.knot.beamsensor.data.networking.callback.AuthenticateRequestCallback;
import br.org.cesar.knot.beamsensor.data.networking.callback.SensorsRequestCallback;
import br.org.cesar.knot.beamsensor.model.BeamSensor;
import br.org.cesar.knot.beamsensor.model.BeamSensorFilter;
import br.org.cesar.knot.lib.connection.FacadeConnection;
import br.org.cesar.knot.lib.event.Event;
import br.org.cesar.knot.lib.exception.InvalidParametersException;
import br.org.cesar.knot.lib.exception.KnotException;
import br.org.cesar.knot.lib.exception.SocketNotConnected;
import br.org.cesar.knot.lib.model.KnotList;

import java.lang.Boolean;

public class WsBeamCommunication implements BeamCommunication {

    private static FacadeConnection connection;
    private static final String ENDPOINT_SCHEMA = "http";

    public WsBeamCommunication() {
        if (connection == null)
            connection = FacadeConnection.getInstance();

        connection.createSocketIo();
    }

    public void authenticate(String url, int port, String user, String password, final AuthenticateRequestCallback callback) {
        String endPoint = null;
        try {
            getEndpoint(url, port);
        }catch (URISyntaxException e) {

            e.printStackTrace();

            if (callback != null) {
                callback.onAuthenticateFailed();
            }
            return;

        }

        connection.setupSocketIO(user, password);
        try {
            connection.connectSocket(endPoint, new Event<Boolean>() {
                @Override
                public void onEventFinish(Boolean object) {
                    try {
                        authenticate(callback);
                    } catch (InvalidParametersException | SocketNotConnected e) {
                        e.printStackTrace();

                        if (callback != null) {
                            callback.onAuthenticateFailed();
                        }
                    }
                }

                @Override
                public void onEventError(Exception e) {

                    if (callback != null) {
                        callback.onAuthenticateFailed();
                    }
                }
            });
        } catch (SocketNotConnected socketNotConnected) {
            socketNotConnected.printStackTrace();

            if (callback != null) {
                callback.onAuthenticateFailed();
            }
        }

    }

    @Override
    public boolean close() {
        connection.disconnectSocket();
        return connection.isSocketConnected();
    }


    public void getSensors(BeamSensorFilter filter, final SensorsRequestCallback callback) {
        KnotList<BeamSensor> list = new KnotList<>(BeamSensor.class);

        JSONObject jsonObject = null;
        try {
            jsonObject = filter.getQuery();
        } catch (JSONException e) {
            if (callback != null) {
                callback.onSensorsFailed();
            }
            return;
        }

        try {
            connection.socketIOGetDeviceList(list, jsonObject, new Event<List<BeamSensor>>() {
                @Override
                public void onEventFinish(List<BeamSensor> object) {

                    if (callback != null) {
                        callback.onSensorsSuccess(object);
                    }
                }

                @Override
                public void onEventError(Exception e) {

                    if (callback != null) {
                        callback.onSensorsFailed();
                    }
                }
            });
        } catch (KnotException | SocketNotConnected | InvalidParametersException e) {
            e.printStackTrace();

            if (callback != null) {
                callback.onSensorsFailed();
            }
            return;
        }
    }

    private String getEndpoint(String url, int port) throws URISyntaxException {
        URI uri = new URI(ENDPOINT_SCHEMA, null, url, port, null, null, null);
        return uri.toASCIIString();
    }

    private void authenticate(final AuthenticateRequestCallback callback) throws InvalidParametersException, SocketNotConnected {
        connection.socketIOAuthenticateDevice(new Event<Boolean>() {

            @Override
            public void onEventFinish(Boolean object) {

                if (object && callback != null) {
                    callback.onAuthenticateSuccess();
                } else {
                    callback.onAuthenticateFailed();
                }
            }

            @Override
            public void onEventError(Exception e) {

                if (callback != null) {
                    callback.onAuthenticateFailed();
                }
            }
        });
    }

}
