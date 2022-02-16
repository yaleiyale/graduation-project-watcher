package com.example.watcher.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watcher.data.device.Device;
import com.example.watcher.databinding.ListItemDeviceBinding;


public class DeviceAdapter extends ListAdapter<Device,RecyclerView.ViewHolder> {

    public DeviceAdapter() {
        super((DiffUtil.ItemCallback) (new DeviceDiffCallBack()));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DeviceViewHolder(ListItemDeviceBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Device device = (Device)getItem(position);
        ((DeviceViewHolder) holder).bind(device);
    }

    class DeviceViewHolder extends RecyclerView.ViewHolder {
        private ListItemDeviceBinding binding;

        public DeviceViewHolder(@NonNull ListItemDeviceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Device item) {
            binding.setDevice(item);
            binding.executePendingBindings();
        }
    }
}

class DeviceDiffCallBack extends DiffUtil.ItemCallback<Device> {

    @Override
    public boolean areItemsTheSame(@NonNull Device oldItem, @NonNull Device newItem) {
        return oldItem.did == newItem.did;
    }


    @SuppressLint("DiffUtilEquals")
    @Override
    public boolean areContentsTheSame(@NonNull Device oldItem, @NonNull Device newItem) {
        return oldItem == newItem;
    }
}


