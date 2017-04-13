package br.org.cesar.knot.beamsensor.ui.list.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.org.cesar.knot.beamsensor.R;
import br.org.cesar.knot.beamsensor.model.BeamSensor;
import br.org.cesar.knot.beamsensor.ui.detail.SensorDetailActivity;
import br.org.cesar.knot.beamsensor.ui.list.adapter.DeviceAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListFragment extends Fragment implements DeviceAdapter.ItemClickListener{

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private DeviceAdapter adapter = new DeviceAdapter();
    private ArrayList<BeamSensor> beamSensors = new ArrayList<>();


    public static ListFragment newInstance() {
        return new ListFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter.setItemClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);

        initRecyclerView();
        if (beamSensors != null && !beamSensors.isEmpty()) {
            updateDeviceList(beamSensors);
        }

        return view;
    }

    private void initRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
    }

    public void updateDeviceList(ArrayList<BeamSensor> newSensors) {

        beamSensors = newSensors;
        adapter.setData(beamSensors);
    }

    @Override
    public void onClick(BeamSensor beamSensor) {
        startActivity(SensorDetailActivity.newIntent(getContext(), beamSensor.getUuid()));
    }
}
