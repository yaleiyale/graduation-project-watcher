package com.example.watcher.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watcher.data.device.Device;
import com.example.watcher.databinding.ListItemDeviceBinding;
import com.example.watcher.ui.device.DeviceListFragmentDirections;


public class DeviceListAdapter extends ListAdapter<Device,RecyclerView.ViewHolder> {

    public DeviceListAdapter() {
        super(new DeviceDiffCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DeviceViewHolder(ListItemDeviceBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Device device = getItem(position);
        ((DeviceViewHolder) holder).bind(device);
    }

    static class DeviceViewHolder extends RecyclerView.ViewHolder {
        private final ListItemDeviceBinding binding;

        public DeviceViewHolder(@NonNull ListItemDeviceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setClickListener(view -> navigateToDeviceDetail(binding.getDevice(), view));
        }

        void bind(Device item) {
            binding.setDevice(item);
            binding.executePendingBindings();
        }

        void navigateToDeviceDetail(Device device, View view){
            NavDirections action = DeviceListFragmentDirections.actionNavigationDeviceToDeviceDetailFragment(device.deviceId);
            Navigation.findNavController(view).navigate(action);
        }
    }
}

class DeviceDiffCallBack extends DiffUtil.ItemCallback<Device> {

    @Override
    public boolean areItemsTheSame(@NonNull Device oldItem, @NonNull Device newItem) {
        return oldItem.deviceId == newItem.deviceId;
    }


    @SuppressLint("DiffUtilEquals")
    @Override
    public boolean areContentsTheSame(@NonNull Device oldItem, @NonNull Device newItem) {
        return oldItem == newItem;
    }
}


