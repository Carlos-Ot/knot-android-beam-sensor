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
import com.google.android.gms.maps.model.Polyline;
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
    public static final int FENCY_VIOLATED = 0;
    private GoogleMap googleMap;
    public List<BeamSensor> beamSensors;
    private LatLngBounds.Builder builder;
    public boolean shouldSetCamera = true;
    private ArrayList<Polyline> polylineList = new ArrayList<>();


    public static MapFragment newInstance() {
        return new MapFragment();
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
        for (Polyline p : polylineList) {
            p.remove();
        }
        polylineList.clear();
        builder = new LatLngBounds.Builder();

        for (int i = 0; i < beamSensors.size(); i++) {
            if (!beamSensors.get(i).isBeamSensorOwner()) {
                createLine(beamSensors.get(i), i);
            }

        }
        setCameraPosition();

    }

    private void createLine(BeamSensor father, int index) {
        boolean isOnline = father.isOnline();

        if (isOnline) {
            googleMap.addMarker(new MarkerOptions().icon(Utils.vectorToBitmap(R.drawable.ic_sensor_active, getResources()))
                    .position(getCoordinate(father.getController()))).setTag(index);
        } else {
            googleMap.addMarker(new MarkerOptions().icon(Utils.vectorToBitmap(R.drawable.ic_sensor_inactive, getResources()))
                    .position(getCoordinate(father.getController()))).setTag(index);
        }
        //add controller marker
        builder.include(getCoordinate(father.getController()));

        ArrayList<LatLng> latLngList = new ArrayList<>();

        for (BeamSensorItem beamSensorItem : father.getBeamSensorItens()) {
            latLngList.clear();
            latLngList.addAll(createVertex(getCoordinate(father.getController()), getCoordinate(beamSensorItem)));

            if (isOnline) {
                if (beamSensorItem.getStatus() == FENCY_VIOLATED) {
                    createPolyline(latLngList, Color.RED);

                } else if (beamSensorItem.getStatus() == FENCY_ACTIVE) {
                    createPolyline(latLngList, Color.BLUE);

                }
            } else {
                createPolyline(latLngList, Color.GRAY);
            }

        }

    }

    private ArrayList<LatLng> createVertex(LatLng controller, LatLng edge) {
        ArrayList<LatLng> vertex = new ArrayList<>();
        vertex.add(controller);
        vertex.add(edge);
        return vertex;
    }


    private void setCameraPosition() {
        if (!shouldSetCamera) return;
        int width = getResources().getDisplayMetrics().widthPixels;
        int padding = (int) (width * 0.20);
        CameraUpdate camera = CameraUpdateFactory.newLatLngBounds(builder.build(), padding);
        googleMap.animateCamera(camera);
        shouldSetCamera = false;
    }


    private void createPolyline(ArrayList<LatLng> latLng, @ColorInt int color) {
        polylineList.add(googleMap.addPolyline(new PolylineOptions()
                .addAll(latLng)
                .width(10)
                .color(color)));
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
