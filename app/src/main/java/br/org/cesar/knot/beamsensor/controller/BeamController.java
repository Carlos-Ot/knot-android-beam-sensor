package br.org.cesar.knot.beamsensor.controller;


import org.json.JSONException;

import br.org.cesar.knot.beamsensor.communication.BeamCommunication;
import br.org.cesar.knot.beamsensor.communication.BeamCommunicationFactory;
import br.org.cesar.knot.beamsensor.data.networking.callback.AuthenticateRequestCallback;
import br.org.cesar.knot.beamsensor.data.networking.callback.SensorsRequestCallback;
import br.org.cesar.knot.beamsensor.model.BeamSensorFilter;
import br.org.cesar.knot.beamsensor.model.Subscriber;
import br.org.cesar.knot.lib.exception.InvalidParametersException;
import br.org.cesar.knot.lib.exception.KnotException;
import br.org.cesar.knot.lib.exception.SocketNotConnected;

/**
 * Created by carlos on 14/03/17.
 */

public class BeamController {
    private BeamCommunication mCommunication;
    private static BeamController sInstance = new BeamController();


    private BeamController() {
        try {
            mCommunication = BeamCommunicationFactory.getBeamCommunication(BeamCommunicationFactory.BeamCommunicationProtocol.WSS);
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
//
//    public void subscribe(Subscriber subscriber) {
//        mCommunication.addListener(subscriber);
//    }
//
//    public void unSubscribe(Subscriber subscriber) {
//        mCommunication.removeListener(subscriber);
//    }

    public void getSensor(BeamSensorFilter filter, SensorsRequestCallback callback) {
        mCommunication.getSensors(filter, callback);
    }

    public void authenticate(String url, int port, String user, String password, AuthenticateRequestCallback callback) {
        mCommunication.authenticate(url, port, user, password, callback);
    }

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
