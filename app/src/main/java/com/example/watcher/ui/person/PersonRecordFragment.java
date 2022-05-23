package com.example.watcher.ui.person;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.watcher.adapters.PersonRecordAdapter;
import com.example.watcher.data.passRecord.PassRecord;
import com.example.watcher.databinding.FragmentPersonRecordBinding;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@AndroidEntryPoint
public class PersonRecordFragment extends Fragment {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private PersonRecordViewModel viewModel;
    private FragmentPersonRecordBinding binding;

    public PersonRecordFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModel = new ViewModelProvider(this).get(PersonRecordViewModel.class);
        binding = FragmentPersonRecordBinding.inflate(inflater, container, false);
        PersonRecordAdapter personRecordAdapter = new PersonRecordAdapter(viewModel.deviceRepository, viewModel.personRepository, viewModel.passRecordRepository);
        binding.recordList.setAdapter(personRecordAdapter);
        subscribeUi(personRecordAdapter);
        return binding.getRoot();


    }

    private void subscribeUi(PersonRecordAdapter adapter) {
        PersonRecordObserver observer = new PersonRecordObserver(adapter);
        viewModel.records.observe(getViewLifecycleOwner(), observer);
    }

}

class PersonRecordObserver implements Observer<List<PassRecord>> {
    final PersonRecordAdapter adapter;

    PersonRecordObserver(PersonRecordAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onChanged(List<PassRecord> records) {
        this.adapter.submitList(records);
    }
}