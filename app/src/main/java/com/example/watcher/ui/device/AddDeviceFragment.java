package com.example.watcher.ui.device;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.watcher.databinding.FragmentAddDeviceBinding;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class AddDeviceFragment extends Fragment {
    private AddDeviceViewModel viewModel;
    private FragmentAddDeviceBinding binding;
    private final CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AddDeviceViewModel.class);
        binding = FragmentAddDeviceBinding.inflate(inflater, container, false);
        binding.buttonAdding.setOnClickListener(view -> insert());
        return binding.getRoot();
    }

    private void insert() {
        String name = binding.deviceName.getText().toString();
        if(name.equals(""))
        {
            Toast.makeText(getContext(), "请确定摄像头位置", Toast.LENGTH_SHORT).show();
        }else
        {
            binding.buttonAdding.setEnabled(false);
            disposable.add(viewModel.deviceRepository.insert(name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> binding.buttonAdding.setEnabled(true),
                            throwable -> Log.e("unable", "Unable to add device", throwable)));
            Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
            binding.deviceName.setText("");
        }

    }
}