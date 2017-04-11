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

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

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

    private FragmentManager mFragmentManager;

    private ListFragment mListFragment;
    private MapFragment mMapFragment;

    private MenuItem mMenuItem;

    private boolean mIsShowingMap = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.title_device);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mFragmentManager = getSupportFragmentManager();

        mMapFragment = MapFragment.newInstance();
        mListFragment = ListFragment.newInstance();

        updateFragmentState();

        BeamController.getInstance().getSensors(new BeamSensorFilter(), this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_devices, menu);
        mMenuItem = menu.findItem(R.id.list_map_menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        } else {
            this.mIsShowingMap = !this.mIsShowingMap;
            updateFragmentState();
        }

        return true;
    }

    private void updateFragmentState() {

        if (this.mIsShowingMap) {

            mFragmentManager.beginTransaction()
                    .replace(fragmentContainer.getId(), mMapFragment)
                    .commit();
            if (mMenuItem != null) {
                mMenuItem.setIcon(R.drawable.ic_list_black_24dp);

            }

        } else {

            mFragmentManager.beginTransaction()
                    .replace(fragmentContainer.getId(), mListFragment)
                    .commit();

            if (mMenuItem != null) {
                mMenuItem.setIcon(R.drawable.ic_map_black_24dp);
            }
        }

    }


    @Override
    public void onDeviceListsSuccess(List<BeamSensor> deviceList) {

        if (deviceList != null && !deviceList.isEmpty()) {
            //TODO
        }


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
