package br.org.cesar.knot.beamsensor.ui.list.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import br.org.cesar.knot.beamsensor.R;
import br.org.cesar.knot.beamsensor.model.BeamSensor;
import br.org.cesar.knot.beamsensor.model.BeamSensorItem;
import br.org.cesar.knot.beamsensor.ui.detail.SensorDetailActivity;
import br.org.cesar.knot.beamsensor.util.Utils;


public class MapFragment extends Fragment implements GoogleMap.OnMarkerClickListener {

    public static final int FENCY_ACTIVE = 1;
    private ArrayList<LatLng> activeFence;
    private ArrayList<LatLng> inactiveFence;
    private ArrayList<LatLng> violatedFence;
    private GoogleMap googleMap;
    public List<BeamSensor> beamSensors;
    private LatLngBounds.Builder builder;
    public boolean shouldSetCamera = true;


    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activeFence = new ArrayList<>();
        inactiveFence = new ArrayList<>();
        violatedFence = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment smf = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        smf.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                googleMap = map;
                // if already has list of beamsensor populate map
                if (beamSensors != null && !beamSensors.isEmpty()) {
                    updateDeviceList();
                }

                googleMap.setOnMarkerClickListener(MapFragment.this);
            }
        });


        return view;
    }

    private LatLng getCoordinate(BeamSensorItem beamSensorItem) {
        return new LatLng(beamSensorItem.getLatitude(), beamSensorItem.getLongitude());
    }

    public void updateDeviceList() {

        //only populate map if googleMap is running
        if (googleMap == null || beamSensors.size() == 0) return;

        // clear old markers
        googleMap.clear();
        builder = new LatLngBounds.Builder();

        for (int i = 0; i < beamSensors.size(); i++) {
            if (!beamSensors.get(i).isBeamSensorOwner() && beamSensors.get(i).isOnline()) {
                activeFence.clear();
                inactiveFence.clear();
                for (BeamSensorItem beamSensorItem : beamSensors.get(i).getBeamSensorItens())
                    if (beamSensorItem.getStatus() == FENCY_ACTIVE) {
                        activeFence.add(getCoordinate(beamSensorItem));
                    } else {
                        inactiveFence.add(getCoordinate(beamSensorItem));
                    }

                //add controller
                activeFence.add(getCoordinate(beamSensors.get(i).getController()));
                builder.include(getCoordinate(beamSensors.get(i).getController()));


                //add controller marker
                googleMap.addMarker(new MarkerOptions().icon(Utils.vectorToBitmap(R.drawable
                        .ic_sensor_active, getResources()))
                        .position(getCoordinate(beamSensors.get(i).getController()))).setTag(i);


            } else if (!beamSensors.get(i).isBeamSensorOwner() && !beamSensors.get(i).isOnline()) {
                for (BeamSensorItem beamSensorItem : beamSensors.get(i).getBeamSensorItens()) {
                    violatedFence.add(getCoordinate(beamSensorItem));
                }

                //add controller
                violatedFence.add(getCoordinate(beamSensors.get(i).getController()));
                builder.include(getCoordinate(beamSensors.get(i).getController()));


                //add controller marker
                googleMap.addMarker(new MarkerOptions().icon(Utils.vectorToBitmap(R.drawable.ic_sensor_inactive, getResources()))
                        .position(getCoordinate(beamSensors.get(i).getController()))).setTag(i);

            }


            createPolyline(activeFence, Color.BLUE);
            createPolyline(violatedFence, Color.RED);
            createPolyline(inactiveFence, Color.GRAY);
        }

        setCameraPosition(builder);

    }

    private void setCameraPosition(LatLngBounds.Builder builder) {
        if(!shouldSetCamera) return;
        int width = getResources().getDisplayMetrics().widthPixels;
        int padding = (int) (width * 0.20);
        CameraUpdate camera = CameraUpdateFactory.newLatLngBounds(builder.build(), padding);
        googleMap.animateCamera(camera);
        shouldSetCamera = false;
    }


    private void createPolyline(ArrayList<LatLng> latlng, @ColorInt int color) {
        googleMap.addPolyline(new PolylineOptions()
                .addAll(latlng)
                .width(10)
                .color(color));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        googleMap = null;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Integer position = (Integer) marker.getTag();
        if (position != null) {
            startActivity(SensorDetailActivity.newIntent(getContext(), beamSensors.get(position).getUuid()));
        }
        return true;
    }
}
