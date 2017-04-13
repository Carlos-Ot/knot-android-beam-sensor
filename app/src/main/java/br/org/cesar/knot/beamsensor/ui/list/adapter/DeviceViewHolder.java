package br.org.cesar.knot.beamsensor.ui.list.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.org.cesar.knot.beamsensor.R;
import br.org.cesar.knot.beamsensor.model.BeamSensor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.sensor_id)
    TextView tvId;

    @BindView(R.id.sensor_image)
    ImageView ivSensorImage;

    @BindView(R.id.sensor_coordantes)
    TextView tvDeviceLatLong;

    @BindView(R.id.sensor_status)
    TextView tvStatus;

    @BindView(R.id.iv_status)
    AppCompatImageView ivStatus;

    public DeviceViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void bind(BeamSensor beamSensor) {
        tvId.setText(beamSensor.getId());
        if(beamSensor.getController() != null) {
            tvDeviceLatLong.setText(String.valueOf(beamSensor.getController().getLatitude()) + ", " + beamSensor
                    .getController().getLongitude());
        }
        int textColor = android.R.color.holo_red_light;
        int iconColor = R.color.expanded_info_secondary_text_color;

        if (beamSensor.isOnline()) {
            tvStatus.setText(tvStatus.getContext().getResources().getString(R.string.device_view_holder_active));
            textColor = R.color.device_view_holder_active;
            ivSensorImage.setImageDrawable(ContextCompat.getDrawable(ivSensorImage.getContext(), R.drawable
                    .ic_sensor_active));
        } else {
            tvStatus.setText(tvStatus.getContext().getResources().getString(R.string.device_view_holder_inactive));
            ivSensorImage.setImageDrawable(ContextCompat.getDrawable(ivSensorImage.getContext(), R.drawable
                    .ic_sensor_inactive));
            iconColor = android.R.color.black;
        }


        tvStatus.setTextColor(ContextCompat.getColor(tvStatus.getContext(), textColor));
        ivStatus.setColorFilter(iconColor);



    }

}
