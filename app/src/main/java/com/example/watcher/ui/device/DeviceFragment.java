package com.example.watcher.ui.device;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.watcher.adapters.DeviceAdapter;
import com.example.watcher.data.device.Device;
import com.example.watcher.databinding.FragmentDeviceBinding;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public
class DeviceFragment extends Fragment {
    private DeviceViewModel deviceViewModel;
    private FragmentDeviceBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        deviceViewModel =
                new ViewModelProvider(this).get(DeviceViewModel.class);

        binding = FragmentDeviceBinding.inflate(inflater, container, false);
        DeviceAdapter adapter = new DeviceAdapter();
        binding.deviceList.setAdapter(adapter);
        subscribeUi(adapter);
        binding.button.setOnClickListener(l -> deviceViewModel.deviceRepository.insert());
        setHasOptionsMenu(false);
        return binding.getRoot();

    }

    private void subscribeUi(DeviceAdapter adapter) {
        DeviceObserver observer = new DeviceObserver(adapter);
        deviceViewModel.devices.observe(getViewLifecycleOwner(), observer);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}

class DeviceObserver implements Observer<List<Device>> {
    final DeviceAdapter deviceAdapter;

    DeviceObserver(DeviceAdapter deviceAdapter) {
        this.deviceAdapter = deviceAdapter;
    }

    @Override
    public void onChanged(List<Device> devices) {
        this.deviceAdapter.submitList(devices);
    }
}

