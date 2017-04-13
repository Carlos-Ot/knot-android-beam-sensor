package br.org.cesar.knot.beamsensor.ui.detail.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.org.cesar.knot.beamsensor.R;
import br.org.cesar.knot.beamsensor.model.BeamSensorData;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DataViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_data_uuid)
    TextView tvDataUuid;

//    @BindView(R.id.tv_name)
//    TextView tvDeviceName;
//
//    @BindView(R.id.tv_coordinate)
//    TextView tvDeviceLatLong;

    @BindView(R.id.tv_data_timestamp)
    TextView tvDataTimestamp;

    public DataViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void bind(BeamSensorData beamSensorData) {

        tvDataUuid.setText(beamSensorData.getId());
        tvDataTimestamp.setText(beamSensorData.getTimestamp());


//        tvDeviceName.setText(beamSensorData.getIpAddress());
//        tvDeviceLatLong.setText(String.valueOf(beamSensorData.getController().getLatitude()) + ", " + beamSensor
//                .getController().getLongitude());
//        tvDeviceTimestamp.setText(beamSensor.getId());


    }

}
