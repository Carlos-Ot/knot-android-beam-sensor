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
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.org.cesar.knot.beamsensor.R;
import br.org.cesar.knot.beamsensor.controller.BeamController;
import br.org.cesar.knot.beamsensor.data.networking.callback.DeviceListRequestCallback;
import br.org.cesar.knot.beamsensor.model.BeamSensor;
import br.org.cesar.knot.beamsensor.model.BeamSensorFilter;
import br.org.cesar.knot.beamsensor.ui.list.fragment.ListFragment;
import br.org.cesar.knot.beamsensor.ui.list.fragment.MapFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceListActivity extends AppCompatActivity implements DeviceListRequestCallback {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fragmentContainer)
    LinearLayout fragmentContainer;

    private FragmentManager fragmentManager;

    private ListFragment listFragment;
    private MapFragment mapFragment;

    private MenuItem menuItem;

    private boolean isShowingMap = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.title_device);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fragmentManager = getSupportFragmentManager();

        mapFragment = MapFragment.newInstance();
        listFragment = ListFragment.newInstance();

        updateFragmentState();


        BeamController.getInstance().getSensors(new BeamSensorFilter(), this);

        // TODO: 11/04/17 remove comments
        //start block of fake response
//        BeamSensor a = new BeamSensor();
//        BeamSensor b = new BeamSensor();
//        BeamSensor c = new BeamSensor();
//
//        a.setId("123123");
//        a.setOnline(true);
//        a.setController(new BeamSensorItem(-8.058344, -34.872363, 1));
//        a.setLeftFence(new BeamSensorItem(-8.058952, -34.872441, 0));
//        a.setRightFence(new BeamSensorItem(-8.058450, -34.871687, 1));
//
//        b.setId("123123");
//        b.setOnline(true);
//        b.setController(new BeamSensorItem(-8.058631, -34.870872, 1));
//        b.setLeftFence(new BeamSensorItem(-8.059156, -34.870898, 1));
//        b.setRightFence(new BeamSensorItem(-8.058455, -34.871489, 0));
//
//        c.setId("123123");
//        c.setOnline(true);
//        c.setController(new BeamSensorItem(-8.059151, -34.871682, 1));
//        c.setLeftFence(new BeamSensorItem(-8.058986, -34.872400, 1));
//        c.setRightFence(new BeamSensorItem(-8.059332, -34.870984, 0));
//
//        ArrayList<BeamSensor> beamSensorArrayList = new ArrayList<>();
//
//        beamSensorArrayList.add(a);
//        beamSensorArrayList.add(b);
//        beamSensorArrayList.add(c);
//        final ArrayList<BeamSensor> teste = beamSensorArrayList;
//
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                onDeviceListsSuccess(teste);
//            }
//        }, 2000);

        //end block of fake response


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

            fragmentManager.beginTransaction()
                    .replace(fragmentContainer.getId(), mapFragment)
                    .commit();
            if (menuItem != null) {
                menuItem.setIcon(R.drawable.ic_list_black_24dp);

            }

        } else {

            fragmentManager.beginTransaction()
                    .replace(fragmentContainer.getId(), listFragment)
                    .commit();

            if (menuItem != null) {
                menuItem.setIcon(R.drawable.ic_map_black_24dp);
            }
        }

    }


    @Override
    public void onDeviceListsSuccess(final List<BeamSensor> deviceList) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (deviceList != null && !deviceList.isEmpty()) {
                    mapFragment.beamSensors = deviceList;
                    mapFragment.updateDeviceList();
                    listFragment.beamSensors = new ArrayList<>();
                    for (BeamSensor bs :
                            deviceList
                            ) {
                        if (bs.getBeamSensorItens().size() > 0)
                            listFragment.beamSensors.add(bs);
                    }

                }
            }
        });
    }

    @Override
    public void onDeviceListFailed() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DeviceListActivity.this, R.string.text_device_list_failed, Toast.LENGTH_SHORT).show();
            }
        });

    }


}
