package br.org.cesar.knot.beamsensor.ui.list.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import br.org.cesar.knot.beamsensor.R;
import br.org.cesar.knot.beamsensor.model.BeamSensor;
import br.org.cesar.knot.beamsensor.model.BeamSensorItem;


public class MapFragment extends Fragment {

    private ArrayList<LatLng> onlineBeamSensor;
    private ArrayList<LatLng> offlineBeamSensor;
    private GoogleMap googleMap;
    public List<BeamSensor> beamSensors;


    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onlineBeamSensor = new ArrayList<>();
        offlineBeamSensor = new ArrayList<>();
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


    public void updateDeviceList() {

        //only populate map if googleMap is running
        if(googleMap == null) return;

        for (BeamSensor beamSensor : beamSensors) {
            onlineBeamSensor.clear();
            offlineBeamSensor.clear();

            if (beamSensor.isOnline()) {
                for (BeamSensorItem beamSensorItem : beamSensor.getBeamSensorItens())
                    if (beamSensorItem.getStatus() == 1) {
                        onlineBeamSensor.add(beamSensor.getCenterReceiver().getLatLng());
                        onlineBeamSensor.add(beamSensorItem.getLatLng());
                    } else {
                        offlineBeamSensor.add(beamSensor.getCenterReceiver().getLatLng());
                        offlineBeamSensor.add(beamSensorItem.getLatLng());
                    }


            } else {

                // TODO: 10/04/17 beamSensor not online

            }
            googleMap.addMarker(new MarkerOptions().position(beamSensor.getCenterReceiver().getLatLng()).title("Beam " +
                    "sensor"));

            createPolyline(onlineBeamSensor, Color.BLUE);
            createPolyline(offlineBeamSensor, Color.RED);
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(beamSensors.get(0).getCenterReceiver().getLatLng(), 15));

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
}
