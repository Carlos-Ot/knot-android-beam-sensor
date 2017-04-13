package br.org.cesar.knot.beamsensor.controller;

import br.org.cesar.knot.beamsensor.communication.HttpBeamCommunication;
import br.org.cesar.knot.beamsensor.communication.WsBeamCommunication;
import br.org.cesar.knot.beamsensor.data.networking.callback.AuthenticateRequestCallback;
import br.org.cesar.knot.beamsensor.data.networking.callback.BeamSensorDataCallback;
import br.org.cesar.knot.beamsensor.data.networking.callback.DeviceListRequestCallback;
import br.org.cesar.knot.beamsensor.model.BeamSensorFilter;
import br.org.cesar.knot.lib.model.KnotQueryData;

public class BeamController {
    private WsBeamCommunication mCommunication;
    private HttpBeamCommunication httpBeamCommunication;
    private static BeamController sInstance = new BeamController();
    private static String currentServiceUrl;
    private static int currentServicePort;

    private BeamController() {
        try {
            mCommunication = new WsBeamCommunication();
//            httpBeamCommunication = new HttpBeamCommunication();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized static BeamController getInstance() {

        synchronized (BeamController.class) {

            if (sInstance == null) {
                sInstance = new BeamController();
            }
        }

        return sInstance;
    }

    public void getBeamDevices(BeamSensorFilter filter, DeviceListRequestCallback callback) {
        mCommunication.getDevices(filter, callback);
    }

    public void authenticate(String url, int port, String user, String password, AuthenticateRequestCallback callback) {
        currentServicePort = port;
        currentServiceUrl = url;
        mCommunication.authenticate(url, port, user, password, callback);
    }

//    public boolean authenticate(String ownerUuid, String ownerToken) {
//        httpBeamCommunication.authenticate(currentServiceUrl, currentServicePort, ownerUuid, ownerToken);
//        return httpBeamCommunication.isValidDeviceOwner();
//    }

    public void getData(KnotQueryData filter, String ownerUuid,String ownerToken,String uuidTargetDevice, BeamSensorDataCallback callback) {
        mCommunication.getData(filter,ownerUuid,ownerToken,uuidTargetDevice,callback);

    }

//    public void getData(KnotQueryData filter, String uuid, BeamSensorDataCallback callback) {
//        try {
//            httpBeamCommunication.getData(filter,uuid,callback);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
    /*
     BeamSensorFilter f = new BeamSensorFilter();
        ArrayList range = new ArrayList();
        range.add("10");
        range.add("13");
        range.add("1");
        f.build("value1",10, BeamSensorFilter.FilterCompareValueMode.GreatThan);
        f.build("range",range, BeamSensorFilter.FilterCompareValueMode.In);
        f.build("name","car", BeamSensorFilter.FilterCompareValueMode.Like);
        f.build("age",10, BeamSensorFilter.FilterCompareValueMode.GreatThan, BeamSensorFilter.FilterLinkType.Or,"index",1, BeamSensorFilter.FilterCompareValueMode.Equal);
        f.build("rate",2, BeamSensorFilter.FilterCompareValueMode.LessThan, BeamSensorFilter.FilterLinkType.And,"item",1, BeamSensorFilter.FilterCompareValueMode.Equal);
        try {
            JSONObject query = f.getQuery();
            System.out.println(query);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        */

}
