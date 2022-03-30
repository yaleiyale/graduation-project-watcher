package com.example.watcher.ui.device;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.watcher.databinding.FragmentDeviceDetailBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DeviceDetailFragment extends Fragment {

    private DeviceDetailViewModel viewModel;
    private FragmentDeviceDetailBinding binding;

    public DeviceDetailFragment() {
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
        viewModel = new ViewModelProvider(this).get(DeviceDetailViewModel.class);

        binding = FragmentDeviceDetailBinding.inflate(inflater, container, false);
        viewModel.setCallback(() -> binding.textView.setText(viewModel.device.customName));
        return binding.getRoot();
    }


}