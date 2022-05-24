package com.example.watcher.ui.passRecord;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.watcher.adapters.RecordListAdapter;
import com.example.watcher.data.passRecord.PassRecord;
import com.example.watcher.databinding.FragmentRecordListBinding;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class RecordListFragment extends Fragment {

    private final CompositeDisposable disposable = new CompositeDisposable();
    RecordListAdapter adapter;
    private RecordListViewModel recordListViewModel;
    private FragmentRecordListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recordListViewModel =
                new ViewModelProvider(this).get(RecordListViewModel.class);
        recordListViewModel.refresh();
        binding = FragmentRecordListBinding.inflate(inflater, container, false);

        adapter = new RecordListAdapter(recordListViewModel.deviceRepository, recordListViewModel.personRepository, recordListViewModel.passRecordRepository);
        binding.recordList.setAdapter(adapter);
        binding.switchBan.setOnCheckedChangeListener((compoundButton, b) -> {
            if (compoundButton.isChecked()) {
                recordListViewModel.showBan();
                binding.switchPass.setChecked(false);
            } else if (!binding.switchPass.isChecked()) {
                recordListViewModel.showAll();
            }
            subscribeUi(adapter);
        });
        binding.switchPass.setOnCheckedChangeListener((compoundButton, b) -> {
            if (compoundButton.isChecked()) {
                recordListViewModel.showPass();
                binding.switchBan.setChecked(false);
            } else if (!binding.switchBan.isChecked()) {
                recordListViewModel.showAll();
            }
            subscribeUi(adapter);
        });
        subscribeUi(adapter);
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
        disposable.add(recordListViewModel.insert()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                        },
                        throwable -> Log.e("unable", "Unable to add device", throwable)));
    }

    private void subscribeUi(RecordListAdapter adapter) {
        RecordListObserver observer = new RecordListObserver(adapter);
        recordListViewModel.records.observe(getViewLifecycleOwner(), observer);
    }
}

class RecordListObserver implements Observer<List<PassRecord>> {
    final RecordListAdapter recordListAdapter;

    RecordListObserver(RecordListAdapter recordListAdapter) {
        this.recordListAdapter = recordListAdapter;
    }

    @Override
    public void onChanged(List<PassRecord> records) {
        this.recordListAdapter.submitList(records);
    }
}