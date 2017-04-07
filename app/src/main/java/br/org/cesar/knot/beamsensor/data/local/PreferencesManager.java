package br.org.cesar.knot.beamsensor.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import br.org.cesar.knot.beamsensor.BeanSensorApplication;
import br.org.cesar.knot.beamsensor.util.Constants;

public class PreferencesManager {

    private static PreferencesManager sInstance;

    private SharedPreferences mSharedPreferences;

    private PreferencesManager() {

        Context context = BeanSensorApplication.getContext();

        mSharedPreferences = context.getSharedPreferences(
                Constants.PREFERENCES, Context.MODE_PRIVATE);

    }

    public static PreferencesManager getInstance() {

        if (sInstance == null) {
            sInstance = new PreferencesManager();
        }

        return sInstance;
    }


    public String getUsername() {
        return mSharedPreferences.getString(Constants.KEY_USERNAME, null);
    }

    public void setUsername(String newUsername) {

        mSharedPreferences.edit().putString(Constants.KEY_USERNAME, newUsername).apply();
    }

    public String getCloudIp() {
        return mSharedPreferences.getString(Constants.KEY_CLOUD_IP, null);
    }

    public void setCloudIp(String cloudIp) {

        if (cloudIp != null && !cloudIp.trim().isEmpty()) {
            mSharedPreferences.edit().putString(Constants.KEY_CLOUD_IP, cloudIp).apply();
        }
    }

    public int getCloudPort() {
        return mSharedPreferences.getInt(Constants.KEY_CLOUD_PORT, Constants.INVALID_CLOUD_PORT);
    }

    public void setCloudPort(String cloudPort) {

        if (cloudPort != null && !cloudPort.trim().isEmpty()) {
            mSharedPreferences.edit().putInt(Constants.KEY_CLOUD_PORT, Integer.valueOf(cloudPort.trim())).apply();
        }
    }



}
