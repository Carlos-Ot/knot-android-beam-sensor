package br.org.cesar.knot.beamsensor;

import android.app.Application;
import android.content.Context;

/**
 * Created by ragpf on 06/04/17.
 */

public class BeanSensorApplication extends Application {

    /**
     * You should avoid doing this. Instead, use dependency injection.
     * We did it only to keep code simple. :)
     */
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = this;
    }


    public static Context getContext() {
        return sContext;
    }
}
