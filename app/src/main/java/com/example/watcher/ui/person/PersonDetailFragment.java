package com.example.watcher.ui.person;

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
import com.example.watcher.data.person.Person;
import com.example.watcher.databinding.FragmentPersonDetailBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PersonDetailFragment extends Fragment {
    private PersonDetailViewModel viewModel;
    private FragmentPersonDetailBinding binding;

    public PersonDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(PersonDetailViewModel.class);
        binding = FragmentPersonDetailBinding.inflate(inflater, container, false);
        binding.buttonAdjust.setOnClickListener(view -> adjust());
        binding.buttonDelete.setOnClickListener(this::delete);
        binding.buttonWatch.setOnClickListener(this::watch);
        //binding.buttonPower.setOnClickListener(this::change);
        viewModel.setCallback(new PersonDetailViewModel.Callback() {
            @Override
            public void OnLoad(Person person) {
                viewModel.person = person;
                binding.setPerson(viewModel.person);
            }

            @Override
            public void OnDelete(View view) {
//                NavDirections action = PersonDetailFragmentDirections.actionPersonDetailFragmentToNavigationPerson();
//                Navigation.findNavController(view).navigate(action);
                Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();

            }
        });
        return binding.getRoot();
    }

    private void adjust() {
        customDialog();
    }

    private void customDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final AlertDialog dialog = builder.create();
        View dialogView = View.inflate(getContext(), R.layout.dialog_adjust_person, null);
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
            Toast.makeText(getContext(), "人员名称：" + name, Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        btn_cancel.setOnClickListener(view -> dialog.dismiss());
    }

    private void change(View view) {
    }

    private void watch(View view) {
        NavDirections action = PersonDetailFragmentDirections.actionPersonDetailFragmentToPersonRecordFragment(viewModel.personId);
        Navigation.findNavController(view).navigate(action);
    }

    private void delete(View view) {
        viewModel.delete(view);
    }


}
