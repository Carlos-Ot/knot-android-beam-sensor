package br.org.cesar.knot.beamsensor.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import br.org.cesar.knot.beamsensor.R;
import br.org.cesar.knot.beamsensor.controller.BeamController;
import br.org.cesar.knot.beamsensor.data.networking.callback.BeamSensorDataCallback;
import br.org.cesar.knot.beamsensor.model.BeamSensorData;

public class SensorDetailActivity extends AppCompatActivity implements BeamSensorDataCallback {

    private static final String EXTRA_TOKEN = "token";
    private static final String EXTRA_UUID = "uuid";

    public static Intent newIntent(Context context, String token, String uuid) {

        Intent it = new Intent(context, SensorDetailActivity.class);
        it.putExtra(EXTRA_TOKEN, token);
        it.putExtra(EXTRA_UUID, uuid);

        return it;
    }

    private BeamController beamController =  BeamController.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_detail);

        beamController.getData(null, getIntent().getStringExtra(EXTRA_UUID), getIntent().getStringExtra(EXTRA_TOKEN), this);

    }


    @Override
    public void onBeamSensorDataSuccess(List<BeamSensorData> data) {

        if (data != null && !data.isEmpty()) {

        }

    }

    @Override
    public void onBeamSensorDataFailed() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SensorDetailActivity.this, R.string.text_authentication_failed, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
