package br.org.cesar.knot.beamsensor.communication;

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
import br.org.cesar.knot.lib.model.KnotList;
import br.org.cesar.knot.lib.model.KnotQueryData;

/**
 * Created by carlos on 12/04/17.
 */

public class HttpBeamCommunication  {

    private static FacadeConnection connection;
    private static final String ENDPOINT_SCHEMA = "http";

    public HttpBeamCommunication() {
        if (connection == null)
            connection = new FacadeConnection();
    }

    public void getData(KnotQueryData filter, String uuid, final BeamSensorDataCallback callback) {
        KnotList<BeamSensorData> list = new KnotList<>(BeamSensorData.class);
        connection.httpGetDataList(uuid, filter, list, new Event<List<BeamSensorData>>() {
            @Override
            public void onEventFinish(List<BeamSensorData> object) {
                if(callback != null){
                    callback.onBeamSensorDataSuccess(object);
                }
            }

            @Override
            public void onEventError(Exception e) {
                if(callback != null){
                    callback.onBeamSensorDataFailed();
                }
            }
        });
    }

    public boolean isValidDeviceOwner(){
        return connection.isValidDeviceOwner();
    }


    public void authenticate(String url, int port, String uuid, String token) {
        try {

            String endPoint  = getEndpoint(url,port);
            connection.setupHttp(endPoint,uuid,token);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    private String getEndpoint(String url, int port) throws URISyntaxException {
        URI uri = new URI(ENDPOINT_SCHEMA, null, url, port, null, null, null);
        return uri.toASCIIString();
    }
}
