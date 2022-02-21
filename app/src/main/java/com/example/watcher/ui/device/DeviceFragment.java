package com.example.watcher.ui.device;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.watcher.adapters.DeviceAdapter;
import com.example.watcher.data.device.Device;
import com.example.watcher.databinding.FragmentDeviceBinding;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


@AndroidEntryPoint
public
class DeviceFragment extends Fragment {
    private DeviceViewModel deviceViewModel;
    private FragmentDeviceBinding binding;

    Button mUpdateButton;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        deviceViewModel =
                new ViewModelProvider(this).get(DeviceViewModel.class);

        binding = FragmentDeviceBinding.inflate(inflater, container, false);
        DeviceAdapter adapter = new DeviceAdapter();
        binding.deviceList.setAdapter(adapter);
        subscribeUi(adapter);
        mUpdateButton = binding.button;
        binding.button.setOnClickListener(l -> insert());
        setHasOptionsMenu(false);
        return binding.getRoot();

    }

    private void insert() {

        // Subscribe to updating the user name.
        // Re-enable the button once the user name has been updated
        deviceViewModel.deviceRepository.insert()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> mUpdateButton.setEnabled(true),
                        throwable -> Log.e("hello", "Unable to update username", throwable));
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

