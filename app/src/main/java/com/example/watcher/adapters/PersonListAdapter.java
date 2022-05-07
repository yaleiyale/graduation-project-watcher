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
import com.example.watcher.data.person.Person;
import com.example.watcher.databinding.ListItemPersonBinding;
import com.example.watcher.ui.device.DeviceListFragmentDirections;


public class PersonListAdapter extends ListAdapter<Person,RecyclerView.ViewHolder> {

    public PersonListAdapter() {
        super(new PersonDiffCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new personViewHolder(ListItemPersonBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Person person = getItem(position);
        ((personViewHolder) holder).bind(person);
    }

    static class personViewHolder extends RecyclerView.ViewHolder {
        private final ListItemPersonBinding binding;

        public personViewHolder(@NonNull ListItemPersonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
           // binding.setClickListener(view -> navigateToDeviceDetail(binding.getPerson(), view));
        }

        void bind(Person item) {
            binding.setPerson(item);
            binding.executePendingBindings();
        }

        void navigateToDeviceDetail(Device device, View view){
            NavDirections action = DeviceListFragmentDirections.actionNavigationDeviceToDeviceDetailFragment(device.did);
            Navigation.findNavController(view).navigate(action);
        }
    }
}

class PersonDiffCallBack extends DiffUtil.ItemCallback<Person> {

    @Override
    public boolean areItemsTheSame(@NonNull Person oldItem, @NonNull Person newItem) {
        return oldItem.pid == newItem.pid;
    }

    @SuppressLint("DiffUtilEquals")
    @Override
    public boolean areContentsTheSame(@NonNull Person oldItem, @NonNull Person newItem) {
        return oldItem == newItem;
    }


}


