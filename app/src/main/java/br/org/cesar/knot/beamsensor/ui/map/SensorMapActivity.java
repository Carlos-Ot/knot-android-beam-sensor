package br.org.cesar.knot.beamsensor.ui.map;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import br.org.cesar.knot.beamsensor.R;
import br.org.cesar.knot.beamsensor.model.BeamSensor;
import br.org.cesar.knot.beamsensor.model.BeamSensorItem;

public class SensorMapActivity extends AppCompatActivity implements OnMapReadyCallback {

//    BeamController beamController =  BeamController.getInstance();


    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


//        BeamSensor a = new BeamSensor();
//        BeamSensor b = new BeamSensor();
//        BeamSensor c = new BeamSensor();
//
//        a.setId("123123");
//        a.setOnline(true);
//        a.setCenterReceiver(new BeamSensorItem(-8.058344, -34.872363, 1));
//        a.setLeftEmitter(new BeamSensorItem(-8.058952, -34.872441, 0));
//        a.setRightEmitter(new BeamSensorItem(-8.058450, -34.871687, 1));
//
//        b.setId("123123");
//        b.setOnline(true);
//        b.setCenterReceiver(new BeamSensorItem(-8.058631, -34.870872, 1));
//        b.setLeftEmitter(new BeamSensorItem(-8.059156, -34.870898, 1));
//        b.setRightEmitter(new BeamSensorItem(-8.058455, -34.871489, 0));
//
//        c.setId("123123");
//        c.setOnline(true);
//        c.setCenterReceiver(new BeamSensorItem(-8.059151, -34.871682, 1));
//        c.setLeftEmitter(new BeamSensorItem(-8.058986, -34.872400, 1));
//        c.setRightEmitter(new BeamSensorItem(-8.059332, -34.870984, 0));
//
//
//        ArrayList<BeamSensor> beamSensorArrayList = new ArrayList<>();
//
//        beamSensorArrayList.add(a);
//        beamSensorArrayList.add(b);
//        beamSensorArrayList.add(c);


////        Polyline line =
//
//        LatLng br = new LatLng(-8.059090, -34.871762);
//        LatLng cb = new LatLng(-8.058325, -34.872001);
//        LatLng bl = new LatLng(-8.058949, -34.872430);
//
//        LatLng tr = new LatLng(-8.058471, -34.871678);
//        LatLng tl = new LatLng(-8.058340, -34.872355);
//
//        PolygonOptions rectOptions = new PolygonOptions()
//                .add(br, cb, bl);
//
//        rectOptions.fillColor(ContextCompat.getColor(this, R.color.colorAccent1));
//
//        Polygon polygon = mMap.addPolygon(rectOptions);
//
//        googleMap.addMarker(new MarkerOptions().position(br).title("Bottom right emitter"));
//        googleMap.addMarker(new MarkerOptions().position(bl).title("Bottom left emitter"));
//        googleMap.addMarker(new MarkerOptions().position(cb).title("Bottom center receiver"));
//        googleMap.addMarker(new MarkerOptions().position(tr).title("Top right"));
//        googleMap.addMarker(new MarkerOptions().position(tl).title("Top left"));

//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(a.getCenterReceiver().getLatLng(), 15));

    }


    private void createPolyline(ArrayList<LatLng> latlng, @ColorInt int color) {
        mMap.addPolyline(new PolylineOptions()
                .addAll(latlng)
                .width(10)
                .color(color));
    }
}