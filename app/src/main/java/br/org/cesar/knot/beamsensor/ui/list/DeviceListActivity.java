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

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.org.cesar.knot.beamsensor.R;
import br.org.cesar.knot.beamsensor.controller.BeamController;
import br.org.cesar.knot.beamsensor.data.local.PreferencesManager;
import br.org.cesar.knot.beamsensor.data.networking.callback.DeviceListRequestCallback;
import br.org.cesar.knot.beamsensor.model.BeamSensor;
import br.org.cesar.knot.beamsensor.model.BeamSensorFilter;
import br.org.cesar.knot.beamsensor.model.BeamSensorOwner;
import br.org.cesar.knot.beamsensor.ui.list.fragment.ListFragment;
import br.org.cesar.knot.beamsensor.ui.list.fragment.MapFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceListActivity extends AppCompatActivity implements DeviceListRequestCallback {

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

        updateFragmentState();

    }

    @Override
    protected void onResume() {
        super.onResume();

        isRunning = true;
        loadDevices();
    }

    @Override
    protected void onPause() {
        super.onPause();

        isRunning = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
            finish();
        } else {
            this.isShowingMap = !this.isShowingMap;
            updateFragmentState();
        }

        return true;
    }

    private void updateFragmentState() {

        if (this.isShowingMap) {

            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, mapFragment).commit();
            if (menuItem != null) {
                menuItem.setIcon(R.drawable.ic_list_black_24dp);
            }

        } else {

            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, listFragment).commit();

            if (menuItem != null) {
                menuItem.setIcon(R.drawable.ic_map_black_24dp);
            }
        }
    }


    @Override
    public void onDeviceListsSuccess(final List<BeamSensor> deviceList) {

        if (!isFinishing() && isRunning) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (deviceList != null && !deviceList.isEmpty()) {
                        mapFragment.beamSensors = deviceList;
                        mapFragment.updateDeviceList();

                        ArrayList<BeamSensor> newBS = new ArrayList<>();
                        for (BeamSensor bs : deviceList) {
                            if (bs.isBeamSensorOwner()) {
                                BeamSensorOwner beamSensorOwner = bs.getBeamSensorOwner();
                                String ownerUuid = beamSensorOwner.getUuid();
                                String ownerToken = beamSensorOwner.getToken();

                                //we are assuming there is only one owner
                                preferencesManager.setOwnerToken(ownerToken);
                                preferencesManager.setOwnerUuid(ownerUuid);
                            } else {
                                newBS.add(bs);
                            }
                        }

                        listFragment.updateDeviceList(newBS);

                    }

                    reloadDevices();
                }
            });
        }


    }

    @Override
    public void onDeviceListFailed() {

        if (!isFinishing() && isRunning) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(DeviceListActivity.this, R.string.text_device_list_failed, Toast.LENGTH_SHORT).show();

                    reloadDevices();
                }
            });
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
            }, 5000);
        }
    }


    private void loadDevices() {
        if (!isFinishing() && isRunning) {
            BeamController.getInstance().getBeamDevices(new BeamSensorFilter(), this);
        }
    }

}
