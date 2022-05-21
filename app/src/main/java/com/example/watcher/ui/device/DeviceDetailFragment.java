package com.example.watcher.ui.device;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.watcher.R;
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
        binding.buttonWatch.setOnClickListener(this::watch);
        binding.buttonDelete.setOnClickListener(this::delete);
        binding.buttonAdjust.setOnClickListener(view -> adjust());
        viewModel.setCallback(new DeviceDetailViewModel.Callback() {
            @Override
            public void OnLoad() {
                binding.textView.setText(viewModel.device.customName);
            }

            @Override
            public void OnDelete(View view) {
                NavDirections action = DeviceDetailFragmentDirections.actionDeviceDetailFragmentToNavigationDevice();
                Navigation.findNavController(view).navigate(action);
                Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }

    public void delete(View view) {
        viewModel.delete(view);
    }

    public void watch(View view) {
        NavDirections action = DeviceDetailFragmentDirections.actionDeviceDetailFragmentToDeviceRecordFragment(viewModel.deviceId);
        Navigation.findNavController(view).navigate(action);
    }

    public void adjust() {
        customDialog();
    }

    public void customDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(getContext(), R.layout.dialog_adjust_device, null);
        dialog.setView(dialogView);
        dialog.show();

        final EditText et_customName = dialogView.findViewById(R.id.et_customName);

        final Button btn_adjust = dialogView.findViewById(R.id.btn_adjust);
        final Button btn_cancel = dialogView.findViewById(R.id.btn_cancel);

        btn_adjust.setOnClickListener(view -> {
            String name = et_customName.getText().toString();
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(getContext(), "未作修改", Toast.LENGTH_SHORT).show();
                return;
            }
            viewModel.adjust(name);//进行数据更新
            Toast.makeText(getContext(), "设备信息：" + name, Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        btn_cancel.setOnClickListener(view -> dialog.dismiss());
    }
}