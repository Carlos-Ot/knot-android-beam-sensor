package br.org.cesar.knot.beamsensor.controller;

import br.org.cesar.knot.beamsensor.communication.WsBeamCommunication;
import br.org.cesar.knot.beamsensor.data.networking.callback.AuthenticateRequestCallback;
import br.org.cesar.knot.beamsensor.data.networking.callback.BeamSensorDataCallback;
import br.org.cesar.knot.beamsensor.data.networking.callback.DeviceListRequestCallback;
import br.org.cesar.knot.beamsensor.model.BeamSensorFilter;
import br.org.cesar.knot.lib.model.KnotQueryData;

public class BeamController {
    private WsBeamCommunication mCommunication;
    private static BeamController sInstance = new BeamController();

    private BeamController() {
        try {
            mCommunication = new WsBeamCommunication();
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
        mCommunication.authenticate(url, port, user, password, callback);
    }

    public void getData(KnotQueryData filter, String ownerUuid,String ownerToken,String uuidTargetDevice, BeamSensorDataCallback callback) {
        mCommunication.getData(filter,ownerUuid,ownerToken,uuidTargetDevice,callback);

    }

}
