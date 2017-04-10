package br.org.cesar.knot.beamsensor.communication;

public class BeamCommunicationFactory {

    public enum BeamCommunicationProtocol {
        HTTP, HTTPS, WS, WSS
    }

    public static BeamCommunication getBeamCommunication(BeamCommunicationProtocol protocol) throws Exception {
        BeamCommunication beamCommunication = null;
        switch (protocol) {
            case HTTP:
            case HTTPS:
            case WS:
                throw new Exception("Protocol type not defined");
            case WSS:
                beamCommunication = new WsBeamCommunication();
                break;
        }
        return beamCommunication;
    }
}
