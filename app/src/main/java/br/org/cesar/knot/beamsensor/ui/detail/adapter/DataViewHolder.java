package br.org.cesar.knot.beamsensor.ui.detail.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.org.cesar.knot.beamsensor.R;
import br.org.cesar.knot.beamsensor.model.BeamSensorData;
import br.org.cesar.knot.beamsensor.util.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DataViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_data_uuid)
    TextView tvDataUuid;
    @BindView(R.id.tv_data_timestamp)
    TextView tvDataTimestamp;

    @BindView(R.id.tv_data_status)
    TextView tvDataStatus;

    public DataViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void bind(BeamSensorData beamSensorData) {

        tvDataUuid.setText(beamSensorData.getUuid());

        tvDataTimestamp.setText(beamSensorData.getTimestamp());

        tvDataStatus.setText(getTextFromStatus(Integer.valueOf(beamSensorData.getValue())));
    }

    private String getTextFromStatus(int status) {

        String result = "Unknown";

        switch (status) {
            case Constants.STATUS_VIOLATION:
                result = "Violation";
                break;
            case Constants.STATUS_NORMAL:
                result = "Normal";
                break;
        }

        return result;
    }

}
