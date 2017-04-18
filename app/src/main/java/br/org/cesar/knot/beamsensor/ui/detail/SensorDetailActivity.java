package br.org.cesar.knot.beamsensor.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import br.org.cesar.knot.beamsensor.R;
import br.org.cesar.knot.beamsensor.controller.BeamController;
import br.org.cesar.knot.beamsensor.data.local.PreferencesManager;
import br.org.cesar.knot.beamsensor.data.networking.callback.BeamSensorDataCallback;
import br.org.cesar.knot.beamsensor.model.BeamSensorData;
import br.org.cesar.knot.beamsensor.ui.detail.adapter.DataAdapter;
import br.org.cesar.knot.beamsensor.util.Constants;
import br.org.cesar.knot.beamsensor.util.Utils;
import br.org.cesar.knot.lib.model.KnotQueryData;
import br.org.cesar.knot.lib.model.KnotQueryDateData;
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

    private String mSearchUuid;
    private KnotQueryData mFilter;
//    private KnotQueryDateData mDateData;

    private PreferencesManager preferencesManager;

    private DataAdapter mAdapter;

    private BeamController beamController = BeamController.getInstance();

    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_detail);
        ButterKnife.bind(this);

        mToolbar.setTitle(R.string.title_data);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        preferencesManager = PreferencesManager.getInstance();

        initRecyclerView();

        mFilter = new KnotQueryData();
        mFilter.setLimit(Constants.FILTER_LIMIT);

        mSearchUuid = getIntent().getStringExtra(EXTRA_UUID);

    }

    private void initRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        mAdapter = new DataAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        isRunning = true;
        loadBeamSensorData();
    }

    @Override
    protected void onPause() {
        super.onPause();

        isRunning = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            finish();
        }
        return true;
    }


    @Override
    public void onBeamSensorDataSuccess(final List<BeamSensorData> data) {

        if (!isFinishing() && isRunning) {
            if (data != null && !data.isEmpty()) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateDataList(data);

                        reloadData();
                    }
                });

            }

        }
    }

    @Override
    public void onBeamSensorDataFailed() {

        if (!isFinishing()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SensorDetailActivity.this, R.string.text_beam_sensor_data_failed, Toast.LENGTH_SHORT).show();

                    reloadData();
                }
            });
        }

    }

    private void updateDataList(List<BeamSensorData> data) {
        mAdapter.updateData(data);
        mAdapter.notifyDataSetChanged();
    }

    private BeamSensorData getLastData() {
        return mAdapter.getLastData();
    }

    private void reloadData() {
        if (!isFinishing()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!isFinishing()) {
//                        updateQueryDate();
                        loadBeamSensorData();
                    }
                }
            }, Constants.POOLING_TIMEOUT);
        }
    }


    private void loadBeamSensorData() {
        if (!isFinishing() && isRunning) {
            beamController.getData(mFilter,
                    preferencesManager.getOwnerUuid(),
                    preferencesManager.getOwnerToken(),
                    mSearchUuid, this);
        }
    }

    private void updateQueryDate() {

        if (mFilter != null) {

//            Calendar calendar = Calendar.getInstance();

//            boolean isFirstTime = (mDateData == null);

            Calendar lastDataCalendar = Utils.loadNextDateFromHistory(getLastData());

            if (lastDataCalendar != null) {
                KnotQueryDateData previous = new KnotQueryDateData(lastDataCalendar.get(Calendar.YEAR),
                        lastDataCalendar.get(Calendar.MONTH),
                        lastDataCalendar.get(Calendar.DAY_OF_MONTH),
                        lastDataCalendar.get(Calendar.HOUR),
                        lastDataCalendar.get(Calendar.MINUTE),
                        lastDataCalendar.get(Calendar.SECOND),
                        lastDataCalendar.get(Calendar.MILLISECOND));

                mFilter.setStartDate(previous);
            }


//            if (!isFirstTime) {
//                mFilter.setStartDate(mDateData);
//            }
//
//            mDateData = new KnotQueryDateData(calendar.get(Calendar.YEAR),
//                    calendar.get(Calendar.MONTH),
//                    calendar.get(Calendar.DAY_OF_MONTH),
//                    calendar.get(Calendar.HOUR),
//                    calendar.get(Calendar.MINUTE),
//                    calendar.get(Calendar.SECOND),
//                    calendar.get(Calendar.MILLISECOND));
//
//            mFilter.setFinishDate(mDateData);
//
//            if (isFirstTime) {
//
//                calendar.add(Calendar.SECOND, (-1 * Constants.POOLING_TIMEOUT));
//
//                KnotQueryDateData previous = new KnotQueryDateData(calendar.get(Calendar.YEAR),
//                        calendar.get(Calendar.MONTH),
//                        calendar.get(Calendar.DAY_OF_MONTH),
//                        calendar.get(Calendar.HOUR),
//                        calendar.get(Calendar.MINUTE),
//                        calendar.get(Calendar.SECOND),
//                        calendar.get(Calendar.MILLISECOND));
//
//                mFilter.setStartDate(previous);
//
//            }

        }
    }
}
