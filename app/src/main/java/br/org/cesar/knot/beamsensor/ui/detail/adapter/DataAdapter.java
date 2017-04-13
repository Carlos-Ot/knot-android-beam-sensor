package br.org.cesar.knot.beamsensor.ui.detail.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.org.cesar.knot.beamsensor.R;
import br.org.cesar.knot.beamsensor.model.BeamSensorData;

public class DataAdapter extends RecyclerView.Adapter<DataViewHolder> {

    private ArrayList<BeamSensorData> beamSensorData = new ArrayList<>();

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.beam_sensor_data_list_item, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        holder.bind(beamSensorData.get(position));
    }

    @Override
    public int getItemCount() {
        return beamSensorData.size();
    }

    public void updateData(List<BeamSensorData> newData) {
        this.beamSensorData.addAll(newData);
        //TODO order
        notifyDataSetChanged();
    }

}
