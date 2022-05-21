package com.example.watcher.ui.device;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.watcher.adapters.DeviceRecordAdapter;
import com.example.watcher.data.passRecord.PassRecord;
import com.example.watcher.databinding.FragmentDeviceRecordBinding;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
@AndroidEntryPoint
public class DeviceRecordFragment extends Fragment {
    private DeviceRecordViewModel viewModel;
    private FragmentDeviceRecordBinding binding;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public DeviceRecordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModel = new ViewModelProvider(this).get(DeviceRecordViewModel.class);
        binding = FragmentDeviceRecordBinding.inflate(inflater, container, false);
        DeviceRecordAdapter deviceRecordAdapter = new DeviceRecordAdapter(viewModel.deviceRepository, viewModel.personRepository, viewModel.passRecordRepository);
        binding.recordList.setAdapter(deviceRecordAdapter);
        subscribeUi(deviceRecordAdapter);
        setHasOptionsMenu(false);
        return binding.getRoot();
    }

    private void subscribeUi(DeviceRecordAdapter adapter) {
        DeviceRecordObserver observer = new DeviceRecordObserver(adapter);
        viewModel.records.observe(getViewLifecycleOwner(), observer);
    }
}

class DeviceRecordObserver implements Observer<List<PassRecord>> {
    final DeviceRecordAdapter adapter;

    DeviceRecordObserver(DeviceRecordAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onChanged(List<PassRecord> records) {
        this.adapter.submitList(records);
    }
}
