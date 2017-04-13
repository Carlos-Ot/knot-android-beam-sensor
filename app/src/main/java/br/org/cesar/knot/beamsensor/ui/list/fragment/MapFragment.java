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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import br.org.cesar.knot.beamsensor.R;
import br.org.cesar.knot.beamsensor.model.BeamSensor;
import br.org.cesar.knot.beamsensor.model.BeamSensorItem;
import br.org.cesar.knot.beamsensor.ui.list.adapter.DeviceAdapter;
import br.org.cesar.knot.beamsensor.util.Utils;


public class MapFragment extends Fragment implements DeviceAdapter.ItemClickListener {

    private ArrayList<LatLng> activeFence;
    private ArrayList<LatLng> inactiveFence;
    private ArrayList<LatLng> violatedFence;
    private GoogleMap googleMap;
    public List<BeamSensor> beamSensors;
    LatLngBounds.Builder builder = new LatLngBounds.Builder();


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
            }
        });


        return view;
    }

    private LatLng getGoogleMapCoordinate(double latitude, double longitude) {
        return new LatLng(latitude, longitude);
    }

    public void updateDeviceList() {

        //only populate map if googleMap is running
        if (googleMap == null) return;

        for (BeamSensor beamSensor : beamSensors) {
            if (!beamSensor.isBeamSensorOwner() && beamSensor.isOnline()) {
                activeFence.clear();
                inactiveFence.clear();
                for (BeamSensorItem beamSensorItem : beamSensor.getBeamSensorItens())
                    if (beamSensorItem.getStatus() == 1) {
                        activeFence.add(getGoogleMapCoordinate(beamSensorItem.getLatitude(), beamSensorItem.getLongitude()));
                    } else {
                        inactiveFence.add(getGoogleMapCoordinate(beamSensorItem.getLatitude(), beamSensorItem.getLongitude()));
                    }
                //add controller
                activeFence.add(getGoogleMapCoordinate(beamSensor.getController().getLatitude(), beamSensor
                        .getController().getLongitude()));

                builder.include(getGoogleMapCoordinate(beamSensor.getController().getLatitude(), beamSensor
                        .getController().getLongitude()));


                googleMap.addMarker(new MarkerOptions().icon(Utils.vectorToBitmap(R.drawable
                        .ic_sensor_active, getResources()))
                        .position(getGoogleMapCoordinate(beamSensor.getController().getLatitude(), beamSensor
                                .getController().getLongitude())).title("Beam " +
                        "sensor active"));


            } else if (!beamSensor.isBeamSensorOwner() && !beamSensor.isOnline()) {
                for (BeamSensorItem beamSensorItem : beamSensor.getBeamSensorItens()) {
                    violatedFence.add(getGoogleMapCoordinate(beamSensorItem.getLatitude(), beamSensorItem.getLongitude()));
                }
                violatedFence.add(getGoogleMapCoordinate(beamSensor.getController().getLatitude(), beamSensor
                        .getController().getLongitude()));

                builder.include(getGoogleMapCoordinate(beamSensor.getController().getLatitude(), beamSensor
                        .getController().getLongitude()));


                googleMap.addMarker(new MarkerOptions().icon(Utils.vectorToBitmap(R.drawable.ic_sensor_inactive, getResources()))
                        .position(getGoogleMapCoordinate(beamSensor.getController()
                        .getLatitude(), beamSensor.getController().getLongitude())).title("Beam " +
                        "sensor inactive"));

            }


            createPolyline(activeFence, Color.BLUE);
            createPolyline(violatedFence, Color.RED);
            createPolyline(inactiveFence, Color.GRAY);
        }

        int width = getResources().getDisplayMetrics().widthPixels;
        int padding = (int) (width * 0.20);
        CameraUpdate camera = CameraUpdateFactory.newLatLngBounds(builder.build(), padding);
        googleMap.animateCamera(camera);

    }


    private void createPolyline(ArrayList<LatLng> latlng, @ColorInt int color) {
        googleMap.addPolyline(new PolylineOptions()
                .addAll(latlng)
                .width(10)
                .color(color));
    }

    @Override
    public void onClick(BeamSensor beamSensor) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        googleMap = null;
    }
}
