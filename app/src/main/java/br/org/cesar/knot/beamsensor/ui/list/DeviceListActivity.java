/*
 * Copyright (c) 2017, CESAR.
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD license. See the LICENSE file for details.
 *
 *
 */
package br.org.cesar.knot.beamsensor.ui.list;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import br.org.cesar.knot.beamsensor.R;
import br.org.cesar.knot.beamsensor.controller.BeamController;
import br.org.cesar.knot.beamsensor.data.local.PreferencesManager;
import br.org.cesar.knot.beamsensor.data.networking.callback.BeamSensorDataCallback;
import br.org.cesar.knot.beamsensor.data.networking.callback.DeviceListRequestCallback;
import br.org.cesar.knot.beamsensor.data.networking.callback.GetOwnerCallback;
import br.org.cesar.knot.beamsensor.model.BeamSensor;
import br.org.cesar.knot.beamsensor.model.BeamSensorData;
import br.org.cesar.knot.beamsensor.model.BeamSensorFilter;
import br.org.cesar.knot.beamsensor.model.BeamSensorItem;
import br.org.cesar.knot.beamsensor.model.BeamSensorOwner;
import br.org.cesar.knot.beamsensor.ui.list.fragment.ListFragment;
import br.org.cesar.knot.beamsensor.ui.list.fragment.MapFragment;
import br.org.cesar.knot.beamsensor.util.Constants;
import br.org.cesar.knot.lib.model.KnotQueryData;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceListActivity extends AppCompatActivity implements DeviceListRequestCallback, GetOwnerCallback, BeamSensorDataCallback {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fragmentContainer)
    FrameLayout fragmentContainer;

    private FragmentManager fragmentManager;

    private ListFragment listFragment;
    private MapFragment mapFragment;

    private MenuItem menuItem;

    private boolean isShowingMap = true;

    private PreferencesManager preferencesManager;

    private boolean isRunning = false;

    private BeamSensorFilter mOwnerFilter = new BeamSensorFilter();

    private BeamSensorFilter mDeviceCloudFilter = new BeamSensorFilter();


    private KnotQueryData mDataFilter = new KnotQueryData();


    private boolean hasLoadedOwners = false;


    private List<BeamSensor> beamSensorList;
    private List<BeamSensorData> beamSensorDataList = new ArrayList<>();

    private int requestDataCounter = 0;
    private boolean shouldFinish = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        ButterKnife.bind(this);

        mToolbar.setTitle(R.string.title_device);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferencesManager = PreferencesManager.getInstance();

        fragmentManager = getSupportFragmentManager();

        mapFragment = MapFragment.newInstance();
        listFragment = ListFragment.newInstance();

        mOwnerFilter.add("user.email", preferencesManager.getUsername(), BeamSensorFilter.FilterCompareValueMode.Equal);

        mDataFilter.setLimit(Constants.FILTER_LIMIT);


        JSONArray jsonArray = new JSONArray();
        jsonArray.put("*");
        mDeviceCloudFilter.add("gateways", jsonArray, BeamSensorFilter.FilterCompareValueMode.Equal);

        //first should request owner and save his token and uuid
        loadOwners();
        isRunning = true;

        updateFragmentState();
    }

    @Override
    protected void onResume() {
        super.onResume();

        isRunning = true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        isRunning = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_devices, menu);
        menuItem = menu.findItem(R.id.list_map_menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            if (isShowingMap) {
                if (!shouldFinish) {
                    Toast.makeText(this, R.string.activity_device_list_click_to_finish, Toast.LENGTH_LONG).show();
                    shouldFinish = true;
                } else {
                    finish();
                }
            } else {
                this.isShowingMap = true;
                updateFragmentState();
            }


        } else {
            this.isShowingMap = !this.isShowingMap;
            updateFragmentState();
        }

        return true;
    }

    private void updateFragmentState() {

        shouldFinish = false;

        if (this.isShowingMap) {

            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, mapFragment).commit();
            if (menuItem != null) {
                menuItem.setIcon(R.drawable.ic_list);
            }

        } else {
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, listFragment).commit();
            mapFragment.shouldSetCamera = true;

            if (menuItem != null) {
                menuItem.setIcon(R.drawable.ic_map_sensor);
            }
        }
    }


    @Override
    public void onDeviceListsSuccess(final List<BeamSensor> deviceList) {

        if (!isFinishing() && isRunning) {
            beamSensorList = deviceList;
            //after get device list, get theirs data
            loadBeamSensorData();
        }

    }

    @Override
    public void onDeviceListFailed() {


        if (!isFinishing() && isRunning) {

            reloadDevices();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(DeviceListActivity.this, R.string.text_device_list_failed, Toast.LENGTH_SHORT).show();

                }
            });
        }

    }

    private void updateListAndMap() {
        if (beamSensorList != null && !beamSensorList.isEmpty()) {
            mapFragment.beamSensors = beamSensorList;
            mapFragment.updateDeviceList();

            ArrayList<BeamSensor> newBS = new ArrayList<>();
            for (BeamSensor bs : beamSensorList) {
                if (!bs.isBeamSensorOwner()) {
                    newBS.add(bs);
                }
            }
            listFragment.updateDeviceList(newBS);

        }

    }

    private void reloadDevices() {

        if (!isFinishing()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!isFinishing()) {
                        loadDevices();
                    }
                }
            }, Constants.POOLING_TIMEOUT);
        }
    }


    private void loadOwners() {
        if (!isFinishing()) {
            BeamController.getInstance().getOwnerDevices(mOwnerFilter, this);
        }
    }

    private void loadDevices() {
        if (!isFinishing() && isRunning) {

            if (preferencesManager.getUseCloud()) {

                BeamController.getInstance().getBeamDevices(mDeviceCloudFilter, this);

            } else {

                BeamController.getInstance().getBeamDevices(new BeamSensorFilter(), this);
            }
        }
    }

    @Override
    public void onGetOwnerSuccess(final List<BeamSensor> ownerList) {

        if (!isFinishing() && isRunning) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (ownerList != null && !ownerList.isEmpty()) {

                        // There can be only one <o>
                        for (BeamSensor bs : ownerList) {
                            if (bs.isBeamSensorOwner()) {
                                BeamSensorOwner beamSensorOwner = bs.getBeamSensorOwner();
                                String ownerUuid = beamSensorOwner.getUuid();
                                String ownerToken = beamSensorOwner.getToken();

                                //we are assuming there is only one owner
                                preferencesManager.setOwnerToken(ownerToken);
                                preferencesManager.setOwnerUuid(ownerUuid);

                                hasLoadedOwners = true;
                                break;
                            }
                        }

                        loadDevices();
                    }
                }
            });
        }

    }

    @Override
    public void onGetOwnerFailed() {

        if (!isFinishing() && isRunning) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(DeviceListActivity.this, R.string.text_owner_list_failed, Toast.LENGTH_SHORT).show();

                }
            });
            loadOwners();
        }
    }


    private void loadBeamSensorData() {
        if (!isFinishing() && isRunning) {
            requestDataCounter = 0;
            beamSensorDataList.clear();

            for (BeamSensor sensor : beamSensorList) {

                BeamController.getInstance().getData(mDataFilter,
                        preferencesManager.getOwnerUuid(),
                        preferencesManager.getOwnerToken(),
                        sensor.getUuid(), this);
            }

        }
    }

    @Override
    public void onBeamSensorDataSuccess(final List<BeamSensorData> data) {

        if (!isFinishing() && isRunning) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    beamSensorDataList = data;
                    checkRequestBeamDataCounter();

                }
            });
        }


    }

    private void checkRequestBeamDataCounter() {
        requestDataCounter++;
        if (requestDataCounter == beamSensorList.size()) {
            if (beamSensorDataList != null && !beamSensorDataList.isEmpty()) {
                populateBeamSensorItem();
                updateListAndMap();
            }
            reloadDevices();
        }
    }

    private void populateBeamSensorItem() {

        for (BeamSensor beamSensor : beamSensorList) {

            for (int i = (beamSensorDataList.size() - 1); i >= 0; i--) {
                BeamSensorData beamSensorData = beamSensorDataList.get(i);
                if (beamSensorData.getData().getUuid().equals(beamSensor.getUuid())) {
                    for (BeamSensorItem beamSensorItem : beamSensor.getSchema()) {
                        if (beamSensorItem.getId().equals(beamSensorData.getData().getSensorId())) {

                            beamSensorItem.setStatus(beamSensorData.getData().getValue());
                            break;
                        }
                    }
                }
            }
        }
    }


    @Override
    public void onBeamSensorDataFailed() {
        if (!isFinishing() && isRunning) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    checkRequestBeamDataCounter();

                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        isRunning = true;
        reloadDevices();
    }

    public void setToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}
