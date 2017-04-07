package br.org.cesar.knot.beamsensor.model;

import java.util.List;

/**
 * Created by carlos on 05/04/17.
 */

public interface Subscriber {

    void notifyError(Exception error);

//    Exception getError();

    void ready();

    void notReady();

    void notifyData(List<BeamSensor> data);

//    List<BeamSensor> getData();
}
