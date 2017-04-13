package br.org.cesar.knot.beamsensor.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import br.org.cesar.knot.beamsensor.R;
import br.org.cesar.knot.beamsensor.controller.BeamController;
import br.org.cesar.knot.beamsensor.data.networking.callback.BeamSensorDataCallback;
import br.org.cesar.knot.beamsensor.model.BeamSensorData;
import br.org.cesar.knot.beamsensor.ui.detail.adapter.DataAdapter;
import br.org.cesar.knot.lib.model.KnotQueryData;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SensorDetailActivity extends AppCompatActivity implements BeamSensorDataCallback {

    private static final String EXTRA_UUID = "uuid";

    public static Intent newIntent(Context context, String uuid) {

        Intent it = new Intent(context, SensorDetailActivity.class);
        it.putExtra(EXTRA_UUID, uuid);
        return it;
    }

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private DataAdapter mAdapter;

    private BeamController beamController =  BeamController.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_detail);
        ButterKnife.bind(this);

        mToolbar.setTitle(R.string.title_device);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initRecyclerView();

        mAdapter = new DataAdapter();

        KnotQueryData filter = new KnotQueryData();
        filter.setLimit(20);

        beamController.getData(filter, "aad047a0-773a-42ef-adbc-1dc031660000","6fe3d80c6e6ebab97919798078ade9deed18b23e","*", this);


    }

    private void initRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(mAdapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }



    @Override
    public void onBeamSensorDataSuccess(List<BeamSensorData> data) {

        if (data != null && !data.isEmpty()) {
            updateDataList(data);
        }
    }

    @Override
    public void onBeamSensorDataFailed() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SensorDetailActivity.this, R.string.text_beam_sensor_data_failed, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateDataList(List<BeamSensorData> data) {
        mAdapter.updateData(data);
    }
}
