package com.example.watcher.ui.person;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.watcher.adapters.PersonListAdapter;
import com.example.watcher.data.person.Person;
import com.example.watcher.databinding.FragmentPersonListBinding;
import com.example.watcher.LoginActivity;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class PersonFragment extends Fragment {

    private PersonViewModel personViewModel;
    private FragmentPersonListBinding binding;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        personViewModel =
                new ViewModelProvider(this).get(PersonViewModel.class);
        binding = FragmentPersonListBinding.inflate(inflater, container, false);
        PersonListAdapter adapter = new PersonListAdapter();
        binding.personList.setAdapter(adapter);
        subscribeUi(adapter);
        binding.button.setOnClickListener(this::toInsert);
        setHasOptionsMenu(false);
        return binding.getRoot();
    }

    private void toInsert(View view) {
//        NavDirections action = DeviceListFragmentDirections.actionNavigationDeviceToAddDeviceFragment();
//        Navigation.findNavController(view).navigate(action);
//        Intent i  = new Intent(getActivity(), LoginActivity.class);
//        startActivity(i);
        disposable.add(personViewModel.insert("李四")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> binding.button.setEnabled(true),
                        throwable -> Log.e("unable", "Unable to add device", throwable)));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void subscribeUi(PersonListAdapter adapter) {
        PersonListObserver observer = new PersonListObserver(adapter);
        personViewModel.people.observe(getViewLifecycleOwner(), observer);
    }
}

class PersonListObserver implements Observer<List<Person>> {
    final PersonListAdapter personListAdapter;

    PersonListObserver(PersonListAdapter personListAdapter) {
        this.personListAdapter = personListAdapter;
    }

    @Override
    public void onChanged(List<Person> people) {
        this.personListAdapter.submitList(people);
    }
}