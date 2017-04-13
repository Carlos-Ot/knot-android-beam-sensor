package br.org.cesar.knot.beamsensor.communication;

import org.json.JSONException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import br.org.cesar.knot.beamsensor.data.networking.callback.AuthenticateRequestCallback;
import br.org.cesar.knot.beamsensor.data.networking.callback.BeamSensorDataCallback;
import br.org.cesar.knot.beamsensor.data.networking.callback.DeviceListRequestCallback;
import br.org.cesar.knot.beamsensor.model.BeamSensor;
import br.org.cesar.knot.beamsensor.model.BeamSensorData;
import br.org.cesar.knot.beamsensor.model.BeamSensorFilter;
import br.org.cesar.knot.lib.connection.FacadeConnection;
import br.org.cesar.knot.lib.event.Event;
import br.org.cesar.knot.lib.exception.InvalidParametersException;
import br.org.cesar.knot.lib.exception.KnotException;
import br.org.cesar.knot.lib.exception.SocketNotConnected;
import br.org.cesar.knot.lib.model.KnotList;
import br.org.cesar.knot.lib.model.KnotQueryData;

import java.lang.Boolean;

public class WsBeamCommunication {

    private static FacadeConnection connection;
    private static final String ENDPOINT_SCHEMA = "http";

    public WsBeamCommunication() {
        if (connection == null)
            connection = FacadeConnection.getInstance();

        connection.createSocketIo();
    }

    public void authenticate(String url, int port, String user, String password, final AuthenticateRequestCallback callback) {

        try {
            String endPoint = getEndpoint(url, port);
            connection.setupSocketIO(user, password);
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
        } catch (URISyntaxException | SocketNotConnected e) {
            e.printStackTrace();

            if (callback != null) {
                callback.onAuthenticateFailed();
            }
        }

    }

    public boolean close() {
        connection.disconnectSocket();
        return connection.isSocketConnected();
    }

    private String getEndpoint(String url, int port) throws URISyntaxException {
        URI uri = new URI(ENDPOINT_SCHEMA, null, url, port, null, null, null);
        return uri.toASCIIString();
    }

    private void authenticate(final AuthenticateRequestCallback callback) throws InvalidParametersException, SocketNotConnected {
        connection.socketIOAuthenticateDevice(new Event<Boolean>() {

            @Override
            public void onEventFinish(Boolean object) {

                if (callback != null) {
                    if (object ) {
                        callback.onAuthenticateSuccess();
                    } else {
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
    }

    public void getDevices(BeamSensorFilter filter, final DeviceListRequestCallback callback) {

        try {
            KnotList<BeamSensor> list = new KnotList<>(BeamSensor.class);
            connection.socketIOGetDeviceList(list, filter.getQuery(), new Event<List<BeamSensor>>() {

                @Override
                public void onEventFinish(List<BeamSensor> object) {

                    if (callback != null) {
                        callback.onDeviceListsSuccess(object);
                    }
                }

                @Override
                public void onEventError(Exception e) {

                    if (callback != null) {
                        callback.onDeviceListFailed();
                    }
                }
            });
        } catch (JSONException | KnotException | SocketNotConnected | InvalidParametersException e) {
            e.printStackTrace();

            if (callback != null) {
                callback.onDeviceListFailed();
            }
        }
    }


    public void getData(KnotQueryData filter, String ownerUuid, String ownerToken,String uuidTargetDevice, final BeamSensorDataCallback callback) {
        KnotList<BeamSensorData> list = new KnotList<>(BeamSensorData.class);
        try {
            connection.socketIOGetData(list, ownerUuid,ownerToken,uuidTargetDevice, filter, new Event<List<BeamSensorData>>() {
                @Override
                public void onEventFinish(List<BeamSensorData> object) {
                    if (callback != null) {
                        callback.onBeamSensorDataSuccess(object);
                    }
                }

                @Override
                public void onEventError(Exception e) {
                    if (callback != null) {
                        callback.onBeamSensorDataFailed();
                    }
                }
            });
        } catch (InvalidParametersException | SocketNotConnected e) {
            e.printStackTrace();

            if (callback != null) {
                callback.onBeamSensorDataFailed();
            }
        }
    }

}
