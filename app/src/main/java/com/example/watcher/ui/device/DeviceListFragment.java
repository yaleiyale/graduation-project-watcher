package com.example.watcher.ui.device;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.watcher.adapters.DeviceListAdapter;
import com.example.watcher.data.device.Device;
import com.example.watcher.databinding.FragmentDeviceListBinding;


import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;


@AndroidEntryPoint
public class DeviceListFragment extends Fragment {
    private DeviceListViewModel deviceListViewModel;
    private FragmentDeviceListBinding binding;
    private final CompositeDisposable disposable = new CompositeDisposable();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        deviceListViewModel =
                new ViewModelProvider(this).get(DeviceListViewModel.class);
        binding = FragmentDeviceListBinding.inflate(inflater, container, false);
        DeviceListAdapter adapter = new DeviceListAdapter();
        binding.deviceList.setAdapter(adapter);
        subscribeUi(adapter);
        binding.button.setOnClickListener(this::toInsert);
        setHasOptionsMenu(false);
        return binding.getRoot();

    }

    @Override
    public void onStop() {
        super.onStop();
        disposable.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void toInsert(View view) {
        NavDirections action = DeviceListFragmentDirections.actionNavigationDeviceToAddDeviceFragment();
        Navigation.findNavController(view).navigate(action);
    }

    private void subscribeUi(DeviceListAdapter adapter) {
        DeviceListObserver observer = new DeviceListObserver(adapter);
        deviceListViewModel.devices.observe(getViewLifecycleOwner(), observer);
    }

}

class DeviceListObserver implements Observer<List<Device>> {
    final DeviceListAdapter deviceListAdapter;

    DeviceListObserver(DeviceListAdapter deviceListAdapter) {
        this.deviceListAdapter = deviceListAdapter;
    }

    @Override
    public void onChanged(List<Device> devices) {
        this.deviceListAdapter.submitList(devices);
    }
}

