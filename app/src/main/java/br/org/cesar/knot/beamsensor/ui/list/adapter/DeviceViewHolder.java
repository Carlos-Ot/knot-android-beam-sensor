package br.org.cesar.knot.beamsensor.ui.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.org.cesar.knot.beamsensor.R;
import br.org.cesar.knot.beamsensor.model.BeamSensor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_uuid)
    TextView tvDeviceUuid;
    @BindView(R.id.tv_name)
    TextView tvDeviceName;

    @BindView(R.id.tv_coordinate)
    TextView tvDeviceLatLong;

    @BindView(R.id.tv_timestamp)
    TextView tvDeviceTimestamp;

    public DeviceViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void bind(BeamSensor beamSensor) {
        tvDeviceUuid.setText(beamSensor.getId());
        tvDeviceName.setText(beamSensor.getIpAddress());
        tvDeviceLatLong.setText(String.valueOf(beamSensor.getCenterReceiver().getLatitude()) + ", " + beamSensor
                .getCenterReceiver().getLongitude());
//        tvDeviceTimestamp.setText(beamSensor.getId());


    }

}
