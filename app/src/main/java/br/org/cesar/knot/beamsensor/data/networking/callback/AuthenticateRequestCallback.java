package br.org.cesar.knot.beamsensor.data.networking.callback;

/**
 * Created by ragpf on 07/04/17.
 */

public interface AuthenticateRequestCallback {

    void onAuthenticateSuccess();

    void onAuthenticateFailed();
}
